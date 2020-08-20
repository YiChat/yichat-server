package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "red_packet")
public class RedPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 0 单聊 1群聊
     */
    private Integer type;
    /**
     * 人数
     */
    @Column(name = "user_count")
    private Integer userCount;
    /**
     * 0 创建  1已领取  2已领完
     */
    private Integer status;
    /**
     * 红包金额
     */
    private BigDecimal money;
    private String content;
    private Date ctime;
    private Date utime;

    public RedPacket(Long id, Integer type, Integer userCount, Integer status, BigDecimal money, String content, Date ctime, Date utime) {
        this.id = id;
        this.type = type;
        this.userCount = userCount;
        this.status = status;
        this.money = money;
        this.content = content;
        this.ctime = ctime;
        this.utime = utime;
    }

    public RedPacket() {
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
     * 获取0 单聊 1群聊
     *
     * @return type - 0 单聊 1群聊
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0 单聊 1群聊
     *
     * @param type 0 单聊 1群聊
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取人数
     *
     * @return user_count - 人数
     */
    public Integer getUserCount() {
        return userCount;
    }

    /**
     * 设置人数
     *
     * @param userCount 人数
     */
    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    /**
     * 获取0 创建  1已领取  2已领完
     *
     * @return status - 0 创建  1已领取  2已领完
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 创建  1已领取  2已领完
     *
     * @param status 0 创建  1已领取  2已领完
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取红包金额
     *
     * @return money - 红包金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置红包金额
     *
     * @param money 红包金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
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