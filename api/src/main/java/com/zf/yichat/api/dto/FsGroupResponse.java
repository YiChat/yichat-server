package com.zf.yichat.api.dto;

import com.zf.yichat.utils.response.FsResponse;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:25 2019/8/2 2019
 */
public class FsGroupResponse extends FsResponse {

    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
