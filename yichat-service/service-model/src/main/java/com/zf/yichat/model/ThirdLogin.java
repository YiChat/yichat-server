package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "third_login")
public class ThirdLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /**
     * 0微信  1qq
     */
    private Integer type;
    /**
     * 识别码
     */
    @Column(name = "unique_code")
    private String uniqueCode;
    /**
     * 微信unionId
     */
    @Column(name = "union_id")
    private String unionId;
    /**
     * 用户ID
     */
    @Column(name = "userId")
    private Long userid;
    private Integer status;
    private Date ctime;

    public ThirdLogin(Long id, Integer type, String uniqueCode, String unionId, Long userid, Integer status, Date ctime) {
        this.id = id;
        this.type = type;
        this.uniqueCode = uniqueCode;
        this.unionId = unionId;
        this.userid = userid;
        this.status = status;
        this.ctime = ctime;
    }

    public ThirdLogin() {
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
     * 获取0微信  1qq
     *
     * @return type - 0微信  1qq
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0微信  1qq
     *
     * @param type 0微信  1qq
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取识别码
     *
     * @return unique_code - 识别码
     */
    public String getUniqueCode() {
        return uniqueCode;
    }

    /**
     * 设置识别码
     *
     * @param uniqueCode 识别码
     */
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode == null ? null : uniqueCode.trim();
    }

    /**
     * 获取微信unionId
     *
     * @return union_id - 微信unionId
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * 设置微信unionId
     *
     * @param unionId 微信unionId
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * 获取用户ID
     *
     * @return userId - 用户ID
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * 设置用户ID
     *
     * @param userid 用户ID
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
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
}