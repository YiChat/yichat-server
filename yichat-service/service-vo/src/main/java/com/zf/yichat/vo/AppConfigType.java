package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:23 2019/9/25 2019
 */
public enum AppConfigType {
    apply_read_time(1), home_url(2), group_protect_status(2), freeze_ip(10);

    private Integer val;

    AppConfigType(Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
