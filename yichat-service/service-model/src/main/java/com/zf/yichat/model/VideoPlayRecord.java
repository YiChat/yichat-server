package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "video_play_record")
public class VideoPlayRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "video_id")
    private Long videoId;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 0未奖励  1奖励
     */
    @Column(name = "balance_status")
    private Integer balanceStatus;
    private Date ctime;

    public VideoPlayRecord(Long id, Long videoId, Long userId, Integer balanceStatus, Date ctime) {
        this.id = id;
        this.videoId = videoId;
        this.userId = userId;
        this.balanceStatus = balanceStatus;
        this.ctime = ctime;
    }

    public VideoPlayRecord() {
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
     * 获取0未奖励  1奖励
     *
     * @return balance_status - 0未奖励  1奖励
     */
    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    /**
     * 设置0未奖励  1奖励
     *
     * @param balanceStatus 0未奖励  1奖励
     */
    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
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