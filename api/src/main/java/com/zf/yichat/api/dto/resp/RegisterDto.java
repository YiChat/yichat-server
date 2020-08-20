package com.zf.yichat.api.dto.resp;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:49 2019/5/29 2019
 */
public class RegisterDto implements Serializable {

    private String imPassword;
    private String token;
    private Long userId;
    private String mobile;
    //唯一码
    private String appId;
    private String ucode;
    private Long recUserId;

    public Long getRecUserId() {
        return recUserId;
    }

    public void setRecUserId(Long recUserId) {
        this.recUserId = recUserId;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImPassword() {
        return imPassword;
    }

    public void setImPassword(String imPassword) {
        this.imPassword = imPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
