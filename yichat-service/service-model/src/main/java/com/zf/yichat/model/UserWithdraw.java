package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_withdraw")
public class UserWithdraw implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 0提交申请  1审核通过  2拒绝申请
     */
    private Integer status;
    @Column(name = "bank_number")
    private String bankNumber;
    private String memo;
    private BigDecimal money;
    /**
     * 审核时间
     */
    @Column(name = "check_time")
    private Date checkTime;
    /**
     * 拒绝原因
     */
    @Column(name = "refuse_reason")
    private String refuseReason;
    private Date ctime;
    private Date utime;

    public UserWithdraw(Integer id, Long userId, Integer status, String bankNumber, String memo, BigDecimal money, Date checkTime, String refuseReason, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.bankNumber = bankNumber;
        this.memo = memo;
        this.money = money;
        this.checkTime = checkTime;
        this.refuseReason = refuseReason;
        this.ctime = ctime;
        this.utime = utime;
    }

    public UserWithdraw() {
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
     * 获取0提交申请  1审核通过  2拒绝申请
     *
     * @return status - 0提交申请  1审核通过  2拒绝申请
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0提交申请  1审核通过  2拒绝申请
     *
     * @param status 0提交申请  1审核通过  2拒绝申请
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return bank_number
     */
    public String getBankNumber() {
        return bankNumber;
    }

    /**
     * @param bankNumber
     */
    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber == null ? null : bankNumber.trim();
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
     * 获取审核时间
     *
     * @return check_time - 审核时间
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * 设置审核时间
     *
     * @param checkTime 审核时间
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 获取拒绝原因
     *
     * @return refuse_reason - 拒绝原因
     */
    public String getRefuseReason() {
        return refuseReason;
    }

    /**
     * 设置拒绝原因
     *
     * @param refuseReason 拒绝原因
     */
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
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