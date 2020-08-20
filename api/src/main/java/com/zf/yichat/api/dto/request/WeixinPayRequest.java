package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:26 2019/8/14 2019
 */
public class WeixinPayRequest extends FsRequest {

    private BigDecimal money;

    //0余额充值 1发送红包
    private Integer type;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(money, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(money.compareTo(BigDecimal.ZERO) > 0, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
