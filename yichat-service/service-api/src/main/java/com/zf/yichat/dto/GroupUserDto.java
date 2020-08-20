package com.zf.yichat.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:10 2019/7/1 2019
 */
public class GroupUserDto extends UserDto {
    private Integer roleType;
    private Integer friendStatus;//0是 1否

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
    }
}
