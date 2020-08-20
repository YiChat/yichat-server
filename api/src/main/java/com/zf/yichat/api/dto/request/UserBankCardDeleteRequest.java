package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:19 2019/7/25 2019
 */
public class UserBankCardDeleteRequest extends FsRequest {

    private Integer cardId;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(cardId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
