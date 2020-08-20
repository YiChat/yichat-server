package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:52 2019/6/3 2019
 */
public enum FriendCheckStatus {

    APPLY(0), PASS(1), REFUSE(2), DELETE(3);

    private Integer val;

    FriendCheckStatus(Integer val) {
        this.val = val;
    }

    public static FriendCheckStatus valOf(Integer status) {
        for (FriendCheckStatus stat : FriendCheckStatus.values()) {
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
