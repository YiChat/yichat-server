package com.zf.yichat.dto;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:14 2019/5/31 2019
 */
public class UserSearchDto implements Serializable {
    private Long userId;
    private String avatar;
    private String nick;
    private String appId;
    private Integer gender;
    private Integer friendStatus;
    private String timeDesc;

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
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
