package com.zf.yichat.dto.im;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:05 2019/5/29 2019
 */
public class ImFieldList {
    private String var;
    private String type;
    private List<String> value;


    public ImFieldList() {
    }

    public ImFieldList(String var, List<String> value) {
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

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
