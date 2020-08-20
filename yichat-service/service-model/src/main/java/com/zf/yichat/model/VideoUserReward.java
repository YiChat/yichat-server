package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "video_user_reward")
public class VideoUserReward implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 总奖励
     */
    private BigDecimal reward;
    private Date ctime;

    public VideoUserReward(Long id, Long userId, BigDecimal reward, Date ctime) {
        this.id = id;
        this.userId = userId;
        this.reward = reward;
        this.ctime = ctime;
    }

    public VideoUserReward() {
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
     * 获取总奖励
     *
     * @return reward - 总奖励
     */
    public BigDecimal getReward() {
        return reward;
    }

    /**
     * 设置总奖励
     *
     * @param reward 总奖励
     */
    public void setReward(BigDecimal reward) {
        this.reward = reward;
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