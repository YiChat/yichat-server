package com.zf.yichat.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_dict")
public class SysDict implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    /**
     * 字典名称
     */
    private String name;
    /**
     * 字典编码
     */
    private String code;
    /**
     * 值
     */
    private String value;
    private Integer status;
    private String memo;
    private Date ctime;
    private Date utime;

    public SysDict(Integer id, String name, String code, String value, Integer status, String memo, Date ctime, Date utime) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.value = value;
        this.status = status;
        this.memo = memo;
        this.ctime = ctime;
        this.utime = utime;
    }

    public SysDict() {
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
     * 获取字典名称
     *
     * @return name - 字典名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字典名称
     *
     * @param name 字典名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取字典编码
     *
     * @return code - 字典编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置字典编码
     *
     * @param code 字典编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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