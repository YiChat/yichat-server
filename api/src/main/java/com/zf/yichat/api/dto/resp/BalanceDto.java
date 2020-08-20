package com.zf.yichat.api.dto.resp;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:16 2019/6/11 2019
 */
public class BalanceDto implements Serializable {
    private String balance;


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
