package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:35 2019/7/15 2019
 */
public class UserPayPasswordRequest extends FsRequest {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public void valid() {
        Contracts.assertNotNull(password, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
