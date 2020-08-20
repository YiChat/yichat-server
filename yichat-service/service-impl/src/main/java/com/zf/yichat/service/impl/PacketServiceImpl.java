package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.*;
import com.zf.yichat.mapper.PacketMapper;
import com.zf.yichat.mapper.PacketReceiveMapper;
import com.zf.yichat.model.Packet;
import com.zf.yichat.model.PacketReceive;
import com.zf.yichat.model.User;
import com.zf.yichat.model.UserBalance;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.PacketCreateService;
import com.zf.yichat.service.PacketService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.service.config.RedisService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.NumberUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.PacketStatus;
import com.zf.yichat.vo.PacketType;
import org.hibernate.validator.internal.util.Contracts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:17 2019/6/11 2019
 */
@Service
public class PacketServiceImpl implements PacketService {

    @Autowired
    private PacketMapper packetMapper;

    @Autowired
    private PacketReceiveMapper packetReceiveMapper;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private PacketCreateService packetCreateService;

    @Autowired
    private ConfigSet configSet;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Packet create(Integer srcType, Long userId, String password, Long receiveUserId, Integer num, BigDecimal money, String content, Long groupId) {

        //解析群是否存在对应的机器人

        //解析content中是否存在可用的


        if (srcType == 0) {
            balanceService.checkPassword(userId, password);
        }

        Packet packet = new Packet();
        packet.setMoney(money);
        packet.setContent(content);
        packet.setUserId(userId);
        packet.setStatus(PacketStatus.NONE.getVal());
        packet.setUserCount(0);
        packet.setCtime(new Date());
        packet.setNum(num);
        packet.setOverStatus(0);

        //检查余额是否充足 如果是是从余额发起的话
        UserBalance balance = balanceService.selectByUserId(userId);
        if (srcType == 0) {
            Contracts.assertTrue(balance.getIncomeBalance().compareTo(money) >= 0, YiChatMsgCode.PACKET_CREATE_NO_ENOUGH_BALANCE.msg());
            Contracts.assertTrue(money.compareTo(NumberUtils.scale2HalfUp(new BigDecimal(0.01).multiply(new BigDecimal(1)))) >= 0, YiChatMsgCode.PACKET_CREATE_MONEY_LIMIT.msg());
        }

        boolean isSingle = Objects.nonNull(receiveUserId);
        //单聊红包
        if (isSingle) {
            packet.setType(PacketType.single.getVal());

        } else {
            //判断金额最大金额是否超过200
            Contracts.assertTrue(NumberUtils.scale2HalfUp(new BigDecimal(num).multiply(new BigDecimal(200))).compareTo(money) >= 0, YiChatMsgCode.PACKET_CREATE_MAX_LIMIT.msg());
            //群红包
            packet.setType(PacketType.group.getVal());
            packet.setGroupId(groupId);

        }

        packetMapper.insertUseGeneratedKeys(packet);
        Contracts.assertNotNull(packet.getId(), YiChatMsgCode.SYSTEM_ERROR.msg());
        logger.debug("创建红包成功:{}", JSON.toJSONString(packet));

        //检查是否存在控制红包逻辑
        if (!isSingle && Objects.nonNull(groupId)) {

            boolean isCtrl = false;

            PacketCtrlItem item = redisService.getCtrl(groupId.intValue());
            if (Objects.nonNull(item)) {
                //控制某个人必抢到多少钱
                if (item.getType() == 1) {
                    BigDecimal resMon = money.subtract(item.getMoney());
                    num--;
                    //校验下钱是否够分
                    if (resMon.compareTo(NumberUtils.scale2HalfUp(new BigDecimal(0.01).multiply(new BigDecimal(num)))) >= 0) {
                        redisService.setReceive(packet.getId(), item.getUserId(), item.getMoney().toString());
                        //失效控制缓存
                        redisService.deleteCtrlKey(groupId.intValue());

                        redisService.setPacketList(packet.getId(), packetCreateService.construct(resMon, num), 24);
                        isCtrl = true;
                    }

                    //控制某个人发布红包必抢尾数为多少
                } else if (item.getType() == 2 && item.getUserId().compareTo(userId) == 0) {

                    BigDecimal big9 = new BigDecimal("0.09");
                    //首先扣除9分钱进行备份
                    if ((money.subtract(big9)).compareTo(NumberUtils.scale2HalfUp(new BigDecimal(0.01).multiply(new BigDecimal(num)))) >= 0) {
                        //尾数为
                        Integer lastnum = item.getLastnum();
                        BigDecimal getM = money.subtract(big9);
                        //拆分的红包列表
                        List<BigDecimal> numList = packetCreateService.construct(getM, num);


                        //先查询红包列表中是否有尾数符合的 如果有则直接绑定到该用户
                        boolean isExistNum = false;

                        int i = 0;
                        for (BigDecimal dec : numList) {
                            //获取末尾数字
                            String midStr = dec.toString();
                            String lastStr = midStr.substring(midStr.length() - 1);
                            if (org.apache.commons.lang3.math.NumberUtils.toInt(lastStr) == lastnum) {
                                isExistNum = true;
                                //取出下一个加上这9分钱
                                if (i < numList.size() - 1) {
                                    numList.set(i + 1, numList.get(i + 1).add(big9));
                                } else {
                                    numList.set(0, numList.get(0).add(big9));
                                }
                                break;
                            }

                            i++;
                        }

                        //如果没有符合尾数的金额 则使用备用9分钱进行尾数处理
                        if (!isExistNum) {
                            BigDecimal b1 = numList.get(0);
                            String midStr = b1.toString();
                            String lastStr = midStr.substring(midStr.length() - 1);
                            //尾数比设置的尾数大
                            int distance = lastnum - org.apache.commons.lang3.math.NumberUtils.toInt(lastStr);
                            BigDecimal dis;
                            if (distance > 0) {
                                dis = new BigDecimal("0.0" + distance);
                            } else {
                                dis = new BigDecimal("0.0" + (-distance)).negate();
                            }

                            logger.info("红包拆分:{}", JSON.toJSONString(numList));
                            logger.info("红包dis:{}", dis);

                            numList.set(0, b1.add(dis));
                            numList.set(1, numList.get(1).add(big9.subtract(dis)));

                            //打断顺序
                            Collections.shuffle(numList);

                        }

                        //redisService.setReceive(packet.getId(), item.getUserId(), getMoney.toString());
                        //失效控制缓存
                        redisService.deleteCtrlKey(groupId.intValue());

                        redisService.setPacketList(packet.getId(), numList, 24);

                        isCtrl = true;

                    }


                    //控制某个人发布红包抢包尾数不为多少
                } else if (item.getType() == 3 && item.getUserId().compareTo(userId) == 0) {
                    //尾数为
                    Integer lastnum = item.getLastnum();
                    //拆分的红包列表
                    List<BigDecimal> numList = packetCreateService.construct(money, num);

                    List<BigDecimal> resList = GeneralUtils.conList(numList, lastnum);

                    //如果计算中出现小雨0.00的数字则拆分失败
                    boolean flag = true;
                    for (BigDecimal d : resList) {
                        if (d.compareTo(BigDecimal.ZERO) <= 0) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag) {
                        redisService.setPacketList(packet.getId(), resList, 24);
                    } else {
                        redisService.setPacketList(packet.getId(), numList, 24);
                    }

                    //将尾数重新划分 不出现指定的num

                    //redisService.setReceive(packet.getId(), item.getUserId(), getMoney.toString());
                    //失效控制缓存
                    redisService.deleteCtrlKey(groupId.intValue());


                    isCtrl = true;
                }
            }

            if (!isCtrl) {
                //默认24小时后红包失效
                redisService.setPacketList(packet.getId(), packetCreateService.construct(money, num), 24);
            }

        } else { //无红包控制逻辑则走出
            //默认24小时后红包失效
            redisService.setPacketList(packet.getId(), packetCreateService.construct(money, num), 24);
        }


        //从创建人的可用余额划出金额到冻结余额中去
        BalanceType type = BalanceType.CREATE_PACKET;
        //从微信支付宝充值创建红包再回调中已扣余额
        if (srcType == 1) {
            //type = BalanceType.WEIXIN_CREATE_PACKET;
        } else if (srcType == 2) {
            //type = BalanceType.ALIPAY_CREATE_PACKET;
        } else {
            balanceService.update(userId, type, money, packet.getId(), isSingle ? "发单聊红包" : "发群聊红包");
        }


        return packet;
    }

