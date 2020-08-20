package com.zf.yichat.dto;


import com.zf.yichat.model.SysUser;
import com.zf.yichat.model.SysUserRole;

public class UserIndexDto {
    private SysUser user;
    private SysUserRole role;


    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysUserRole getRole() {
        return role;
    }

    public void setRole(SysUserRole role) {
        this.role = role;
    }
}
