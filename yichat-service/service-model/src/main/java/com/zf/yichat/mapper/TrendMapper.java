package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.Trend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrendMapper extends FsMapper<Trend> {
    List<Trend> selectFriendList(@Param("userId") Long userId);

    void updatePraiseCount(@Param("trendId") Long trendId, @Param("count") int i);

    void updateCommentCount(@Param("trendId") Long trendId, @Param("count") int i);
}