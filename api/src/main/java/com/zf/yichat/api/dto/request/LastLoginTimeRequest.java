package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:49 2019/12/10 2019
 */
public class LastLoginTimeRequest extends FsRequest {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(userId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
