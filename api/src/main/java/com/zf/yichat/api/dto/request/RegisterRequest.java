package com.zf.yichat.api.dto.request;

import com.zf.yichat.model.User;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:12 2019/5/28 2019
 */
public class RegisterRequest extends FsRequest {

    private String mobile;
    private String nick;
    private String password;
    private String avatar;
    private String platform;
    private String recommendCode;
    private String deviceId;

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    //参数校验
    @Override
    public void valid() {
        Contracts.assertTrue(StringUtils.isNotBlank(mobile), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        //Contracts.assertTrue(mobile.length() >= 11, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        //Contracts.assertTrue(GeneralUtils.validMobile(ServiceUtils.splitMobile(mobile).getMobile()), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(password), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(nick), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(platform), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(recommendCode), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }


    public User generateUser() {
        User user = new User();
        user.setNick(GeneralUtils.removeAllEmojis(this.nick));
        user.setMobile(this.mobile);
        user.setPassword(this.password);
        user.setAvatar(this.avatar);
        user.setPlatform(this.platform);
        user.setStatus(0);

        return user;
    }
}
