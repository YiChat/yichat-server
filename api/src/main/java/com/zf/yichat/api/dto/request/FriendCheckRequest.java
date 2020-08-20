package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.vo.FriendCheckStatus;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:57 2019/6/4 2019
 */
public class FriendCheckRequest extends FsRequest {

    private Long fid;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(fid, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(status, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(status.compareTo(FriendCheckStatus.PASS.getVal()) == 0 || status.compareTo(FriendCheckStatus.REFUSE.getVal()) == 0, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
