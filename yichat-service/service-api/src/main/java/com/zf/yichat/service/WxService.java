package com.zf.yichat.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:26 2019/6/24 2019
 */
public interface WxService {

    Map<String, String> prePayApp(String appId, String appKey, String mchId, BigDecimal fee, String tradeNo);
}
