package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:45 2019/6/21 2019
 */
public class MessageGroupChatRequest extends FsRequest {

    private Long time;
    private String groupIds;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    @Override
    public void valid() {

    }
}
