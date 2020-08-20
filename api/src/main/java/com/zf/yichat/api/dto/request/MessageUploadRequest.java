package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:41 2019/6/17 2019
 */
public class MessageUploadRequest extends FsRequest {


    private String referId;
    private Integer referType;//0个人 1群
    private String content;
    private long time;
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public Integer getReferType() {
        return referType;
    }

    public void setReferType(Integer referType) {
        this.referType = referType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    @Override
    public void valid() {

        Contracts.assertNotNull(content, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

        Contracts.assertTrue(StringUtils.isNotBlank(this.content), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(this.messageId), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }

}
