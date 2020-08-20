package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:09 2020/5/13 2020
 */
public class VideoListRequest {
    //0默认排序 1最新 2最热 3关注
    private Integer type;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
