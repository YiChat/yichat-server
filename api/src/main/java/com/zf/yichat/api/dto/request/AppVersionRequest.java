package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:12 2019/7/30 2019
 */
public class AppVersionRequest extends FsRequest {

    private Integer type;//0 安卓  1 苹果

    private Integer currentVersion;

    public Integer getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Integer currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public void valid() {
        Contracts.assertNotNull(type, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
