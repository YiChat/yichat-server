package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:51 2019/6/5 2019
 */
public class TrendDeleteRequest extends FsRequest {


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
