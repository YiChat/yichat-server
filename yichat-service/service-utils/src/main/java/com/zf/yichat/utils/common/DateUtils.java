package com.zf.yichat.utils.common;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    public static final String yyyyMMddHHmmss18 = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss16 = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMddHHmmss15 = "yyyyMMdd_HHmmss";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String TIME_START_SUFFIX = " 00:00:00";
    public static final String TIME_END_SUFFIX = " 23:59:59";
    public static Map<String, ThreadLocal<DateFormat>> threadLocal = new HashMap<>();

    public static DateFormat getDateFormat(String format) {
        ThreadLocal<DateFormat> local = threadLocal.get(format);
        if (Objects.isNull(local)) {
            local = new ThreadLocal<>();
        }
        DateFormat df = local.get();
        if (df == null) {
            df = new SimpleDateFormat(format);
            local.set(df);
            threadLocal.put(format, local);
        }
        return df;
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return FsConst.Str.EMPTY;
        }
        return getDateFormat(yyyyMMddHHmmss18).format(date);
    }

    public static String formatDate(Date date, String format) {
        if (Objects.isNull(date)) {
            return null;
        }
        return getDateFormat(format).format(date);
    }

    public static String getDistanceDays(Date date, int days, String format) {
        if (Objects.isNull(date)) {
            return null;
        }
        return getDateFormat(format).format(new Date(date.getTime() + days * 24 * 60 * 60 * 1000L));
    }

    public static String formatCurrentDate() {
        return getDateFormat(yyyyMMddHHmmss18).format(new Date());
    }

    public static String formatCurrentDate(String pattern) {
        return getDateFormat(pattern).format(new Date());
    }


    public static Date parse(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        try {
            return getDateFormat(yyyyMMddHHmmss18).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date parse(String strDate, String format) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        try {
            return getDateFormat(format).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Date getWeekFirstDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.DAY_OF_WEEK, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();

    }

    public static Date getWeekLastDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.DAY_OF_WEEK, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();

    }

    public static Date getMonthFirstDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.DAY_OF_MONTH, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();

    }

    public static Date getMonthLastDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();

    }


    public static Date getYearFirstDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.DAY_OF_YEAR, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();

    }

    public static Date getYearLastDate() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
            cal.set(Calendar.DAY_OF_YEAR, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();

    }


    public static boolean isToday(Date utime) {
        return formatDate(utime, "yyyy-MM-dd").equals(formatCurrentDate("yyyy-MM-dd"));
    }

    public static long todayMillis() {
        return parse(formatCurrentDate("yyyy-MM-dd"), "yyyy-MM-dd").getTime();
    }

    public static void main(String[] args) {
        System.out.println(todayMillis());
    }
}
