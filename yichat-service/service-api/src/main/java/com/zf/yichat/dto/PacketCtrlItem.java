package com.zf.yichat.dto;

import java.math.BigDecimal;

/**
 * 红包控制单元参数方案一
 *
 * @author yichat
 * @date create in 10:06 2019/9/25 2019
 */
public class PacketCtrlItem {

    private Integer type;

    private Long userId;

    private BigDecimal money;

    private Integer lastnum;

    public Integer getLastnum() {
        return lastnum;
    }

    public void setLastnum(Integer lastnum) {
        this.lastnum = lastnum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
