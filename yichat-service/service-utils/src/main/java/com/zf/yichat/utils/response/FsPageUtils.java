package com.zf.yichat.utils.response;

import com.github.pagehelper.PageInfo;
import com.zf.yichat.utils.common.FsConst;

import java.util.List;

public class FsPageUtils<T> {

    public static FsResponse genreateListResponse(List<?> list) {
        PageInfo pageInfo = new PageInfo(list);
        FsResponse response = FsResponseGen.successData(pageInfo.getList());
        response.setPageNo(pageInfo.getPageNum());
        response.setPageSize(pageInfo.getPageSize());
        response.setCount(pageInfo.getTotal());
        response.setCode(FsConst.ResponseCode.SUCCESS);
        return response;
    }
}
