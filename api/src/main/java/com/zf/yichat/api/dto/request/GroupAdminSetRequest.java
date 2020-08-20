package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:01 2019/7/1 2019
 */
public class GroupAdminSetRequest extends FsRequest {


    private Integer groupId;
    private String userIds;
    private Integer status;// |0设置 1取消  |

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

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(StringUtils.isNotBlank(userIds), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(groupId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(status, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
