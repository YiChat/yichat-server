package com.zf.yichat.api.controller.message;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.dto.MessageBodyDto;
import com.zf.yichat.dto.MessageUnreadDto;
import com.zf.yichat.model.Message;
import com.zf.yichat.model.TigGroupMember;
import com.zf.yichat.model.User;
import com.zf.yichat.service.*;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.MD5Util;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:27 2019/6/17 2019
 */
@RestController
@RequestMapping("message")
public class MessageController extends BaseController {

    private static final String robot_sign = "001";
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ImApiService imApiService;
    @Autowired
    private ConfigSet configSet;
    @Autowired
    private OssService ossService;

    @PostMapping("upload")
    public FsResponse upload(@RequestBody MessageUploadRequest params) {
        params.valid();

        Message message = new Message();
        message.setMessageId(params.getMessageId());
        message.setContent(params.getContent());
        message.setTime(params.getTime());
        message.setReferId(Long.parseLong(params.getReferId()));
        message.setReferType(params.getReferType());
        message.setUserId(getUserId());
        return messageService.save(message, false);

    }


    //历史消息
    @PostMapping("list")
    public FsResponse list(@RequestBody MessageListRequest params) {
        params.valid();
        //FsResponse response = messageService.selectList(FsPage.init(params.getPageNo(), params.getPageSize()), params.getReferType(), params.getReferId(), params.getTime(), getUserId(), true);

        /*if (response.getCount() > 0) {
            Collections.reverse((List<MessageListDto>) (response.getData()));

        }*/

        //单聊
        if (params.getReferType() == 1) {
            return FsResponseGen.successData(messageService.selectSingleHistoryList(params.getPageNo(), Long.parseLong(params.getReferId()), params.getTime(), getUserId()));
        }

        return FsResponseGen.successData(messageService.selectHistoryList(params.getPageNo(), Integer.parseInt(params.getReferId()), params.getTime(), getUserId()));
    }

    @PostMapping("search/list")
    public FsResponse searchList(@RequestBody MessageSearchListRequest params) {
        params.valid();

        return messageService.selectSearchList(FsPage.init(params.getPageNo(), params.getPageSize()), GeneralUtils.splitIntExcludeEmpty(params.getGroupIds()), params.getSearchContent(), getUserId());
    }

    @PostMapping("update")
    public FsResponse cancle(@RequestBody MessageCancleRequest params) {
        params.valid();

        messageService.cancle(params.getMessageId(), getUserId(), params.getContent());
        return FsResponseGen.success();
    }

    @PostMapping("del/time")
    public FsResponse delTime(@RequestBody MessageDelTimeRequest params) {
        params.valid();

        messageService.saveDelTime(getUserId(), params.getReferType(), params.getReferId(), params.getTime());
        return FsResponseGen.success();
    }


    @PostMapping("delete")
    public FsResponse deleteMessage(@RequestBody MessageCancleRequest params) {
        params.valid();

        messageService.deleteMsg(params.getMessageId());
        return FsResponseGen.success();
    }


    //未读消息数
    @PostMapping("unread/list")
    @Deprecated
    public FsResponse unreadList() {

        List<Integer> gids = Optional.ofNullable(groupService.selectGroupByUserId(getUserId())).map(v -> v.stream().map(TigGroupMember::getGid).collect(Collectors.toList())).orElse(new ArrayList<>(0));
        Long time = userService.getTimestamp(getUserId());
        FsResponse res = FsResponseGen.successData(DtoChangeUtils.getList(gids, v -> {
            MessageUnreadDto dto = new MessageUnreadDto();
            FsResponse response = messageService.selectList(FsPage.init(1, 20), 2, String.valueOf(v), time, getUserId(), false);
            dto.setUnreadCount(response.getCount().intValue());
            //dto.setList((List<MessageListDto>) response.getData());
            dto.setGroupId(v);
            /*if (dto.getUnreadCount() > 0) {
                dto.setLastmessage(((List<MessageListDto>) response.getData()).get(0));
            }*/
            return dto;
        }));

        userService.updateTimestamp(getUserId(), System.currentTimeMillis());

        return res;
    }


