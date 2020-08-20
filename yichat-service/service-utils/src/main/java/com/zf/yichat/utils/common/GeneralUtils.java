package com.zf.yichat.utils.common;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.squareup.okhttp.Request;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.internal.util.Contracts;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;

/**
 * Created by yichat on 2018/7/28.
 *
 * @author yichat
 */
public class GeneralUtils<T> {

    public static boolean isNotNullOrEmpty(Collection collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return !isNotNullOrEmpty(collection);
    }

    public static List<Long> string2ListLong(String str) {
        return Optional.ofNullable(str).map(v -> Arrays.stream(str.split(",")).filter(StringUtils::isNotBlank).map(NumberUtils::toLong).collect(Collectors.toList())).orElse(new ArrayList<>());
    }

    public static List<String> string2ListString(String str) {
        return Optional.ofNullable(str).map(v -> Arrays.stream(str.split(",")).filter(StringUtils::isNotBlank).collect(Collectors.toList())).orElse(new ArrayList<>());
    }

    public static String stringSub(String str, Integer length) {
        if (StringUtils.isNoneEmpty(str) && length > 0 && str.length() > length) {
            return str.substring(0, length);
        }

        return str;
    }

    public static List<String> splitExcludeEmpty(String str) {
        if (StringUtils.isNotBlank(str)) {
            return Arrays.stream(str.split(FsConst.Symbols.COMMA)).filter(StringUtils::isNotBlank).collect(Collectors.toList());

        }

        return Lists.newArrayList();
    }

    public static List<Integer> splitIntExcludeEmpty(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return splitExcludeEmpty(str).stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public static String[] splitByNewline(String str) {

        return str.split("\\r?\\n");
    }

    public static List<String> splitExcludeEmpty(String str, String symbol) {
        if (StringUtils.isNotBlank(str)) {
            return Arrays.stream(str.replaceAll(String.valueOf((char) 160), " ").split(symbol)).filter(StringUtils::isNotBlank).map(String::trim).collect(Collectors.toList());

        }

        return Lists.newArrayList();
    }

    public static List<String> splitIncludeEmpty(String str) {
        if (StringUtils.isNotBlank(str)) {
            List<String> result = new ArrayList<>();
            String mid = FsConst.Str.EMPTY;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c == ',') {
                    result.add(mid);
                    mid = FsConst.Str.EMPTY;
                } else {
                    mid = mid + c;
                }
            }

            if (str.lastIndexOf(FsConst.Symbols.COMMA) == str.length() - 1) {
                result.add(FsConst.Str.EMPTY);
            }

            return result;

        }

