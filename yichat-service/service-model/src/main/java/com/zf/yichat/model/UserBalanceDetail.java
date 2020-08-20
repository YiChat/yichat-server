package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_balance_detail")
public class UserBalanceDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 余额表主键ID
     */
    @Column(name = "balance_id")
    private Long balanceId;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 发生金额
     */
    private BigDecimal money;
    @Column(name = "b_balance")
    private BigDecimal bBalance;
    /**
     * 之前可支配金额
     */
    @Column(name = "b_income")
    private BigDecimal bIncome;
    /**
     * 之前可冻结余额
     */
    @Column(name = "b_freeze")
    private BigDecimal bFreeze;
    /**
     * 之后可支配金额
     */
    @Column(name = "a_income")
    private BigDecimal aIncome;
    /**
     * 之后可冻结余额
     */
    @Column(name = "a_freeze")
    private BigDecimal aFreeze;
    @Column(name = "a_balance")
    private BigDecimal aBalance;
    /**
     * 类型   0 充值 1提现 2 创建红包 3领取红包
     */
    private Integer type;
    /**
     * 类型对应的数据ID
     */
    @Column(name = "refer_id")
    private Long referId;
    private String memo;
    private Date ctime;
    private Date utime;

    public UserBalanceDetail(Long id, Long balanceId, Long userId, BigDecimal money, BigDecimal bBalance, BigDecimal bIncome, BigDecimal bFreeze, BigDecimal aIncome, BigDecimal aFreeze, BigDecimal aBalance, Integer type, Long referId, String memo, Date ctime, Date utime) {
        this.id = id;
        this.balanceId = balanceId;
        this.userId = userId;
        this.money = money;
        this.bBalance = bBalance;
        this.bIncome = bIncome;
        this.bFreeze = bFreeze;
        this.aIncome = aIncome;
        this.aFreeze = aFreeze;
        this.aBalance = aBalance;
        this.type = type;
        this.referId = referId;
        this.memo = memo;
        this.ctime = ctime;
        this.utime = utime;
    }

    public UserBalanceDetail() {
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
     * 获取余额表主键ID
     *
     * @return balance_id - 余额表主键ID
     */
    public Long getBalanceId() {
        return balanceId;
    }

    /**
     * 设置余额表主键ID
     *
     * @param balanceId 余额表主键ID
     */
    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
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
     * 获取发生金额
     *
     * @return money - 发生金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置发生金额
     *
     * @param money 发生金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * @return b_balance
     */
    public BigDecimal getbBalance() {
        return bBalance;
    }

    /**
     * @param bBalance
     */
    public void setbBalance(BigDecimal bBalance) {
        this.bBalance = bBalance;
    }

    /**
     * 获取之前可支配金额
     *
     * @return b_income - 之前可支配金额
     */
    public BigDecimal getbIncome() {
        return bIncome;
    }

    /**
     * 设置之前可支配金额
     *
     * @param bIncome 之前可支配金额
     */
    public void setbIncome(BigDecimal bIncome) {
        this.bIncome = bIncome;
    }

    /**
     * 获取之前可冻结余额
     *
     * @return b_freeze - 之前可冻结余额
     */
    public BigDecimal getbFreeze() {
        return bFreeze;
    }

    /**
     * 设置之前可冻结余额
     *
     * @param bFreeze 之前可冻结余额
     */
    public void setbFreeze(BigDecimal bFreeze) {
        this.bFreeze = bFreeze;
    }

    /**
     * 获取之后可支配金额
     *
     * @return a_income - 之后可支配金额
     */
    public BigDecimal getaIncome() {
        return aIncome;
    }

    /**
     * 设置之后可支配金额
     *
     * @param aIncome 之后可支配金额
     */
    public void setaIncome(BigDecimal aIncome) {
        this.aIncome = aIncome;
    }

    /**
     * 获取之后可冻结余额
     *
     * @return a_freeze - 之后可冻结余额
     */
    public BigDecimal getaFreeze() {
        return aFreeze;
    }

    /**
     * 设置之后可冻结余额
     *
     * @param aFreeze 之后可冻结余额
     */
    public void setaFreeze(BigDecimal aFreeze) {
        this.aFreeze = aFreeze;
    }

    /**
     * @return a_balance
     */
    public BigDecimal getaBalance() {
        return aBalance;
    }

    /**
     * @param aBalance
     */
    public void setaBalance(BigDecimal aBalance) {
        this.aBalance = aBalance;
    }

    /**
     * 获取类型   0 充值 1提现 2 创建红包 3领取红包
     *
     * @return type - 类型   0 充值 1提现 2 创建红包 3领取红包
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型   0 充值 1提现 2 创建红包 3领取红包
     *
     * @param type 类型   0 充值 1提现 2 创建红包 3领取红包
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取类型对应的数据ID
     *
     * @return refer_id - 类型对应的数据ID
     */
    public Long getReferId() {
        return referId;
    }

    /**
     * 设置类型对应的数据ID
     *
     * @param referId 类型对应的数据ID
     */
    public void setReferId(Long referId) {
        this.referId = referId;
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