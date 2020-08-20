package com.zf.yichat.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_dict_data")
public class SysDictData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    /**
     * 父级code
     */
    private String pcode;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    private String value;
    private Integer status;
    private Integer sort;
    private String memo;
    private Date ctime;
    private Date utime;

    public SysDictData(Integer id, String pcode, String name, String code, String value, Integer status, Integer sort, String memo, Date ctime, Date utime) {
        this.id = id;
        this.pcode = pcode;
        this.name = name;
        this.code = code;
        this.value = value;
        this.status = status;
        this.sort = sort;
        this.memo = memo;
        this.ctime = ctime;
        this.utime = utime;
    }

    public SysDictData() {
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
     * 获取父级code
     *
     * @return pcode - 父级code
     */
    public String getPcode() {
        return pcode;
    }

    /**
     * 设置父级code
     *
     * @param pcode 父级code
     */
    public void setPcode(String pcode) {
        this.pcode = pcode == null ? null : pcode.trim();
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
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
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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