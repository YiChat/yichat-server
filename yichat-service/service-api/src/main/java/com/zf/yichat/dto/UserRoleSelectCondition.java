package com.zf.yichat.dto;


import com.zf.yichat.vo.UserRoleEnum;

/**
 * 角色查询
 */
public class UserRoleSelectCondition {

    private Integer companyId;
    private Integer userId;

    private UserRoleEnum role;

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
