package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Trend implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String content;
    /**
     * 图片地址逗号分隔
     */
    private String imgs;
    /**
     * 视频地址 多个逗号分隔
     */
    private String videos;
    /**
     * 位置信息
     */
    private String location;
    @Column(name = "user_id")
    private Long userId;
    private Integer status;
    /**
     * 评论数
     */
    @Column(name = "comment_count")
    private Integer commentCount;
    /**
     * 点赞数
     */
    @Column(name = "praise_count")
    private Integer praiseCount;
    private Date ctime;
    private Date utime;

    public Trend(Long id, String content, String imgs, String videos, String location, Long userId, Integer status, Integer commentCount, Integer praiseCount, Date ctime, Date utime) {
        this.id = id;
        this.content = content;
        this.imgs = imgs;
        this.videos = videos;
        this.location = location;
        this.userId = userId;
        this.status = status;
        this.commentCount = commentCount;
        this.praiseCount = praiseCount;
        this.ctime = ctime;
        this.utime = utime;
    }

    public Trend() {
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
     * 获取图片地址逗号分隔
     *
     * @return imgs - 图片地址逗号分隔
     */
    public String getImgs() {
        return imgs;
    }

    /**
     * 设置图片地址逗号分隔
     *
     * @param imgs 图片地址逗号分隔
     */
    public void setImgs(String imgs) {
        this.imgs = imgs == null ? null : imgs.trim();
    }

    /**
     * 获取视频地址 多个逗号分隔
     *
     * @return videos - 视频地址 多个逗号分隔
     */
    public String getVideos() {
        return videos;
    }

    /**
     * 设置视频地址 多个逗号分隔
     *
     * @param videos 视频地址 多个逗号分隔
     */
    public void setVideos(String videos) {
        this.videos = videos == null ? null : videos.trim();
    }

    /**
     * 获取位置信息
     *
     * @return location - 位置信息
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置位置信息
     *
     * @param location 位置信息
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
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
     * 获取评论数
     *
     * @return comment_count - 评论数
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * 设置评论数
     *
     * @param commentCount 评论数
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * 获取点赞数
     *
     * @return praise_count - 点赞数
     */
    public Integer getPraiseCount() {
        return praiseCount;
    }

    /**
     * 设置点赞数
     *
     * @param praiseCount 点赞数
     */
    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
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