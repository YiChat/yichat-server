package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:06 2019/7/25 2019
 */
public class UserWithdrawApplyRequest extends FsRequest {

    private String bankNumber;
    private String memo;
    private BigDecimal money;

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(money, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
