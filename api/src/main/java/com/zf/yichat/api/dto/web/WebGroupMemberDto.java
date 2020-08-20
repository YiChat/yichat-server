package com.zf.yichat.api.dto.web;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:33 2019/8/27 2019
 */
public class WebGroupMemberDto {

    private Long userId;
    private String tel;
    private String nick;
    private String avatar;
    private Integer sex;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
