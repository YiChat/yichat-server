package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:22 2019/6/12 2019
 */
public class PacketReceiveRequest extends FsRequest {

    private Long packetId;

    public Long getPacketId() {
        return packetId;
    }

    public void setPacketId(Long packetId) {
        this.packetId = packetId;
    }

    @Override
    public void valid() {
        //Contracts.assertNotNull(packetId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
