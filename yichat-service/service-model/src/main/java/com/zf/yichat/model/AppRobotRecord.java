package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_robot_record")
public class AppRobotRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 消息ID
     */
    @Column(name = "message_id")
    private String messageId;
    /**
     * 0 发送失败  1已再次发送成功
     */
    private Integer status;
    @Column(name = "success_time")
    private Date successTime;
    /**
     * 机器人ID
     */
    @Column(name = "robot_id")
    private Long robotId;
    private Date ctime;

    public AppRobotRecord(Long id, String messageId, Integer status, Date successTime, Long robotId, Date ctime) {
        this.id = id;
        this.messageId = messageId;
        this.status = status;
        this.successTime = successTime;
        this.robotId = robotId;
        this.ctime = ctime;
    }

    public AppRobotRecord() {
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
     * 获取消息ID
     *
     * @return message_id - 消息ID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 设置消息ID
     *
     * @param messageId 消息ID
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    /**
     * 获取0 发送失败  1已再次发送成功
     *
     * @return status - 0 发送失败  1已再次发送成功
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 发送失败  1已再次发送成功
     *
     * @param status 0 发送失败  1已再次发送成功
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return success_time
     */
    public Date getSuccessTime() {
        return successTime;
    }

    /**
     * @param successTime
     */
    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    /**
     * 获取机器人ID
     *
     * @return robot_id - 机器人ID
     */
    public Long getRobotId() {
        return robotId;
    }

    /**
     * 设置机器人ID
     *
     * @param robotId 机器人ID
     */
    public void setRobotId(Long robotId) {
        this.robotId = robotId;
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