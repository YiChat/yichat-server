package com.zf.yichat.api.controller.user;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.CheckTokenRequest;
import com.zf.yichat.api.dto.request.LoginRequest;
import com.zf.yichat.api.dto.request.LoginThirdRequest;
import com.zf.yichat.api.dto.resp.LoginDto;
import com.zf.yichat.model.User;
import com.zf.yichat.model.UserIm;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.SecurityService;
import com.zf.yichat.service.SmsService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.ServiceUtils;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.common.MD5Util;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * 登陆
 *
 * @author yichat
 * @date create in 15:10 2019/5/28 2019
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ConfigSet configSet;

    @PostMapping("login")
    @ResponseBody
    public FsResponse login(@RequestBody LoginRequest params) {
        params.valid();
        User user = userService.selectByMobile(params.getMobile());
        if (Objects.isNull(user)) {

            //已经配置可以通过appId登陆
            if (configSet.isSupportAppIdLogin()) {
                // 判断是否存在APPID
                user = userService.selectByAppId(params.getMobile());
            }

            if (Objects.isNull(user)) {
                return FsResponseGen.fail(YiChatMsgCode.LOGIN_USER_NONE);
            }

        }

        //验证短信验证码
        if (Objects.nonNull(params.getType()) && params.getType() == 1) {
            FsResponse re = smsService.check(params.getMobile(), params.getPassword());
            if (!StringUtils.equals(re.getCode(), YiChatMsgCode.SUCCESS)) {
                return re;
            }
        } else {
            //验证密码
            if (!(user.getPassword()).equals(MD5Util.shaEncode(params.getPassword()))) {
                return FsResponseGen.fail(YiChatMsgCode.LOGIN_PASSWORD_ERROR);
            }
        }


        //账号被冻结
        if (user.getStatus().compareTo(FsConst.Status.INVALID) == 0) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_USER_CLOSE);
        }

        //只有设备为空的时候才校验
        if (StringUtils.isNotBlank(params.getDeviceId())) {
            boolean isDeviceClose = Optional.ofNullable(userService.selectDevice(user.getId(), params.getDeviceId())).map(v -> v.getStatus().compareTo(FsConst.Status.INVALID) == 0).orElse(false);
            if (isDeviceClose) {
                return FsResponseGen.fail(YiChatMsgCode.LOGIN_DEVICE_CLOSE);
            }
        }

        //更新用户设备
        userService.updateDevice(user.getId(), params.getDeviceId());

        //登录会更新盐值
        User saltUpdate = new User();
        saltUpdate.setId(user.getId());
        saltUpdate.setSalt(ServiceUtils.generateSalt());
        String token = null;
        try {
            token = securityService.token(saltUpdate.getSalt(), user.getId());
        } catch (Exception e) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_ERROR);
        }
        saltUpdate.setToken(token);
        saltUpdate.setLoginTime(new Date());
        saltUpdate.setPlatform(String.valueOf(params.getPlatform()));
        userService.update(saltUpdate);

        //获取im信息
        UserIm im = userService.selectIm(user.getId());
        Contracts.assertTrue(Objects.nonNull(im), YiChatMsgCode.SYSTEM_USER_ERROR.msg());

        LoginDto data = new LoginDto();
        data.setUcode(user.getUcode());
        data.setAvatar(user.getAvatar());
        data.setNick(user.getNick());
        data.setUserId(user.getId());
        data.setAppId(user.getAppid());
        data.setImPassword(im.getImPassword());
        data.setToken(saltUpdate.getToken());
        data.setGender(user.getGender());
        data.setMobile(user.getMobile());
        //data.setRecUserId(Long.valueOf(user.getRecommendCode()));
        data.setPayPasswordStatus(StringUtils.isNotBlank(balanceService.selectByUserId(user.getId()).getPassword()) ? 1 : 0);
        return FsResponseGen.successData(data);
    }

    @PostMapping("login/third")
    @ResponseBody
    public FsResponse login(@RequestBody LoginThirdRequest params) {
        params.valid();

        return FsResponseGen.fail();

        //检查是否已存在对应账号
        //1.先根据openId去查询uniqueCode  老数据默认都是openId
        //2.查询不到则用unionId查询
//        User user = null;
//        if (StringUtils.isNotBlank(params.getOpenId()) || StringUtils.isNotBlank(params.getUniqueCode())) {
//            user = userService.selectByThirdAccount(params.getOpenId(), params.getUniqueCode());
//        }
//
//        if (Objects.nonNull(user) && Objects.nonNull(user.getId())) {
//            //账号被冻结
//            if (user.getStatus().compareTo(FsConst.Status.INVALID) == 0) {
//                return FsResponseGen.fail(YiChatMsgCode.SYSTEM_USER_CLOSE);
//            }
//        } else {
//            userService.addThird(params.getNick(), params.getAvatar(),params.getOpenId(), params.getUniqueCode(), ThirdType.valOf(params.getType()));
//            user = userService.selectByThirdAccount(params.getOpenId(), params.getUniqueCode());
//        }
//
//        if (StringUtils.isNotBlank(params.getDeviceId()) ) {
//            boolean isDeviceClose = Optional.ofNullable(userService.selectDevice(user.getId(), params.getDeviceId())).map(v -> v.getStatus().compareTo(FsConst.Status.INVALID) == 0).orElse(false);
//            if (isDeviceClose) {
//                return FsResponseGen.fail(YiChatMsgCode.LOGIN_DEVICE_CLOSE);
//            }
//        }
//
//        //更新用户设备
//        userService.updateDevice(user.getId(), params.getDeviceId());
//
//        //登录会更新盐值
//        User saltUpdate = new User();
//        saltUpdate.setPlatform(params.getPlatform());
//        saltUpdate.setId(user.getId());
//        saltUpdate.setSalt(ServiceUtils.generateSalt());
//        saltUpdate.setAvatar(params.getAvatar());
//        String token = null;
//        try {
//            token = securityService.token(saltUpdate.getSalt(), user.getId());
//        } catch (Exception e) {
//            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_ERROR);
//        }
//        saltUpdate.setToken(token);
//        saltUpdate.setLoginTime(new Date());
//        userService.update(saltUpdate);
//
//
//
//        Contracts.assertNotNull(user, YiChatMsgCode.SYSTEM_ERROR.msg());
//        UserIm userIm = userService.selectIm(user.getId());
//        Contracts.assertNotNull(userIm, YiChatMsgCode.SYSTEM_ERROR.msg());
//
//        LoginDto data = new LoginDto();
//        data.setAvatar(user.getAvatar());
//        data.setNick(user.getNick());
//        data.setUserId(user.getId());
//        data.setAppId(user.getAppid());
//        data.setImPassword(userIm.getImPassword());
//        data.setToken(saltUpdate.getToken());
//        data.setGender(user.getGender());
//        data.setMobile(user.getMobile());
//        data.setUcode(user.getUcode());
//        data.setPayPasswordStatus(StringUtils.isNotBlank(balanceService.selectByUserId(user.getId()).getPassword()) ? 1 : 0);
        //return FsResponseGen.successData(data);


    }


    @PostMapping("login/out")
    @ResponseBody
    public FsResponse loginOut() {
        securityService.clearToken(getUserId());
        return FsResponseGen.success();

    }

    @PostMapping("check/token")
    @ResponseBody
    public FsResponse tokenCheck(@RequestBody CheckTokenRequest request) {

        return FsResponseGen.successData(securityService.validToken(request.getToken()) ? "0" : "1");

    }


    /**
     * 绑定二维码
     */
    @PostMapping("login/bind/qrcode")
    @ResponseBody
    public FsResponse bindQrcode() {

        return FsResponseGen.success();

    }

}
