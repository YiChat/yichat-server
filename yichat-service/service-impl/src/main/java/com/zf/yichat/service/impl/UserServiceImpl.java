package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.UserSearchDto;
import com.zf.yichat.dto.UserSignDayDto;
import com.zf.yichat.dto.UserSignDto;
import com.zf.yichat.dto.im.ImCommand;
import com.zf.yichat.mapper.*;
import com.zf.yichat.model.*;
import com.zf.yichat.service.*;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.service.config.RedisService;
import com.zf.yichat.utils.MobileSplit;
import com.zf.yichat.utils.ServiceUtils;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.*;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.DictKey;
import com.zf.yichat.vo.ThirdType;
import com.zf.yichat.vo.UserStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:50 2019/5/28 2019
 */
@Service
public class UserServiceImpl implements UserService {

    private static String user_time_pre = "timestamp_";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDeviceMapper userDeviceMapper;
    @Autowired
    private ImApiService imApiService;
    @Autowired
    private UserImMapper userImMapper;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ThirdLoginMapper thirdLoginMapper;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private UserBankMapper userBankMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserSignMapper userSignMapper;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private UserBalanceMapper userBalanceMapper;
    @Autowired
    private UserBalanceDetailMapper userBalanceDetailMapper;
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private FriendApplyMapper friendApplyMapper;
    @Autowired
    private UserWithdrawMapper userWithdrawMapper;
    @Autowired
    private ConfigSet configSet;
    @Autowired
    private UserIpMapper userIpMapper;
    @Autowired
    private FriendService friendService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserIm add(User user) {

        MobileSplit split = ServiceUtils.splitMobile(user.getMobile());
        user.setAreaCode(split.getAreaCode());
        user.setMobile(StringUtils.isBlank(split.getMobile()) ? null : split.getMobile());
        user.setPassword(MD5Util.shaEncode(user.getPassword()));
        //获取盐值  验证token
        user.setSalt(ServiceUtils.generateSalt());

        //不设置默认有建群权限
        user.setCreateGroupAuth(Optional.ofNullable(configSet.getCreateGroupAuth()).orElse(1));
        user.setCtime(new Date());
        user.setStatus(FsConst.Status.EFFECT);
        if (configSet.isMobileAppIdStatus()) {
            user.setAppid(user.getMobile());
            user.setMobile(null);
        }
        userMapper.insertUseGeneratedKeys(user);

        Contracts.assertTrue(Objects.nonNull(user.getId()), YiChatMsgCode.REGISTER_FAIL.msg());
        try {
            String token = securityService.token(user.getSalt(), user.getId());
            user.setToken(token);
            //生成我的推荐码
            user.setUcode(String.valueOf(user.getId()));
            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建IM账号 im密码随机6位数字
        String accountId = String.valueOf(user.getId());
        String imPassword = GeneralUtils.randomNum(6);

        ImCommand res = imApiService.create(accountId, imPassword);

        Contracts.assertTrue(Objects.nonNull(res), YiChatMsgCode.REGISTER_FAIL.msg());

        UserIm userIm = new UserIm();
        userIm.setImPassword(imPassword);
        userIm.setUserId(user.getId());
        userImMapper.insertSelective(userIm);

        //创建余额账号
        balanceService.create(user.getId());

        return userIm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addThird(String nick, String avatar, String openId, String unionId, ThirdType thirdType) {


        //注册账号
        User add = new User();
        add.setNick(GeneralUtils.removeAllEmojis(nick));
        add.setAvatar(avatar);
        String imPassword = add(add).getImPassword();

        //第三方账号信息保存
        ThirdLogin thirdLogin = new ThirdLogin();
        thirdLogin.setType(thirdType.getVal());
        thirdLogin.setUniqueCode(openId);
        thirdLogin.setUnionId(unionId);
        thirdLogin.setUserid(add.getId());
        thirdLoginMapper.insertSelective(thirdLogin);

        return imPassword;
    }

    @Override
    public User selectByMobile(String mobile) {
        User query = new User();
        if (configSet.isMobileAppIdStatus()) {
            query.setMobile(mobile);
        } else {
            MobileSplit split = ServiceUtils.splitMobile(mobile);
            query.setMobile(split.getMobile());
        }
        return userMapper.selectOne(query);
    }

    @Override
    public List<UserDevice> selectDeviceByUserId(Long userId) {
        Example select = new Example(UserDevice.class);
        select.createCriteria().andEqualTo("userId", userId);
        return userDeviceMapper.selectByExample(select);
    }

    @Override
    public UserDevice selectDevice(Long id, String deviceId) {
        Example select = new Example(UserDevice.class);
        select.createCriteria().andEqualTo("userId", id).andEqualTo("deviceId", deviceId);
        return userDeviceMapper.selectOneByExample(select);
    }

    @Override
    public boolean existDeviceId(String deviceId) {
        Example select = new Example(UserDevice.class);
        select.createCriteria().andEqualTo("deviceId", deviceId);
        return userDeviceMapper.selectCountByExample(select) > 0;
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public UserIm selectIm(Long id) {
        Example example = new Example(UserIm.class);
        example.createCriteria().andEqualTo("userId", id);
        return userImMapper.selectOneByExample(example);
    }

    @Override
    public void updateDevice(Long id, String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            return;
        }
        userDeviceMapper.updateInsert(id, deviceId);
    }

    @Override
    public User selectById(Long userId) {

        return Optional.ofNullable(userMapper.selectByPrimaryKey(userId)).orElse(new User());

    }

    @Override
    public List<User> searchByMobileOrAppId(String content) {
        Example select = new Example(User.class);
        select.createCriteria().andEqualTo("status", UserStatusEnum.OPEN.getVal());

        //通过配置
        Example.Criteria other = select.createCriteria();
        other.andEqualTo("mobile", content);
        //是否开启应用ID搜索
        if (!configSet.getSearch().isCloseAppId()) {
            other.orEqualTo("appid", content);
        }
        select.and(other);
        return userMapper.selectByExample(select);
    }

    @Override
    public User selectByThirdAccount(String openId, String unionId) {
        Example example = new Example(ThirdLogin.class);
        example.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT).andEqualTo("uniqueCode", openId);
        ThirdLogin thirdLogin = thirdLoginMapper.selectOneByExample(example);
        User user = null;
        if (Objects.nonNull(thirdLogin)) {
            user = selectById(thirdLogin.getUserid());
            //更新下unionId
            if (Objects.nonNull(unionId)) {
                thirdLogin.setUnionId(unionId);
                thirdLoginMapper.updateByPrimaryKeySelective(thirdLogin);
            }
        }
        if (Objects.isNull(user)) {
            Example example1 = new Example(ThirdLogin.class);
            example1.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT).andEqualTo("unionId", unionId);
            ThirdLogin thirdLogin1 = thirdLoginMapper.selectOneByExample(example1);
            if (Objects.nonNull(thirdLogin1)) {
                user = selectById(thirdLogin1.getUserid());
            }
        }
        return user;
    }

