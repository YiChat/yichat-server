package com.zf.yichat.service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:34 2019/8/14 2019
 */
public interface PayService {
    Map<String, String> weixinPrePay(Long userId, Integer tradeType, BigDecimal money);

    String payNotify(String rexXml);

    String alipayPre(Long userId, Integer tradeType, BigDecimal money);

    String alipayNotufy(HttpServletRequest res) throws UnsupportedEncodingException;

}
