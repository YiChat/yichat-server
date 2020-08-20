package com.zf.yichat.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:48 2019/8/8 2019
 */
public class UserSignDto implements Serializable {


    private List<UserSignDayDto> list;


    //签到描述
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<UserSignDayDto> getList() {
        return list;
    }

    public void setList(List<UserSignDayDto> list) {
        this.list = list;
    }
}
