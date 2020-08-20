package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:45 2019/9/4 2019
 */
public class UserFeedbackRequest extends FsRequest {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void valid() {

        Contracts.assertTrue(StringUtils.isNotBlank(content), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
