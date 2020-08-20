package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:48 2019/6/14 2019
 */
public class PacketSendInfoRequest extends FsRequest {

    //0单聊红包 1群红包
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public void valid() {

        //Contracts.assertNotNull(type, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
