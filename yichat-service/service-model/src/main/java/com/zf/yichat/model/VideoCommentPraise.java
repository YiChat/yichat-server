package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "video_comment_praise")
public class VideoCommentPraise implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 评论ID
     */
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "user_id")
    private Long userId;
    private Date ctime;

    public VideoCommentPraise(Long id, Long commentId, Long userId, Date ctime) {
        this.id = id;
        this.commentId = commentId;
        this.userId = userId;
        this.ctime = ctime;
    }

    public VideoCommentPraise() {
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
     * 获取评论ID
     *
     * @return comment_id - 评论ID
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * 设置评论ID
     *
     * @param commentId 评论ID
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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