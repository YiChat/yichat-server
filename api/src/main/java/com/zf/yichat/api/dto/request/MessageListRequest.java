package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:27 2019/6/17 2019
 */
public class MessageListRequest extends FsRequest {

    private Integer referType;
    private String referId;
    private Integer pageNo;
    private Integer pageSize;
    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getReferType() {
        return referType;
    }

    public void setReferType(Integer referType) {
        this.referType = referType;
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(referId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(referType, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
