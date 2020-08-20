package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.VideoPraise;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface VideoPraiseMapper extends FsMapper<VideoPraise> {
    Boolean limitMoney(@Param("userId") Long userId, @Param("limit") BigDecimal limitMoney);

    void batchUpdateBalanceStatus(@Param("vpId") Long vpId);
}