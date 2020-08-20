package com.zf.yichat.service.dto;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:04 2019/7/15 2019
 */
public class BalanceListDto {
    private String dateDesc;
    private String ctime;
    private String memo;
    private String moneyDesc;
    private BigDecimal money;

    public String getDateDesc() {
        return dateDesc;
    }

    public void setDateDesc(String dateDesc) {
        this.dateDesc = dateDesc;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMoneyDesc() {
        return moneyDesc;
    }

    public void setMoneyDesc(String moneyDesc) {
        this.moneyDesc = moneyDesc;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
