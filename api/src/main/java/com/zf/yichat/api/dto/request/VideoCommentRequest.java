package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:30 2020/4/15 2020
 */
public class VideoCommentRequest extends FsRequest {

    private String content;
    private Long commentId;
    private Long videoId;
    private Long srcCommentId;

    public Long getSrcCommentId() {
        return srcCommentId;
    }

    public void setSrcCommentId(Long srcCommentId) {
        this.srcCommentId = srcCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }


    @Override
    public void valid() {
        Contracts.assertNotNull(videoId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
