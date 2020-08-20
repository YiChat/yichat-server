package com.zf.yichat.utils.response;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:48 2019/5/28 2019
 */
public class MsgCode implements Serializable {

    private String code;
    private String msg;
    private boolean success;

    public MsgCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MsgCode(String code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return this.code + "=" + this.msg + "=" + (success ? 0 : 1);
    }
}
