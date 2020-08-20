package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.UserWidthdrawDto;
import com.zf.yichat.mapper.UserWithdrawMapper;
import com.zf.yichat.model.UserWithdraw;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.UserWithdrawService;
import com.zf.yichat.service.dto.UserWithdrawDto;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.NumberUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.BalanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:56 2019/7/25 2019
 */
@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {


    @Autowired
    private BalanceService balanceService;

    @Autowired
    private UserWithdrawMapper userWithdrawMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FsResponse apply(String cardNumber, String memo, BigDecimal money, Long userId) {

        BigDecimal income = balanceService.selectByUserId(userId).getIncomeBalance();
        //余额不足
        if (income.compareTo(money) < 0) {
            return FsResponseGen.successData(YiChatMsgCode.WITHDRAW_APPLY_BALANCE_LITTLE, null);
        }

        //生成提现申请
        UserWithdraw withdraw = new UserWithdraw();
        withdraw.setBankNumber(cardNumber);
        withdraw.setMemo(memo);
        withdraw.setMoney(money);
        withdraw.setUserId(userId);
        withdraw.setCtime(new Date());
        withdraw.setStatus(0);
        userWithdrawMapper.insertUseGeneratedKeys(withdraw);

        //扣除余额到冻结
        balanceService.update(userId, BalanceType.WITHDRAW_APPLY, money, Long.valueOf(withdraw.getId()), "提现申请");
        return FsResponseGen.successData(DtoChangeUtils.getDto(NumberUtils.halfTwo(income.subtract(money)), v -> {
            UserWidthdrawDto dto = new UserWidthdrawDto();
            dto.setBalance(v);
            return dto;
        }));
    }

    @Override
    public FsResponse selectList(FsPage page, Long userId) {
        Example example = new Example(UserWithdraw.class);
        example.createCriteria().andEqualTo("userId", userId);
        example.setOrderByClause(" ctime desc");
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(userWithdrawMapper.selectByExample(example), v -> {
            UserWithdrawDto dto = new UserWithdrawDto();
            dto.setMoney(v.getMoney());
            dto.setRefuseReason(v.getRefuseReason());
            dto.setStatus(v.getStatus());
            if (v.getStatus() == 0) {
                dto.setTime(DateUtils.formatDate(v.getCtime()));
            } else {
                dto.setTime(DateUtils.formatDate(v.getCheckTime()));
            }
            dto.setBankNumber(v.getBankNumber());
            return dto;
        });
    }

    private Integer countExistApply(Long userId) {
        Example example = new Example(UserWithdraw.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("status", 0);
        return userWithdrawMapper.selectCountByExample(example);
    }
}
