package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:57 2019/7/24 2019
 */
public class CheckTokenRequest extends FsRequest {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void valid() {

    }
}
