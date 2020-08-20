package com.zf.yichat.vo;


import org.apache.commons.lang.StringUtils;

/**
 * 用户角色
 *
 * @author yichat
 */
public enum UserRoleEnum {
    /**
     * 超级管理员
     */
    ADMIN("100", "超级管理员"),
    COMPANY_ADMIN("200", "公司管理员"),
    SALESMAN("300", "业务员"),
    NULL("", "");

    private String code;
    private String desc;

    UserRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserRoleEnum getRole(String code) {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            if (StringUtils.equals(code, role.getCode())) {
                return role;
            }
        }

        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
