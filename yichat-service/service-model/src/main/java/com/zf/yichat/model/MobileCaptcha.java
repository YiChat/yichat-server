package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "mobile_captcha")
public class MobileCaptcha implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String content;
    /**
     * 过期时间
     */
    @Column(name = "invalid_time")
    private Date invalidTime;
    /**
     * 100 短信验证码
     */
    private Integer type;
    private Date ctime;

    public MobileCaptcha(Long id, String mobile, String content, Date invalidTime, Integer type, Date ctime) {
        this.id = id;
        this.mobile = mobile;
        this.content = content;
        this.invalidTime = invalidTime;
        this.type = type;
        this.ctime = ctime;
    }

    public MobileCaptcha() {
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
     * 获取验证码
     *
     * @return content - 验证码
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置验证码
     *
     * @param content 验证码
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取过期时间
     *
     * @return invalid_time - 过期时间
     */
    public Date getInvalidTime() {
        return invalidTime;
    }

    /**
     * 设置过期时间
     *
     * @param invalidTime 过期时间
     */
    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    /**
     * 获取100 短信验证码
     *
     * @return type - 100 短信验证码
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置100 短信验证码
     *
     * @param type 100 短信验证码
     */
    public void setType(Integer type) {
        this.type = type;
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