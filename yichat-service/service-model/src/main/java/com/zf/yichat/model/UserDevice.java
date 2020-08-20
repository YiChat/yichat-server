package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_device")
public class UserDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "device_id")
    private String deviceId;
    /**
     * 0 可用 1不可用
     */
    private Integer status;
    private Date ctime;

    public UserDevice(Long id, Long userId, String deviceId, Integer status, Date ctime) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.status = status;
        this.ctime = ctime;
    }

    public UserDevice() {
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
     * @return device_id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    /**
     * 获取0 可用 1不可用
     *
     * @return status - 0 可用 1不可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 可用 1不可用
     *
     * @param status 0 可用 1不可用
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
}