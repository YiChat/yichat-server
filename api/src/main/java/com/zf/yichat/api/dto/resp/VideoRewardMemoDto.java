package com.zf.yichat.api.dto.resp;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:09 2020/5/6 2020
 */
public class VideoRewardMemoDto {
    private String content;
    private Long id;

    public VideoRewardMemoDto() {

    }

    public VideoRewardMemoDto(String content, Long id) {
        this.content = content;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
