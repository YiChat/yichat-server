package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:08 2019/8/18 2019
 */
public class GroupNoticeRequest extends FsRequest {

    private String title;

    private String content;

    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void valid() {

        Contracts.assertTrue(StringUtils.isNotBlank(title), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull((groupId), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(StringUtils.isNotBlank(content), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
