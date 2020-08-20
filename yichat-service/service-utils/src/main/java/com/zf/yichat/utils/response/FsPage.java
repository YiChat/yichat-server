package com.zf.yichat.utils.response;

import java.io.Serializable;

public class FsPage implements Serializable {
    private Integer pageSize;
    private Integer pageNo;

    private FsPage() {
    }

    private FsPage(Integer pageNo, Integer pageSize) {
        this.pageSize = pageSize == null ? 10 : pageSize;
        this.pageNo = pageNo == null ? 1 : pageNo;
    }

    public static FsPage init(Integer pageNo, Integer pageSize) {
        return new FsPage(pageNo, pageSize);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    private void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    private void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public boolean hasPageNo() {
        return this.pageNo != -1;
    }
}
