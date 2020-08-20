package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:18 2019/6/4 2019
 */
public class FriendDeleteRequest extends FsRequest {

    private Long friendId;

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(friendId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
