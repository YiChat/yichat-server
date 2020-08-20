package com.zf.yichat.dto;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:19 2019/7/17 2019
 */
public class GroupDto {

    private Integer groupId;
    private String groupName;
    private String groupDescription;
    private String groupAvatar;
    private Long owner;
    private long crateUnixTime;
    private String version;
    //群成员总数
    private Integer memberCount;
    //群禁言状态 0未禁言  1已禁言
    private Integer groupSilentStatus;
    //请保护模式 0默认不保护 1保护状态
    private Integer protectStatus;


    private List<GroupUserDto> adminList;

    //禁言列表
    private List<GroupUserDto> silentList;

    private List<MessageListDto> lastList;//最新消息

    //用户角色
    private Integer roleType;

    public Integer getGroupSilentStatus() {
        return groupSilentStatus;
    }

    public void setGroupSilentStatus(Integer groupSilentStatus) {
        this.groupSilentStatus = groupSilentStatus;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public List<MessageListDto> getLastList() {
        return lastList;
    }

    public void setLastList(List<MessageListDto> lastList) {
        this.lastList = lastList;
    }

    public List<GroupUserDto> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<GroupUserDto> adminList) {
        this.adminList = adminList;
    }


    public List<GroupUserDto> getSilentList() {
        return silentList;
    }

    public void setSilentList(List<GroupUserDto> silentList) {
        this.silentList = silentList;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public long getCrateUnixTime() {
        return crateUnixTime;
    }

    public void setCrateUnixTime(long crateUnixTime) {
        this.crateUnixTime = crateUnixTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getProtectStatus() {
        return protectStatus;
    }

    public void setProtectStatus(Integer protectStatus) {
        this.protectStatus = protectStatus;
    }
}
