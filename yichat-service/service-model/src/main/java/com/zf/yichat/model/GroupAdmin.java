package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "group_admin")
public class GroupAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 0有效 1删除
     */
    private Integer status;
    private Date ctime;
    private Date utime;

    public GroupAdmin(Integer id, Integer groupId, Long userId, Integer status, Date ctime, Date utime) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.status = status;
        this.ctime = ctime;
        this.utime = utime;
    }

    public GroupAdmin() {
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
     * 获取0有效 1删除
     *
     * @return status - 0有效 1删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0有效 1删除
     *
     * @param status 0有效 1删除
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