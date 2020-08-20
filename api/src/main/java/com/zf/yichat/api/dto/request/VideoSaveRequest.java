package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:18 2020/4/15 2020
 */
public class VideoSaveRequest extends FsRequest {

    private String content;
    private String path;
    private String thumbnail;
    private Integer seconds;
    //宽高比
    private BigDecimal rate;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    @Override
    public void valid() {

        Contracts.assertTrue(StringUtils.isNotBlank(path), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        //Contracts.assertTrue(StringUtils.isNotBlank(thumbnail), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(seconds, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

    }
}
