package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:23 2019/7/19 2019
 */
public class FriendRemarkSetRequest extends FsRequest {

    private Long friendId;
    private String remark;

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(friendId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