        return Lists.newArrayList();
    }

    public static boolean isNotNullOverZero(Integer num) {
        return Optional.ofNullable(num).orElse(FsConst.Number.INTEGER_ZERO) > FsConst.Number.INTEGER_ZERO;
    }

    public static boolean isNotNullOverZero(BigDecimal num) {
        return Optional.ofNullable(num).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNullOrZero(BigDecimal num) {
        return Optional.ofNullable(num).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0;
    }


    public static String replaceHtmlSign(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.replace("&", "&amp;");
            str = removeAttr(str);
            return str.replace("<", "&lt;").replace(">", "&gt;");
        }

        return "";
    }

    /*删除指定的属性*/
    public static String deleteAttr(String attr, String html) {
        if (StringUtils.isBlank(html)) {
            return html;
        }
        Pattern p = compile(attr + "=\"([^\"]+)\"");
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }

    public static String removeAttr(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        str = deleteAttr("lang", str);
        str = deleteAttr("class", str);
        str = deleteAttr("style", str);
        str = deleteAttr("tabindex", str);
        str = deleteAttr("title", str);
        str = deleteAttr("id", str);
        str = deleteAttr("width", str);
        str = deleteAttr("heght", str);
        str = deleteAttr("cellspacing", str);
        str = deleteAttr("cellpadding", str);
        str = deleteAttr("border", str);
        str = deleteAttr("align", str);
        //str = str.replace("<span>", "").replace("</span>", "");
        str = filterHtml(str, "<\\s*span\\s+([^>]*)\\s*>");
        str = filterHtml(str, "</span>");
        str = filterHtml(str, "<span>");
        str = filterHtml(str, "<o:p>");
        str = filterHtml(str, "</o:p>");
        str = filterHtml(str, "</div>");
        str = filterHtml(str, "<div>");
        str = filterHtml(str, "</table>");
        str = filterHtml(str, "<table>");
        str = filterHtml(str, "<tbody>");
        str = filterHtml(str, "</tbody>");

        str = filterHtml(str, "<\\s*td\\s+([^>]*)\\s*>");
        str = filterHtml(str, "<\\s*table\\s+([^>]*)\\s*>");
        str = filterHtml(str, "<\\s*tr\\s+([^>]*)\\s*>");
        str = filterHtml(str, "</td>");
        str = filterHtml(str, "<td>");
        str = filterHtml(str, "</tr>");
        str = filterHtml(str, "<\\s*div\\s+([^>]*)\\s*>");

        return str;
    }

    /**
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     * <p>
     *
     * @param str
     * @return String
     */
    public static String filterHtml(String str, String regex) {
        Pattern pattern = compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String localToUTC(Date localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = localTime;

        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return formatDate(utcDate, "yyyy-MM-dd'T'hh:mm:ss'Z'");
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static List<Integer> asList(Integer... ins) {

        List<Integer> list = new ArrayList<>(ins.length);
        for (Integer i : ins) {
            list.add(i);
        }
        return list;
    }

    public static List<Long> asList(Long... ins) {

        List<Long> list = new ArrayList<>(ins.length);
        for (Long i : ins) {
            list.add(i);
        }
        return list;
    }

    public static List<String> asList(String... ins) {

        List<String> list = new ArrayList<>(ins.length);
        for (String i : ins) {
            list.add(i);
        }
        return list;
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉"-"符号
        return s.replace(FsConst.Symbols.DASH, FsConst.Str.EMPTY);
    }


    public static Map<String, String> asMap(String... keyValues) {

        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < keyValues.length; i++) {
            result.put(keyValues[i], keyValues[++i]);
        }
        return result;
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }


    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getPhone(String userAgent) {
        Pattern pattern = compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
        Matcher matcher = pattern.matcher(userAgent);
        String model = null;
        if (matcher.find()) {
            model = matcher.group(1).trim();
        }

        return model;
    }

    public static boolean isMobileDevice(String requestHeader) {
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[]{"android", "mac os", "windows phone"};
        if (requestHeader == null) {
            return false;
        }
        requestHeader = requestHeader.toLowerCase();
        for (int i = 0; i < deviceArray.length; i++) {
            if (requestHeader.indexOf(deviceArray[i]) > 0) {
                return true;
            }
        }
        return false;
    }


    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("/[\\x{10000}-\\x{10FFFF}]/u", "");
        } else {
            return source;
        }
    }


    public static boolean validMobile(String phone) {
        return true;
        /*String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);

        return m.matches();*/


    }


    public static String contact(List<String> strList, String symbol) {
        return GeneralUtils.isNotNullOrEmpty(strList) ? strList.stream().collect(Collectors.joining(symbol)) : FsConst.Str.EMPTY;
    }

    /*
      * 获取随机数
      * @param num 随机几位数
      */
    public static String randomNum(int num) {
        String rst = "";
        int number = 0;
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < num; i++) {
            number = Math.abs(random.nextInt()) % 10;
            rst += number;
        }
        return rst;
    }


    public static boolean validBankInfo(String cardNumber, String idNumber, String mobile, String name) {
        String url = "https://b4bankcard.market.alicloudapi.com/bank4Check?accountNo="
                + cardNumber + "&idCard=" + idNumber + "&mobile=" + mobile + "&name=" + name;
        String appcode = "550fa906ed5d4057988dc478be15b1d2";

        try {


            Request request = new Request.Builder().addHeader("Authorization", "APPCODE " + appcode).url(url).build();
            String res = OKHttpUtil.httpGet(request);
            //System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
            //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            //获取response的body
            String code = JSON.parseObject(res).getString("status");
            Contracts.assertTrue(!code.equals("403"), "第三方验证银行卡接口次数用完");
            return code.equals("01");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static List<BigDecimal> conList(List<BigDecimal> list, Integer lastNum) {

        int i = 0;
        BigDecimal big1 = new BigDecimal("0.01");
        for (BigDecimal dec : list) {
            String midStr = dec.toString();
            String laM = midStr.substring(midStr.length() - 1);
            if (StringUtils.equals(laM, String.valueOf(lastNum))) {
                if (i == list.size() - 1) {
                    list.set(i, dec.add(big1));
                    list.set(0, list.get(0).subtract(big1));
                } else {
                    list.set(i, dec.add(big1));
                    list.set(i + 1, list.get(i + 1).subtract(big1));
                }
            }
            i++;
        }


        for (BigDecimal dec : list) {
            String midStr = dec.toString();
            String laM = midStr.substring(midStr.length() - 1);
            if (StringUtils.equals(laM, String.valueOf(lastNum))) {
                return conList(list, lastNum);
            }
        }


        return list;


    }


    /**
     *
     */
    public static String removeAllEmojis(String str) {

        return str;
//        if (StringUtils.isBlank(str)) {
//            return str;
//        } else {
//            return EmojiParser.removeAllEmojis(str);
//        }
    }


}
