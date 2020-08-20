package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.dto.MessageStg;
import com.zf.yichat.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends FsMapper<Message> {
    List<Message> selectLastListByGroup(@Param("groupId") Long referId, @Param("time") Long time, @Param("limit") Integer limit);

    MessageStg countGroupUnreadCount(@Param("groupId") Integer groupId, @Param("time") Long time);

    List<Message> selectHistoryListByGroup(@Param("groupId") Long referId, @Param("time") Long time, @Param("addTime") Long addTime, @Param("limit") Integer limit);

    List<Message> selectHistoryListByUser(@Param("referId") Long referId, @Param("userId") Long userId, @Param("time") Long time, @Param("addTime") Long addTime, @Param("limit") Integer limit);
}