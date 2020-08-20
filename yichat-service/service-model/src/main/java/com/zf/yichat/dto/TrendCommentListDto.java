package com.zf.yichat.dto;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:51 2019/6/6 2019
 */
public class TrendCommentListDto implements Serializable {

    private Long commentId;
    private String content;
    private Long srcUserId;
    private String srcNick;
    private Long userId;
    private String nick;
    private Long trendId;
    private String timeDesc;

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSrcUserId() {
        return srcUserId;
    }

    public void setSrcUserId(Long srcUserId) {
        this.srcUserId = srcUserId;
    }

    public String getSrcNick() {
        return srcNick;
    }

    public void setSrcNick(String srcNick) {
        this.srcNick = srcNick;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Long getTrendId() {
        return trendId;
    }

    public void setTrendId(Long trendId) {
        this.trendId = trendId;
    }
}
