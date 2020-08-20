package com.zf.yichat.service;

/**
 * 极光推送服务
 *
 * @author yichat
 * @date create in 14:54 2019/8/26 2019
 */
public interface PushService {


    void sendAll(String title, String content);

    void sendGroup(Integer groupId, String title, String content);

    void sendSingle(Long userId, String title, String content);

}
