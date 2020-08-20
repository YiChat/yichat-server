package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper extends FsMapper<Video> {
    List<Video> list(@Param("userId") Long userId, @Param("type") Integer type);

    void incrementCommentCount(@Param("videoId") Long videoId);

    void reduceCommentCount(@Param("videoId") Long videoId, @Param("count") int num);

    void incrementPraiseCount(@Param("videoId") Long videoId);

    void incrementViewCount(@Param("videoId") Long videoId);

    void reducePraiseCount(@Param("videoId") Long videoId);

    List<Video> selectCreateList(@Param("userId") Long userId);

    List<Video> selectFriendList(@Param("userId") Long userId);

    List<Video> selectPraiseList(@Param("userId") Long userId);
}