package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:59 2019/7/15 2019
 */
public class SmsSendRequest extends FsRequest {

    private String mobile;
    private String type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public void valid() {
        Contracts.assertTrue(StringUtils.isNotBlank(mobile), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(type), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
