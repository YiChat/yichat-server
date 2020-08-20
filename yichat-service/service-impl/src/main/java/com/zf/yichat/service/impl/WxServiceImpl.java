package com.zf.yichat.service.impl;

import com.google.common.collect.Maps;
import com.zf.yichat.service.WxService;
import com.zf.yichat.utils.common.Sign;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * 微信支付
 *
 * @author yichat
 * @date create in 17:26 2019/6/24 2019
 */
@Service
public class WxServiceImpl implements WxService {
    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
            return "";
        }
    }

    @Override
    public Map<String, String> prePayApp(String appId, String appKey, String mchId, BigDecimal fee, String tradeNo) {
        Map<String, String> map = Maps.newHashMap();
        map.put("trade_type", "APP");
        map.put("appid", appId);
        map.put("mch_id", mchId);
        map.put("nonce_str", Sign.createNoncestr());
        map.put("body", "充值请求");


        map.put("out_trade_no", tradeNo);
        map.put("total_fee", fee.multiply(BigDecimal.valueOf(100L)).longValue() + "");
        map.put("spbill_create_ip", getIp());
        map.put("notify_url", "");
        map.put("sign", Sign.createSign(map, appKey));
        return map;
    }


}
