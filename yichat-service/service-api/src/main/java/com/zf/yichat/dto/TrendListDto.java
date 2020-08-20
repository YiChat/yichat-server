package com.zf.yichat.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:08 2019/6/5 2019
 */
public class TrendListDto implements Serializable {
    private Long trendId;
    private Long userId;
    private String content;
    private String imgs;
    private String videos;
    private String location;
    private String nick;
    private String avatar;
    private Integer praiseCount;
    private Integer commentCount;
    private List<PraiseUser> praiseList;
    private String timeDesc;
    private List<TrendCommentListDto> commentList;

    public List<TrendCommentListDto> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<TrendCommentListDto> commentList) {
        this.commentList = commentList;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public List<PraiseUser> getPraiseList() {
        return praiseList;
    }

    public void setPraiseList(List<PraiseUser> praiseList) {
        this.praiseList = praiseList;
    }

    public Long getTrendId() {
        return trendId;
    }

    public void setTrendId(Long trendId) {
        this.trendId = trendId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public class PraiseUser {

        private Long userId;
        private String nick;

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
    }
}
