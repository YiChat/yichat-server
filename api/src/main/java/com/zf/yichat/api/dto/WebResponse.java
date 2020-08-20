package com.zf.yichat.api.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:55 2019/8/27 2019
 */
public class WebResponse<T> {
    private Integer code;
    private T data;

    public WebResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public WebResponse(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
