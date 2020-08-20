package com.zf.yichat.dto;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:37 2020/4/15 2020
 */
public class VideoDto {

    private Long videoId;
    private String content;
    private String path;
    private String thumbnail;
    private Integer seconds;
    private Long userId;
    private String nick;
    private String avatar;
    private Integer praseCount;
    private Integer commentCount;
    private Integer viewCount;
    private BigDecimal rate;

    //是否点赞 0否 1是
    private Integer praiseStatus;

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Integer getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(Integer praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getPraseCount() {
        return praseCount;
    }

    public void setPraseCount(Integer praseCount) {
        this.praseCount = praseCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
