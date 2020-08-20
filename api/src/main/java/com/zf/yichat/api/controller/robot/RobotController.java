package com.zf.yichat.api.controller.robot;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.RobotResponse;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.dto.MessageBodyDto;
import com.zf.yichat.dto.PacketCtrlItem;
import com.zf.yichat.mapper.PacketReceiveMapper;
import com.zf.yichat.model.*;
import com.zf.yichat.service.*;
import com.zf.yichat.service.config.RedisService;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:29 2019/9/11 2019
 */

@RestController
public class RobotController extends BaseController {

    @Autowired
    private RobotService robotService;

    @Autowired
    private ImApiService imApiService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PacketReceiveMapper packetReceiveMapper;

    @Autowired
    private PacketService packetService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @PostMapping("robot/send/msg/{token}")
    public RobotResponse receiveRobot(@RequestBody RobotReceiveMsgRequest params, @PathVariable String token) {

        RobotResponse response = new RobotResponse();
        AppRobot robot = robotService.selectRobot(token, params.getRobotId());
        if (Objects.isNull(robot)) {
            //机器人身份信息失效
            response.setCode(1002);
            return response;
        }


        if (Objects.isNull(params.getChatId()) || Objects.isNull(params.getChatType()) || Objects.isNull(params.getText())) {

            //参数缺失
            response.setCode(1001);
            return response;
        }

        MessageBodyDto dto = new MessageBodyDto();
        if (params.getChatType() == 1) {

        }

        User user = userService.selectById(Long.parseLong(params.getUserId()));
        //群聊 文字消息
        if (params.getChatType() == 2) {
            MessageBodyDto.Ext ext = dto.new Ext();
            ext.setAvatar(user.getAvatar());
            ext.setNick(user.getNick());
            ext.setUserId(params.getUserId());

            MessageBodyDto.Body body = dto.new Body();
            body.setContent(params.getText());


            MessageBodyDto.Data data = dto.new Data();
            data.setExt(ext);
            data.setBody(body);
            data.setChatType(2);
            data.setMsgType(2001);
            data.setMsgId(UUID.randomUUID().toString());
            data.setFrom(params.getUserId());
            data.setTimestamp(System.currentTimeMillis());

            data.setTo(String.valueOf(params.getChatId()));


            dto.setData(data);
            dto.setType(2000);

            //发送消息
            imApiService.sendRobotMsg(2, params.getChatId(), URLEncoder.encode(JSON.toJSONString(dto)));


            Message save = new Message();
            save.setContent(URLEncoder.encode(JSON.toJSONString(dto)));
            save.setUserId(Long.valueOf(params.getUserId()));
            save.setReferType(2);
            save.setReferId(params.getChatId());
            save.setMessageId(data.getMsgId());
            save.setTime(System.currentTimeMillis());
            //保存历史消息
            messageService.save(save, false);
        }

        response.setCode(0);

        return response;
    }

    @PostMapping("robot/send/image/{token}")
    public RobotResponse receiveRobot(@RequestBody RobotReceiveImageRequest params, @PathVariable String token) {

        RobotResponse response = new RobotResponse();
        AppRobot robot = robotService.selectRobot(token, params.getRobotId());
        if (Objects.isNull(robot)) {
            //机器人身份信息失效
            response.setCode(1002);
            return response;
        }


        if (Objects.isNull(params.getChatId()) || Objects.isNull(params.getChatType()) || Objects.isNull(params.getFileName())) {

            //参数缺失
            response.setCode(1001);
            return response;
        }

        MessageBodyDto dto = new MessageBodyDto();
        if (params.getChatType() == 1) {

        }

        //当前用户
        User user = userService.selectById(Long.parseLong(params.getUserId()));

        //群聊 文字消息
        if (params.getChatType() == 2) {
            MessageBodyDto.Ext ext = dto.new Ext();
            ext.setAvatar(user.getAvatar());
            ext.setNick(user.getNick());
            ext.setUserId(params.getUserId());

            MessageBodyDto.Body body = dto.new Body();
            body.setFileName(params.getFileName());
            body.setRemotePath(params.getPath());
            body.setSize(params.getWidth() + "," + params.getHeight());

            MessageBodyDto.Data data = dto.new Data();
            data.setExt(ext);
            data.setBody(body);
            data.setChatType(2);
            data.setMsgType(2002);
            data.setMsgId(UUID.randomUUID().toString());
            data.setFrom(params.getUserId());
            data.setTo(String.valueOf(params.getChatId()));
            data.setTimestamp(System.currentTimeMillis());


            dto.setData(data);
            dto.setType(2000);

            //发送消息
            imApiService.sendRobotMsg(2, params.getChatId(), URLEncoder.encode(JSON.toJSONString(dto)));


            Message save = new Message();
            save.setContent(URLEncoder.encode(JSON.toJSONString(dto)));
            save.setUserId(Long.valueOf(params.getUserId()));
            save.setReferType(2);
            save.setReferId(params.getChatId());
            save.setMessageId(data.getMsgId());
            save.setTime(System.currentTimeMillis());
            //保存历史消息
            messageService.save(save, false);
        }

        response.setCode(0);

        return response;
    }


