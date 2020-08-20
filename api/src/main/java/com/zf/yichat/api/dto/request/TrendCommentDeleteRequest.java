package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:44 2019/6/6 2019
 */
public class TrendCommentDeleteRequest extends FsRequest {

    private Long commentId;


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(commentId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
