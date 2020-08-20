package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:55 2019/9/11 2019
 */
public class RobotReceiveImageRequest extends FsRequest {

    private Integer chatType;
    private Long chatId;
    private String path;
    private String fileName;
    private String width;
    private String height;
    private String userId;
    private String robotId;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    @Override
    public void valid() {


    }
}
