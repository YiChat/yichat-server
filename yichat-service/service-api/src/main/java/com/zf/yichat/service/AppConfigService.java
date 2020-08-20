package com.zf.yichat.service;

import com.zf.yichat.model.AppVersion;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:47 2019/7/18 2019
 */
public interface AppConfigService {
    void save(AppVersion appVersion);

    /**
     * 获取用户建群权限 0无 1有
     */
    Integer getCreateGroupAuthStatus(Long userId);

    /**
     * 保存首页html链接
     */
    void saveHomeUrl(String url);

    String getHomeUrl();

    boolean isFreezeIp(String ip);

    void freezeIp(String ip);

    void unfreezeIp(String ip);
}
