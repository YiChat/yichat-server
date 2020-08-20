package com.zf.yichat.api.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:30 2019/9/11 2019
 */
public class RobotResponse<T> {

    private Integer code;
    private T data;

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
