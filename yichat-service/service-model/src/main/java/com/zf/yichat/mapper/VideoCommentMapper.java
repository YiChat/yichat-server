package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.VideoComment;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface VideoCommentMapper extends FsMapper<VideoComment> {

    void incrementPraiseCount(@Param("commentId") Long commentId);

    void reductPraiseCount(@Param("commentId") Long commentId);

    void incrementReplyCount(@Param("commentId") Long commentId);

    Boolean limitMoney(@Param("userId") Long userId, @Param("limit") BigDecimal limitMoney);

    void updateBalanceStatus(@Param("commentId") Long commentId);

    int updateReplyStatus(@Param("srcId") Long srcCommentId);
}