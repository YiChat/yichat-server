package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:45 2019/6/10 2019
 */
public enum GroupRoleType {

    /**
     * 普通成员
     */
    common(0),
    /**
     * 管理员
     */
    amdin(1),
    /**
     * 群主
     */
    creator(2);

    private int val;

    GroupRoleType(Integer val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
