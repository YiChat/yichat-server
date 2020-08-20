package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_robot")
public class AppRobot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 机器人ID
     */
    private String uid;
    /**
     * 回调地址
     */
    @Column(name = "callback_url")
    private String callbackUrl;
    /**
     * 关联数据 逗号分隔
     */
    @Column(name = "refer_ids")
    private String referIds;
    /**
     * 关联类型 0个人 1群组
     */
    @Column(name = "refer_type")
    private Integer referType;
    private String token;
    private Integer status;
    @Column(name = "user_id")
    private Long userId;
    private String usernick;
    @Column(name = "user_avatar")
    private String userAvatar;
    /**
     * 激活码
     */
    private String code;
    private Date ctime;
    private Date utime;

    public AppRobot(Long id, String uid, String callbackUrl, String referIds, Integer referType, String token, Integer status, Long userId, String usernick, String userAvatar, String code, Date ctime, Date utime) {
        this.id = id;
        this.uid = uid;
        this.callbackUrl = callbackUrl;
        this.referIds = referIds;
        this.referType = referType;
        this.token = token;
        this.status = status;
        this.userId = userId;
        this.usernick = usernick;
        this.userAvatar = userAvatar;
        this.code = code;
        this.ctime = ctime;
        this.utime = utime;
    }

    public AppRobot() {
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
     * 获取机器人ID
     *
     * @return uid - 机器人ID
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置机器人ID
     *
     * @param uid 机器人ID
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取回调地址
     *
     * @return callback_url - 回调地址
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * 设置回调地址
     *
     * @param callbackUrl 回调地址
     */
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl == null ? null : callbackUrl.trim();
    }

    /**
     * 获取关联数据 逗号分隔
     *
     * @return refer_ids - 关联数据 逗号分隔
     */
    public String getReferIds() {
        return referIds;
    }

    /**
     * 设置关联数据 逗号分隔
     *
     * @param referIds 关联数据 逗号分隔
     */
    public void setReferIds(String referIds) {
        this.referIds = referIds == null ? null : referIds.trim();
    }

    /**
     * 获取关联类型 0个人 1群组
     *
     * @return refer_type - 关联类型 0个人 1群组
     */
    public Integer getReferType() {
        return referType;
    }

    /**
     * 设置关联类型 0个人 1群组
     *
     * @param referType 关联类型 0个人 1群组
     */
    public void setReferType(Integer referType) {
        this.referType = referType;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return usernick
     */
    public String getUsernick() {
        return usernick;
    }

    /**
     * @param usernick
     */
    public void setUsernick(String usernick) {
        this.usernick = usernick == null ? null : usernick.trim();
    }

    /**
     * @return user_avatar
     */
    public String getUserAvatar() {
        return userAvatar;
    }

    /**
     * @param userAvatar
     */
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar == null ? null : userAvatar.trim();
    }

    /**
     * 获取激活码
     *
     * @return code - 激活码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置激活码
     *
     * @param code 激活码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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