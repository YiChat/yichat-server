package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:46 2019/8/8 2019
 */
public class UserSignRequest extends FsRequest {

    //0或不传为获取签到信息  1签到动作
    private Integer signType;

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    @Override
    public void valid() {

    }
}
