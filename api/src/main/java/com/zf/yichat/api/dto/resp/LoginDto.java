package com.zf.yichat.api.dto.resp;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:11 2019/5/29 2019
 */
public class LoginDto implements Serializable {
    private Long userId;
    private String imPassword;
    private String nick;
    private String avatar;
    private String appId;
    private String token;
    private Integer gender;
    private String mobile;
    private String ucode;
    private Integer payPasswordStatus;
    //推荐人ID
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

    public Integer getPayPasswordStatus() {
        return payPasswordStatus;
    }

    public void setPayPasswordStatus(Integer payPasswordStatus) {
        this.payPasswordStatus = payPasswordStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
