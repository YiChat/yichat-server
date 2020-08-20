package com.zf.yichat.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:52 2020/4/15 2020
 */
public class VideoCommentReplyDto {

    private String nick;
    private Long commentId;
    private String avatar;
    private Long userId;
    private String timeDesc;
    private Long replyUserId;
    private String replyNick;
    private Long replayCommentId;
    private String content;
    private Integer praiseStatus;
    private Integer praiseCount;


    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyNick() {
        return replyNick;
    }

    public void setReplyNick(String replyNick) {
        this.replyNick = replyNick;
    }

    public Long getReplayCommentId() {
        return replayCommentId;
    }

    public void setReplayCommentId(Long replayCommentId) {
        this.replayCommentId = replayCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
