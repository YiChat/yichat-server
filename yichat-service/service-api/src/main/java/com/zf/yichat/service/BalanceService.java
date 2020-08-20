package com.zf.yichat.service;

import com.zf.yichat.model.UserBalance;
import com.zf.yichat.model.UserTrade;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.PayType;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:43 2019/6/10 2019
 */
public interface BalanceService {

    /**
     * 余额账号创建
     */
    void create(Long userId);

    /**
     * 更新余额
     */
    void update(Long userId, BalanceType type, BigDecimal money, Long referId, String memo);

    /**
     * 更新支付密码
     */
    void updatePassword(Long userId, String password);

    /**
     * 校验支付密码
     */
    void checkPassword(Long userId, String password);

    UserBalance selectByUserId(Long userId);

    void record(Long userId, Integer type, BigDecimal money, BigDecimal balance, BigDecimal freezeMoney, BigDecimal incomeMoney, Long referId, String desc);

    FsResponse selectList(FsPage init, Integer type, Long userId);

    void checkWithdraw(Integer id, Integer status, String reason);

    /**
     * 生成交易记录
     *
     * @param userId    用户ID
     * @param tradeId   订单号
     * @param money     金额
     * @param payType   支付类型
     * @param tradeType 交易类型
     */
    Long createTradeRecord(Long userId, String tradeId, BigDecimal money, PayType payType, Integer tradeType);

    UserTrade selectTradeByTradeNo(String tradeNo);

    void completeTrade(Long id);
}
