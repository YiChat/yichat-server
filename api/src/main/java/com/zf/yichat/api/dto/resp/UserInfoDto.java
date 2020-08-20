package com.zf.yichat.api.dto.resp;

import java.io.Serializable;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:14 2019/5/31 2019
 */
public class UserInfoDto implements Serializable {
    private String userId;
    private String avatar;
    private String nick;
    private String appId;
    private Integer gender;
    private String mobile;
    //0未设置  1设置
    private Integer payPasswordStatus;
    //好友备注
    private String remark;
    private Integer friendStatus;
    private String recommendCode;
    private String ucode;
    //邀请人数
    private Long recommendCount;


    public Long getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(Long recommendCount) {
        this.recommendCount = recommendCount;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPayPasswordStatus() {
        return payPasswordStatus;
    }

    public void setPayPasswordStatus(Integer payPasswordStatus) {
        this.payPasswordStatus = payPasswordStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
