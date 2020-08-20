package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:58 2019/6/11 2019
 */
public enum PacketStatus {
    /**
     * 未领取
     */
    NONE(0),
    /**
     * 已领取
     */
    DOING(1),
    /**
     * 领取完
     */
    OVER(2),
    /**
     * 已超时
     */
    OVER_TIME(3);

    private int val;

    PacketStatus(Integer val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