    @Override
    public Packet selectById(Long id) {
        Packet packet = packetMapper.selectByPrimaryKey(id);
        //超过24小时 状态变成
        if (packet.getCtime().getTime() + configSet.getPacket().getEffectHour() *
                60 * 60 * 1000L < System.currentTimeMillis()) {
            packet.setStatus(PacketStatus.OVER_TIME.getVal());
        }
        return packet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FsResponse receive(Long packetId, Long userId) {
        Packet packet = selectById(packetId);

        boolean isOverTime = !(packet.getCtime().getTime() + configSet.getPacket().getEffectHour() *
                60 * 60 * 1000L > System.currentTimeMillis());


        //自己发送的单聊红包不能抢
        if (packet.getType().compareTo(PacketType.single.getVal()) == 0 && packet.getUserId().compareTo(userId) == 0) {
            if (isOverTime) {
                return FsResponseGen.successData(YiChatMsgCode.PACKET_RECEIVE_OVER_TIME, selectList(packetId, userId));
            }

            //判断是否抢完
            if (packet.getStatus().compareTo(PacketStatus.DOING.getVal()) == 0) {
                packet.setStatus(PacketStatus.OVER.getVal());
                packetMapper.updateByPrimaryKeySelective(packet);

                return FsResponseGen.successData(YiChatMsgCode.PACKET_RECEIVE_NONE, selectList(packetId, userId));
            }


            return FsResponseGen.successData(YiChatMsgCode.PACKET_RECEIVE_NONEMINE, selectList(packetId, userId));
        }

        BigDecimal val = null;
        //查看用户是否预设红包
        BigDecimal prec = redisService.getReceive(packetId, userId);
        if (Objects.nonNull(prec)) {
            val = prec;
        } else {
            //先查询是否能抢到
            val = redisService.getPacket(packetId, userId);
        }

        //如果当前用户不是预设用户  但是可使用红包已被抢完  则默认使用预设红包
        if (Objects.isNull(val)) {
            val = redisService.getReceiveAbs(packetId);
        }


        //已抢完
        if (Objects.isNull(val) && !isOverTime) {
            packet.setStatus(PacketStatus.OVER.getVal());
            packet.setOverStatus(1);
            packetMapper.updateByPrimaryKeySelective(packet);

            return FsResponseGen.successData(YiChatMsgCode.PACKET_RECEIVE_NONE, selectList(packetId, userId));
        }

        //已抢过
        if (val.compareTo(BigDecimal.ZERO) == 0) {
            return FsResponseGen.successData(YiChatMsgCode.PACKET_RECEIVE_REPEAT, selectList(packetId, userId));
        }

        //已超时
        if (isOverTime) {
            return FsResponseGen.successData(YiChatMsgCode.PACKET_RECEIVE_OVER_TIME, selectList(packetId, userId));
        }


        //领取逻辑
        //1.插入记录
        PacketReceive receive = new PacketReceive();
        receive.setMoney(val);
        receive.setPacketId(packet.getId());
        //已领取
        receive.setStatus(1);
        receive.setUserId(userId);
        packetReceiveMapper.insertSelective(receive);


        //红包状态更新为已领取
        packet.setStatus(PacketStatus.DOING.getVal());

        //2.更新红包数据 如果是单聊 则状态变为已抢完
        if (packet.getType().compareTo(PacketType.single.getVal()) == 0) {
            packet.setStatus(PacketStatus.OVER.getVal());
            packet.setOverStatus(1);
        }


        packetMapper.updateByPrimaryKeySelective(packet);

        //余额数据流转

        //balanceService.update(packet.getUserId(),BalanceType.RECEIVE_PACKET_CREATOR,);
        //领取者增加余额可支配金额
        balanceService.update(userId, BalanceType.RECEIVE_PACKET, val, packetId, packet.getType() == 0 ? "领取单聊红包" : "领取群聊红包");

        //单聊领取直接扣除
        if (packet.getType().compareTo(PacketType.single.getVal()) == 0) {
            //balanceService.update(packet.getUserId(), BalanceType.RECEIVE_PACKET_CREATOR, val, packetId, packet.getType() == 0 ? "领取单聊红包" : "领取群聊红包");
        }


        return FsResponseGen.successData(selectList(packetId, userId));

    }

    @Override
    public PacketInfoDto selectList(Long packetId, Long userId) {

        PacketInfoDto dto = new PacketInfoDto();
        Packet packet = selectById(packetId);
        User user = userService.selectById(packet.getUserId());
        List<PacketReceiveDto> list = packetReceiveMapper.selectList(packetId);

        if (packet.getType().compareTo(PacketType.group.getVal()) == 0) {

            BigDecimal totalReceive = BigDecimal.ZERO;

            for (PacketReceiveDto receive : list) {
                totalReceive = totalReceive.add(receive.getMoney());
            }
            //判断红包是否已抢完
            if (packet.getMoney().compareTo(totalReceive) == 0) {
                packet.setStatus(PacketStatus.OVER.getVal());
                packetMapper.updateByPrimaryKeySelective(packet);
            }

            //结束才计算手气最佳
            if (packet.getStatus().compareTo(PacketStatus.OVER.getVal()) == 0) {
                Optional<PacketReceiveDto> userOp = list.stream().max(Comparator.comparing(PacketReceiveDto::getMoney));
                if (userOp.isPresent()) {
                    userOp.get().setMaxStatus(1);
                }
            }
        }


        dto.setContent(packet.getContent());
        dto.setMoney(packet.getMoney());
        dto.setNick(user.getNick());
        dto.setUserId(user.getId());
        dto.setAvatar(user.getAvatar());
        dto.setList(list);
        dto.setPacketId(packetId);

        Integer status = packet.getStatus();
        if (packet.getType().compareTo(PacketType.single.getVal()) == 0 && packet.getUserId().compareTo(userId) == 0) {
            //status=packet.getStatus().compareTo()
        } else {
            if (status.compareTo(PacketStatus.DOING.getVal()) == 0) {
                status = Optional.ofNullable(selectReceive(userId, packetId)).map(PacketReceive::getStatus).orElse(PacketStatus.NONE.getVal());
            }
        }
        dto.setStatus((packet.getStatus() == 3 && packet.getOverStatus() == 1) ? PacketStatus.OVER.getVal() : status);
        dto.setNum(packet.getNum());
        dto.setReceiveNum(list.size());
        dto.setReceiveMoney(list.stream().filter(v -> v.getUserId().compareTo(userId) == 0).findFirst().map(m -> m.getMoney()).orElse(null));

        return dto;

    }

    private PacketReceive selectReceive(Long userId, Long packetId) {
        Example sel = new Example(PacketReceive.class);
        sel.createCriteria().andEqualTo("userId", userId).andEqualTo("packetId", packetId);
        return packetReceiveMapper.selectOneByExample(sel);
    }

    @Override
    public PacketInfoDto detail(Long packetId, Long userId) {
        PacketInfoDto dto = new PacketInfoDto();
        Packet packet = selectById(packetId);
        User user = userService.selectById(packet.getUserId());
        List<PacketReceiveDto> list = packetReceiveMapper.selectList(packetId);

        if (packet.getType().compareTo(PacketType.group.getVal()) == 0) {

            //结束才计算手气最佳
            if (packet.getStatus().compareTo(PacketStatus.OVER.getVal()) == 0) {
                Optional<PacketReceiveDto> userOp = list.stream().max(Comparator.comparing(PacketReceiveDto::getMoney));
                if (userOp.isPresent()) {
                    userOp.get().setMaxStatus(1);
                }
            }
        }

        dto.setContent(packet.getContent());
        dto.setMoney(packet.getMoney());
        dto.setNick(user.getNick());
        dto.setUserId(user.getId());
        dto.setAvatar(user.getAvatar());
        dto.setList(list);
        dto.setPacketId(packetId);
        Integer status = packet.getStatus();

        //如果是单聊且是发送人 状态为receive的状态为准
        if (packet.getType().compareTo(PacketType.single.getVal()) == 0 && packet.getUserId().compareTo(userId) == 0) {
            //status=packet.getStatus().compareTo()
        } else {
            //有领取判断当前用户是否领取 如果不是当前用户则返回未领取
            if (status.compareTo(PacketStatus.DOING.getVal()) == 0) {
                status = Optional.ofNullable(selectReceive(userId, packetId)).map(PacketReceive::getStatus).orElse(PacketStatus.NONE.getVal());
            }
        }
        dto.setStatus((packet.getStatus() == 3 && packet.getOverStatus() == 1) ? PacketStatus.OVER.getVal() : status);
        dto.setNum(packet.getNum());
        dto.setReceiveNum(list.size());
        dto.setReceiveMoney(list.stream().filter(v -> v.getUserId().compareTo(userId) == 0).findFirst().map(m -> m.getMoney()).orElse(null));

        return dto;
    }

    @Override
    public PacketSendInfoDto countSendMoney(PacketType type, Long userId) {
        return packetMapper.selectSendCount(type.getVal(), userId);
    }

    @Override
    public PacketReceiveInfoDto countReceiveMoney(PacketType type, Long userId) {
        PacketReceiveInfoDto dto = packetMapper.selectReceiveCount(type.getVal(), userId);
        dto.setLuckCount(packetMapper.countUserReceiveLuck(userId));
        return dto;
    }

    @Override
    public FsResponse selectSendList(FsPage page, PacketType type, Long userId) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(packetMapper.selectSendList(type.getVal(), userId), v -> v);
    }

