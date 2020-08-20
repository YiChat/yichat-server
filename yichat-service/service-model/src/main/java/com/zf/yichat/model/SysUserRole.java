package com.zf.yichat.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_user_role")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    /**
     * 编码
     */
    private String code;
    private String name;
    private Integer status;
    /**
     * 描述
     */
    private String memo;
    private Date ctime;
    private Date utime;

    public SysUserRole(Integer id, String code, String name, Integer status, String memo, Date ctime, Date utime) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.memo = memo;
        this.ctime = ctime;
        this.utime = utime;
    }

    public SysUserRole() {
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
     * 获取编码
     *
     * @return code - 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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
     * 获取描述
     *
     * @return memo - 描述
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置描述
     *
     * @param memo 描述
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