package com.zf.yichat.utils;

import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.MD5Util;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:57 2019/5/28 2019
 */
public class ServiceUtils {


    public static String IM_USER_ID_SUFFIX = "@app.im";

    /**
     * 分隔区号和手机号
     */
    public static MobileSplit splitMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return new MobileSplit();
        }
        MobileSplit mobileSplit = new MobileSplit();
        if (mobile.length() > 11) {
            mobileSplit.setAreaCode(mobile.substring(0, mobile.length() - 11));
            mobileSplit.setMobile(mobile.substring(mobile.length() - 11));
        } else {
            mobileSplit.setMobile(mobile);
        }
        return mobileSplit;
    }

    public static String encode(String salt, String password) {
        return MD5Util.shaEncode(salt + password);
    }

    public static String generateSalt() {
        return GeneralUtils.getUUID().substring(0, 6);
    }

    public static String trendTimeDesc(Date date) {

        long hour = 60 * 60 * 1000;
        long minute = 60 * 1000;
        long time = System.currentTimeMillis();
        long distance = time - date.getTime();
        if (distance < minute) {
            return "刚刚";
        }
        if (distance < hour) {
            return distance / minute + "分钟前";
        }

        if (distance < 24 * hour) {
            return distance / hour + "小时前";
        }

        if (distance < 14 * 24 * hour) {
            return distance / (24 * hour) + "天前";
        }

        return DateUtils.formatDate(date, "yyyy年MM月dd日 HH:mm");
    }
}
