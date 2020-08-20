package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:52 2019/6/3 2019
 */
public enum ThirdType {

    WEIXIN(0), QQ(1);

    private Integer val;

    ThirdType(Integer val) {
        this.val = val;
    }

    public static ThirdType valOf(Integer status) {
        for (ThirdType stat : ThirdType.values()) {
            if (stat.getVal().compareTo(status) == 0) {
                return stat;
            }
        }
        return null;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
