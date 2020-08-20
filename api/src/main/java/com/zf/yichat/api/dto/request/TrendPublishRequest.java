package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:47 2019/6/5 2019
 */
public class TrendPublishRequest extends FsRequest {

    private String imgs;
    private String videos;
    private String content;
    private String location;

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void valid() {

        Contracts.assertTrue(!(StringUtils.isBlank(imgs) && StringUtils.isBlank(videos) && StringUtils.isBlank(content)), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
