package com.zf.yichat.im.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.TigGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TigGroupMapper extends FsMapper<TigGroup> {
    List<TigGroup> selectList(@Param("userId") Long userId);
}