    @Override
    public User selectByUnionId(String unionId) {
        Example example = new Example(ThirdLogin.class);
        example.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT).andEqualTo("unionId", unionId);
        ThirdLogin thirdLogin = thirdLoginMapper.selectOneByExample(example);
        User user = null;
        if (Objects.nonNull(thirdLogin)) {
            user = selectById(thirdLogin.getUserid());
        }
        return user;
    }

    @Override
    public List<User> selectList(List<Long> idList) {
        Example example = new Example(User.class);
        example.createCriteria().andIn("id", idList);

        return userMapper.selectByExample(example);
    }

    @Override
    public FsResponse selectIndexList(FsPage page, String nick, Integer status, String mobile, String ucode) {

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        /*if(){

        }*/
        example.setOrderByClause(" ctime desc");
        PageHelper.startPage(page.getPageNo(), page.getPageNo());
        return DtoChangeUtils.getPageList(userMapper.selectByExample(example), v -> v);
    }

    @Override
    public void adBankCard(UserBank userBank) {
        userBankMapper.insertSelective(userBank);
    }

    @Override
    public List<UserBank> selectBankList(Long userId) {
        Example example = new Example(UserBank.class);
        example.createCriteria().andEqualTo("status", 0).andEqualTo("userId", userId);
        example.setOrderByClause(" id");
        return userBankMapper.selectByExample(example);
    }

    @Override
    public void deleteBank(Integer bankId) {
        UserBank bank = new UserBank();
        bank.setId(bankId);
        bank.setStatus(1);
        userBankMapper.updateByPrimaryKeySelective(bank);
    }

    @Override
    public User selectByAppId(String mobile) {
        User query = new User();
        query.setAppid(mobile);
        return userMapper.selectOne(query);
    }

    @Override
    @Async
    public void updateTimestamp(Long userId, Long time) {
        User query = new User();
        query.setId(userId);
        query.setTimestamp(time);
        userMapper.updateByPrimaryKeySelective(query);
        redisService.setVal(user_time_pre + userId, String.valueOf(time));
    }

    @Override
    public Long getTimestamp(Long userId) {
        String time = redisService.getVal(user_time_pre + userId);
        if (StringUtils.isNotBlank(time)) {
            return org.apache.commons.lang3.math.NumberUtils.toLong(time);
        } else {
            return Optional.ofNullable(selectById(userId).getTimestamp()).orElse(0L);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void crashUser(List<Long> userIds) {
        if (GeneralUtils.isNullOrEmpty(userIds)) {
            return;
        }
        userIds.forEach(v -> {
            User u = selectById(v);
            if (Objects.nonNull(u)) {
                //删除用户主体
                userMapper.deleteByPrimaryKey(v);
                //删除用户设备
                Example deviceDel = new Example(UserDevice.class);
                deviceDel.createCriteria().andEqualTo("userId", v);
                userDeviceMapper.deleteByExample(deviceDel);
                //删除余额信息
                Example balanceDel = new Example(UserBalance.class);
                balanceDel.createCriteria().andEqualTo("userId", v);
                userBalanceMapper.deleteByExample(balanceDel);
                //删除余额详细信息
                Example balanceDetailDel = new Example(UserBalanceDetail.class);
                balanceDetailDel.createCriteria().andEqualTo("userId", v);
                userBalanceDetailMapper.deleteByExample(balanceDetailDel);
                //删除银行信息
                Example bankDel = new Example(UserBank.class);
                bankDel.createCriteria().andEqualTo("userId", v);
                userBankMapper.deleteByExample(bankDel);

                //删除提现信息
                Example withDel = new Example(UserWithdraw.class);
                withDel.createCriteria().andEqualTo("userId", v);
                userWithdrawMapper.deleteByExample(withDel);

                //删除im账号信息
                Example imDel = new Example(UserIm.class);
                imDel.createCriteria().andEqualTo("userId", v);
                userImMapper.deleteByExample(imDel);

                //删除用户好友
                Example example = new Example(Friend.class);
                example.createCriteria().andEqualTo("userId", v);
                example.or(example.createCriteria().andEqualTo("friendId", v));
                friendMapper.deleteByExample(example);

                //删除好友申请
                Example appDel = new Example(FriendApply.class);
                appDel.createCriteria().andEqualTo("userId", v);
                appDel.or(appDel.createCriteria().andEqualTo("friendId", v));
                friendApplyMapper.deleteByExample(appDel);

                //删除第三方登陆
                Example tDel = new Example(ThirdLogin.class);
                tDel.createCriteria().andEqualTo("userid", v);
                thirdLoginMapper.deleteByExample(tDel);


            }
        });

        //im服务删除
        imApiService.deleteUser(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FsResponse recordSign(Long userId, boolean isSign) {

        UserSignDto dto = new UserSignDto();

        BigDecimal score = new BigDecimal(Optional.ofNullable(redisService.getVal(DictKey.user_sign_get.name())).orElse("0"));
        if (score.compareTo(BigDecimal.ZERO) == 0) {
            SysDict sysDict = sysDictService.selectOne(DictKey.user_sign_get);
            Contracts.assertNotNull(sysDict, YiChatMsgCode.USER_SIGN_FAIL.msg());
            score = new BigDecimal(sysDict.getValue());
        }


        Calendar calendar = Calendar.getInstance();
        String todayDate = DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd");
        boolean isLastDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) == 1;
        List<String> weekDays = new ArrayList<>();

        boolean isHasGet = false;
        if (isSign) {

            Example ex = new Example(UserSign.class);
            ex.createCriteria().andEqualTo("userId", userId).andEqualTo("signDate", todayDate);
            if (Objects.isNull(userSignMapper.selectOneByExample(ex))) {
                //获取每天签到的值
                UserSign sign = new UserSign();
                sign.setSignDate(new Date());
                sign.setScore(score);
                sign.setUserId(userId);
                sign.setCtime(new Date());
                userSignMapper.insertUseGeneratedKeys(sign);

                //添加入账记录
                balanceService.update(userId, BalanceType.SIGN, score, sign.getId().longValue(), "签到获取");
            } else {
                isHasGet = true;
            }


        }


        //获取一周签到情况
        //重置日期为这周的第一天
        int d = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_YEAR, d == 1 ? -6 : (2 - d));

        weekDays.add(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd"));
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            weekDays.add(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd"));
        }

        //查询
        Example example = new Example(UserSign.class);
        example.createCriteria().andIn("signDate", weekDays).andEqualTo("userId", userId);

        List<UserSign> list = userSignMapper.selectByExample(example);

        //判断是否符合一周奖励
        //当前是否一周的最后一天
        //判断是否七天都签到
        BigDecimal weekScore = new BigDecimal(Optional.ofNullable(redisService.getVal(DictKey.user_sign_continue_get.name())).orElse("0"));
        if (weekScore.compareTo(BigDecimal.ZERO) == 0) {
            SysDict sysDict = sysDictService.selectOne(DictKey.user_sign_continue_get);
            Contracts.assertNotNull(sysDict, YiChatMsgCode.USER_SIGN_FAIL.msg());
            weekScore = new BigDecimal(sysDict.getValue());
        }

        if (isLastDayOfWeek && list.size() == 7 && isSign && !isHasGet) {


            balanceService.update(userId, BalanceType.SIGN, weekScore, null, "签到一周获取");
        }

        List<UserSignDayDto> dayDtoList = new ArrayList<>();
        int i = 1;
        String memo;
        for (String day : weekDays) {
            UserSignDayDto signDto = new UserSignDayDto();

            switch (i) {
                case 1:
                    memo = "周一";
                    break;
                case 2:
                    memo = "周二";
                    break;
                case 3:
                    memo = "周三";
                    break;
                case 4:
                    memo = "周四";
                    break;
                case 5:
                    memo = "周五";
                    break;
                case 6:
                    memo = "周六";
                    break;
                case 7:
                    memo = "周日";
                    break;
                default:
                    memo = "";
            }

            System.out.println(day + ":" + todayDate);

            signDto.setIsToday(StringUtils.equals(day, todayDate) ? 1 : 0);
            signDto.setSignStatus(list.stream().anyMatch(v -> StringUtils.equals(DateUtils.formatDate(v.getSignDate(), "yyyy-MM-dd"), day)) ? 1 : 0);
            signDto.setMemo(memo);

            dayDtoList.add(signDto);
            i++;

        }

        dto.setList(dayDtoList);
        dto.setContent(sysDictService.selectOne(DictKey.user_sign_desc).getMemo());


        return FsResponseGen.successData(dto);
    }


    @Override
    public void updatePassword(Long userId, String password) {
        User user = new User();
        user.setId(userId);
        user.setPassword(MD5Util.shaEncode(password));
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Async
    @Override
    public void saveIp(String ip, Long userId) {
        if (StringUtils.isBlank(ip)) {
            return;
        }
        //查询记录的数据
        Example select = new Example(UserIp.class);
        select.createCriteria().andEqualTo("userId", userId);
        select.setOrderByClause(" id desc");
        List<UserIp> ipList = userIpMapper.selectByExample(select);
        if (GeneralUtils.isNotNullOrEmpty(ipList)) {
            for (int i = 0; i < ipList.size(); i++) {
                //只保存最近5条登陆IP记录
                if (i > 4 || StringUtils.equals(ipList.get(i).getIp(), ip)) {
                    userIpMapper.deleteByPrimaryKey(ipList.get(i).getId());
                }
            }
        }

        //插入当前数据
        UserIp inser = new UserIp();
        inser.setIp(ip);
        inser.setUserId(userId);
        userIpMapper.insertSelective(inser);

    }

    @Override
    public User selectByRecommendCode(String recommendCode) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("ucode", recommendCode);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public FsResponse selectRecommendList(FsPage page, Long userId) {

        User user = selectById(userId);

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("recommendCode", user.getUcode());
        example.setOrderByClause(" id desc");
        //PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(userMapper.selectByExample(example), copy -> {
            UserSearchDto dto = new UserSearchDto();
            //dto.setGender(copy.getGender());
            dto.setUserId(copy.getId());
            dto.setAvatar(copy.getAvatar());
            dto.setNick(copy.getNick());
            //dto.setAppId(copy.getAppid());
            //0好友关系 1否
            //dto.setFriendStatus(friendService.isFriend(userId, copy.getId()) ? 0 : 1);
            //dto.setTimeDesc(DateUtils.formatDate(copy.getRecommendTime()));
            return dto;
        });
    }

    @Override
    public Long selectRecommendCount(String ucode) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("recommendCode", ucode);

        return Long.valueOf(userMapper.selectCountByExample(example));
    }
}