    @PostMapping("robot/send/at/{token}")
    public RobotResponse receiveAtRobot(@RequestBody RobotReceiveAtRequest params, @PathVariable String token) {

        RobotResponse response = new RobotResponse();
        AppRobot robot = robotService.selectRobot(token, params.getRobotId());
        if (Objects.isNull(robot)) {
            //机器人身份信息失效
            response.setCode(1002);
            return response;
        }


        if (Objects.isNull(params.getChatId()) || Objects.isNull(params.getChatType()) || Objects.isNull(params.getUserId())) {

            //参数缺失
            response.setCode(1001);
            return response;
        }

        User user = userService.selectById(Long.parseLong(params.getUserId()));
        MessageBodyDto dto = new MessageBodyDto();
        if (params.getChatType() == 1) {

        }
        //@
        if (params.getChatType() == 2) {
            MessageBodyDto.Ext ext = dto.new Ext();
            ext.setAvatar(user.getAvatar());
            ext.setNick(user.getNick());
            ext.setUserId(params.getUserId());
            ext.setAtUser(params.getAtUserIds());

            MessageBodyDto.Body body = dto.new Body();
            List<String> list = GeneralUtils.splitExcludeEmpty(params.getAtUserIds());
            String content = list.stream().map(v -> "@" + userService.selectById(Long.parseLong(v)).getNick()).collect(Collectors.joining());

            body.setContent(content + params.getText());

            MessageBodyDto.Data data = dto.new Data();
            data.setExt(ext);
            data.setBody(body);
            data.setChatType(2);
            data.setMsgType(2001);
            data.setMsgId(UUID.randomUUID().toString());
            data.setFrom(params.getUserId());
            data.setTo(String.valueOf(params.getChatId()));
            data.setTimestamp(System.currentTimeMillis());


            dto.setData(data);
            dto.setType(2000);

            //发送消息
            imApiService.sendRobotMsg(2, params.getChatId(), URLEncoder.encode(JSON.toJSONString(dto)));


            Message save = new Message();
            save.setContent(URLEncoder.encode(JSON.toJSONString(dto)));
            save.setUserId(Long.valueOf(params.getUserId()));
            save.setReferType(2);
            save.setReferId(params.getChatId());
            save.setMessageId(data.getMsgId());
            save.setTime(System.currentTimeMillis());
            //保存历史消息
            messageService.save(save, false);
        }

        response.setCode(0);

        return response;
    }


    //机器人获取群列表
    @PostMapping("/robot/group/list/{token}")
    public FsResponse getGroupListRobot(@RequestBody RobotGroupRequest params, @PathVariable String token) {

        AppRobot robot = robotService.selectRobot(token, params.getRobotId());

        return FsResponseGen.successData(groupService.selectGroupListByCreater(robot.getUserId()));

    }


    //机器人获取群用户
    @PostMapping("/robot/group/member/{token}")
    public FsResponse getGroupMemberRobot(@RequestBody RobotGroupRequest params, @PathVariable String token) {

        AppRobot robot = robotService.selectRobot(token, params.getRobotId());

        List<AppRobotRelation> list = robotService.selectRelation(robot.getId());
        Long groupId = GeneralUtils.isNotNullOrEmpty(list) ? list.get(0).getReferId() : 0;
        if (groupId > 0) {
            return groupService.selectUserList(FsPage.init(-1, 10), null, groupId.intValue());
        } else {
            return FsResponseGen.successData(new ArrayList<>());
        }

    }

    //机器人获取红包详情
    @PostMapping("/robot/packet/detail/{token}")
    public FsResponse getPacketRobot(@RequestBody RobotPacketRequest params, @PathVariable String token) {

        Packet packet = packetService.selectById(params.getPacketId());
        //AppRobot robot = robotService.selectRobot(token, params.getRobotId());

        FsResponse response = FsResponseGen.successData(packetReceiveMapper.selectList(params.getPacketId()));

        response.setCount(packet.getStatus().longValue());
        return response;

    }


