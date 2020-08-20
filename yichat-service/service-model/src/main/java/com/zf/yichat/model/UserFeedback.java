package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_feedback")
public class UserFeedback implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 0正常 1删除
     */
    private Integer status;
    private Date ctime;

    public UserFeedback(Integer id, String content, Long userId, Integer status, Date ctime) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.status = status;
        this.ctime = ctime;
    }

    public UserFeedback() {
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
     * 获取反馈内容
     *
     * @return content - 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置反馈内容
     *
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
     * 获取0正常 1删除
     *
     * @return status - 0正常 1删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0正常 1删除
     *
     * @param status 0正常 1删除
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