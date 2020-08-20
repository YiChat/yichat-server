package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:38 2019/8/18 2019
 */
public class GroupNoticeGetRequest extends FsRequest {

    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(groupId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
