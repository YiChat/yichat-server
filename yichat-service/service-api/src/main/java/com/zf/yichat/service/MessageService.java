package com.zf.yichat.service;

import com.zf.yichat.dto.MessageListDto;
import com.zf.yichat.dto.MessageUnreadDto;
import com.zf.yichat.model.Message;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:56 2019/6/17 2019
 */
public interface MessageService {

    FsResponse save(Message message, boolean isRobot);

    void storeMessage(Message message);

    FsResponse selectList(FsPage page, Integer referType, String referId, Long time, Long userdId, boolean isLess);

    /**
     * 群聊历史消息
     */
    List<MessageListDto> selectHistoryList(Integer limit, Integer groupId, Long time, Long userId);

    /**
     * 单聊历史消息
     */
    List<MessageListDto> selectSingleHistoryList(Integer limit, Long referUserId, Long time, Long userId);

    List<MessageListDto> selecLastList(Integer groupId, Long userId, Long timestamp);

    List<MessageListDto> selectWebLastList(Integer groupId, Long userId, Long timestamp);

    void cancle(String messageId, Long userId, String content);

    Integer countUnread(Long time, Integer v);

    FsResponse selectSearchList(FsPage init, List<Integer> groupId, String searchContent, Long userId);

    MessageUnreadDto selectGroupUnreadList(Integer groupId, Long time);

    void deleteMsg(String messageId);

    void saveDelTime(Long userId, Integer referType, Long referId, Long time);

    long selectFriendDelTime(Long userId, Long friendId);

    long selectGroupDelTime(Long userId, Integer groupId);
}
