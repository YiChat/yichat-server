package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "message_del_time")
public class MessageDelTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 根据类型 显示是群还是用户
     */
    @Column(name = "refer_id")
    private Long referId;
    /**
     * 0用户 1群
     */
    @Column(name = "refer_type")
    private Integer referType;
    /**
     * 删除时间节点
     */
    @Column(name = "del_time")
    private Long delTime;
    private Date ctime;
    private Date utime;

    public MessageDelTime(Long id, Long userId, Long referId, Integer referType, Long delTime, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.referId = referId;
        this.referType = referType;
        this.delTime = delTime;
        this.ctime = ctime;
        this.utime = utime;
    }

    public MessageDelTime() {
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
     * 获取根据类型 显示是群还是用户
     *
     * @return refer_id - 根据类型 显示是群还是用户
     */
    public Long getReferId() {
        return referId;
    }

    /**
     * 设置根据类型 显示是群还是用户
     *
     * @param referId 根据类型 显示是群还是用户
     */
    public void setReferId(Long referId) {
        this.referId = referId;
    }

    /**
     * 获取0用户 1群
     *
     * @return refer_type - 0用户 1群
     */
    public Integer getReferType() {
        return referType;
    }

    /**
     * 设置0用户 1群
     *
     * @param referType 0用户 1群
     */
    public void setReferType(Integer referType) {
        this.referType = referType;
    }

    /**
     * 获取删除时间节点
     *
     * @return del_time - 删除时间节点
     */
    public Long getDelTime() {
        return delTime;
    }

    /**
     * 设置删除时间节点
     *
     * @param delTime 删除时间节点
     */
    public void setDelTime(Long delTime) {
        this.delTime = delTime;
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