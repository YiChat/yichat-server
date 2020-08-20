package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.dto.TrendCommentListDto;
import com.zf.yichat.model.TrendComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrendCommentMapper extends FsMapper<TrendComment> {

    List<TrendCommentListDto> selectList(@Param("trendId") Long trendId, @Param("userId") Long userId);
}