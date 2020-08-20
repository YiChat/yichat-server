package com.zf.yichat.dto;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:57 2019/8/8 2019
 */
public class UserSignDayDto {

    private Integer signStatus;//0未签到 1签到
    private Integer isToday;//0否 1是
    private String memo;//描述 周一


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Integer getIsToday() {
        return isToday;
    }

    public void setIsToday(Integer isToday) {
        this.isToday = isToday;
    }
}
