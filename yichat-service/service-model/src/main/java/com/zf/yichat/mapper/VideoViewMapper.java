package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.VideoView;
import org.apache.ibatis.annotations.Param;

public interface VideoViewMapper extends FsMapper<VideoView> {

    void saveOrUpdate(@Param("videoId") Long videoId, @Param("userId") Long userId);
}