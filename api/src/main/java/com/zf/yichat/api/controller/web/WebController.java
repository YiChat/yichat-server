package com.zf.yichat.api.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.WebResponse;
import com.zf.yichat.api.dto.WebUserResponse;
import com.zf.yichat.api.dto.web.WebFriendDto;
import com.zf.yichat.api.dto.web.WebLoginDto;
import com.zf.yichat.api.dto.web.WebMessageDto;
import com.zf.yichat.api.dto.web.WebUserDto;
import com.zf.yichat.mapper.MessageMapper;
import com.zf.yichat.mapper.UserMapper;
import com.zf.yichat.model.Message;
import com.zf.yichat.model.User;
import com.zf.yichat.model.UserIm;
import com.zf.yichat.service.*;
import com.zf.yichat.service.dto.FriendListDto;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.MD5Util;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:51 2019/8/27 2019
 */

@RestController
public class WebController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ImApiService imApiService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/web/api/login")
    public WebUserResponse login(String mobile, String password) {

        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
            return new WebUserResponse(-2);
        }

        User user = userService.selectByMobile(mobile);
        if (Objects.isNull(user) || !StringUtils.equals(MD5Util.shaEncode(password), user.getPassword())) {
            return new WebUserResponse(-2);
        }

        if (user.getStatus() != 0) {
            return new WebUserResponse(-1);
        }

        user.setPlatform("3");
        userMapper.updateByPrimaryKeySelective(user);

        WebUserDto dto = new WebUserDto();
        dto.setAppid(user.getAppid());
        dto.setAvatar(user.getAvatar());
        dto.setUserId(String.valueOf(user.getId()));
        dto.setNick(user.getNick());
        dto.setTel(user.getMobile());
        dto.setSex(user.getGender());
        dto.setImPassword(userService.selectIm(user.getId()).getImPassword());

        return new WebUserResponse(1, dto);
    }

    @PostMapping("/web/api/getContactList")
    public WebResponse getContactList(Long uid) {

        return new WebResponse(1, DtoChangeUtils.getList(((List<FriendListDto>) friendService.selectList(-1, null, uid).getData()), v -> {
            WebFriendDto dto = new WebFriendDto();
            dto.setAvatar(v.getAvatar());
            dto.setFxid(v.getAppId());
            dto.setNick(v.getNick());
            dto.setSex(v.getGender());
            dto.setTime(DateUtils.formatDate(v.getCtime(), "yyyy-MM-dd HH:mm:ss"));
            dto.setRemark(v.getRemark());
            dto.setTel(v.getMobile());
            dto.setUserId(v.getUserId());

            return dto;
        }));

    }


    @PostMapping("/web/api/getmygroup")
    public WebResponse myGroup(String jsonStr) {

        return new WebResponse(1, JSON.parseObject(imApiService.getMyGroup(jsonStr)));
    }

    @PostMapping("/web/api/getmucMembers")
    public WebResponse getGroupMember(Long uid, Integer gid) {

        return new WebResponse(1, new ArrayList<>());

        /*return new WebResponse(1, DtoChangeUtils.getList(((List<GroupUserDto>) groupService.selectUserList(FsPage.init(-1, 10), uid, gid).getData()), v -> {
            WebGroupMemberDto dto = new WebGroupMemberDto();
            dto.setAvatar(v.getAvatar());
            dto.setNick(v.getNick());
            dto.setUserId(v.getUserId());
            return dto;
        }));*/
    }

    @PostMapping("/web/api/getMsgByTimestamp")
    public WebResponse getMsg(Long uid, Integer groupId, Long otherId, Long timestamp) {

        if (Objects.nonNull(groupId)) {
            return new WebResponse(1, DtoChangeUtils.getList(messageService.selectWebLastList(groupId, uid, timestamp), v -> {
                WebMessageDto dto = new WebMessageDto();
                dto.setMessage(v.getContent());
                //解析content增加ext
                if (StringUtils.isNotBlank(dto.getMessage())) {
                    JSONObject object = JSON.parseObject(URLDecoder.decode(dto.getMessage()));
                    if (!object.getJSONObject("data").containsKey("ext")) {
                        object.getJSONObject("data").put("ext", new Object());
                        dto.setMessage(object.toJSONString());
                    }
                }
                dto.setTimestamp(String.valueOf(v.getTime()));
                return dto;
            }));
        } else {
            return new WebResponse(1, DtoChangeUtils.getList(messageService.selectSingleHistoryList(20, otherId, timestamp, uid), v -> {
                WebMessageDto dto = new WebMessageDto();
                dto.setMessage(v.getContent());
                //解析content增加ext
                if (StringUtils.isNotBlank(dto.getMessage())) {
                    JSONObject object = JSON.parseObject(URLDecoder.decode(dto.getMessage()));
                    if (!object.getJSONObject("data").containsKey("ext")) {
                        object.getJSONObject("data").put("ext", new Object());
                        dto.setMessage(object.toJSONString());
                    }
                }
                dto.setTimestamp(String.valueOf(v.getTime()));
                return dto;
            }));
        }

    }

    @PostMapping("/web/api/getSingleMsg")
    public WebResponse getMsg(Long uid, Long friendId) {

        return new WebResponse(1, DtoChangeUtils.getList(messageService.selectSingleHistoryList(20, friendId, null, uid), v -> {
            WebMessageDto dto = new WebMessageDto();
            dto.setMessage(v.getContent());
            //解析content增加ext
            if (StringUtils.isNotBlank(dto.getMessage())) {
                JSONObject object = JSON.parseObject(URLDecoder.decode(dto.getMessage()));
                if (!object.getJSONObject("data").containsKey("ext")) {
                    object.getJSONObject("data").put("ext", new Object());
                    dto.setMessage(object.toJSONString());
                }
            }
            dto.setTimestamp(String.valueOf(v.getTime()));
            return dto;
        }));
    }

    @PostMapping("/web/api/webUploadMessage")
    public WebResponse uploadMessage(Long fromId, String toId, Integer chattype, String message, String mid) {
        Message m = new Message();
        m.setMessageId(mid);
        m.setContent(message);
        m.setTime(System.currentTimeMillis());
        m.setReferId(Long.parseLong(toId));
        m.setReferType(chattype);
        m.setUserId(fromId);
        messageService.save(m, false);
        return new WebResponse(1);
    }

    @PostMapping("/web/api/withdrawMessage")
    public WebResponse cancleMessage(String mid, String message) {

        Example se = new Example(Message.class);
        se.createCriteria().andEqualTo("messageId", mid);
        Message m = messageMapper.selectOneByExample(se);


        if (Objects.nonNull(m)) {
            m.setContent(message);//删除
            messageMapper.updateByPrimaryKeySelective(m);
        }

        return new WebResponse(1);
    }

    @PostMapping("/web/api/addgroupnum")
    public WebResponse addGroupMember(String jsonStr) {

        JSONObject command = JSON.parseObject(jsonStr);
        JSONArray arr = command.getJSONObject("command").getJSONArray("fields");
        for (int i = 0; i < arr.size(); i++) {
            if (arr.getJSONObject(i).getString("var").equals("oid")) {
                String s = arr.getJSONObject(i).getString("value");
                arr.getJSONObject(i).put("value", JSON.parseArray(s, String.class));
            }
        }

        System.out.println(JSON.toJSONString(command));

        return new WebResponse(1, JSON.parseObject(imApiService.addGroupMember(JSON.toJSONString(command))));
    }

    @PostMapping("/web/api/delgroupnum")
    public WebResponse deleteGroupMember(String jsonStr) {

        JSONObject command = JSON.parseObject(jsonStr);
        JSONArray arr = command.getJSONObject("command").getJSONArray("fields");
        for (int i = 0; i < arr.size(); i++) {
            if (arr.getJSONObject(i).getString("var").equals("oid")) {
                String s = arr.getJSONObject(i).getString("value");
                arr.getJSONObject(i).put("value", JSON.parseArray(s, String.class));
            }
        }

        System.out.println(JSON.toJSONString(command));

        return new WebResponse(1, JSON.parseObject(imApiService.deleteGroupMember(JSON.toJSONString(command))));
    }

    @PostMapping("/web/api/delgroup")
    public WebResponse deleteGroup(String jsonStr) {

        return new WebResponse(1, JSON.parseObject(imApiService.deleteGroup(jsonStr)));
    }

    @PostMapping("/web/api/getQruuid")
    public WebResponse getQruid() {

        return new WebResponse(1, UUID.randomUUID().toString().replace("-", "").substring(0, 16));

    }


    @GetMapping("/web/api/webLogin")
    public FsResponse webLogin(String qruuid) {

        User user = userService.selectById(getUserId());
        if (Objects.isNull(user)) {
            return FsResponseGen.fail(YiChatMsgCode.SYSTEM_PARAM_ERROR);
        }

        User u = new User();
        u.setId(user.getId());
        user.setQrcode(qruuid);
        userMapper.updateByPrimaryKeySelective(user);

        return FsResponseGen.success();

    }

    @PostMapping("/web/api/checkQruuid")
    public WebResponse checkQruuid(String qruuid) {

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("qruuid", qruuid);
        List<User> list = userMapper.selectByExample(example);
        if (GeneralUtils.isNotNullOrEmpty(list)) {

            User user = list.get(0);

            UserIm userIm = userService.selectIm(user.getId());

            WebLoginDto dto = new WebLoginDto();
            dto.setCardId(user.getAppid());
            dto.setNick(user.getNick());
            dto.setAvatar(user.getAvatar());
            dto.setUsernick(user.getNick());
            dto.setFxid(user.getAppid());
            dto.setHxpassword(userIm.getImPassword());
            dto.setImPassword(userIm.getImPassword());
            dto.setPassword(user.getPassword());
            dto.setTel(user.getMobile());
            return new WebResponse(1, dto);
        }

        return new WebResponse(0);

    }

}
