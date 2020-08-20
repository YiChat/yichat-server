package com.zf.yichat.api.controller.pay;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.PayTradeStatusRequest;
import com.zf.yichat.api.dto.request.WeixinPayRequest;
import com.zf.yichat.model.User;
import com.zf.yichat.model.UserTrade;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.PayService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.service.config.ConfigPay;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:18 2019/8/14 2019
 */
@RequestMapping("pay")
@Controller
public class PayController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private PayService payService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private ConfigPay configPay;

    @RequestMapping("/weixin/pre")
    @ResponseBody
    public FsResponse weixinpay(@RequestBody WeixinPayRequest request) {
        request.valid();

        User user = userService.selectById(getUserId());
        Contracts.assertNotNull(user, YiChatMsgCode.USER_INFO_NONE.msg());
        Contracts.assertTrue(user.getStatus().compareTo(FsConst.Status.EFFECT) == 0, YiChatMsgCode.SYSTEM_USER_CLOSE.msg());

        return FsResponseGen.successData(payService.weixinPrePay(getUserId(), request.getType(), request.getMoney()));

    }

    @RequestMapping("/alipay/pre")
    @ResponseBody
    public FsResponse alipay(@RequestBody WeixinPayRequest request) {
        request.valid();


        if (configPay.getAlipay().getAppId().equals("0000")) {
            return FsResponseGen.failMsg("暂停充值！");
        }


        User user = userService.selectById(getUserId());
        Contracts.assertNotNull(user, YiChatMsgCode.USER_INFO_NONE.msg());
        Contracts.assertTrue(user.getStatus().compareTo(FsConst.Status.EFFECT) == 0, YiChatMsgCode.SYSTEM_USER_CLOSE.msg());

        return FsResponseGen.successData(payService.alipayPre(getUserId(), request.getType(), request.getMoney()));

    }


    @RequestMapping("/weixin/notify/")
    @ResponseBody
    public String weixinNotify(HttpServletRequest request) {


        System.out.println("微信回调开始");

        String resXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            //将InputStream转换成xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            return payService.payNotify(resXml);
        } catch (Exception e) {
            System.out.println("微信手机支付失败:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }

    @RequestMapping("/alipay/notify")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        return payService.alipayNotufy(request);
    }

    @RequestMapping("/trade/status")
    @ResponseBody
    public FsResponse tradeStatus(@RequestBody PayTradeStatusRequest params) {

        params.valid();
        UserTrade trade = balanceService.selectTradeByTradeNo(params.getTradeNo());
        return FsResponseGen.successData(Optional.ofNullable(trade).map(UserTrade::getStatus).orElse(0));
    }


}
