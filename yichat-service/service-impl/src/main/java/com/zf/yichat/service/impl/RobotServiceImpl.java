package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.dto.MsgRequest;
import com.zf.yichat.mapper.AppRobotCodeMapper;
import com.zf.yichat.mapper.AppRobotMapper;
import com.zf.yichat.mapper.AppRobotRelationMapper;
import com.zf.yichat.model.*;
import com.zf.yichat.service.RobotService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.MD5Util;
import com.zf.yichat.utils.common.OKHttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:22 2019/9/11 2019
 */
@Service
public class RobotServiceImpl implements RobotService {

    private static Logger logger = LoggerFactory.getLogger(RobotServiceImpl.class);

    @Autowired
    private AppRobotMapper appRobotMapper;

    @Autowired
    private AppRobotRelationMapper appRobotRelationMapper;

    @Autowired
    private AppRobotCodeMapper appRobotCodeMapper;

    @Autowired
    private UserService userService;

    @Override
    public AppRobot selectRobot(String token, String robotId) {
        Example select = new Example(AppRobot.class);
        select.createCriteria().andEqualTo("token", token).andEqualTo("uid", robotId).andEqualTo("status", 0);
        return appRobotMapper.selectOneByExample(select);
    }

    @Async
    @Override
    public void callback(String msgId, String userId, Integer chatType, Long chatId) {

        //查询是否有匹配机器人
        Example select = new Example(AppRobotRelation.class);
        select.createCriteria().andEqualTo("referId", chatId).andEqualTo("type", chatType);
        List<AppRobotRelation> list = appRobotRelationMapper.selectByExample(select);
        //开始请求机器人接收消息
        if (GeneralUtils.isNotNullOrEmpty(list)) {
            //就是
        }


        //

    }

    private void send() {

        //OKHttpUtil.httpPostJson()
    }


    @Override
    public List<AppRobotRelation> selectRelation(Long robotId) {
        Example select = new Example(AppRobotRelation.class);
        select.createCriteria().andEqualTo("robotId", robotId);
        return appRobotRelationMapper.selectByExample(select);
    }

    @Override
    @Async
    public void sendMessage(Message message, String usernick) {
        //查询是否有匹配机器人
        Example select = new Example(AppRobotRelation.class);
        select.createCriteria().andEqualTo("referId", message.getReferId()).andEqualTo("type", message.getReferType());
        List<AppRobotRelation> list = appRobotRelationMapper.selectByExample(select);
        //开始请求机器人接收消息
        if (GeneralUtils.isNotNullOrEmpty(list)) {
            //就是
            list.forEach(v -> {
                AppRobot robot = appRobotMapper.selectByPrimaryKey(v.getRobotId());
                logger.debug("请求机器人接口开始:{}", robot.getCallbackUrl());
                MsgRequest params = new MsgRequest();
                params.setChatId(message.getReferId());
                params.setMessageContent(message.getContent());
                params.setUserId(String.valueOf(message.getUserId()));
                params.setSendTime(message.getTime().toString());
                params.setRobotId(robot.getUid());
                params.setSign(MD5Util.string2MD5(message.getUserId() + robot.getToken()));
                params.setMsgId(message.getMessageId());
                params.setChatType(2);
                params.setUsernick(usernick);

                String res = OKHttpUtil.httpPostJson(robot.getCallbackUrl(), JSON.toJSONString(params));
                logger.debug("返回结果:{}", res);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppRobot registerUseCode(String code) {


        AppRobotCode appRobotCode = validCode(code, 0);
        //激活码不存在数据库中 则注册失败
        if (Objects.isNull(appRobotCode)) {
            return null;
        }

        AppRobot robot = new AppRobot();

        robot.setUid(appRobotCode.getId() + code.substring(0, 6));
        //回调地址
        robot.setCallbackUrl("http://216.83.59.3:8082/admin/robot/receive/msg");
        //token生成用于接口校验
        robot.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        robot.setCode(code);

        //机器人对应的绑定用户 信息等待用户登陆机器人后台进行绑定
        appRobotMapper.insertSelective(robot);


        robot.setId(appRobotCode.getLimitDay().longValue());
        return robot;
    }

    @Override
    public AppRobotCode validCode(String code, int type) {
        Example ce = new Example(AppRobotCode.class);
        ce.createCriteria().andEqualTo("code", code).andEqualTo("type", type);
        AppRobotCode appRobotCode = appRobotCodeMapper.selectOneByExample(ce);

        //激活码不存在数据库中 则注册失败
        if (Objects.isNull(appRobotCode)) {
            return null;
        }

        //删除激活码
        appRobotCodeMapper.deleteByPrimaryKey(appRobotCode.getId());

        return appRobotCode;
    }

    @Override
    public AppRobot bindUser(String appId, String password, AppRobot robot) {

        User user = userService.selectByAppId(appId);
        if (Objects.nonNull(user) && StringUtils.equals(MD5Util.shaEncode(password), user.getPassword())) {
            robot.setUserId(user.getId());
            robot.setUsernick(user.getNick());
            robot.setUserAvatar(user.getAvatar());

            appRobotMapper.updateByPrimaryKeySelective(robot);

            return robot;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindGroup(Integer groupId, AppRobot robot) {
        //绑定之前删除
        Example example = new Example(AppRobotRelation.class);
        example.createCriteria().andEqualTo("robotId", robot.getId());
        appRobotRelationMapper.deleteByExample(example);
        //绑定群组
        AppRobotRelation relation = new AppRobotRelation();
        relation.setType(2);
        relation.setReferId(groupId.longValue());
        relation.setRobotId(robot.getId());
        appRobotRelationMapper.insertSelective(relation);
    }
}
