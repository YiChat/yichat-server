package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.TrendPraise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrendPraiseMapper extends FsMapper<TrendPraise> {
    List<TrendPraise> selectList(@Param("trendId") Long trendId, @Param("userId") Long userId);
}