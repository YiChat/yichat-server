package com.zf.yichat.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_role_menu_relation")
public class SysRoleMenuRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "menu_id")
    private Integer menuId;
    /**
     * 角色
     */
    @Column(name = "role_id")
    private Integer roleId;
    private Date ctime;
    private Date utime;

    public SysRoleMenuRelation(Integer menuId, Integer roleId, Date ctime, Date utime) {
        this.menuId = menuId;
        this.roleId = roleId;
        this.ctime = ctime;
        this.utime = utime;
    }

    public SysRoleMenuRelation() {
        super();
    }

    /**
     * @return menu_id
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取角色
     *
     * @return role_id - 角色
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色
     *
     * @param roleId 角色
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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