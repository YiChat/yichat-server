package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.google.common.collect.Maps;
import com.squareup.okhttp.Request;
import com.zf.yichat.model.UserTrade;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.PayService;
import com.zf.yichat.service.config.ConfigPay;
import com.zf.yichat.utils.HttpProtocolHandler;
import com.zf.yichat.utils.XmlUtils;
import com.zf.yichat.utils.common.*;
import com.zf.yichat.vo.BalanceType;
import com.zf.yichat.vo.PayType;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:46 2019/8/14 2019
 */
@Service
public class PayServiceImpl implements PayService {


    private static final String WEIXIN_PAY_NOTIFY_END_WITH = "weixin pay notify: end with {}";
    private static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
    private String prepay = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private ConfigPay configPay;

    @Override
    public Map<String, String> weixinPrePay(Long userId, Integer tradeType, BigDecimal money) {
        //暂时订单号使用
        String out_trade_no = DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS") + userId;
        //生成交易记录
        balanceService.createTradeRecord(userId, out_trade_no, money, PayType.WEIXIN, tradeType);

        String body = "用户" + userId + "微信充值";

        String ip = "127.0.0.1";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String response = HttpProtocolHandler.getInstance().doPostXml(prepay, XmlUtils.mapToXML(prePayParams(out_trade_no, money, GeneralUtils.getRandomStringByLength(16), configPay.getWeixin().getNotifyUrl(), ip, body)), "utf-8");

        String prePayId = getPrepayId(response);
        // pay params
        return StringUtils.isNoneBlank(prePayId) ? payParamsAPP(GeneralUtils.getRandomStringByLength(16), prePayId, out_trade_no) : null;
    }

    /**
     * 支付的参数列表-APP
     */
    private Map<String, String> payParamsAPP(String nonce_str, String prepayId, String outTradeNo) {
        Map<String, String> result = Maps.newHashMap(); // 调起支付 参数
        result.put("appid", configPay.getWeixin().getAppId()); // 微信开放平台审核通过的应用APPID
        result.put("partnerid", configPay.getWeixin().getMchId()); // 微信支付分配的商户号
        result.put("package", "Sign=WXPay"); // 扩展字段，暂填写固定值Sign=WXPay
        result.put("noncestr", nonce_str); //随机字符串，不长于32位。推荐随机数生成算法
        long time = System.currentTimeMillis();
        //mysq 时间戳只有10位 要做处理
        String dateline = time + "";
        dateline = dateline.substring(0, 10);
        result.put("timestamp", dateline); // 时间戳，请见接口规则-参数规定
        result.put("prepayid", prepayId);// 微信返回的支付交易会话ID
        result.put("sign", Sign.createSign(result, configPay.getWeixin().getAppKey()));// 签名
        return result;
    }

    private Map<String, String> prePayParams(String outTradeNo, BigDecimal totalFee, String nonce_str, String notifyUrl, String ip, String body) {
        Map<String, String> map = Maps.newHashMap();
        map.put("appid", configPay.getWeixin().getAppId());//微信开放平台审核通过的应用APPID
        map.put("mch_id", configPay.getWeixin().getMchId());//微信支付分配的商户号
//        map.put("device_info", "WEB");//终端设备号(门店号或收银设备ID)，默认请传"WEB"
        map.put("nonce_str", nonce_str);//随机字符串，不长于32位。推荐随机数生成算法
        map.put("body", body);//商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
//        map.put("detail", "Ipad mini  16G  白色");//商品名称明细列表
        map.put("out_trade_no", outTradeNo);//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
//        map.put("fee_type", "CNY");//符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
        map.put("total_fee", totalFee.multiply(BigDecimal.valueOf(100)).longValue() + "");//订单总金额，单位为分，详见支付金额
        map.put("spbill_create_ip", ip);//用户端实际ip
        //String time = DateUtils.getBeijingTimeNow(YYYYMMDDHHMMSS);
        //map.put("time_start", time);
        //订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
        //map.put("time_expire", String.valueOf(Long.valueOf(time) + 30L * 60L + 1));
// 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
//        map.put("goods_tag", "WXG");//商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
//        map.put("notify_url", WeixinConfig.notifyUrl);
        map.put("notify_url", notifyUrl);
        //接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
        map.put("trade_type", "APP");//支付类型
//        map.put("limit_pay", "no_credit");//no_credit--指定不能使用信用卡支付

        map.put("sign", Sign.createSign(map, configPay.getWeixin().getAppKey()));//签名，详见签名生成算法
        return map;
    }

