package com.zf.yichat.api.dto.resp;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:55 2019/9/3 2019
 */
public class WithdrawConfigDto {
    private String minLimit;
    private String rate;
    private String text;
    private Integer openStatus;

    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    public String getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
