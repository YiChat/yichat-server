package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.UserBalance;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface UserBalanceMapper extends FsMapper<UserBalance> {
    int updateBalance(@Param("userId") Long userId, @Param("money") BigDecimal money, @Param("freezeMoney") BigDecimal freezeMoney, @Param("incomeMoney") BigDecimal incomeMoney);
}