package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:10 2020/4/28 2020
 */
public class VideoCommentDeleteRequest extends FsRequest {

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
