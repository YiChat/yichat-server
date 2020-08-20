package com.zf.yichat.service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:59 2019/6/4 2019
 */
public class FriendListDto implements Serializable {

    private Long userId;
    private String avatar;
    private String nick;
    private String appId;
    private Integer gender;
    private String mobile;
    //唯一码
    private String remark;
    private Date ctime;

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