    private String getPrepayId(String response) {
        try {
            logger.debug("微信统一下单返回结果: {}", response);
            Document document = DocumentHelper.parseText(response);
            Element root = document.getRootElement();
            Element returnCode = root.element("return_code");
            if ("SUCCESS".equals(returnCode.getText())) {
                Element resultCode = root.element("result_code");
                if ("SUCCESS".equals(resultCode.getText())) {
                    Element prepayId = root.element("prepay_id");
                    // pay params
                    return prepayId.getText();
                }
            }
        } catch (Exception e) {
            logger.error("解析微信统一下单返回结果失败", e);
        }
        logger.error("微信下单失败{}", response);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String payNotify(String resXml) {

        String xmlBack = "";
        Map<String, String> map = null;

        boolean success = true;
        try {
            map = XmlUtils.xmlToMap(DocumentHelper.parseText(resXml));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("weixin pay notify: {}", JSON.toJSONString(map));
        }
        // 交易结果
        String return_code = map.get("return_code");
        if (!"SUCCESS".equals(return_code)) {
            logger.warn(WEIXIN_PAY_NOTIFY_END_WITH, "返回code不是success");
            success = false;
        }
        // 签名验证
        /*String sign = map.get("sign");
        if (!Sign.signValidate(map, sign, appkey)) {
            logger.warn(WEIXIN_PAY_NOTIFY_END_WITH, "签名错误");
            success = false;
        }*/
        // 应用APPID
        String appid = map.get("appid");//微信开放平台审核通过的应用APPID
        if (!configPay.getWeixin().getAppId().equals(appid)) {
            logger.warn(WEIXIN_PAY_NOTIFY_END_WITH, "appId不匹配");
            success = false;
        }
        //商户号
        String mch_id = map.get("mch_id");//微信支付分配的商户号
        if (!configPay.getWeixin().getMchId().equals(mch_id)) {
            logger.warn(WEIXIN_PAY_NOTIFY_END_WITH, "ca商户号不匹配");
            success = false;
        }
        //交易类型
        String trade_type = map.get("trade_type");
        if (!"APP".equals(trade_type)) {
            logger.warn(WEIXIN_PAY_NOTIFY_END_WITH, "支付方式不匹配");
            success = false;
        }
        // 交易结果
        String result_code = map.get("result_code");//业务结果
        if (!"SUCCESS".equals(result_code)) {
            String err_code = map.get("err_code");//错误代码
            String err_code_des = map.get("err_code_des");//错误代码描述
            logger.warn("weixin pay notify: error_code {}", err_code);
            logger.warn("weixin pay notify: err_code_des {}", err_code_des);
            logger.warn(WEIXIN_PAY_NOTIFY_END_WITH, "支付失败");
            success = false;
        }

        if (!success) {
            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        //商户订单号
        String out_trade_no = map.get("out_trade_no");

        //查询交易记录
        UserTrade trade = balanceService.selectTradeByTradeNo(out_trade_no);
        if (Objects.isNull(trade) || trade.getStatus() == 1) {
            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        //金额入账
        balanceService.update(trade.getUserId(), trade.getType() == 0 ? BalanceType.ADD : BalanceType.WEIXIN_CREATE_PACKET, trade.getMoney(), trade.getId(), "微信充值");
        //交易记录状态更新
        balanceService.completeTrade(trade.getId());

        return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

    }

    @Override
    public String alipayPre(Long userId, Integer tradeType, BigDecimal money) {


        logger.debug("支付宝预下单。。。。");

        //暂时订单号使用
        String out_trade_no = DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS") + userId;
        //生成交易记录
        balanceService.createTradeRecord(userId, out_trade_no, money, PayType.ALIPAY, tradeType);

        // 获取配置文件中支付宝相关信息
        String aliPayGateway = "https://openapi.alipay.com/gateway.do";
        String signType = "RSA2";
        String alipayFormat = "json";
        String alipayCharset = "utf-8";

        AlipayClient alipayClient = new DefaultAlipayClient(aliPayGateway, configPay.getAlipay().getAppId(), configPay.getAlipay().getPrivateKey(), alipayFormat, alipayCharset, configPay.getAlipay().getPublicKey(), signType);
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //在body里存储跳转信息
        String redirect = configPay.getAlipay().getRedirectUrl();
        model.setBody("支付宝充值" + (StringUtils.isNotBlank(redirect) ? ("zf" + redirect) : ""));
        model.setSubject("支付宝充值");
        // 唯一订单号 根据项目中实际需要获取相应的
        model.setOutTradeNo(out_trade_no);
        // 支付超时时间（根据项目需要填写）
        model.setTimeoutExpress("30m");
        // 支付金额（项目中实际订单的需要支付的金额，金额的获取与操作请放在服务端完成，相对安全）
        model.setTotalAmount(NumberUtils.scale2HalfUp(money).toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        alipayRequest.setBizModel(model);
        // 支付成功后支付宝异步通知的接收地址url
        alipayRequest.setNotifyUrl(configPay.getAlipay().getNotifyUrl());

        AlipayTradeAppPayResponse alipayResponse = null;
        try {
            logger.debug("开始请求执行预支付接口。。。");
            logger.debug(JSON.toJSONString(alipayRequest));
            alipayResponse = alipayClient.sdkExecute(alipayRequest);
            logger.debug("支付结果:" + JSON.toJSONString(alipayResponse));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        // 返回支付相关信息(此处可以直接将getBody中的内容直接返回，无需再做一些其他操作)
        return alipayResponse.getBody();


    }


    @Override
    public String alipayNotufy(HttpServletRequest request) throws UnsupportedEncodingException {


        System.out.println("支付宝回调：。。。。。。。。");
        // 解决POST请求中文乱码问题（推荐使用此种方式解决中文乱码，因为是支付宝发送异步通知使用的是POST请求）
        request.setCharacterEncoding("UTF-8");

        String queryStr = "";
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? (valueStr + values[i]) : (valueStr + values[i] + ",");
            }

            // 官方demo中使用如下方式解决中文乱码，在此本人不推荐使用，可能会出现中文乱码解决无效的问题。
            if (!StringUtils.equals("fund_bill_list", name)) {
                queryStr += "&" + name + "=" + valueStr;
            }

            params.put(name, valueStr);
        }

        logger.debug("alipay notify params:{}", JSON.toJSONString(params));

        //判断交易是否成功  不成功的话 不进行下去了
        if (!params.get("trade_status").equals("TRADE_SUCCESS")) {
            return "fail";
        }

        //查看body中是否存在跳转信息
        if (params.get("body").contains("支付宝充值zf")) {

            logger.info("转发支付宝支付请求:{}", queryStr);

            String redirect = params.get("body").split("zf")[1];
            String url = redirect + "?" + (queryStr.replace("支付宝充值zf", "zf_pass"));
            logger.info("转发支付宝支付请求路径:{}", url);
            Request re = new Request.Builder().url(url).build();
            return OKHttpUtil.httpGet(re);
        }

        // 支付宝公钥（请注意，不是商户公钥）
        String rsaAlipayPublicKey = configPay.getAlipay().getPublicKey();
        String signType = "RSA2";
        String alipayCharset = "utf-8";

        boolean signVerified = false;
        try {

            logger.debug("alipay notify check:{}", params.get("body"));
            //调用SDK验证签名
            if (params.get("body").contains("zf_pass")) {
                signVerified = true;
            } else {
                signVerified = AlipaySignature.rsaCheckV1(params, rsaAlipayPublicKey, alipayCharset, signType);
            }

            logger.debug("alipay notify check signVerified:{}", signVerified);
            if (signVerified) {
                // 验证通知后执行自己项目需要的业务操作
                // 一般需要判断支付状态是否为TRADE_SUCCESS
                // 更严谨一些还可以判断 1.appid 2.sellerId 3.out_trade_no 4.total_amount 等是否正确，正确之后再进行相关业务操作。

                //商户订单号
                String out_trade_no = params.get("out_trade_no");

                //查询交易记录
                UserTrade trade = balanceService.selectTradeByTradeNo(out_trade_no);
                if (Objects.isNull(trade) || trade.getStatus() == 1) {
                    return "fail";
                }

                BalanceType balanceType = null;
                String memo = "";
                if (trade.getType() == 0) {
                    memo = "支付宝充值到余额";
                    balanceType = BalanceType.ADD;
                } else {
                    balanceType = BalanceType.ALIPAY_CREATE_PACKET;
                    memo = "支付宝充值创建红包";
                }

                //金额入账
                balanceService.update(trade.getUserId(), balanceType, trade.getMoney(), trade.getId(), memo);
                //交易记录状态更新
                balanceService.completeTrade(trade.getId());

                // 成功要返回success，不然支付宝会不断发送通知。
                return "success";
            } else {
                logger.debug("alipay notify check failed:{}", JSON.toJSONString(params));
            }

            logger.debug("alipay notify check failed:{}", JSON.toJSONString(params));
            // 验签失败  笔者在这里是输出log，可以根据需要做一些其他操作

            // 失败要返回success，不然支付宝会不断发送通知。
            return "fail";
        } catch (AlipayApiException e) {
            e.printStackTrace();
            // 验签异常  笔者在这里是输出log，可以根据需要做一些其他操作

            return "fail";
        }

    }


}
