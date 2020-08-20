package com.zf.yichat.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:47 2019/7/30 2019
 */
public class MessageUnreadDto {
    private Integer groupId;
    private Integer unreadCount;
    private MessageListDto lastmessage;

    public MessageListDto getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(MessageListDto lastmessage) {
        this.lastmessage = lastmessage;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }
}
