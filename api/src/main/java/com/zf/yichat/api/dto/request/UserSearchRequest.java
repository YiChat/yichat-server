package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:58 2019/5/31 2019
 */
public class UserSearchRequest extends FsRequest {

    /**
     * 手机号或者appId
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void valid() {

    }
}
