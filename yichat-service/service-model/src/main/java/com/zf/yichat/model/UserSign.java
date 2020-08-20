package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_sign")
public class UserSign implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "sign_date")
    private Date signDate;
    /**
     * 获取奖励
     */
    private BigDecimal score;
    private Date ctime;

    public UserSign(Integer id, Long userId, Date signDate, BigDecimal score, Date ctime) {
        this.id = id;
        this.userId = userId;
        this.signDate = signDate;
        this.score = score;
        this.ctime = ctime;
    }

    public UserSign() {
        super();
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
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
     * @return sign_date
     */
    public Date getSignDate() {
        return signDate;
    }

    /**
     * @param signDate
     */
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    /**
     * 获取获取奖励
     *
     * @return score - 获取奖励
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 设置获取奖励
     *
     * @param score 获取奖励
     */
    public void setScore(BigDecimal score) {
        this.score = score;
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