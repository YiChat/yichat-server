package com.zf.yichat.utils.common;

import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:09 2019/5/28 2019
 */
public class MD5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        if (StringUtils.isBlank(inStr)) {
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * @return
     * @Comment SHA1实现
     * @Author Ron
     * @Date 2017年9月13日 下午3:30:36
     */
    public static String shaEncode(String inStr) {
        MessageDigest sha = null;
        if (StringUtils.isBlank(inStr)) {
            return null;
        }
        try {
            sha = MessageDigest.getInstance("SHA");
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = sha.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();

        }
        return "";

    }
    /**
     * 根据自己的规则进行MD5加密
     * 例如，现在需求是有字符串传入zhang，xy
     * 其中zhang是传入的字符
     * 然后需要将zhang的字符进行拆分z，和hang，
     * 最后需要加密的字段为zxyhang
     */
    /*public static String MD5Test(String inStr) {
        String xy = "xy";
        String finalStr = "";
        if (inStr != null) {
            String fStr = inStr.substring(0, 1);
            String lStr = inStr.substring(1, inStr.length());
            finalStr = string2MD5(fStr + xy + lStr);

        } else {
            finalStr = string2MD5(xy);
        }
        return finalStr;
    }*/

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    public static String decode(String inStr) {
        return null;
    }

    // 测试
    public static void main(String args[]) {
        String s1 = "123456";
        System.out.println(string2MD5("900033683c77d5ce9b977ba61b6e"));
        System.out.println(shaEncode("123456"));
        System.out.println("解密的：" + convertMD5(string2MD5(s1)));

    }
}