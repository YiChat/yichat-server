package com.zf.yichat.utils.common;

public class HtmlUtils {


    /**
     * *返回纯文本,去掉html的所有标签,并且去掉空行
     * *
     * *@paraminput
     * *@return
     */

    public static String splitAndFilterString(String input)

    {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        //去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        return str.replaceAll("[(/>)<]", "");
    }
}
