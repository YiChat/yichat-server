package com.zf.yichat.service.impl;

import com.zf.yichat.mapper.UserFeedbackMapper;
import com.zf.yichat.model.UserFeedback;
import com.zf.yichat.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:47 2019/9/4 2019
 */
@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Autowired
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    public void save(String content, Long userId) {

        UserFeedback feedback = new UserFeedback();
        feedback.setContent(content);
        feedback.setUserId(userId);

        userFeedbackMapper.insertSelective(feedback);
    }
}
