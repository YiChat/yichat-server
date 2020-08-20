package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:44 2019/5/30 2019
 */
public enum AppPlatformEnum {
    ios(0), android(1);

    private int val;

    AppPlatformEnum(Integer val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