    @PostMapping("group/send")
    public FsResponse sendTextRobot(@RequestBody MessageSendGroupRequest params) {

        if (Objects.isNull(params.getGroupId()) || Objects.isNull(params.getUserId())
                || StringUtils.isBlank(params.getText()) || StringUtils.isBlank(params.getSign())) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_ERROR);
        }

        //校验key
        if (!StringUtils.equals(MD5Util.string2MD5(params.getUserId() + configSet.getKey().getMessage()), params.getSign())) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_KEY_ERROR);
        }


        User user = userService.selectById(params.getUserId());
        if (Objects.isNull(user)) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_ERROR);
        }

        MessageBodyDto dto = new MessageBodyDto();

        MessageBodyDto.Ext ext = dto.new Ext();
        ext.setAvatar(user.getAvatar());
        ext.setNick(user.getNick());
        ext.setUserId(user.getId().toString());

        MessageBodyDto.Body body = dto.new Body();
        body.setContent(params.getText());


        MessageBodyDto.Data data = dto.new Data();
        data.setExt(ext);
        data.setBody(body);
        data.setChatType(2);
        data.setMsgType(2001);
        data.setMsgId(UUID.randomUUID().toString() + robot_sign);
        data.setFrom(params.getUserId().toString());
        data.setTo(String.valueOf(params.getGroupId()));


        dto.setData(data);
        dto.setType(2000);

        //发送消息
        imApiService.sendRobotMsg(2, params.getGroupId().longValue(), URLEncoder.encode(JSON.toJSONString(dto)));


        Message save = new Message();
        save.setContent(URLEncoder.encode(JSON.toJSONString(dto)));
        save.setUserId(params.getUserId());
        save.setReferType(2);
        save.setReferId(params.getGroupId().longValue());
        save.setMessageId(data.getMsgId());
        save.setTime(System.currentTimeMillis());
        //保存历史消息
        messageService.save(save, false);

        return FsResponseGen.success();

    }


    @PostMapping("group/send/img")
    public FsResponse sendImgRobot(MessageSendGroupRequest params, MultipartFile file) throws IOException {

        String path = ossService.upload(file.getInputStream());

        if (StringUtils.isBlank(path)) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_ERROR);
        }

        path = "http://" + configSet.getOss().getBucketName() + "." + configSet.getOss().getEndpoint() + "/" + path;

        System.out.println(path);

        if (Objects.isNull(params.getGroupId()) || Objects.isNull(params.getUserId())
                || StringUtils.isBlank(params.getSign())) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_ERROR);
        }

        //校验key
        if (!StringUtils.equals(MD5Util.string2MD5(params.getUserId() + configSet.getKey().getMessage()), params.getSign())) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_KEY_ERROR);
        }


        User user = userService.selectById(params.getUserId());
        if (Objects.isNull(user)) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_ERROR);
        }


        MessageBodyDto dto = new MessageBodyDto();

        MessageBodyDto.Ext ext = dto.new Ext();
        ext.setAvatar(user.getAvatar());
        ext.setNick(user.getNick());
        ext.setUserId(String.valueOf(params.getUserId()));

        MessageBodyDto.Body body = dto.new Body();
        body.setFileName(path);
        body.setRemotePath(path);
        BufferedImage image = ImageIO.read(file.getInputStream());
        body.setSize(image.getWidth() + "," + image.getHeight());

        MessageBodyDto.Data data = dto.new Data();
        data.setExt(ext);
        data.setBody(body);
        data.setChatType(2);
        data.setMsgType(2002);
        data.setMsgId(UUID.randomUUID().toString() + robot_sign);
        data.setFrom(String.valueOf(params.getUserId()));
        data.setTo(String.valueOf(params.getGroupId()));
        data.setTimestamp(System.currentTimeMillis());


        dto.setData(data);
        dto.setType(2000);

        //发送消息
        imApiService.sendRobotMsg(2, params.getGroupId().longValue(), URLEncoder.encode(JSON.toJSONString(dto)));


        Message save = new Message();
        save.setContent(URLEncoder.encode(JSON.toJSONString(dto)));
        save.setUserId(Long.valueOf(params.getUserId()));
        save.setReferType(2);
        save.setReferId(params.getGroupId().longValue());
        save.setMessageId(data.getMsgId());
        save.setTime(System.currentTimeMillis());
        //保存历史消息
        messageService.save(save, false);


        return FsResponseGen.success();

    }


}
