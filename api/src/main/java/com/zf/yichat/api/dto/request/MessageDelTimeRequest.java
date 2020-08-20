package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:17 2019/7/24 2019
 */
public class MessageDelTimeRequest extends FsRequest {


    private Integer referType;

    private Long referId;

    private Long time;

    public Integer getReferType() {
        return referType;
    }

    public void setReferType(Integer referType) {
        this.referType = referType;
    }


    public Long getReferId() {
        return referId;
    }

    public void setReferId(Long referId) {
        this.referId = referId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(referId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(referType, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
