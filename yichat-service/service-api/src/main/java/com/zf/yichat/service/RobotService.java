package com.zf.yichat.service;

import com.zf.yichat.model.AppRobot;
import com.zf.yichat.model.AppRobotCode;
import com.zf.yichat.model.AppRobotRelation;
import com.zf.yichat.model.Message;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:17 2019/9/11 2019
 */
public interface RobotService {

    AppRobot selectRobot(String token, String robotId);

    //回调函数
    void callback(String msgId, String userId, Integer chatType, Long chatId);

    void sendMessage(Message message, String usernick);

    List<AppRobotRelation> selectRelation(Long robotId);

    AppRobot registerUseCode(String code);

    AppRobot bindUser(String appId, String password, AppRobot robot);

    void bindGroup(Integer groupId, AppRobot robot);

    AppRobotCode validCode(String code, int type);
}
