package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:49 2019/9/26 2019
 */
public class RobotPacketReceiveRequest {
    private Long packetId;
    private Long userId;
    private String chatId;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Long getPacketId() {
        return packetId;
    }

    public void setPacketId(Long packetId) {
        this.packetId = packetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
