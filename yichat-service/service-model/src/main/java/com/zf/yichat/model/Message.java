package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 0user 1group
     */
    @Column(name = "refer_type")
    private Integer referType;
    @Column(name = "refer_id")
    private Long referId;
    /**
     * 消息时间
     */
    private Long time;
    private Integer status;
    @Column(name = "message_id")
    private String messageId;
    private Date ctime;
    /**
     * 2000
     */
    private String content;
    private String text;

    public Message(Long id, Long userId, Integer referType, Long referId, Long time, Integer status, String messageId, Date ctime, String content, String text) {
        this.id = id;
        this.userId = userId;
        this.referType = referType;
        this.referId = referId;
        this.time = time;
        this.status = status;
        this.messageId = messageId;
        this.ctime = ctime;
        this.content = content;
        this.text = text;
    }

    public Message() {
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
     * 获取0user 1group
     *
     * @return refer_type - 0user 1group
     */
    public Integer getReferType() {
        return referType;
    }

    /**
     * 设置0user 1group
     *
     * @param referType 0user 1group
     */
    public void setReferType(Integer referType) {
        this.referType = referType;
    }

    /**
     * @return refer_id
     */
    public Long getReferId() {
        return referId;
    }

    /**
     * @param referId
     */
    public void setReferId(Long referId) {
        this.referId = referId;
    }

    /**
     * 获取消息时间
     *
     * @return time - 消息时间
     */
    public Long getTime() {
        return time;
    }

    /**
     * 设置消息时间
     *
     * @param time 消息时间
     */
    public void setTime(Long time) {
        this.time = time;
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
     * @return message_id
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
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
     * 获取2000
     *
     * @return content - 2000
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置2000
     *
     * @param content 2000
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }
}