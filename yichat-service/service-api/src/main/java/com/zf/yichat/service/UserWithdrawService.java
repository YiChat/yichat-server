package com.zf.yichat.service;

import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:42 2019/5/28 2019
 */
public interface UserWithdrawService {


    FsResponse apply(String cardNumber, String memo, BigDecimal money, Long userId);

    FsResponse selectList(FsPage page, Long userId);

}
