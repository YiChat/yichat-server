package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:39 2019/8/16 2019
 */
public class TrendDetailRequest extends FsRequest {

    private Long trendId;

    public Long getTrendId() {
        return trendId;
    }

    public void setTrendId(Long trendId) {
        this.trendId = trendId;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(trendId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
