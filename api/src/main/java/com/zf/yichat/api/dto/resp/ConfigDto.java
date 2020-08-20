package com.zf.yichat.api.dto.resp;

import com.zf.yichat.dto.MessageUnreadDto;

import java.io.Serializable;
import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:33 2019/8/1 2019
 */
public class ConfigDto implements Serializable {


    //版本设置
    private AppVersionDto androidVersion;
    private AppVersionDto iosVersion;

    //未读数
    private List<MessageUnreadDto> unreadCountList;

    private ShareDto share;
    private Integer createGroupAuthStatus;//0所有用户都有 1部分用户有

    public ShareDto getShare() {
        return share;
    }

    public void setShare(ShareDto share) {
        this.share = share;
    }

    public AppVersionDto getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(AppVersionDto androidVersion) {
        this.androidVersion = androidVersion;
    }

    public AppVersionDto getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(AppVersionDto iosVersion) {
        this.iosVersion = iosVersion;
    }

    public List<MessageUnreadDto> getUnreadCountList() {
        return unreadCountList;
    }

    public void setUnreadCountList(List<MessageUnreadDto> unreadCountList) {
        this.unreadCountList = unreadCountList;
    }

    public Integer getCreateGroupAuthStatus() {
        return createGroupAuthStatus;
    }

    public void setCreateGroupAuthStatus(Integer createGroupAuthStatus) {
        this.createGroupAuthStatus = createGroupAuthStatus;
    }
}
