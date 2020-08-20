package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_im")
public class UserIm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "im_password")
    private String imPassword;
    private Date ctime;
    private Date utime;

    public UserIm(Long id, Long userId, String imPassword, Date ctime, Date utime) {
        this.id = id;
        this.userId = userId;
        this.imPassword = imPassword;
        this.ctime = ctime;
        this.utime = utime;
    }

    public UserIm() {
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
     * @return im_password
     */
    public String getImPassword() {
        return imPassword;
    }

    /**
     * @param imPassword
     */
    public void setImPassword(String imPassword) {
        this.imPassword = imPassword == null ? null : imPassword.trim();
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