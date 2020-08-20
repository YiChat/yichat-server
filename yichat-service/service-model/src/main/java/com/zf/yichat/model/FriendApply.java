package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "friend_apply")
public class FriendApply implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 申请好友用户
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 被申请用户
     */
    @Column(name = "friend_id")
    private Long friendId;
    /**
     * 0 当前状态 1删除状态
     */
    private Integer status;
    /**
     * 审核时间
     */
    @Column(name = "check_time")
    private Date checkTime;
    /**
     * 0申请中 1拒绝 2通过
     */
    @Column(name = "check_status")
    private Integer checkStatus;
    /**
     * userID对应的删除状态
     */
    @Column(name = "delete_user_status")
    private Integer deleteUserStatus;
    /**
     * friendId对应的删除状态
     */
    @Column(name = "delete_friend_status")
    private Integer deleteFriendStatus;
    /**
     * 申请理由
     */
    private String reason;
    private Date ctime;

    public FriendApply(Long id, Long userId, Long friendId, Integer status, Date checkTime, Integer checkStatus, Integer deleteUserStatus, Integer deleteFriendStatus, String reason, Date ctime) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
        this.checkTime = checkTime;
        this.checkStatus = checkStatus;
        this.deleteUserStatus = deleteUserStatus;
        this.deleteFriendStatus = deleteFriendStatus;
        this.reason = reason;
        this.ctime = ctime;
    }

    public FriendApply() {
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
     * 获取申请好友用户
     *
     * @return user_id - 申请好友用户
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置申请好友用户
     *
     * @param userId 申请好友用户
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取被申请用户
     *
     * @return friend_id - 被申请用户
     */
    public Long getFriendId() {
        return friendId;
    }

    /**
     * 设置被申请用户
     *
     * @param friendId 被申请用户
     */
    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    /**
     * 获取0 当前状态 1删除状态
     *
     * @return status - 0 当前状态 1删除状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 当前状态 1删除状态
     *
     * @param status 0 当前状态 1删除状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取审核时间
     *
     * @return check_time - 审核时间
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 设置审核时间
     *
     * @param checkTime 审核时间
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 获取0申请中 1拒绝 2通过
     *
     * @return check_status - 0申请中 1拒绝 2通过
     */
    public Integer getCheckStatus() {
        return checkStatus;
    }

    /**
     * 设置0申请中 1拒绝 2通过
     *
     * @param checkStatus 0申请中 1拒绝 2通过
     */
    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * 获取userID对应的删除状态
     *
     * @return delete_user_status - userID对应的删除状态
     */
    public Integer getDeleteUserStatus() {
        return deleteUserStatus;
    }

    /**
     * 设置userID对应的删除状态
     *
     * @param deleteUserStatus userID对应的删除状态
     */
    public void setDeleteUserStatus(Integer deleteUserStatus) {
        this.deleteUserStatus = deleteUserStatus;
    }

    /**
     * 获取friendId对应的删除状态
     *
     * @return delete_friend_status - friendId对应的删除状态
     */
    public Integer getDeleteFriendStatus() {
        return deleteFriendStatus;
    }

    /**
     * 设置friendId对应的删除状态
     *
     * @param deleteFriendStatus friendId对应的删除状态
     */
    public void setDeleteFriendStatus(Integer deleteFriendStatus) {
        this.deleteFriendStatus = deleteFriendStatus;
    }

    /**
     * 获取申请理由
     *
     * @return reason - 申请理由
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置申请理由
     *
     * @param reason 申请理由
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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
}