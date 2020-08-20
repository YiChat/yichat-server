package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "trend_config")
public class TrendConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 背景图
     */
    private String img;
    private Date ctime;
    private Date utime;

    public TrendConfig(Long id, Long userId, String img, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.img = img;
        this.ctime = ctime;
        this.utime = utime;
    }

    public TrendConfig() {
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
     * 获取背景图
     *
     * @return img - 背景图
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置背景图
     *
     * @param img 背景图
     */
    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
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