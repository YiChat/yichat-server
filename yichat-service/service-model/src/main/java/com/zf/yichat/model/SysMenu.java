package com.zf.yichat.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_menu")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    /**
     * 父级ID
     */
    private Integer pid;
    /**
     * 菜单名称
     */
    private String name;
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 菜单路径
     */
    private String path;
    private String icon;
    private String memo;
    private Date ctime;
    private Date utime;

    public SysMenu(Integer id, Integer pid, String name, Integer status, Integer sort, String path, String icon, String memo, Date ctime, Date utime) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.status = status;
        this.sort = sort;
        this.path = path;
        this.icon = icon;
        this.memo = memo;
        this.ctime = ctime;
        this.utime = utime;
    }

    public SysMenu() {
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
     * 获取父级ID
     *
     * @return pid - 父级ID
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父级ID
     *
     * @param pid 父级ID
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取菜单名称
     *
     * @return name - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
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
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取菜单路径
     *
     * @return path - 菜单路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置菜单路径
     *
     * @param path 菜单路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
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