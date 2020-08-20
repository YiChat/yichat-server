package com.zf.yichat.dto;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:11 2019/8/16 2019
 */
public class TrendItemDto {
    private List<TrendListDto> list;
    private String background;

    public TrendItemDto(List<TrendListDto> list, String background) {
        this.list = list;
        this.background = background;
    }

    public List<TrendListDto> getList() {
        return list;
    }

    public void setList(List<TrendListDto> list) {
        this.list = list;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
