package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:15 2019/8/1 2019
 */
public class FriendApplyDeleteRequest extends FsRequest {

    private Long fid;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    @Override
    public void valid() {

        //Contracts.assertNotNull(fid, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
