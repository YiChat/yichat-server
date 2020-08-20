package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "app_robot_code")
public class AppRobotCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String code;
    @Column(name = "limit_day")
    private Integer limitDay;
    /**
     * 0 激活机器人后台激活码  1激活拖激活码
     */
    private Integer type;
    private Date ctime;

    public AppRobotCode(Long id, String code, Integer limitDay, Integer type, Date ctime) {
        this.id = id;
        this.code = code;
        this.limitDay = limitDay;
        this.type = type;
        this.ctime = ctime;
    }

    public AppRobotCode() {
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
     * @return limit_day
     */
    public Integer getLimitDay() {
        return limitDay;
    }

    /**
     * @param limitDay
     */
    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
    }

    /**
     * 获取0 激活机器人后台激活码  1激活拖激活码
     *
     * @return type - 0 激活机器人后台激活码  1激活拖激活码
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0 激活机器人后台激活码  1激活拖激活码
     *
     * @param type 0 激活机器人后台激活码  1激活拖激活码
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