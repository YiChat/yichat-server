package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:39 2019/8/14 2019
 */
public enum TradeType {


    DEPOSIT(0, "充值"),
    SEND_PACKET(1, "发送红包");


    private int val;
    private String desc;
    TradeType(Integer val, String desc) {
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
