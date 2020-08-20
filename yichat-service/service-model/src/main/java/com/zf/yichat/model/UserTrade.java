package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_trade")
public class UserTrade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 交易订单号
     */
    @Column(name = "trade_no")
    private String tradeNo;
    /**
     * 0充值
     */
    private Integer type;
    /**
     * 0微信 1支付宝
     */
    @Column(name = "pay_type")
    private Integer payType;
    /**
     * 0未支付 1已支付
     */
    private Integer status;
    private BigDecimal money;
    private String memo;
    private Date ctime;
    private Date utime;

    public UserTrade(Long id, Long userId, String tradeNo, Integer type, Integer payType, Integer status, BigDecimal money, String memo, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.tradeNo = tradeNo;
        this.type = type;
        this.payType = payType;
        this.status = status;
        this.money = money;
        this.memo = memo;
        this.ctime = ctime;
        this.utime = utime;
    }

    public UserTrade() {
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
     * 获取交易订单号
     *
     * @return trade_no - 交易订单号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 设置交易订单号
     *
     * @param tradeNo 交易订单号
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * 获取0充值
     *
     * @return type - 0充值
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0充值
     *
     * @param type 0充值
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取0微信 1支付宝
     *
     * @return pay_type - 0微信 1支付宝
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置0微信 1支付宝
     *
     * @param payType 0微信 1支付宝
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取0未支付 1已支付
     *
     * @return status - 0未支付 1已支付
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0未支付 1已支付
     *
     * @param status 0未支付 1已支付
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return money
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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