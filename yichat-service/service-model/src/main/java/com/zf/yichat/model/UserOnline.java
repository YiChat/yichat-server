package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_online")
public class UserOnline implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 在线秒数
     */
    private Long seconds;
    /**
     * 今天是否开始记录 0否 1是
     */
    @Column(name = "today_status")
    private Integer todayStatus;
    private Date ctime;
    private Date utime;

    public UserOnline(Long id, Long userId, Long seconds, Integer todayStatus, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.seconds = seconds;
        this.todayStatus = todayStatus;
        this.ctime = ctime;
        this.utime = utime;
    }

    public UserOnline() {
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
     * 获取在线秒数
     *
     * @return seconds - 在线秒数
     */
    public Long getSeconds() {
        return seconds;
    }

    /**
     * 设置在线秒数
     *
     * @param seconds 在线秒数
     */
    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

    /**
     * 获取今天是否开始记录 0否 1是
     *
     * @return today_status - 今天是否开始记录 0否 1是
     */
    public Integer getTodayStatus() {
        return todayStatus;
    }

    /**
     * 设置今天是否开始记录 0否 1是
     *
     * @param todayStatus 今天是否开始记录 0否 1是
     */
    public void setTodayStatus(Integer todayStatus) {
        this.todayStatus = todayStatus;
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