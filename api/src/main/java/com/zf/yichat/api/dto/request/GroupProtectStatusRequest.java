package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:31 2019/7/17 2019
 */
public class GroupProtectStatusRequest extends FsRequest {

    private Integer groupId;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public void valid() {


        Contracts.assertNotNull(groupId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(status, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
