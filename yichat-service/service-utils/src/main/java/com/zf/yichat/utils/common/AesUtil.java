package com.zf.yichat.utils.common;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:27 2019/5/29 2019
 */
public class AesUtil {
    // 加密
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new Base64().encodeToString(encrypted);
    }


    // 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = new Base64().decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "A286D372M63HFUQW";
        // 需要加密的字串
        //String cSrc = "{}";
        String cSrc = "{\"pageNo\":1,\"pageSize\":10}";
        System.out.println(cSrc);
        // 加密
        String enString = URLEncoder.encode(AesUtil.encrypt(cSrc, cKey));
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AesUtil.decrypt(URLDecoder.decode(enString), cKey);

        System.out.println("解密后的字串是：" + DeString);
        System.out.println("解密后的字串是：" + AesUtil.decrypt("jQFqM+aKQJn9Sb2Z0DxKtA==", "A286D372M63HFYUS"));

        System.out.println(AesUtil.decrypt("M6z/lJoUxtX1NYdDT5gaZMkna8PP3g3hDZDYdMms9Orx9AOZxgpOFS0pMmcPkGCFMkeouB6Ioi4zsKOToipa2vsZAj8cJ+EcIgklXzTWCsf82QPD7h57w01cT5BAZZijMWmnqTLCp/B7o8Bu7H+2cQ==", cKey));


        System.out.println(URLDecoder.decode("alipay_sdk=alipay-sdk-java-3.3.87.ALL&app_id=2019081466233373&biz_content=%7B%22body%22%3A%22%E6%94%AF%E4%BB%98%E5%AE%9D%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%222019081614583527714012455%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F39.98.231.27%3A8015%2Fapi%2Fpay%2Falipay%2Fnotify&sign=N5yWkNlsHWelxTr6TU9TlE%2B0VXdey5kqg6Ss5O8HVJ88a%2FC%2Fe1fojx%2F5TFe7yQ5zVCV20XJP0%2BNVLiVN72nwfi0PQYGF5T5y2Olk7%2B7KVwTybE9Mkmo1a9060Rnf%2FN%2BN9%2B2qX7wTS6UGCF3B1FAmn0sssD1tJ42BvMbV5TBDKyCIexPr%2FMuPubztJ3pltvtR8cGialLE6X7TyB9h35Td6PxN8MI2YXZdpFMdSr9z7yxmDjb3ltT6pYznoZ9RyW1DRfcy6sqv2e7av1dYK6KJ3FWR75CgN3qJ7zROqhSwj54bqq2FmkIEi%2Bzo7EMMlEeFyYhg%2FbMbqsZB9HfYNJ82oA%3D%3D&sign_type=RSA2&timestamp=2019-08-16+14%3A58%3A35&version=1.0"));
    }
}
