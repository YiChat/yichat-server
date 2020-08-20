package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:52 2019/7/30 2019
 */
public class MessageSearchListRequest extends FsRequest {

    private Integer pageNo;
    private Integer pageSize;
    private String searchContent;
    private String groupIds;

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
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

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override
    public void valid() {

        Contracts.assertTrue(StringUtils.isNotBlank(searchContent), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        //Contracts.assertNotNull((groupIds), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
    }
}
