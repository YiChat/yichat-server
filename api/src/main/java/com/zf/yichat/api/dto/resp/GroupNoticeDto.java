package com.zf.yichat.api.dto.resp;

import com.zf.yichat.dto.UserDto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:20 2019/8/18 2019
 */
public class GroupNoticeDto extends UserDto {
    private String title;
    private String content;
    private String nick;
    private String avatar;
    private String userId;
    private long time;
    private String timeDesc;
    private Long noticeId;

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
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


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
