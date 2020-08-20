package com.zf.yichat.api.dto;

import com.zf.yichat.api.dto.web.WebUserDto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:55 2019/8/27 2019
 */
public class WebUserResponse {
    private Integer code;
    private WebUserDto user;

    public WebUserResponse(Integer code, WebUserDto data) {
        this.code = code;
        this.user = data;
    }

    public WebUserResponse(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public WebUserDto getUser() {
        return user;
    }

    public void setUser(WebUserDto user) {
        this.user = user;
    }
}
