package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 13:45 2019/12/19 2019
 */
public class PayTradeStatusRequest extends FsRequest {

    private String tradeNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(tradeNo, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
