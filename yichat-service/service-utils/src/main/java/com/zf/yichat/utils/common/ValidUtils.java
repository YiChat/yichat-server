package com.zf.yichat.utils.common;

import java.util.regex.Pattern;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 08:36 2019/1/14 2019
 */
public class ValidUtils {

    public static boolean isUrl(String url) {
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";
        //设置正则表达式
        Pattern pat = Pattern.compile(regex.trim());
        //比对
        return pat.matcher(url.trim()).matches();


    }
}
