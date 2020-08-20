package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Friend implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 用户好友ID
     */
    @Column(name = "friend_id")
    private Long friendId;
    /**
     * 0 正常 1删除
     */
    private Integer status;
    /**
     * 0新 1旧
     */
    @Column(name = "view_status")
    private Integer viewStatus;
    /**
     * userId对应的好友备注
     */
    @Column(name = "user_mark")
    private String userMark;
    /**
     * friendId用户对应的备注
     */
    @Column(name = "friend_mark")
    private String friendMark;
    private Date ctime;
    private Date utime;

    public Friend(Long id, Long userId, Long friendId, Integer status, Integer viewStatus, String userMark, String friendMark, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.viewStatus = viewStatus;
        this.userMark = userMark;
        this.friendMark = friendMark;
        this.ctime = ctime;
        this.utime = utime;
    }

    public Friend() {
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取用户好友ID
     *
     * @return friend_id - 用户好友ID
     */
    public Long getFriendId() {
        return friendId;
    }

    /**
     * 设置用户好友ID
     *
     * @param friendId 用户好友ID
     */
    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    /**
     * 获取0 正常 1删除
     *
     * @return status - 0 正常 1删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 正常 1删除
     *
     * @param status 0 正常 1删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取0新 1旧
     *
     * @return view_status - 0新 1旧
     */
    public Integer getViewStatus() {
        return viewStatus;
    }

    /**
     * 设置0新 1旧
     *
     * @param viewStatus 0新 1旧
     */
    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }

    /**
     * 获取userId对应的好友备注
     *
     * @return user_mark - userId对应的好友备注
     */
    public String getUserMark() {
        return userMark;
    }

    /**
     * 设置userId对应的好友备注
     *
     * @param userMark userId对应的好友备注
     */
    public void setUserMark(String userMark) {
        this.userMark = userMark == null ? null : userMark.trim();
    }

    /**
     * 获取friendId用户对应的备注
     *
     * @return friend_mark - friendId用户对应的备注
     */
    public String getFriendMark() {
        return friendMark;
    }

    /**
     * 设置friendId用户对应的备注
     *
     * @param friendMark friendId用户对应的备注
     */
    public void setFriendMark(String friendMark) {
        this.friendMark = friendMark == null ? null : friendMark.trim();
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