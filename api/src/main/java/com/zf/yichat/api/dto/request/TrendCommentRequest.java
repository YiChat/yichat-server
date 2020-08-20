package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:41 2019/6/6 2019
 */
public class TrendCommentRequest extends FsRequest {

    private String content;
    private Long trendId;
    private Long commentId;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTrendId() {
        return trendId;
    }

    public void setTrendId(Long trendId) {
        this.trendId = trendId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public void valid() {

        Contracts.assertTrue(StringUtils.isNotBlank(content), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(trendId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
