package com.zf.yichat.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:18 2019/6/18 2019
 */
public class UserDto {
    protected Long userId;
    protected String avatar;
    protected String nick;

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
}
