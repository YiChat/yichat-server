package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:39 2019/8/14 2019
 */
public enum PayType {


    SYSTEM(0, "系统"),
    WEIXIN(1, "微信"),
    ALIPAY(2, "支付宝");


    private int val;
    private String desc;
    PayType(Integer val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
