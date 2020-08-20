package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_bank")
public class UserBank implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Long userId;
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    private String password;
    /**
     * 身份证号
     */
    @Column(name = "id_number")
    private String idNumber;
    /**
     * 银行
     */
    @Column(name = "bank_name")
    private String bankName;
    /**
     * 银行卡号
     */
    @Column(name = "bank_number")
    private String bankNumber;
    /**
     * 0存在  1删除
     */
    private Integer status;
    private Date ctime;
    private Date utime;

    public UserBank(Integer id, Long userId, String name, String mobile, String password, String idNumber, String bankName, String bankNumber, Integer status, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.idNumber = idNumber;
        this.bankName = bankName;
        this.bankNumber = bankNumber;
        this.status = status;
        this.ctime = ctime;
        this.utime = utime;
    }

    public UserBank() {
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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取身份证号
     *
     * @return id_number - 身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置身份证号
     *
     * @param idNumber 身份证号
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    /**
     * 获取银行
     *
     * @return bank_name - 银行
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置银行
     *
     * @param bankName 银行
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * 获取银行卡号
     *
     * @return bank_number - 银行卡号
     */
    public String getBankNumber() {
        return bankNumber;
    }

    /**
     * 设置银行卡号
     *
     * @param bankNumber 银行卡号
     */
    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber == null ? null : bankNumber.trim();
    }

    /**
     * 获取0存在  1删除
     *
     * @return status - 0存在  1删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0存在  1删除
     *
     * @param status 0存在  1删除
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