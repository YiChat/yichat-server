package com.zf.yichat.utils.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:29 2019/6/24 2019
 */
public class Sign {
    private static final Logger logger = LoggerFactory.getLogger(Sign.class);

    public Sign() {
    }

    public static String createSign(Map<String, String> map, String apiKey) {
        StringBuilder sb = new StringBuilder();
        List<String> akeys = new ArrayList(map.keySet());
        Collections.sort(akeys);
        System.out.println(listToString(akeys));
        Iterator var4 = akeys.iterator();

        while (var4.hasNext()) {
            String k = (String) var4.next();
            String v = (String) map.get(k);
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=").append(apiKey);
        System.out.println(sb.toString());
        String sign = getMd5(sb.toString()).toUpperCase();
        System.out.println(sign);
        return sign;
    }

    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        } else {
            StringBuilder result = new StringBuilder();
            boolean flag = false;

            String string;
            for (Iterator var3 = stringList.iterator(); var3.hasNext(); result.append(string)) {
                string = (String) var3.next();
                if (flag) {
                    result.append(",");
                } else {
                    flag = true;
                }
            }

            return result.toString();
        }
    }

    public static boolean signValidate(Map<String, String> map, String sign, String apiKey) {
        return createSign(map, apiKey).equals(sign);
    }

    public static String getMd5(String plainText) {
        return DigestUtils.md5Hex(plainText);
    }

    public static String createNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        Random rd = null;

        for (int i = 0; i < 16; ++i) {
            rd = new Random();
            res = res + chars.charAt(rd.nextInt(chars.length() - 1));
        }

        return res;
    }

    public static String createJsapiSign(Map<String, String> map) {
        String decrypt = "jsapi_ticket=" + (String) map.get("jsapi_ticket") + "&noncestr=" + (String) map.get("noncestr") + "&timestamp=" + (String) map.get("timestamp") + "&url=" + (String) map.get("url");
        logger.debug(decrypt);
        return DigestUtils.sha1Hex(decrypt);
    }
}
