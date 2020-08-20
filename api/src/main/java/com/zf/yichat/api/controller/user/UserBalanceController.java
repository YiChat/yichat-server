package com.zf.yichat.api.controller.user;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.BalanceListRequest;
import com.zf.yichat.api.dto.resp.BalanceDto;
import com.zf.yichat.model.UserBalance;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.SysDictService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.NumberUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.DictKey;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 13:54 2019/6/11 2019
 */
@RestController
public class UserBalanceController extends BaseController {

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private SysDictService sysDictService;

    /**
     * 余额
     */
    @RequestMapping("user/balance")
    public FsResponse balance() {

        UserBalance balance = balanceService.selectByUserId(getUserId());
        Contracts.assertNotNull(balance, YiChatMsgCode.SYSTEM_USER_BALANCE_ACCOUNT.msg());
        BalanceDto dto = new BalanceDto();
        dto.setBalance(NumberUtils.scale2HalfUp(balance.getIncomeBalance()).toString());
        return FsResponseGen.successData(dto);
    }


    /**
     * 余额列表
     */
    @RequestMapping("user/balance/list")
    public FsResponse balanceList(@RequestBody BalanceListRequest params) {
        params.valid();

        return balanceService.selectList(FsPage.init(params.getPageNo(), params.getPageSize()), params.getType(), getUserId());
    }


    /**
     * 充值项目列表
     */
    @PostMapping("user/deposit/item")
    public FsResponse depositItem() {

        return FsResponseGen.successData(DtoChangeUtils.getList(sysDictService.selectData(DictKey.balance_deposit).stream().filter(s -> s.getStatus() == null || s.getStatus() == 0).collect(Collectors.toList()), v -> v));
    }


}
