package com.zf.yichat.api.controller.user;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.api.dto.resp.BankDto;
import com.zf.yichat.api.dto.resp.UserInfoDto;
import com.zf.yichat.api.dto.resp.WithdrawConfigDto;
import com.zf.yichat.dto.UserSearchDto;
import com.zf.yichat.mapper.UserOnlineMapper;
import com.zf.yichat.model.*;
import com.zf.yichat.service.*;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.MD5Util;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.DictKey;
import com.zf.yichat.vo.UserStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户相关接口
 *
 * @author yichat
 * @date create in 09:52 2019/5/31 2019
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private UserWithdrawService userWithdrawService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private ConfigSet configSet;

    @Autowired
    private UserOnlineMapper userOnlineMapper;

    /**
     * 搜索用户 完全匹配手机号或appid
     */
    @PostMapping("/search")
    public FsResponse search(@RequestBody UserSearchRequest params) {

        List<UserSearchDto> dtos = null;
        if (StringUtils.isNotBlank(params.getContent())) {
            List<User> copys = userService.searchByMobileOrAppId(params.getContent());
            if (GeneralUtils.isNotNullOrEmpty(copys)) {
                dtos = copys.stream().map(copy -> {
                    UserSearchDto dto = new UserSearchDto();
                    dto.setGender(copy.getGender());
                    dto.setUserId(copy.getId());
                    dto.setAvatar(copy.getAvatar());
                    dto.setNick(copy.getNick());
                    dto.setAppId(copy.getAppid());
                    //0好友关系 1否
                    dto.setFriendStatus(friendService.isFriend(getUserId(), copy.getId()) ? 0 : 1);
                    return dto;
                }).collect(Collectors.toList());
            }
        }

        return FsResponseGen.successData(dtos);
    }

    @PostMapping("/info")
    public FsResponse info(@RequestBody UserInfoRequest request) {


        User user = userService.selectById(Optional.ofNullable(request.getUserId()).orElse(getUserId()));
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            return FsResponseGen.fail(YiChatMsgCode.USER_INFO_NONE);
        }
        Contracts.assertNotNull(UserStatusEnum.isOpen(user.getStatus()), YiChatMsgCode.SYSTEM_USER_CLOSE.msg());

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setAppId(user.getAppid());
        userInfoDto.setAvatar(user.getAvatar());
        userInfoDto.setNick(user.getNick());
        userInfoDto.setUserId(String.valueOf(user.getId()));
        userInfoDto.setGender(user.getGender());
        userInfoDto.setMobile(user.getMobile());
        userInfoDto.setUcode(user.getUcode());
        userInfoDto.setRecommendCount(userService.selectRecommendCount(user.getUcode()));
        userInfoDto.setRecommendCode(user.getRecommendCode());
        userInfoDto.setPayPasswordStatus(StringUtils.isNotBlank(balanceService.selectByUserId(getUserId()).getPassword()) ? 1 : 0);
        //获取好友备注
        userInfoDto.setFriendStatus(0);
        if (Objects.nonNull(request.getUserId()) && request.getUserId().compareTo(getUserId()) != 0) {
            Friend friend = friendService.selectOne(getUserId(), request.getUserId());
            //0好友关系 1否
            if (Objects.nonNull(friend)) {
                userInfoDto.setRemark(friend.getUserId().compareTo(getUserId()) == 0 ? friend.getUserMark() : friend.getFriendMark());
            } else {
                userInfoDto.setRemark("");
                userInfoDto.setFriendStatus(1);
            }

        }

        return FsResponseGen.successData(userInfoDto);
    }

    /**
     * 用户资料更新
     */
    @PostMapping("info/update")
    public FsResponse update(@RequestBody UserInfoUpdateRequest params) {
        params.valid();

        //未登录情况下修改密码 重置密码
        if (getUserId() == null && StringUtils.isNotBlank(params.getMobile()) && StringUtils.isNotBlank(params.getPassword())) {
            User src = userService.selectByMobile(params.getMobile());
            Contracts.assertNotNull(src, YiChatMsgCode.SYSTEM_ERROR.msg());
            Contracts.assertNotNull(UserStatusEnum.isOpen(src.getStatus()), YiChatMsgCode.SYSTEM_USER_CLOSE.msg());


            User user = new User();
            user.setId(src.getId());
            user.setPassword(MD5Util.shaEncode(params.getPassword()));
            userService.update(user);


            return FsResponseGen.success();

        }


        if (StringUtils.isNotBlank(params.getMobile())) {
            //手机号已存在
            Contracts.assertTrue(Objects.isNull(userService.selectByMobile(params.getMobile())), YiChatMsgCode.USER_INFO_MOBILE_EXIST.msg());
        }

        User src = userService.selectById(getUserId());

        Contracts.assertNotNull(src, YiChatMsgCode.SYSTEM_ERROR.msg());
        Contracts.assertNotNull(UserStatusEnum.isOpen(src.getStatus()), YiChatMsgCode.SYSTEM_USER_CLOSE.msg());

        //识别码存在则不能修改
        if (StringUtils.isNotBlank(params.getAppId()) && StringUtils.isNotBlank(src.getAppid())) {
            return FsResponseGen.fail(YiChatMsgCode.USER_INFO_UPDATE_UCODE);
        } else if (StringUtils.isNotBlank(params.getAppId())) {//识别码已存在校验
            if (Optional.ofNullable(userService.selectByAppId(params.getAppId())).map(v -> v.getId().compareTo(getUserId()) != 0).orElse(false)) {
                return FsResponseGen.fail(YiChatMsgCode.USER_INFO_UPDATE_UCODE);
            }
        }


        User user = new User();

        //绑定推荐码
        if (StringUtils.isBlank(src.getRecommendCode()) && StringUtils.isNotBlank(params.getRecommendCode())) {
            User recUser = userService.selectByRecommendCode(params.getRecommendCode());
            if (Objects.nonNull(recUser)) {

                //获取配置的奖励
                BigDecimal reword = new BigDecimal(sysDictService.selectOne(DictKey.recommend_user_reward).getValue());

                user.setRecommendCode(src.getRecommendCode());
                user.setRecommendTime(new Date());
                //增加奖励
                balanceService.update(recUser.getId(), BalanceType.RECOMMEND_USER, reword, recUser.getId(), "推荐用户注册奖励");
            }
        }

        user.setId(getUserId());
        user.setAvatar(params.getAvatar());
        user.setNick(GeneralUtils.removeAllEmojis(params.getNick()));
        user.setGender(params.getGender());
        user.setAppid(params.getAppId());
        user.setUtime(new Date());
        user.setMobile(params.getMobile());
        if (StringUtils.isNotBlank(params.getPassword())) {
            user.setPassword(MD5Util.shaEncode(params.getPassword()));
            //清除登录token
            //securityService.clearToken(getUserId());
        }
        userService.update(user);

        return FsResponseGen.success();
    }

    /**
     * 密码重置
     */
    @PostMapping("password/reset")
    public FsResponse resetPassword(@RequestBody UserPasswordResetRequest params) {
        params.valid();
        User src = null;
        if (configSet.isMobileAppIdStatus()) {
            src = userService.selectById(getUserId());
        } else {
            if (StringUtils.isNotBlank(params.getMobile())) {
                src = userService.selectByMobile(params.getMobile());
            } else {
                src = userService.selectById(getUserId());
            }

            Contracts.assertNotNull(src, YiChatMsgCode.SYSTEM_ERROR.msg());
            Contracts.assertNotNull(UserStatusEnum.isOpen(src.getStatus()), YiChatMsgCode.SYSTEM_USER_CLOSE.msg());
        }

        //校验原密码
        if (StringUtils.isNotBlank(params.getSrcPassword()) && StringUtils.isNotBlank(src.getPassword())) {
            if (!StringUtils.equals(src.getPassword(), MD5Util.shaEncode(params.getSrcPassword()))) {
                return FsResponseGen.fail(YiChatMsgCode.USE_PASSWORD_SRC_WRONG);
            }
        }

        User user = new User();
        user.setId(src.getId());
        user.setPassword(MD5Util.shaEncode(params.getPassword()));
        userService.update(user);

        //清除登录token
        securityService.clearToken(getUserId());

        return FsResponseGen.success();
    }

    /**
     * 支付密码设置
     */
    @RequestMapping("/pay/password/set")
    public FsResponse payPasswordSet(@RequestBody UserPayPasswordRequest params) {

        params.valid();
        balanceService.updatePassword(getUserId(), params.getPassword());

        return FsResponseGen.successData(params);
    }


    /**
     * 银行卡设置
     */
    @RequestMapping("/bank/card/add")
    public FsResponse cardAdd(@RequestBody UserBankCardAddRequest params) {

        params.valid();

        //校验银行卡
        if (!GeneralUtils.validBankInfo(params.getBankNumber(), params.getIdNumber(), params.getMobile(), params.getName())) {

            return FsResponseGen.successData(YiChatMsgCode.USER_BANK_VALID_FAILED, null);
        }


        UserBank bank = new UserBank();
        bank.setUserId(getUserId());
        bank.setStatus(0);
        bank.setBankName(params.getBankName());
        bank.setBankNumber(params.getBankNumber());
        bank.setName(params.getName());
        //bank.setPassword(params.getPassword());
        bank.setIdNumber(params.getIdNumber());
        bank.setMobile(params.getMobile());

        userService.adBankCard(bank);


        return FsResponseGen.successData(DtoChangeUtils.getList(userService.selectBankList(getUserId()), v -> {
            BankDto dto = new BankDto();
            dto.setBankName(v.getBankName());
            dto.setBankNumber(v.getBankNumber());
            dto.setId(v.getId());
            return dto;
        }));
    }


    /**
     * 银行卡列表
     */
    @RequestMapping("/bank/card/list")
    public FsResponse cardList() {

        return FsResponseGen.successData(DtoChangeUtils.getList(userService.selectBankList(getUserId()), v -> {
            BankDto dto = new BankDto();
            dto.setBankName(v.getBankName());
            dto.setBankNumber(v.getBankNumber());
            dto.setId(v.getId());
            return dto;
        }));
    }

    /**
     * 银行卡删除
     */
    @RequestMapping("/bank/card/delete")
    public FsResponse deleteCard(@RequestBody UserBankCardDeleteRequest params) {

        params.valid();
        userService.deleteBank(params.getCardId());
        return FsResponseGen.successData(DtoChangeUtils.getList(userService.selectBankList(getUserId()), v -> {
            BankDto dto = new BankDto();
            dto.setBankName(v.getBankName());
            dto.setBankNumber(v.getBankNumber());
            dto.setId(v.getId());
            return dto;
        }));
    }


    /**
     * 提现配置
     */
    @RequestMapping("/withdraw/config")
    public FsResponse withdrawConfig() {

        WithdrawConfigDto dto = new WithdrawConfigDto();

        dto.setMinLimit(Optional.ofNullable(sysDictService.selectOne(DictKey.sys_withdraw_money)).map(SysDict::getValue).orElse("0"));
        dto.setRate(Optional.ofNullable(sysDictService.selectOne(DictKey.sys_withdraw_rate)).map(SysDict::getValue).orElse("0"));
        dto.setText(Optional.ofNullable(sysDictService.selectOne(DictKey.sys_withdraw_text)).map(SysDict::getMemo).orElse(""));
        dto.setOpenStatus(Optional.ofNullable(sysDictService.selectOne(DictKey.sys_withdraw_open_status)).map(SysDict::getStatus).orElse(1));

        return FsResponseGen.successData(dto);
    }


    /**
     * 提现申请
     */
    @RequestMapping("/withdraw/apply")
    public FsResponse withdrawApply(@RequestBody UserWithdrawApplyRequest params) {

        params.valid();
        return userWithdrawService.apply(params.getBankNumber(), params.getMemo(), params.getMoney(), getUserId());
    }

    /**
     * 提现申请
     */
    @RequestMapping("/withdraw/list")
    public FsResponse withdrawList(@RequestBody UserWithdrawListRequest params) {

        params.valid();
        return userWithdrawService.selectList(FsPage.init(params.getPageNo(), params.getPageSize()), getUserId());
    }


    @PostMapping("timestamp/update")
    public FsResponse timestamp(@RequestBody MessageTimestampRequest params) {
        params.valid();
        userService.updateTimestamp(getUserId(), params.getTimestamp());
        return FsResponseGen.success();
    }

    //创建群权限检查
    @PostMapping("auth/group/create")
    public FsResponse groupAuth() {
        return FsResponseGen.successData(appConfigService.getCreateGroupAuthStatus(getUserId()));
    }


    @PostMapping("last/login/time")
    public FsResponse lastLoginTime(@RequestBody LastLoginTimeRequest params) {

        params.valid();
        Date loginTime = userService.selectById(params.getUserId()).getLoginTime();
        return FsResponseGen.successData(Optional.ofNullable(loginTime).map(v -> v.getTime() / 1000L).orElse(null));
    }


    //在线时间记录
    @PostMapping("online/record")
    public FsResponse onlineTime() {

        long interval = 5 * 60;

        //首先判断是否有记录数据
        Example example = new Example(UserOnline.class);
        example.createCriteria().andEqualTo("userId", getUserId());

        UserOnline online = userOnlineMapper.selectOneByExample(example);
        if (Objects.nonNull(online)) {


            if (DateUtils.isToday(online.getUtime())) {
                //如果小于5分钟 则按实际描述
                long realS = (System.currentTimeMillis() - online.getUtime().getTime()) / 1000;
                if (realS < interval) {
                    online.setSeconds(online.getSeconds() + realS);
                } else {
                    online.setSeconds(online.getSeconds() + interval);
                }


            } else {//如果更新时间是昨天 则在线描述为5分钟
                online.setTodayStatus(1);

                //如果当前时间没到一天开始的5分钟 则取实际为准
                long realS = (System.currentTimeMillis() - DateUtils.todayMillis()) / 1000;
                if (realS < interval) {
                    online.setSeconds(realS);
                } else {
                    online.setSeconds(interval);
                }


            }

            online.setUtime(new Date());
            userOnlineMapper.updateByPrimaryKeySelective(online);

        } else {

            online = new UserOnline();
            online.setSeconds(interval);
            online.setTodayStatus(1);
            online.setUserId(getUserId());
            online.setUtime(new Date());
            userOnlineMapper.insertSelective(online);
        }

        return FsResponseGen.success();
    }


    /**
     * 我邀请的人
     */
    @PostMapping("/recommend/list")
    public FsResponse search(@RequestBody FsPage page) {


        return userService.selectRecommendList(page, getUserId());
    }


}
