package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

import java.util.Objects;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:51 2019/5/29 2019
 */
public class LoginRequest extends FsRequest {
    private String mobile;
    private String password;
    private Integer platform;
    private String deviceId;
    private Integer type;//空默认账号密码登陆 1验证码登陆

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public void valid() {
        Contracts.assertTrue(StringUtils.isNotBlank(mobile), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(password), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(Objects.nonNull(platform), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
