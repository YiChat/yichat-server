package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "trend_praise")
public class TrendPraise implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "trend_id")
    private Long trendId;
    @Column(name = "user_id")
    private Long userId;
    private Integer status;
    private Date ctime;
    private Date utime;

    public TrendPraise(Long id, Long trendId, Long userId, Integer status, Date ctime, Date utime) {
        this.id = id;
        this.trendId = trendId;
        this.userId = userId;
        this.status = status;
        this.ctime = ctime;
        this.utime = utime;
    }

    public TrendPraise() {
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
     * @return trend_id
     */
    public Long getTrendId() {
        return trendId;
    }

    /**
     * @param trendId
     */
    public void setTrendId(Long trendId) {
        this.trendId = trendId;
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