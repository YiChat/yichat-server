package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 0 单聊 1群聊
     */
    private Integer type;
    /**
     * 红包个数
     */
    private Integer num;
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
     * 0为抢完  1已抢完
     */
    @Column(name = "over_status")
    private Integer overStatus;
    /**
     * 红包金额
     */
    private BigDecimal money;
    private String content;
    /**
     * 创建人
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 群ID
     */
    @Column(name = "group_id")
    private Long groupId;
    private Date ctime;
    private Date utime;

    public Packet(Long id, Integer type, Integer num, Integer userCount, Integer status, Integer overStatus, BigDecimal money, String content, Long userId, Long groupId, Date ctime, Date utime) {
        this.id = id;
        this.type = type;
        this.num = num;
        this.userCount = userCount;
        this.status = status;
        this.overStatus = overStatus;
        this.money = money;
        this.content = content;
        this.userId = userId;
        this.groupId = groupId;
        this.ctime = ctime;
        this.utime = utime;
    }

    public Packet() {
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
     * 获取红包个数
     *
     * @return num - 红包个数
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置红包个数
     *
     * @param num 红包个数
     */
    public void setNum(Integer num) {
        this.num = num;
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
     * 获取0为抢完  1已抢完
     *
     * @return over_status - 0为抢完  1已抢完
     */
    public Integer getOverStatus() {
        return overStatus;
    }

    /**
     * 设置0为抢完  1已抢完
     *
     * @param overStatus 0为抢完  1已抢完
     */
    public void setOverStatus(Integer overStatus) {
        this.overStatus = overStatus;
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
     * 获取创建人
     *
     * @return user_id - 创建人
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置创建人
     *
     * @param userId 创建人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取群ID
     *
     * @return group_id - 群ID
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * 设置群ID
     *
     * @param groupId 群ID
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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