    @Override
    public FsResponse selectReceiveList(FsPage page, PacketType type, Long userId) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(packetMapper.selectReceiveList(type.getVal(), userId), v -> v);
    }

    @Override
    public void packetTimeoutBack() {
        //查询所有超时的红包
        Example example = new Example(Packet.class);
        example.createCriteria().andLessThan("ctime", DateUtils.formatDate(new Date(System.currentTimeMillis() - configSet.getPacket().getEffectHour() * 60 * 60 * 1000L), "yyyy-MM-dd HH:mm:ss")).andLessThan("status", PacketStatus.OVER_TIME.getVal());
        List<Packet> timeList = packetMapper.selectByExample(example);
        if (GeneralUtils.isNotNullOrEmpty(timeList)) {
            timeList.forEach(v -> {

                //如果已抢完 则直接修改状态
                if (v.getStatus().compareTo(PacketStatus.OVER.getVal()) != 0) {
                    //查询未领取红包退回
                    Example sel = new Example(PacketReceive.class);
                    sel.createCriteria().andEqualTo("status", 1).andEqualTo("packetId", v.getId());
                    List<PacketReceive> re = packetReceiveMapper.selectByExample(sel);
                    BigDecimal noneReceive = v.getMoney();
                    if (GeneralUtils.isNotNullOrEmpty(re)) {
                        BigDecimal receive = re.stream().map(PacketReceive::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
                        noneReceive = v.getMoney().subtract(receive);

                    }

                    //还未待领取的红包
                    if (noneReceive.compareTo(BigDecimal.ZERO) > 0) {
                        logger.debug("红包[{}]还有金额【{}】超时未被领取", v.getId(), noneReceive);
                        if (noneReceive.compareTo(BigDecimal.ZERO) > 0) {
                            balanceService.update(v.getUserId(), BalanceType.RETURN_PACKET, noneReceive, v.getUserId(), (v.getType() == 0 ? "单聊" : "群聊") + "红包超时退回");
                        }
                    } else {
                        logger.debug("红包[{}]已被领完", v.getId());
                    }

                }

                v.setStatus(PacketStatus.OVER_TIME.getVal());
                packetMapper.updateByPrimaryKeySelective(v);
                //删除redis key
                redisService.deleteKey(v.getId());
                logger.debug("红包[{}]状态变更为超时", v.getId());
            });
        }
    }


}
