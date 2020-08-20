package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 文字内容
     */
    private String content;
    /**
     * 视频地址
     */
    private String path;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 视频宽高比
     */
    private BigDecimal rate;
    /**
     * 视频秒数
     */
    private Integer seconds;
    /**
     * 点赞数
     */
    @Column(name = "praise_count")
    private Integer praiseCount;
    /**
     * 评论数
     */
    @Column(name = "comment_count")
    private Integer commentCount;
    @Column(name = "view_count")
    private Integer viewCount;
    /**
     * 0 待审核  1通过 2删除
     */
    private Integer status;
    /**
     * 审核时间
     */
    @Column(name = "check_time")
    private Date checkTime;
    /**
     * 0未奖励 1已奖励
     */
    @Column(name = "balance_status")
    private Integer balanceStatus;
    /**
     * 奖励金额
     */
    @Column(name = "balance_money")
    private BigDecimal balanceMoney;
    @Column(name = "user_id")
    private Long userId;
    private Date ctime;

    public Video(Long id, String content, String path, String thumbnail, BigDecimal rate, Integer seconds, Integer praiseCount, Integer commentCount, Integer viewCount, Integer status, Date checkTime, Integer balanceStatus, BigDecimal balanceMoney, Long userId, Date ctime) {
        this.id = id;
        this.content = content;
        this.path = path;
        this.thumbnail = thumbnail;
        this.rate = rate;
        this.seconds = seconds;
        this.praiseCount = praiseCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.status = status;
        this.checkTime = checkTime;
        this.balanceStatus = balanceStatus;
        this.balanceMoney = balanceMoney;
        this.userId = userId;
        this.ctime = ctime;
    }

    public Video() {
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
     * 获取文字内容
     *
     * @return content - 文字内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文字内容
     *
     * @param content 文字内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取视频地址
     *
     * @return path - 视频地址
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置视频地址
     *
     * @param path 视频地址
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取缩略图
     *
     * @return thumbnail - 缩略图
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 设置缩略图
     *
     * @param thumbnail 缩略图
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    /**
     * 获取视频宽高比
     *
     * @return rate - 视频宽高比
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 设置视频宽高比
     *
     * @param rate 视频宽高比
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 获取视频秒数
     *
     * @return seconds - 视频秒数
     */
    public Integer getSeconds() {
        return seconds;
    }

    /**
     * 设置视频秒数
     *
     * @param seconds 视频秒数
     */
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
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
     * @return view_count
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * 获取0 待审核  1通过 2删除
     *
     * @return status - 0 待审核  1通过 2删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 待审核  1通过 2删除
     *
     * @param status 0 待审核  1通过 2删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取审核时间
     *
     * @return check_time - 审核时间
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 设置审核时间
     *
     * @param checkTime 审核时间
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 获取0未奖励 1已奖励
     *
     * @return balance_status - 0未奖励 1已奖励
     */
    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    /**
     * 设置0未奖励 1已奖励
     *
     * @param balanceStatus 0未奖励 1已奖励
     */
    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    /**
     * 获取奖励金额
     *
     * @return balance_money - 奖励金额
     */
    public BigDecimal getBalanceMoney() {
        return balanceMoney;
    }

    /**
     * 设置奖励金额
     *
     * @param balanceMoney 奖励金额
     */
    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
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