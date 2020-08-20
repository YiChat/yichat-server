package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.MessageBodyDto;
import com.zf.yichat.mapper.AppConfigMapper;
import com.zf.yichat.mapper.FriendApplyMapper;
import com.zf.yichat.mapper.FriendMapper;
import com.zf.yichat.mapper.UserMapper;
import com.zf.yichat.model.AppConfig;
import com.zf.yichat.model.Friend;
import com.zf.yichat.model.FriendApply;
import com.zf.yichat.model.User;
import com.zf.yichat.service.FriendService;
import com.zf.yichat.service.ImApiService;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.service.dto.FriendApplyListDto;
import com.zf.yichat.service.dto.FriendListDto;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.AppConfigType;
import com.zf.yichat.vo.FriendCheckStatus;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:59 2019/6/3 2019
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private FriendApplyMapper friendApplyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Autowired
    private ConfigSet configSet;

    @Autowired
    private ImApiService imApiService;

    @Override
    public void apply(Long userId, Long friendUserId, String reason) {


        //已是好友状态
        Friend friend = selectOne(userId, friendUserId);
        Contracts.assertTrue(Objects.isNull(friend), YiChatMsgCode.FRIEND_APPLY_ADD_EXIST_FRIEND.msg());
        //已存在申请记录更新申请时间
        FriendApply existApply = selectOneApply(userId, friendUserId);
        if (Objects.nonNull(existApply)) {
            //Contracts.assertTrue(Objects.isNull(existApply), YiChatMsgCode.FRIEND_APPLY_ADD_EXIST.msg());
            existApply.setDeleteUserStatus(FsConst.Status.EFFECT);
            existApply.setDeleteFriendStatus(FsConst.Status.EFFECT);
            existApply.setCtime(new Date());
            friendApplyMapper.updateByPrimaryKeySelective(existApply);
        } else {
            FriendApply apply = new FriendApply();
            apply.setUserId(userId);
            apply.setFriendId(friendUserId);
            apply.setCheckStatus(FriendCheckStatus.APPLY.getVal());
            apply.setReason(reason);
            friendApplyMapper.insertSelective(apply);
        }


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(Long userId, Long friendId, FriendCheckStatus status) {
        FriendApply apply = null;
        switch (status) {
            case PASS:
                apply = friendApplyMapper.selectByPrimaryKey(friendId);
                if (Objects.nonNull(apply) && Objects.isNull(apply.getCheckTime()) && apply.getFriendId().compareTo(userId) == 0) {
                    //修改状态
                    apply.setCheckStatus(FriendCheckStatus.PASS.getVal());
                    apply.setCheckTime(new Date());
                    friendApplyMapper.updateByPrimaryKeySelective(apply);

                    //修改对方存在添加我的待审核记录
                    FriendApply existApply = selectOneApply(userId, apply.getUserId());
                    if (Objects.nonNull(existApply)) {
                        existApply.setCheckStatus(FriendCheckStatus.PASS.getVal());
                        friendApplyMapper.updateByPrimaryKeySelective(existApply);
                    }

                    //如存在删除的好友关系则变更状态

                    //添加好友关系
                    Friend friend = friendMapper.selectOneExistFriend(userId, friendId);
                    if (Objects.isNull(friend)) {
                        friend = new Friend();
                        friend.setUserId(userId);
                        friend.setFriendId(apply.getUserId());
                        friendMapper.insertOrUpdate(userId, apply.getUserId());
                    } else {
                        friend.setStatus(FsConst.Status.EFFECT);
                        friendMapper.updateByPrimaryKeySelective(friend);
                    }

                }
               /* Contracts.assertNotNull(apply, YiChatMsgCode.FRIEND_CHECK_NONE.msg());
                Contracts.assertTrue(, YiChatMsgCode.FRIEND_CHECK_REPEAT.msg());
                Contracts.assertTrue(, YiChatMsgCode.FRIEND_CHECK_AUTH.msg());*/

                break;
            case REFUSE:
                //修改状态
                apply = friendApplyMapper.selectByPrimaryKey(friendId);
                Contracts.assertNotNull(apply, YiChatMsgCode.FRIEND_CHECK_NONE.msg());
                Contracts.assertTrue(apply.getFriendId().compareTo(userId) == 0, YiChatMsgCode.FRIEND_CHECK_AUTH.msg());
                apply.setCheckStatus(FriendCheckStatus.REFUSE.getVal());
                apply.setCheckTime(new Date());
                friendApplyMapper.updateByPrimaryKeySelective(apply);
                break;

            default:
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId, Long friendId) {
        Friend f = selectOne(userId, friendId);
        if (Objects.nonNull(f)) {
            f.setStatus(FsConst.Status.INVALID);
            friendMapper.updateByPrimaryKeySelective(f);
        }


        //删除记录
        /*Example example = new Example(FriendApply.class);
        example.createCriteria()
                .andEqualTo("status", ApiConst.Status.INVALID)
                .andEqualTo("checkStatus", FriendCheckStatus.PASS.getVal())
                .andIsNull("deleteUserId");
        FriendApply apply = friendApplyMapper.selectOneByExample(example);
        if (Objects.nonNull(apply)) {
            *//*apply.setDeleteTime(new Date());
            apply.setDeleteUserId(userId);
            apply.setCheckStatus(FriendCheckStatus.DELETE.getVal());
            friendApplyMapper.updateByPrimaryKeySelective(apply);*//*
        }*/


    }

    @Override
    public Friend selectOne(Long userId, Long friendId) {
        return friendMapper.selectOneFriend(userId, friendId);
    }

    @Override
    public FriendApply selectOneApply(Long userId, Long friendId) {
        Example example = new Example(FriendApply.class);
        //存在进行中的状态只会有一个
        example.createCriteria().andEqualTo("status", 0)
                .andEqualTo("userId", userId).andEqualTo("friendId", friendId)
                .andEqualTo("checkStatus", FriendCheckStatus.APPLY.getVal());
        return friendApplyMapper.selectOneByExample(example);
    }

    @Override
    public FsResponse selectApplyList(Integer pageNo, Integer pageSize, Long userId) {

        Example select = new Example(FriendApply.class);
        Integer[] checkStatus = {FriendCheckStatus.APPLY.getVal(), FriendCheckStatus.PASS.getVal()};
        select.createCriteria().andEqualTo("deleteFriendStatus", FsConst.Status.EFFECT)
                .andEqualTo("friendId", userId).andIn("checkStatus", Arrays.asList(checkStatus));
        select.or(select.createCriteria().andEqualTo("deleteUserStatus", FsConst.Status.EFFECT)
                .andEqualTo("userId", userId).andEqualTo("checkStatus", FriendCheckStatus.PASS.getVal()));

        //更新阅读时间戳
        saveApplyReadTimestamp(userId);

        //申请列表 只能看到被申请及申请同意后的数据
        //.andEqualTo("checkStatus", FriendCheckStatus.APPLY.getVal());
        select.setOrderByClause(" ifnull(check_time,ctime) desc");
        PageHelper.startPage(pageNo, pageSize);
        return DtoChangeUtils.getPageList(friendApplyMapper.selectByExample(select), v -> {
            FriendApplyListDto dto = new FriendApplyListDto();
            User user = userMapper.selectByPrimaryKey(v.getUserId().compareTo(userId) == 0 ? v.getFriendId() : v.getUserId());
            dto.setUserId(user.getId());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setFid(v.getId());
            dto.setReason(v.getReason());
            if (v.getCheckStatus() == 0) {//待审核
                dto.setStatus(v.getCheckStatus());
            } else {
                //当前用户是审核人
                if (v.getFriendId().compareTo(userId) == 0) {
                    dto.setStatus(1);
                } else {//当前用户是被审核人
                    dto.setStatus(2);
                }
            }
            return dto;
        });
    }

    @Override
    public boolean isFriend(Long userId, Long friendId) {
        return Objects.nonNull(selectOne(userId, friendId));
    }

    @Override
    public void remarkSet(Long userId, Long friendId, String remark) {

        Friend friend = selectOne(userId, friendId);
        Contracts.assertNotNull(friend, YiChatMsgCode.FRIEND_NONE.msg());
        if (friend.getUserId().compareTo(userId) == 0) {
            friend.setUserMark(remark);
        } else {
            friend.setFriendMark(remark);
        }

        friendMapper.updateByPrimaryKeySelective(friend);
    }

    @Override
    public FsResponse selectList(Integer pageNo, Integer pageSize, Long userId) {
        if (pageNo != -1) {

            PageHelper.startPage(pageNo, 5000);
        }

        Example select = new Example(Friend.class);
        select.createCriteria().andEqualTo("status", FsConst.Status.EFFECT);
        select.and(select.createCriteria().andEqualTo("friendId", userId)
                .orEqualTo("userId", userId));

        return DtoChangeUtils.getPageList(friendMapper.selectFriendList(userId), copy -> {
            FriendListDto dto = new FriendListDto();
            dto.setMobile(copy.getMobile());
            dto.setGender(copy.getGender());
            dto.setUserId(copy.getId());
            dto.setAvatar(copy.getAvatar());
            dto.setNick(copy.getNick());
            dto.setAppId(copy.getAppid());
            dto.setRemark(copy.getRemark());

            dto.setCtime(copy.getCtime());

            return dto;
        });
    }

    @Override
    public void deleteApply(Long userId, Long fid) {
        if (Objects.nonNull(fid)) {
            FriendApply apply = friendApplyMapper.selectByPrimaryKey(fid);
            if (Objects.nonNull(apply)) {
                if (apply.getFriendId().compareTo(userId) == 0) {
                    apply.setDeleteFriendStatus(FsConst.Status.INVALID);
                }

                if (apply.getUserId().compareTo(userId) == 0) {
                    apply.setDeleteUserStatus(FsConst.Status.INVALID);
                }

                friendApplyMapper.updateByPrimaryKeySelective(apply);
            }
        } else { //清除所有操作记录
            Example select = new Example(FriendApply.class);
            Integer[] checkStatus = {FriendCheckStatus.APPLY.getVal(), FriendCheckStatus.PASS.getVal()};
            select.createCriteria().andEqualTo("deleteFriendStatus", FsConst.Status.EFFECT)
                    .andEqualTo("friendId", userId).andIn("checkStatus", Arrays.asList(checkStatus));
            select.or(select.createCriteria().andEqualTo("deleteUserStatus", FsConst.Status.EFFECT)
                    .andEqualTo("userId", userId).andEqualTo("checkStatus", FriendCheckStatus.PASS.getVal()));
            select.setOrderByClause(" ifnull(check_time,ctime) desc");

            friendApplyMapper.selectByExample(select).forEach(apply -> {
                if (Objects.nonNull(apply)) {
                    if (apply.getFriendId().compareTo(userId) == 0) {
                        apply.setDeleteFriendStatus(FsConst.Status.INVALID);
                    }

                    if (apply.getUserId().compareTo(userId) == 0) {
                        apply.setDeleteUserStatus(FsConst.Status.INVALID);
                    }

                    friendApplyMapper.updateByPrimaryKeySelective(apply);
                }
            });

        }
    }

    @Override
    public int applyUnreadCount(Long userId) {

        Example ss = new Example(AppConfig.class);
        ss.createCriteria().andEqualTo("userId", userId).andEqualTo("type", AppConfigType.apply_read_time.getVal());
        AppConfig config = appConfigMapper.selectOneByExample(ss);


        Example example = new Example(FriendApply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("friendId", userId).andEqualTo("checkStatus", FriendCheckStatus.APPLY.getVal());

        if (Objects.nonNull(config)) {
            criteria.andGreaterThan("ctime", config.getMemo());
        }

        return friendApplyMapper.selectCountByExample(example);

    }

    @Async
    @Override
    public void saveApplyReadTimestamp(Long userId) {
        Example sel = new Example(AppConfig.class);
        sel.createCriteria().andEqualTo("userId", userId).andEqualTo("type", AppConfigType.apply_read_time.getVal());
        AppConfig config = appConfigMapper.selectOneByExample(sel);

        if (Objects.nonNull(config)) {
            config.setMemo(DateUtils.formatCurrentDate());
            appConfigMapper.updateByPrimaryKeySelective(config);
        } else {
            config = new AppConfig();
            config.setUserId(userId);
            config.setType(AppConfigType.apply_read_time.getVal());
            config.setMemo(DateUtils.formatCurrentDate());
            appConfigMapper.insertSelective(config);
        }

    }

    @Override
    public void duang(Long id, Long id1) {

        friendMapper.insertOrUpdate(id, id1);
        //发送消息
        User user = userMapper.selectByPrimaryKey(id);
        MessageBodyDto dto = new MessageBodyDto();
        MessageBodyDto.Ext ext = dto.new Ext();
        ext.setAvatar(user.getAvatar());
        ext.setNick(user.getNick());
        ext.setUserId(String.valueOf(user.getId()));

        MessageBodyDto.Body body = dto.new Body();
        body.setContent("我们已经成为好友，请开始聊天吧");


        MessageBodyDto.Data data = dto.new Data();
        data.setExt(ext);
        data.setBody(body);
        data.setChatType(2);
        data.setMsgType(2001);
        data.setMsgId(UUID.randomUUID().toString());
        data.setFrom(String.valueOf(id));
        data.setTimestamp(System.currentTimeMillis());

        data.setTo(String.valueOf(id1));


        dto.setData(data);
        dto.setType(2000);

        //发送消息
        imApiService.sendRobotMsg(2, id1, URLEncoder.encode(JSON.toJSONString(dto)));
    }
}
