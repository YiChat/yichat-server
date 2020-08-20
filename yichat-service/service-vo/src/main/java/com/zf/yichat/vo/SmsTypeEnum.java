package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:59 2019/7/15 2019
 */
public enum SmsTypeEnum {
    //注册
    REGISTER(100),
    //支付密码修改
    PAY_PASSWORD(200);

    private Integer type;

    SmsTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
