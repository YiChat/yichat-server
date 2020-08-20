package com.zf.yichat.api.dto.web;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:09 2019/8/27 2019
 */
public class WebUserDto {

    private String appid;
    private String avatar;
    private String city;
    private String imPassword;
    private String nick;
    private String province;
    private Integer sex;
    private String sign;
    private String tel;
    private String userId;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
