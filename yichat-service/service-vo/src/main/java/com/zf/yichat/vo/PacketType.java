package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:44 2019/5/30 2019
 */
public enum PacketType {


    single(0), group(1), NULL(null);

    private Integer val;

    PacketType(Integer val) {
        this.val = val;
    }

    public static PacketType valOf(Integer val) {

        if (val == null) {
            return NULL;
        }
        for (PacketType type : PacketType.values()) {

            if (type.getVal() == val) {
                return type;
            }
        }

        return NULL;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
