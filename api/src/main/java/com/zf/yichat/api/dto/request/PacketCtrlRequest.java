package com.zf.yichat.api.dto.request;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:49 2019/9/25 2019
 */
public class PacketCtrlRequest {

    //0控制红包金额 1控制红包尾数必为几 1控制红包尾数必不为几
    private Integer type;

    //红包金额大小
    private BigDecimal money;

    private String robotIds;

    private Long userId;

    private Integer lastNum;

    public Integer getLastNum() {
        return lastNum;
    }

    public void setLastNum(Integer lastNum) {
        this.lastNum = lastNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRobotIds() {
        return robotIds;
    }

    public void setRobotIds(String robotIds) {
        this.robotIds = robotIds;
    }
}
