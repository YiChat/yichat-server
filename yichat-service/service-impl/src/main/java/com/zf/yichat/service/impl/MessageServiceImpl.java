package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.MessageListDto;
import com.zf.yichat.dto.MessageUnreadDto;
import com.zf.yichat.mapper.MessageDelTimeMapper;
import com.zf.yichat.mapper.MessageMapper;
import com.zf.yichat.mapper.MessageStoreMapper;
import com.zf.yichat.model.*;
import com.zf.yichat.service.*;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.GroupRoleType;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:57 2019/6/17 2019
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PushService pushService;

    @Autowired
    private MessageStoreMapper messageStoreMapper;

    @Autowired
    private RobotService robotService;


    @Autowired
    private MessageDelTimeMapper messageDelTimeMapper;

    @Override
    public FsResponse save(Message message, boolean isRobot) {

        //解析content
        try {
            String text = URLDecoder.decode(message.getContent(), "utf-8");
            JSONObject data = JSON.parseObject(text).getJSONObject("data");
            message.setText(data.getJSONObject("body").getString("content"));


            userService.updateTimestamp(message.getUserId(), System.currentTimeMillis());

            String msgType = data.getString("msgType");
            JSONObject ext = data.getJSONObject("ext");

            //群消息进入机器人发送消息方法
            if (message.getReferType() == 2) {
                robotService.sendMessage(message, ext.getString("nick"));
            }

            if (isRobot) {
                return FsResponseGen.success();
            }


            if (ext != null) {
                String action = ext.containsKey("action") ? ext.getString("action") : null;

                //清聊服务器抢红包消息不存储
//                if (StringUtils.equals(action, "10004")) {
//                    return FsResponseGen.success();
//                }

                text = message.getText();
                //红包
                if (StringUtils.equals(action, "10001")) {
                    text = "发送了一个红包";
                    //消息撤回
                } else if (StringUtils.equals(action, "6000") || StringUtils.equals(action, "6001")) {
                    text = "撤回了一条消息";
                } else if (StringUtils.equals(msgType, "2001")) {

                    //图片
                } else if (StringUtils.equals(msgType, "2002")) {
                    text = "发送了一张图片";
                } else if (StringUtils.equals(msgType, "2003")) {
                    text = "发送了一条语音消息";
                } else if (StringUtils.equals(msgType, "2004")) {
                    text = "发送了一条视频";

                } else if (StringUtils.equals(msgType, "2005")) {
                    text = "上传了一条文件";

                } else if (StringUtils.equals(msgType, "2006")) {
                    text = "发送了一条位置消息";
                }

                //推送
                if (message.getReferType() == 1) {
                    pushService.sendSingle(message.getReferId(), ext.getString("nick"), text);
                } else if (message.getReferType() == 2) {
                    String title = groupService.selectById(message.getReferId().intValue()).getName();
                    pushService.sendGroup(message.getReferId().intValue(), title, text);
                }
            }

            messageMapper.insertSelective(message);
            storeMessage(message);


        } catch (Exception e) {
            e.printStackTrace();

            System.out.println(" 暂时消息解析出错 ,消息不保存");
            return FsResponseGen.fail(YiChatMsgCode.MESSAGE_UPLOAD_CONTENT_FAILED);

        }

        return FsResponseGen.success();


    }

    @Async
    @Override
    public void storeMessage(Message copy) {
        MessageStore store = new MessageStore();
        store.setUserId(copy.getUserId());
        store.setReferType(copy.getReferType());
        store.setReferId(copy.getReferId());
        store.setTime(copy.getTime());
        store.setStatus(copy.getStatus());
        store.setMessageId(copy.getMessageId());
        store.setCtime(copy.getCtime());
        store.setContent(copy.getContent());
        store.setText(copy.getText());

        messageStoreMapper.insertSelective(store);
    }

    @Override
    public FsResponse selectList(FsPage page, Integer referType, String referId, Long time, Long userId, boolean isLess) {
        Example example = new Example(Message.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 0).andEqualTo("referType", referType)
                .andEqualTo("referId", referId);

        if (Objects.nonNull(time) && time != -1) {
            if (isLess) {
                criteria.andLessThan("time", time);
            } else {
                criteria.andGreaterThan("time", time);
            }
        }

        //发送单人
        if (referType == 1) {
            criteria.andEqualTo("userId", userId);
        } else { //群
            TigGroupMember member = groupService.selectGroupMember(Integer.parseInt(referId), userId);
            Contracts.assertNotNull(member, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
            criteria.andGreaterThanOrEqualTo("time", member.getTimestamp().getTime());
        }
        example.setOrderByClause(" time desc");
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        FsResponse res = DtoChangeUtils.getPageList(messageMapper.selectByExample(example), (Message v) -> {
            MessageListDto dto = new MessageListDto();


            dto.setTime(v.getTime());
            dto.setContent(v.getContent());
            dto.setUserId(v.getUserId());
            dto.setMessageId(v.getMessageId());
            if (isLess) {
                User user = Optional.ofNullable(userService.selectById(v.getUserId())).orElse(new User());
                dto.setAvatar(user.getAvatar());
                dto.setNick(user.getNick());
                dto.setRoleType(groupService.selectRoleType(Integer.parseInt(referId), v.getUserId()).getVal());
            }
            return dto;
        });

        if (res.getCount() > 0) {
            MessageListDto dto = (((List<MessageListDto>) (res.getData())).get(0));

            User user = Optional.ofNullable(userService.selectById(dto.getUserId())).orElse(new User());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
        }

        return res;
    }

    @Override
    public List<MessageListDto> selectHistoryList(Integer limit, Integer groupId, Long time, Long userId) {
        if (Objects.isNull(time) || time == -1) {
            time = null;
        }

        //默认拉取20条
        if (Objects.isNull(limit)) {
            limit = 20;
        }

        TigGroupMember member = groupService.selectGroupMember(groupId, userId);
        if (Objects.isNull(member)) {
            return new ArrayList<>();
        }


        Long addTime = selectGroupDelTime(userId, groupId);

        addTime = (member.getTimestamp().getTime() > addTime ? member.getTimestamp().getTime() : addTime);

        List<MessageListDto> list = DtoChangeUtils.getList(messageMapper.selectHistoryListByGroup(groupId.longValue(), time, addTime, limit), v -> {
            MessageListDto dto = new MessageListDto();


            dto.setTime(v.getTime());
            dto.setContent(v.getContent());
            dto.setUserId(v.getUserId());
            dto.setMessageId(v.getMessageId());

            User user = Optional.ofNullable(userService.selectById(v.getUserId())).orElse(new User());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setRoleType(groupService.selectRoleType(groupId, v.getUserId()).getVal());

            return dto;
        });

        //app页面显示要正序
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<MessageListDto> selectSingleHistoryList(Integer limit, Long referUserId, Long time, Long userId) {
        if (Objects.isNull(time) || time == -1) {
            time = null;
        }

        //默认拉取20条
        if (Objects.isNull(limit)) {
            limit = 20;
        }

        //获取限制开始时间
        Long addTime = selectFriendDelTime(userId, referUserId);

        List<MessageListDto> list = DtoChangeUtils.getList(messageMapper.selectHistoryListByUser(referUserId, userId, time, addTime, limit), v -> {
            MessageListDto dto = new MessageListDto();


            dto.setTime(v.getTime());
            dto.setContent(v.getContent());
            dto.setUserId(v.getUserId());
            dto.setMessageId(v.getMessageId());

            User user = Optional.ofNullable(userService.selectById(v.getUserId())).orElse(new User());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());


            return dto;
        });

        //app页面显示要正序
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<MessageListDto> selecLastList(Integer groupId, Long userId, Long timestamp) {

        TigGroupMember groupMember = groupService.selectGroupMember(groupId, userId);
        Contracts.assertNotNull(groupMember, YiChatMsgCode.GROUP_MEMBER_NONE.msg());

        //进群时间和时间戳做比较
        Long time = groupMember.getTimestamp().getTime();
        if (Objects.nonNull(timestamp)) {
            time = time.compareTo(timestamp) > 0 ? time : timestamp;
        }

        Long addTime = selectGroupDelTime(userId, groupId);

        time = (time > addTime ? time : addTime);


        List<MessageListDto> list = DtoChangeUtils.getList(messageMapper.selectLastListByGroup(groupId.longValue(), time, 15), (Message v) -> {
            MessageListDto dto = new MessageListDto();
            User user = Optional.ofNullable(userService.selectById(v.getUserId())).orElse(new User());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setTime(v.getTime());
            dto.setContent(v.getContent());
            dto.setUserId(v.getUserId());
            dto.setMessageId(v.getMessageId());
            return dto;
        });

        if (GeneralUtils.isNotNullOrEmpty(list)) {
            Collections.reverse(list);
        }

        userService.updateTimestamp(userId, System.currentTimeMillis());
        return list;
    }


    @Override
    public List<MessageListDto> selectWebLastList(Integer groupId, Long userId, Long timestamp) {
        Example example = new Example(Message.class);

        TigGroupMember groupMember = groupService.selectGroupMember(groupId, userId);

        Contracts.assertNotNull(groupMember, YiChatMsgCode.GROUP_MEMBER_NONE.msg());
        //
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 0).andEqualTo("referType", 2)
                .andEqualTo("referId", groupId)
                .andGreaterThan("time", groupMember.getTimestamp().getTime());

        if (Objects.nonNull(timestamp)) {
            criteria.andGreaterThan("time", timestamp);
        }


        example.setOrderByClause(" time desc");
        PageHelper.startPage(1, 20);
        List<MessageListDto> list = DtoChangeUtils.getList(messageMapper.selectByExample(example), (Message v) -> {
            MessageListDto dto = new MessageListDto();
            User user = Optional.ofNullable(userService.selectById(v.getUserId())).orElse(new User());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setTime(v.getTime());
            dto.setContent(v.getContent());
            dto.setUserId(v.getUserId());
            dto.setMessageId(v.getMessageId());
            //dto.setRoleType(groupService.selectRoleType(Integer.parseInt(referId), v.getUserId()).getVal());
            return dto;
        });

        if (GeneralUtils.isNotNullOrEmpty(list)) {
            Collections.reverse(list);
        }

        userService.updateTimestamp(userId, System.currentTimeMillis());
        return list;
    }

    @Override
    public void deleteMsg(String messageId) {
        Example se = new Example(Message.class);
        se.createCriteria().andEqualTo("messageId", messageId);
        Message message = messageMapper.selectOneByExample(se);
        //消息为空 消息数据已被清除 则返回成功
        if (Objects.isNull(message)) {
            return;
        }

        message.setStatus(1);
        messageMapper.updateByPrimaryKeySelective(message);

    }

    @Override
    public void cancle(String messageId, Long userId, String content) {

        Example se = new Example(Message.class);
        se.createCriteria().andEqualTo("messageId", messageId);
        Message message = messageMapper.selectOneByExample(se);
        //消息为空 消息数据已被清除 则返回成功
        if (Objects.isNull(message)) {
            return;
        }

        //群消息进入机器人发送消息方法
        if (message.getReferType() == 2) {
            String text = null;
            try {
                text = URLDecoder.decode(message.getContent(), "utf-8");
                JSONObject data = JSON.parseObject(text).getJSONObject("data");
                message.setText(data.getJSONObject("body").getString("content"));
                JSONObject ext = data.getJSONObject("ext");
                robotService.sendMessage(message, ext.getString("nick"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        //Contracts.assertNotNull(message.getUserId().compareTo(userId) == 0, YiChatMsgCode.MESSAGE_CANCLE_AUTH.msg());
        //个人信息
        if (message.getReferType() == 1) {
            Contracts.assertTrue(userId.compareTo(message.getUserId()) == 0, YiChatMsgCode.MESSAGE_CANCLE_AUTH.msg());
            message.setContent(content);//删除
            messageMapper.updateByPrimaryKeySelective(message);
        } else {

            Integer type = groupService.selectRoleType(message.getReferId().intValue(), userId).getVal();
            //群组消息
            //自己消息直接删除
            if (userId.compareTo(message.getUserId()) == 0) {
                message.setContent(content);
                messageMapper.updateByPrimaryKeySelective(message);
            } else if (GroupRoleType.creator.getVal() == type) {//群主直接删除
                message.setContent(content);
                messageMapper.updateByPrimaryKeySelective(message);
                //管理员直接删除
            } else if (groupService.isAdmin(message.getReferId().intValue(), userId)) {
                message.setContent(content);
                messageMapper.updateByPrimaryKeySelective(message);
            } else {
                //必定报错
                Contracts.assertTrue(type == 100, YiChatMsgCode.MESSAGE_CANCLE_AUTH.msg());
            }

            Example delEx = new Example(MessageStore.class);
            delEx.createCriteria().andEqualTo("messageId", messageId);
            //删除
            messageStoreMapper.deleteByExample(delEx);
        }
    }

    @Override
    public Integer countUnread(Long time, Integer v) {
        Example example = new Example(Message.class);
        example.createCriteria().andEqualTo("referId", v).andEqualTo("referType", 2).andGreaterThanOrEqualTo("time", time);
        return messageMapper.selectCountByExample(example);
    }

    @Override
    public FsResponse selectSearchList(FsPage page, List<Integer> groupId, String searchContent, Long userId) {
        Example example = new Example(Message.class);
        //

        List<TigGroupMember> groupList = null;

        if (GeneralUtils.isNullOrEmpty(groupId)) {
            groupList = groupService.selectGroupByUserId(userId);
        } else {
            groupList = groupService.selectGroupByIds(groupId, userId);
        }


        if (GeneralUtils.isNotNullOrEmpty(groupList)) {
            groupList.forEach(v -> {
                example.or(example.createCriteria().andEqualTo("status", 0).andEqualTo("referType", 2).
                        andLike("text", "%" + searchContent + "%").andEqualTo("referId", v.getGid()).andGreaterThanOrEqualTo("time", v.getTimestamp().getTime()));
            });

        }


        example.setOrderByClause(" time desc");
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(messageMapper.selectByExample(example), (Message v) -> {
            MessageListDto dto = new MessageListDto();
            User user = Optional.ofNullable(userService.selectById(v.getUserId())).orElse(new User());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setTime(v.getTime());
            dto.setContent(v.getContent());
            dto.setUserId(v.getUserId());
            dto.setMessageId(v.getMessageId());
            dto.setGroupId(v.getReferId().intValue());
            return dto;
        });
    }

    @Override
    public MessageUnreadDto selectGroupUnreadList(Integer groupId, Long time) {

        messageMapper.countGroupUnreadCount(groupId, time);
        return null;

    }

    @Override
    public void saveDelTime(Long userId, Integer referType, Long referId, Long time) {


        Example example = new Example(MessageDelTime.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("referId", referId)
                .andEqualTo("referType", referType);

        MessageDelTime delTime = messageDelTimeMapper.selectOneByExample(example);
        if (Objects.nonNull(delTime)) {
            delTime.setDelTime(Optional.ofNullable(time).orElse(System.currentTimeMillis()));
            messageDelTimeMapper.updateByPrimaryKeySelective(delTime);
        } else {
            delTime = new MessageDelTime();
            delTime.setDelTime(Optional.ofNullable(time).orElse(System.currentTimeMillis()));
            delTime.setReferId(referId);
            delTime.setReferType(referType);
            delTime.setUserId(userId);
            messageDelTimeMapper.insertSelective(delTime);
        }
    }

    @Override
    public long selectFriendDelTime(Long userId, Long friendId) {
        Example example = new Example(MessageDelTime.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("referId", friendId)
                .andEqualTo("referType", 0);
        return Optional.ofNullable(messageDelTimeMapper.selectOneByExample(example)).map(MessageDelTime::getDelTime).orElse(0L);
    }

    @Override
    public long selectGroupDelTime(Long userId, Integer groupId) {
        Example example = new Example(MessageDelTime.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("referId", groupId)
                .andEqualTo("referType", 1);
        return Optional.ofNullable(messageDelTimeMapper.selectOneByExample(example)).map(MessageDelTime::getDelTime).orElse(0L);
    }
}
