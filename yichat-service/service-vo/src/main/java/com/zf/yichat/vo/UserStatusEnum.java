package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:17 2019/5/31 2019
 */
public enum UserStatusEnum {
    OPEN(0), CLOSE(1);

    private Integer val;

    UserStatusEnum(Integer val) {
        this.val = val;
    }

    public static boolean isOpen(Integer val) {
        return val.compareTo(OPEN.getVal()) == 0;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