    //机器人创建红包 控制逻辑
    @PostMapping("/robot/packet/create/{token}")
    public FsResponse createPacketRobot(@RequestBody PacketCtrlRequest params, @PathVariable String token) {

        List<String> strs = GeneralUtils.splitExcludeEmpty(params.getRobotIds());
        if (GeneralUtils.isNotNullOrEmpty(strs)) {
            for (String s : strs) {
                AppRobot robot = robotService.selectRobot(token, s);
                List<AppRobotRelation> relation = robotService.selectRelation(robot.getId());
                if (GeneralUtils.isNotNullOrEmpty(relation)) {
                    AppRobotRelation rel = relation.get(0);
                    PacketCtrlItem item = new PacketCtrlItem();
                    item.setMoney(params.getMoney());
                    item.setUserId(params.getUserId());
                    item.setType(params.getType());
                    item.setLastnum(params.getLastNum());
                    redisService.setCtrlKey(rel.getReferId().intValue(), item);
                }
            }

        }

        return FsResponseGen.success();

    }

    //注册机器人
    @PostMapping("/robot/register/{code}")
    public FsResponse registerRobot(@PathVariable String code) {

        //验证code是否有效
        AppRobot robot = robotService.registerUseCode(code);

        if (Objects.nonNull(robot)) {
            return FsResponseGen.successData(robot);
        } else {
            return FsResponseGen.fail();
        }

    }

    //校验激活码
    @PostMapping("/robot/valid/{token}")
    public FsResponse validCodeRobot(@RequestBody RobotValidCodeRequest params, @PathVariable String token) {
        AppRobot robot = robotService.selectRobot(token, params.getRobotId());
        //验证code是否有效
        return FsResponseGen.successData(Optional.ofNullable(robotService.validCode(params.getCode(), params.getType())).map(AppRobotCode::getLimitDay).orElse(null));

    }

    //机器人主动点红包
    @PostMapping("/robot/packet/receive/{token}")
    public FsResponse getPacketRobot(@RequestBody RobotPacketReceiveRequest params, @PathVariable String token) {


        FsResponse response = packetService.receive(params.getPacketId(), params.getUserId());

        try {
            Packet packet = packetService.selectById(params.getPacketId());

            User user = userService.selectById(params.getUserId());
            User sendUser = userService.selectById(packet.getUserId());
            MessageBodyDto dto = new MessageBodyDto();

            //发送抢红包消息
            MessageBodyDto.Ext ext = dto.new Ext();
            ext.setMsgFromNick(sendUser.getNick());
            ext.setMsgFrom(packet.getUserId());
            ext.setUserId(String.valueOf(params.getUserId()));
            ext.setAction(10004);

            MessageBodyDto.Body body = dto.new Body();

            body.setContent(user.getNick() + "领取了" + sendUser.getNick() + "的红包");

            MessageBodyDto.Data data = dto.new Data();
            data.setExt(ext);
            data.setBody(body);
            data.setChatType(2);
            data.setMsgType(2001);
            data.setMsgId(UUID.randomUUID().toString());
            data.setFrom(String.valueOf(params.getUserId()));
            data.setTo(String.valueOf(params.getChatId()));
            data.setTimestamp(System.currentTimeMillis());


            dto.setData(data);
            dto.setType(2000);

            //发送消息
            //imApiService.sendRobotMsg(2, Long.parseLong(params.getChatId()), URLEncoder.encode(JSON.toJSONString(dto)));


            //保存历史消息
            Message save = new Message();
            save.setContent(URLEncoder.encode(JSON.toJSONString(dto)));
            save.setUserId(Long.valueOf(params.getUserId()));
            save.setReferType(2);
            save.setReferId(Long.valueOf(params.getChatId()));
            save.setMessageId(data.getMsgId());
            save.setTime(System.currentTimeMillis());
            messageService.save(save, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }


    //机器人绑定用户
    @PostMapping("/robot/bind/{token}")
    public FsResponse bindRobot(@RequestBody RobotBindRequest params, @PathVariable String token) {

        AppRobot robot = robotService.selectRobot(token, params.getRobotId());

        AppRobot res = robotService.bindUser(params.getAppId(), params.getPassword(), robot);

        if (Objects.nonNull(res)) {
            return FsResponseGen.successData(res);
        } else {
            return FsResponseGen.fail();
        }

    }

    //机器人绑定群
    @PostMapping("/robot/bind/group/{token}")
    public FsResponse bindGroupRobot(@RequestBody RobotBindGroupRequest params, @PathVariable String token) {

        AppRobot robot = robotService.selectRobot(token, params.getRobotId());

        robotService.bindGroup(params.getGroupId(), robot);

        return FsResponseGen.success();
    }


}
