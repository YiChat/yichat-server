package com.zf.yichat.api.dto.request;

import java.util.Objects;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:47 2019/6/4 2019
 */
public class FriendListRequest extends FsRequest {

    private Integer pageNo;

    private Integer pageSize;


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

        pageNo = Objects.isNull(pageNo) ? 1 : pageNo;
        pageSize = Objects.isNull(pageSize) ? 10 : pageSize;


    }
}
