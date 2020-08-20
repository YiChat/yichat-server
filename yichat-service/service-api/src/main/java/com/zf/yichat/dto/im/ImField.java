package com.zf.yichat.dto.im;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:05 2019/5/29 2019
 */
public class ImField {
    private String var;
    private String type;
    private String value;


    public ImField() {
    }

    public ImField(String var, String value) {
        this.var = var;
        this.value = value;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
