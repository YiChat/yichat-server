package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_config")
public class AppConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 0创建群权限
     */
    private Integer type;
    private String memo;
    private Date ctime;

    public AppConfig(Long id, Long userId, Integer type, String memo, Date ctime) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.memo = memo;
        this.ctime = ctime;
    }

    public AppConfig() {
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
     * 获取0创建群权限
     *
     * @return type - 0创建群权限
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0创建群权限
     *
     * @param type 0创建群权限
     */
    public void setType(Integer type) {
        this.type = type;
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
}