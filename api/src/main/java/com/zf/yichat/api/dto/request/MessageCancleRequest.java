package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:17 2019/7/24 2019
 */
public class MessageCancleRequest extends FsRequest {


    private String messageId;

    private String content;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void valid() {
        Contracts.assertNotNull(messageId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());


    }
}
