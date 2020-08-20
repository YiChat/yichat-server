package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:10 2020/4/28 2020
 */
public class VideoDeleteRequest extends FsRequest {

    private Long videoId;

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
