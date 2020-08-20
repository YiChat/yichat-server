package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.VideoPlayRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface VideoPlayRecordMapper extends FsMapper<VideoPlayRecord> {
    Boolean limitMoney(@Param("userId") Long userId, @Param("limitMoney") BigDecimal limitMoney);

    void updateBalanceStatus(@Param("recordId") Long recordId);

    VideoPlayRecord selectByVideoId(@Param("videoId") Long videoId, @Param("userId") Long userId);
}