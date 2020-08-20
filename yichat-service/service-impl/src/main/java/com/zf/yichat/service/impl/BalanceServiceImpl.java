package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.zf.yichat.mapper.UserBalanceDetailMapper;
import com.zf.yichat.mapper.UserBalanceMapper;
import com.zf.yichat.mapper.UserTradeMapper;
import com.zf.yichat.mapper.UserWithdrawMapper;
import com.zf.yichat.model.UserBalance;
import com.zf.yichat.model.UserBalanceDetail;
import com.zf.yichat.model.UserTrade;
import com.zf.yichat.model.UserWithdraw;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.PushService;
import com.zf.yichat.service.dto.BalanceListDto;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.*;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.PayType;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:52 2019/6/10 2019
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Autowired
    private UserBalanceDetailMapper userBalanceDetailMapper;

    @Autowired
    private UserWithdrawMapper userWithdrawMapper;

    @Autowired
    private UserTradeMapper userTradeMapper;

    @Autowired
    private PushService pushService;

    @Override
    public void create(Long userId) {
        UserBalance balance = new UserBalance();
        balance.setBalance(BigDecimal.ZERO);
        balance.setUserId(userId);
        userBalanceMapper.insertSelective(balance);

    }

    @Override
    public void update(Long userId, BalanceType type, BigDecimal money, Long referId, String memo) {


        switch (type) {
            case ADD:
                //充值余额   总余额+ 冻结 -- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case BACK_ADD:
                //系统充值  总余额+ 冻结 -- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case PACKET_ADD:
                //红包充值 往冻结余额中充值  总余额+ 冻结+ 可用--   ---已停用
                record(userId, type.getVal(), money, money, money, BigDecimal.ZERO, referId, memo);
                break;
            case WEIXIN_CREATE_PACKET:
                //红包充值 微信 往冻结余额中充值  总余额+ 冻结+ 可用--
                record(userId, type.getVal(), money, money, money, BigDecimal.ZERO, referId, memo);
                break;
            case ALIPAY_CREATE_PACKET:
                //红包充值 支付宝 往冻结余额中充值  总余额+ 冻结+ 可用--
                record(userId, type.getVal(), money, money, money, BigDecimal.ZERO, referId, memo);
                break;
            case WITHDRAW:
                //提现 直接从冻结余额中提取  总余额- 冻结- 可用--
                record(userId, type.getVal(), money, money.negate(), money.negate(), BigDecimal.ZERO, referId, memo);
                break;
            case WITHDRAW_APPLY:
                //提现申请  从可提现提往冻结  总余额-- 冻结+ 可用-
                record(userId, type.getVal(), money, BigDecimal.ZERO, money, money.negate(), referId, memo);
                break;
            case WITHDRAW_FAIL:
                //提现失败  从冻结余额返回到可支配余额  总余额-- 冻结- 可用+
                record(userId, type.getVal(), money, BigDecimal.ZERO, money.negate(), money, referId, memo);
                break;
            case BACK_WITHDRAW:
                //系统提现 直接从可用余额中提取  总余额- 冻结-- 可用-
                record(userId, type.getVal(), money, money.negate(), BigDecimal.ZERO, money.negate(), referId, memo);
                break;
            case CREATE_PACKET:
                //从可用余额中扣除金额到冻结余额中去 总余额-- 冻结+ 可用-
                record(userId, type.getVal(), money, BigDecimal.ZERO, money, money.negate(), referId, memo);
                break;
            case RECEIVE_PACKET:
                //添加可用余额  总余额+ 冻结- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case RECEIVE_PACKET_CREATOR:
                //从冻结余额中扣除  总余额- 冻结- 可用--
                record(userId, type.getVal(), money, money.negate(), money.negate(), BigDecimal.ZERO, referId, memo);
                break;
            case RETURN_PACKET:
                //超时返回可用余额  从冻结余额返回到可支配余额  总余额-- 冻结- 可用+
                record(userId, type.getVal(), money, BigDecimal.ZERO, money.negate(), money, referId, memo);
                break;
            case SIGN:
                //签到积分充值  总余额+ 冻结-- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case VIDEO_PLAY:
                //看视频奖励   总余额+ 冻结 -- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case VIDEO_COMMENT:
                //评论视频奖励   总余额+ 冻结 -- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case VIDEO_PRAISE:
                //点赞视频奖励   总余额+ 冻结 -- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case RECOMMEND_USER:
                //推荐用户注册   总余额+ 冻结 -- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case VIDEO_PUBLISH:
                //发布视频   总余额+ 冻结 -- 可用+
                record(userId, type.getVal(), money, money, BigDecimal.ZERO, money, referId, memo);
                break;
            case VIDEO_PUBLISH_DELETE:
                //扣除余额 总余额- 冻结-- 可用-
                record(userId, type.getVal(), money, money.negate(), BigDecimal.ZERO, money.negate(), referId, memo);
                break;
            default:
        }


    }

    @Override
    public void updatePassword(Long userId, String password) {
        UserBalance balance = new UserBalance();
        balance.setPassword(MD5Util.shaEncode(password));

        Example update = new Example(UserBalance.class);
        update.createCriteria().andEqualTo("userId", userId);
        userBalanceMapper.updateByExampleSelective(balance, update);
    }

    @Override
    public void checkPassword(Long userId, String password) {
        UserBalance balance = selectByUserId(userId);

        Contracts.assertTrue(MD5Util.shaEncode(password).equals(balance.getPassword()), YiChatMsgCode.SYSTEM_USER_BALANCE_ACCOUNT_PASSWORD_ERROR.msg());

    }

    @Override
    public UserBalance selectByUserId(Long userId) {
        Example select = new Example(UserBalance.class);
        select.createCriteria().andEqualTo("userId", userId);
        UserBalance balance = userBalanceMapper.selectOneByExample(select);
        Contracts.assertNotNull(balance, YiChatMsgCode.SYSTEM_USER_BALANCE_ACCOUNT.msg());
        Contracts.assertTrue(balance.getStatus().compareTo(FsConst.Status.EFFECT) == 0, YiChatMsgCode.SYSTEM_USER_BALANCE_ACCOUNT_STATUS.msg());
        return balance;
    }

    @Override
    public void record(Long userId, Integer type, BigDecimal money, BigDecimal balance, BigDecimal freezeMoney, BigDecimal incomeMoney, Long referId, String desc) {


        UserBalance bb = selectByUserId(userId);

        //添加流水记录
        UserBalanceDetail detail = new UserBalanceDetail();
        detail.setUserId(userId);
        detail.setBalanceId(bb.getId());
        detail.setMoney(money);
        detail.setbFreeze(bb.getFreezeBalance());
        detail.setbIncome(bb.getIncomeBalance());
        detail.setbBalance(bb.getBalance());

        detail.setaBalance(bb.getBalance().add(balance));
        detail.setaFreeze(bb.getFreezeBalance().add(freezeMoney));
        detail.setaIncome(bb.getIncomeBalance().add(incomeMoney));

        detail.setMemo(desc);

        detail.setReferId(referId);
        detail.setType(type);
        int i = userBalanceDetailMapper.insertSelective(detail);
        Contracts.assertTrue(i == 1, YiChatMsgCode.SYSTEM_ERROR.msg());


        //更新余额
        int ii = userBalanceMapper.updateBalance(userId, balance, freezeMoney, incomeMoney);
        Contracts.assertTrue(ii == 1, YiChatMsgCode.SYSTEM_ERROR.msg());
    }


    @Override
    public FsResponse selectList(FsPage init, Integer type, Long userId) {

        Example select = new Example(UserBalanceDetail.class);

        Example.Criteria criteria = select.createCriteria();

        criteria.andEqualTo("userId", userId);
        if (Objects.nonNull(type)) {
            //支出
            if (type == 0) {
                criteria.andIn("type",
                        Arrays.asList(BalanceType.CREATE_PACKET.getVal(),
                                BalanceType.WITHDRAW.getVal(),
                                BalanceType.BACK_WITHDRAW.getVal(),
                                BalanceType.VIDEO_PUBLISH_DELETE.getVal()

                        ));
            } else {//收入
                criteria.andIn("type",
                        Arrays.asList(BalanceType.RECEIVE_PACKET.getVal(),
                                BalanceType.RETURN_PACKET.getVal(),
                                BalanceType.ADD.getVal(),
                                BalanceType.SIGN.getVal(),
                                BalanceType.BACK_ADD.getVal(),
                                BalanceType.VIDEO_COMMENT.getVal(),
                                BalanceType.VIDEO_PRAISE.getVal(),
                                BalanceType.VIDEO_PLAY.getVal(),
                                BalanceType.VIDEO_PUBLISH.getVal(),
                                BalanceType.RECOMMEND_USER.getVal()

                        ));
            }
        } else {
            criteria.andIn("type", Arrays.asList(
                    BalanceType.CREATE_PACKET.getVal(),
                    BalanceType.WITHDRAW.getVal(),
                    BalanceType.RECEIVE_PACKET.getVal(),
                    BalanceType.RETURN_PACKET.getVal(),
                    BalanceType.ADD.getVal(),
                    BalanceType.SIGN.getVal(),
                    BalanceType.BACK_ADD.getVal(),
                    BalanceType.BACK_WITHDRAW.getVal(),
                    BalanceType.VIDEO_COMMENT.getVal(),
                    BalanceType.VIDEO_PRAISE.getVal(),
                    BalanceType.VIDEO_PLAY.getVal(),
                    BalanceType.VIDEO_PUBLISH.getVal(),
                    BalanceType.VIDEO_PUBLISH_DELETE.getVal(),
                    BalanceType.RECOMMEND_USER.getVal()
            ));
        }

        select.setOrderByClause(" ctime desc");

        PageHelper.startPage(init.getPageNo(), init.getPageSize());
        return DtoChangeUtils.getPageList(userBalanceDetailMapper.selectByExample(select), v -> {
            BalanceListDto dto = new BalanceListDto();
            dto.setCtime(DateUtils.formatDate(v.getCtime(), "yyyy-MM-dd HH:mm:ss"));
            dto.setDateDesc(DateUtils.formatDate(v.getCtime(), "MM-dd"));
            dto.setMemo(v.getMemo());
            dto.setMoney(v.getMoney());
            //dto.setMoneyDesc((v.getMoney().compareTo(BigDecimal.ZERO) < 0 ? ("-" + v.getMoney()) : ("+" + v.getMoney())) + "元");
            dto.setMoneyDesc(BalanceType.valOf(v.getType()).getDirect() + v.getMoney() + FsConst.UNIT);
            return dto;
        });
    }


    @Override
    public void checkWithdraw(Integer id, Integer status, String reason) {
        UserWithdraw withdraw = userWithdrawMapper.selectByPrimaryKey(id);
        if (Objects.nonNull(withdraw)) {
            withdraw.setStatus(status);
            withdraw.setRefuseReason(reason);
            withdraw.setCheckTime(new Date());
            userWithdrawMapper.updateByPrimaryKey(withdraw);

            //通过
            if (status == 1) {
                //余额变更
                update(withdraw.getUserId(), BalanceType.WITHDRAW, withdraw.getMoney(), id.longValue(), "提现成功");

                pushService.sendSingle(withdraw.getUserId(), "提现消息", "提现成功，提现金额【" + NumberUtils.halfTwo(withdraw.getMoney()) + FsConst.UNIT + "】");

            } else { //拒绝
                //余额变更
                update(withdraw.getUserId(), BalanceType.WITHDRAW_FAIL, withdraw.getMoney(), id.longValue(), "提现失败");

                pushService.sendSingle(withdraw.getUserId(), "提现消息", "提现申请被拒绝，拒绝理由【" + withdraw.getRefuseReason() + "】");
            }
        }
    }

    @Override
    public Long createTradeRecord(Long userId, String tradeId, BigDecimal money, PayType payType, Integer tradeType) {

        UserTrade trade = new UserTrade();
        trade.setMoney(money);
        trade.setTradeNo(tradeId);
        trade.setUserId(userId);
        trade.setPayType(payType.getVal());
        trade.setType(tradeType);
        trade.setStatus(0);
        trade.setCtime(new Date());

        userTradeMapper.insertUseGeneratedKeys(trade);

        return trade.getId();
    }

    @Override
    public UserTrade selectTradeByTradeNo(String tradeNo) {
        Example example = new Example(UserTrade.class);
        example.createCriteria().andEqualTo("tradeNo", tradeNo);
        return userTradeMapper.selectOneByExample(example);
    }

    @Override
    public void completeTrade(Long id) {
        UserTrade trade = new UserTrade();
        trade.setId(id);
        trade.setStatus(1);

        userTradeMapper.updateByPrimaryKeySelective(trade);
    }
}
