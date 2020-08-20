package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
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
    @Column(name = "recommend_code")
    private String recommendCode;
    /**
     * 推荐时间
     */
    @Column(name = "recommend_time")
    private Date recommendTime;
    private Long timestamp;
    /**
     * 最近登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;
    /**
     * 0无权限  1有权限  是否有建群权限
     */
    @Column(name = "create_group_auth")
    private Integer createGroupAuth;
    /**
     * 二维码ID
     */
    private String qrcode;
    private Date ctime;
    private Date utime;

    public User(Long id, String nick, String areaCode, String mobile, String password, String salt, String token, String avatar, String platform, Integer status, String appid, Integer gender, String ucode, String recommendCode, Date recommendTime, Long timestamp, Date loginTime, Integer createGroupAuth, String qrcode, Date ctime, Date utime) {
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
        this.recommendCode = recommendCode;
        this.recommendTime = recommendTime;
        this.timestamp = timestamp;
        this.loginTime = loginTime;
        this.createGroupAuth = createGroupAuth;
        this.qrcode = qrcode;
        this.ctime = ctime;
        this.utime = utime;
    }

    public User() {
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
     * @return recommend_code
     */
    public String getRecommendCode() {
        return recommendCode;
    }

    /**
     * @param recommendCode
     */
    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode == null ? null : recommendCode.trim();
    }

    /**
     * 获取推荐时间
     *
     * @return recommend_time - 推荐时间
     */
    public Date getRecommendTime() {
        return recommendTime;
    }

    /**
     * 设置推荐时间
     *
     * @param recommendTime 推荐时间
     */
    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }

    /**
     * @return timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取最近登录时间
     *
     * @return login_time - 最近登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置最近登录时间
     *
     * @param loginTime 最近登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取0无权限  1有权限  是否有建群权限
     *
     * @return create_group_auth - 0无权限  1有权限  是否有建群权限
     */
    public Integer getCreateGroupAuth() {
        return createGroupAuth;
    }

    /**
     * 设置0无权限  1有权限  是否有建群权限
     *
     * @param createGroupAuth 0无权限  1有权限  是否有建群权限
     */
    public void setCreateGroupAuth(Integer createGroupAuth) {
        this.createGroupAuth = createGroupAuth;
    }

    /**
     * 获取二维码ID
     *
     * @return qrcode - 二维码ID
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置二维码ID
     *
     * @param qrcode 二维码ID
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
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
}