package com.zf.yichat.dto;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:00 2019/6/14 2019
 */
public class PacketSendInfoDto {

    private Integer count;
    private BigDecimal money;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
