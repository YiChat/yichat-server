package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "video_comment")
public class VideoComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "video_id")
    private Long videoId;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 顶级评论ID
     */
    @Column(name = "src_comment_id")
    private Long srcCommentId;
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "comment_user_id")
    private Long commentUserId;
    /**
     * 评论内容
     */
    private String content;
    @Column(name = "praise_count")
    private Integer praiseCount;
    /**
     * 回复总数
     */
    @Column(name = "reply_count")
    private Integer replyCount;
    /**
     * 是否被奖励过
     */
    @Column(name = "balance_status")
    private Integer balanceStatus;
    private Integer status;
    private Date ctime;

    public VideoComment(Long id, Long videoId, Long userId, Long srcCommentId, Long commentId, Long commentUserId, String content, Integer praiseCount, Integer replyCount, Integer balanceStatus, Integer status, Date ctime) {
        this.id = id;
        this.videoId = videoId;
        this.userId = userId;
        this.srcCommentId = srcCommentId;
        this.commentId = commentId;
        this.commentUserId = commentUserId;
        this.content = content;
        this.praiseCount = praiseCount;
        this.replyCount = replyCount;
        this.balanceStatus = balanceStatus;
        this.status = status;
        this.ctime = ctime;
    }

    public VideoComment() {
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
     * @return video_id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * @param videoId
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
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
     * 获取顶级评论ID
     *
     * @return src_comment_id - 顶级评论ID
     */
    public Long getSrcCommentId() {
        return srcCommentId;
    }

    /**
     * 设置顶级评论ID
     *
     * @param srcCommentId 顶级评论ID
     */
    public void setSrcCommentId(Long srcCommentId) {
        this.srcCommentId = srcCommentId;
    }

    /**
     * @return comment_id
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * @param commentId
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * @return comment_user_id
     */
    public Long getCommentUserId() {
        return commentUserId;
    }

    /**
     * @param commentUserId
     */
    public void setCommentUserId(Long commentUserId) {
        this.commentUserId = commentUserId;
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
     * @return praise_count
     */
    public Integer getPraiseCount() {
        return praiseCount;
    }

    /**
     * @param praiseCount
     */
    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    /**
     * 获取回复总数
     *
     * @return reply_count - 回复总数
     */
    public Integer getReplyCount() {
        return replyCount;
    }

    /**
     * 设置回复总数
     *
     * @param replyCount 回复总数
     */
    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * 获取是否被奖励过
     *
     * @return balance_status - 是否被奖励过
     */
    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    /**
     * 设置是否被奖励过
     *
     * @param balanceStatus 是否被奖励过
     */
    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
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
}