package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:45 2019/6/10 2019
 */
public enum AppNoticeType {


    GROUP(0, "群公告"),
    SYSTEM(1, "系统通知");


    private int val;
    private String desc;
    AppNoticeType(Integer val, String desc) {
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
