package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:34 2019/7/30 2019
 */
public class MessageTimestampRequest extends FsRequest {


    private Long timestamp;


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(timestamp, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
