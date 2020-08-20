package com.zf.yichat.utils;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:59 2019/5/28 2019
 */
public class MobileSplit {
    private String areaCode;
    private String mobile;

    public MobileSplit() {
        this.areaCode = "";
        this.mobile = "";
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
