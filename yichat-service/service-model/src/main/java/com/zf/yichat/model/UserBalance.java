package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_balance")
public class UserBalance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 可支配余额
     */
    @Column(name = "income_balance")
    private BigDecimal incomeBalance;
    /**
     * 冻结余额
     */
    @Column(name = "freeze_balance")
    private BigDecimal freezeBalance;
    /**
     * 0 正常 1冻结
     */
    private Integer status;
    /**
     * 支付密码
     */
    private String password;
    private Date ctime;
    private Date utime;

    public UserBalance(Long id, Long userId, BigDecimal balance, BigDecimal incomeBalance, BigDecimal freezeBalance, Integer status, String password, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.incomeBalance = incomeBalance;
        this.freezeBalance = freezeBalance;
        this.status = status;
        this.password = password;
        this.ctime = ctime;
        this.utime = utime;
    }

    public UserBalance() {
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取余额
     *
     * @return balance - 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额
     *
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取可支配余额
     *
     * @return income_balance - 可支配余额
     */
    public BigDecimal getIncomeBalance() {
        return incomeBalance;
    }

    /**
     * 设置可支配余额
     *
     * @param incomeBalance 可支配余额
     */
    public void setIncomeBalance(BigDecimal incomeBalance) {
        this.incomeBalance = incomeBalance;
    }

    /**
     * 获取冻结余额
     *
     * @return freeze_balance - 冻结余额
     */
    public BigDecimal getFreezeBalance() {
        return freezeBalance;
    }

    /**
     * 设置冻结余额
     *
     * @param freezeBalance 冻结余额
     */
    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    /**
     * 获取0 正常 1冻结
     *
     * @return status - 0 正常 1冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 正常 1冻结
     *
     * @param status 0 正常 1冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取支付密码
     *
     * @return password - 支付密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置支付密码
     *
     * @param password 支付密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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