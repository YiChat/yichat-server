package com.zf.yichat.vo;

/**
 * 创建红包金额来源
 *
 * @author yichat
 * @date create in 10:55 2019/7/19 2019
 */
public enum PacketBalanceType {

    balance(0), weixin(1), alipay(2), NULL(null);

    private Integer val;

    PacketBalanceType(Integer val) {
        this.val = val;
    }

    public static PacketBalanceType valOf(Integer val) {


        for (PacketBalanceType type : PacketBalanceType.values()) {

            if (type.getVal().compareTo(val) == 0) {
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
