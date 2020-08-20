package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "video_view")
public class VideoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "video_id")
    private Long videoId;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 浏览次数
     */
    private Long count;
    private Date ctime;

    public VideoView(Long id, Long videoId, Long userId, Long count, Date ctime) {
        this.id = id;
        this.videoId = videoId;
        this.userId = userId;
        this.count = count;
        this.ctime = ctime;
    }

    public VideoView() {
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
     * 获取浏览次数
     *
     * @return count - 浏览次数
     */
    public Long getCount() {
        return count;
    }

    /**
     * 设置浏览次数
     *
     * @param count 浏览次数
     */
    public void setCount(Long count) {
        this.count = count;
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