package com.zf.yichat.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:20 2019/7/19 2019
 */
public class UserStg {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 区号  前缀
     */
    @Column(name = "area_code")
    private String areaCode;
    /**
     * 手机号
     */
    private String mobile;
    private String password;
    private String salt;
    /**
     * 加密后token
     */
    private String token;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 用户注册平台  iOs  android
     */
    private String platform;
    /**
     * 0正常 1删除/冻结
     */
    private Integer status;
    /**
     * 微信ID
     */
    @Column(name = "appId")
    private String appid;
    /**
     * 0 女 1男
     */
    private Integer gender;
    /**
     * 唯一码
     */
    private String ucode;
    private Date ctime;
    private Date utime;
    private Date time;
    private String remark;

    public UserStg(Long id, String nick, String areaCode, String mobile, String password, String salt, String token, String avatar, String platform, Integer status, String appid, Integer gender, String ucode, Date ctime, Date utime, String remark, Date time) {
        this.id = id;
        this.nick = nick;
        this.areaCode = areaCode;
        this.mobile = mobile;
        this.password = password;
        this.salt = salt;
        this.token = token;
        this.avatar = avatar;
        this.platform = platform;
        this.status = status;
        this.appid = appid;
        this.gender = gender;
        this.ucode = ucode;
        this.ctime = ctime;
        this.utime = utime;
        this.remark = remark;
        this.time = time;
    }

    public UserStg() {
        super();
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取昵称
     *
     * @return nick - 昵称
     */
    public String getNick() {
        return nick;
    }

    /**
     * 设置昵称
     *
     * @param nick 昵称
     */
    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    /**
     * 获取区号  前缀
     *
     * @return area_code - 区号  前缀
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置区号  前缀
     *
     * @param areaCode 区号  前缀
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取加密后token
     *
     * @return token - 加密后token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置加密后token
     *
     * @param token 加密后token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取头像路径
     *
     * @return avatar - 头像路径
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像路径
     *
     * @param avatar 头像路径
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取用户注册平台  iOs  android
     *
     * @return platform - 用户注册平台  iOs  android
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置用户注册平台  iOs  android
     *
     * @param platform 用户注册平台  iOs  android
     */
    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    /**
     * 获取0正常 1删除/冻结
     *
     * @return status - 0正常 1删除/冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0正常 1删除/冻结
     *
     * @param status 0正常 1删除/冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取微信ID
     *
     * @return appId - 微信ID
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置微信ID
     *
     * @param appid 微信ID
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 获取0 女 1男
     *
     * @return gender - 0 女 1男
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置0 女 1男
     *
     * @param gender 0 女 1男
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取唯一码
     *
     * @return ucode - 唯一码
     */
    public String getUcode() {
        return ucode;
    }

    /**
     * 设置唯一码
     *
     * @param ucode 唯一码
     */
    public void setUcode(String ucode) {
        this.ucode = ucode == null ? null : ucode.trim();
    }

    /**
     * @return ctime
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * @return utime
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * @param utime
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
