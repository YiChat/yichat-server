package com.zf.yichat.service;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:46 2019/9/4 2019
 */
public interface UserFeedbackService {


    void save(String content, Long userId);

}
