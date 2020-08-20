package com.zf.yichat.api.dto.resp;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:48 2019/9/3 2019
 */
public class ShareDto {
    private String androidLink;
    private String iosLink;
    private String content;

    public String getAndroidLink() {
        return androidLink;
    }

    public void setAndroidLink(String androidLink) {
        this.androidLink = androidLink;
    }

    public String getIosLink() {
        return iosLink;
    }

    public void setIosLink(String iosLink) {
        this.iosLink = iosLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
