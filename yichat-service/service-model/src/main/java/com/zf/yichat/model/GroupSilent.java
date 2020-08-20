package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "group_silent")
public class GroupSilent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 0群成员禁言 1群禁言
     */
    private Integer type;
    private Integer status;
    private Date ctime;
    private Date utime;

    public GroupSilent(Integer id, Integer groupId, Long userId, Integer type, Integer status, Date ctime, Date utime) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.type = type;
        this.status = status;
        this.ctime = ctime;
        this.utime = utime;
    }

    public GroupSilent() {
        super();
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return group_id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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
     * 获取0群成员禁言 1群禁言
     *
     * @return type - 0群成员禁言 1群禁言
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0群成员禁言 1群禁言
     *
     * @param type 0群成员禁言 1群禁言
     */
    public void setType(Integer type) {
        this.type = type;
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