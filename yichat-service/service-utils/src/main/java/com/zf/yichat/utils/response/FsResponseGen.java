package com.zf.yichat.utils.response;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.utils.common.FsConst;

/**
 * Created by yichat on 2018/7/28.
 */
public class FsResponseGen {

    private static FsResponse success = new FsResponse("操作成功", FsConst.ResponseCode.SUCCESS, null, true);
    private static FsResponse fail = new FsResponse("操作失败", null, null, false);

    public static FsResponse success() {
        return success;
    }

    public static FsResponse successMsg(String str) {
        return new FsResponse(str, FsConst.ResponseCode.SUCCESS, null, true);
    }

    public static FsResponse fail(MsgCode msgCode) {
        FsResponse fail = fail();
        fail.setCode(msgCode.getCode());
        fail.setMsg(msgCode.getMsg());
        return fail;
    }

    public static String successStr() {
        return toJson(success());
    }

    public static FsResponse fail() {
        return fail;
    }

    public static FsResponse failMsg(String msg) {
        return new FsResponse(msg, null, null, false);
    }

    public static FsResponse fail(String code, String msg) {
        return new FsResponse(msg, code, null, false);
    }

    public static String failStr() {
        return toJson(fail());
    }

    public static <T> FsResponse successData(T t) {
        return new FsResponse("操作成功", FsConst.ResponseCode.SUCCESS, t, true);
    }

    public static <T> FsResponse successData(MsgCode msgCode, T t) {
        return new FsResponse(msgCode.getMsg(), msgCode.getCode(), t, true);
    }


    public static <T> String successDataStr(T t) {
        return toJson(successData(t));
    }

    private static String toJson(Object data) {
        return JSON.toJSONString(data);
    }

    public static FsResponse gen(boolean flag) {
        return flag ? success() : fail();
    }

    public static FsResponse gen(boolean flag, String failMsg) {
        return flag ? success() : failMsg(failMsg);
    }
}
