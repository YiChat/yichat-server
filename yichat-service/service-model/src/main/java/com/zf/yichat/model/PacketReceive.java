package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "packet_receive")
public class PacketReceive implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 领取人
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 红包ID
     */
    @Column(name = "packet_id")
    private Long packetId;
    /**
     * 0未领取  1已领取
     */
    private Integer status;
    /**
     * 领取钱数
     */
    private BigDecimal money;
    private Date ctime;
    private Date utime;

    public PacketReceive(Long id, Long userId, Long packetId, Integer status, BigDecimal money, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.packetId = packetId;
        this.status = status;
        this.money = money;
        this.ctime = ctime;
        this.utime = utime;
    }

    public PacketReceive() {
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
     * 获取领取人
     *
     * @return user_id - 领取人
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置领取人
     *
     * @param userId 领取人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取红包ID
     *
     * @return packet_id - 红包ID
     */
    public Long getPacketId() {
        return packetId;
    }

    /**
     * 设置红包ID
     *
     * @param packetId 红包ID
     */
    public void setPacketId(Long packetId) {
        this.packetId = packetId;
    }

    /**
     * 获取0未领取  1已领取
     *
     * @return status - 0未领取  1已领取
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0未领取  1已领取
     *
     * @param status 0未领取  1已领取
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取领取钱数
     *
     * @return money - 领取钱数
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置领取钱数
     *
     * @param money 领取钱数
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
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