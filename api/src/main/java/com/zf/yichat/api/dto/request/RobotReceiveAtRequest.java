package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:55 2019/9/11 2019
 */
public class RobotReceiveAtRequest extends FsRequest {

    private Integer chatType;
    private Long chatId;
    private String atUserIds;
    private String robotId;
    private String userId;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getAtUserIds() {
        return atUserIds;
    }

    public void setAtUserIds(String atUserIds) {
        this.atUserIds = atUserIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void valid() {


    }
}
