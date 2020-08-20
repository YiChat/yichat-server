package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "trend_comment")
public class TrendComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 动态ID
     */
    @Column(name = "trend_id")
    private Long trendId;
    /**
     * 被回复的评论
     */
    @Column(name = "comment_id")
    private Long commentId;
    /**
     * 被回复人ID
     */
    @Column(name = "src_user_id")
    private Long srcUserId;
    /**
     * 评论人ID
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 评论内容
     */
    private String content;
    private Integer status;
    private Date ctime;
    private Date utime;

    public TrendComment(Long id, Long trendId, Long commentId, Long srcUserId, Long userId, String content, Integer status, Date ctime, Date utime) {
        this.id = id;
        this.trendId = trendId;
        this.commentId = commentId;
        this.srcUserId = srcUserId;
        this.userId = userId;
        this.content = content;
        this.status = status;
        this.ctime = ctime;
        this.utime = utime;
    }

    public TrendComment() {
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
     * 获取动态ID
     *
     * @return trend_id - 动态ID
     */
    public Long getTrendId() {
        return trendId;
    }

    /**
     * 设置动态ID
     *
     * @param trendId 动态ID
     */
    public void setTrendId(Long trendId) {
        this.trendId = trendId;
    }

    /**
     * 获取被回复的评论
     *
     * @return comment_id - 被回复的评论
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * 设置被回复的评论
     *
     * @param commentId 被回复的评论
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * 获取被回复人ID
     *
     * @return src_user_id - 被回复人ID
     */
    public Long getSrcUserId() {
        return srcUserId;
    }

    /**
     * 设置被回复人ID
     *
     * @param srcUserId 被回复人ID
     */
    public void setSrcUserId(Long srcUserId) {
        this.srcUserId = srcUserId;
    }

    /**
     * 获取评论人ID
     *
     * @return user_id - 评论人ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置评论人ID
     *
     * @param userId 评论人ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
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