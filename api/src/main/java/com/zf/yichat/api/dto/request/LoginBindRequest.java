package com.zf.yichat.api.dto.request;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:25 2019/9/5 2019
 */
public class LoginBindRequest extends FsRequest {

    private String qrcode;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public void valid() {


    }
}
