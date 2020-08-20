package com.zf.yichat.utils.response;

/**
 * Created by yichat on 2018/7/28.
 */
public class FsResponse<T> {
    private String msg;
    private String code;
    private T data;
    private boolean success;
    private Long count;
    private Integer pageSize;
    private Integer pageNo;

    public FsResponse() {
    }

    public FsResponse(String msg, String code, T data, boolean success) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.success = success;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
