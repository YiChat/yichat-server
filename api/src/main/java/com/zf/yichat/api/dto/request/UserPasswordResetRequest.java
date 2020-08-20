package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:05 2019/5/31 2019
 */
public class UserPasswordResetRequest extends FsRequest {

    private String mobile;
    private String password;
    private String srcPassword;

    public String getSrcPassword() {
        return srcPassword;
    }

    public void setSrcPassword(String srcPassword) {
        this.srcPassword = srcPassword;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void valid() {

        Contracts.assertTrue(StringUtils.isNotBlank(password), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        //Contracts.assertTrue(StringUtils.isNotBlank(mobile), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
