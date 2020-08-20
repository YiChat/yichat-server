package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.dto.UserStg;
import com.zf.yichat.model.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper extends FsMapper<Friend> {
    Friend selectOneFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    Friend selectOneExistFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    List<UserStg> selectFriendList(@Param("userId") Long userId);

    void insertOrUpdate(@Param("userId") Long userId, @Param("friendId") Long userId1);
}