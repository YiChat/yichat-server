package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:36 2020/4/15 2020
 */
public class VideoPraiseRequest extends FsRequest {

    private Long videoId;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Override
    public void valid() {

    }
}
