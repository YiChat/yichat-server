package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Sms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String code;
    /**
     * 100 注册  200修改支付密码
     */
    private String type;
    private String mobile;
    /**
     * 0未验证  1已验证
     */
    private Integer status;
    /**
     * 已校验次数
     */
    @Column(name = "check_time")
    private Integer checkTime;
    private Date ctime;

    public Sms(Long id, String code, String type, String mobile, Integer status, Integer checkTime, Date ctime) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.mobile = mobile;
        this.status = status;
        this.checkTime = checkTime;
        this.ctime = ctime;
    }

    public Sms() {
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
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取100 注册  200修改支付密码
     *
     * @return type - 100 注册  200修改支付密码
     */
    public String getType() {
        return type;
    }

    /**
     * 设置100 注册  200修改支付密码
     *
     * @param type 100 注册  200修改支付密码
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取0未验证  1已验证
     *
     * @return status - 0未验证  1已验证
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0未验证  1已验证
     *
     * @param status 0未验证  1已验证
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取已校验次数
     *
     * @return check_time - 已校验次数
     */
    public Integer getCheckTime() {
        return checkTime;
    }

    /**
     * 设置已校验次数
     *
     * @param checkTime 已校验次数
     */
    public void setCheckTime(Integer checkTime) {
        this.checkTime = checkTime;
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