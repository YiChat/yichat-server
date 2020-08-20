package com.zf.yichat.api.dto.web;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:47 2019/8/27 2019
 */
public class WebMessageDto {
    private String message;
    private String timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
