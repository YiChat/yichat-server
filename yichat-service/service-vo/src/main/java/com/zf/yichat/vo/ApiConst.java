package com.zf.yichat.vo;

import java.math.BigDecimal;

/**
 * 常用字符串数字  干掉魔法数字
 *
 * @author yichat
 */
public class ApiConst {

    public static String UNIT = "元";

    public static SmsClient client = null;

    /**
     * 常用状态描述
     */
    public interface Status {
        /**
         * 有效状态
         */
        int EFFECT = 0;

        /**
         * 无效
         */
        int INVALID = 1;

        /**
         * 异常
         */
        int EXCEED = -1;


        /**
         * 有效
         */
        int YES = 0;


        /**
         * 无效
         */
        int NO = 1;


    }

    /**
     * 常用状态码
     */
    public interface ResponseCode {
        String SUCCESS = "0";
    }

    public interface Number {
        int INTEGER_ZERO = 0;
        int INTEGER_ONE = 1;
        int LONG_ZERO = 0;
        BigDecimal BIG_DECIMAL_100 = new BigDecimal(100);
    }

    /**
     * 常用字符串
     */
    public interface Str {
        String EMPTY = "";

        String SUCCESS = "success";
        String FAIL = "fail";
    }

    public interface SqlColumn {
        String STATUS = "status";
        String NAME = "name";
        String ID = "id";
    }

    public interface Symbols {
        String COMMA = ",";
        String DOT = ".";
        String AT = "@";
        /**
         * 斜线
         */
        String VIRGULE = "/";
        /**
         * 加号
         */
        String PLUS = "+";
        /**
         * 冒号
         */
        String COLON = ":";
        /**
         * 破折号
         */
        String DASH = "-";
        /**
         * 问号
         */
        String QUESTION = "?";
        /**
         * 空格
         */
        String BLANK = " ";

        /**
         * 下划线
         */
        String UNDERLINE = "_";

        /**
         * 感叹号
         */
        String EXCLAMATORY = "!";

        String NEXTLINE = "\n";
        String PERCENT = "%";
        String EQUAL = "=";
    }


}

