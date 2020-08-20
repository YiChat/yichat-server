package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_notice")
public class AppNotice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 0公告 1系统消息
     */
    private Integer type;
    private String title;
    private String content;
    private Integer status;
    /**
     * 群ID
     */
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "user_id")
    private Long userId;
    private Date ctime;

    public AppNotice(Long id, Integer type, String title, String content, Integer status, Integer groupId, Long userId, Date ctime) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = status;
        this.groupId = groupId;
        this.userId = userId;
        this.ctime = ctime;
    }

    public AppNotice() {
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
     * 获取0公告 1系统消息
     *
     * @return type - 0公告 1系统消息
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0公告 1系统消息
     *
     * @param type 0公告 1系统消息
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
     * 获取群ID
     *
     * @return group_id - 群ID
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置群ID
     *
     * @param groupId 群ID
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