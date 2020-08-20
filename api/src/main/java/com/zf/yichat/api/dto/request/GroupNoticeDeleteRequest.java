package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:43 2019/9/11 2019
 */
public class GroupNoticeDeleteRequest extends FsRequest {

    private Long noticeId;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(noticeId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
