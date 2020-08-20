package com.zf.yichat.utils.common;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author yichat
 * @date 22:11  2018/12/19.
 */
public class NumberUtils {
    public static BigDecimal scale2Up(BigDecimal val) {
        return val.setScale(2, BigDecimal.ROUND_UP);

    }

    public static BigDecimal scale1Down(BigDecimal val) {
        return val.setScale(1, BigDecimal.ROUND_DOWN);

    }

    public static BigDecimal scale2HalfUp(BigDecimal val) {
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);

    }

    public static BigDecimal scale0HalfUp(BigDecimal val) {
        return val.setScale(0, BigDecimal.ROUND_HALF_UP);

    }

    public static BigDecimal pow(BigDecimal val) {
        return val.setScale(0, BigDecimal.ROUND_HALF_UP);

    }

    public static BigDecimal getSmall(BigDecimal... vals) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal v : vals) {
            result = result.compareTo(v) > 0 ? result : v;
        }
        return result;
    }

    public static void main(String[] args) {
        BigDecimal val = new BigDecimal("2.8");
        System.out.println(val.setScale(1, BigDecimal.ROUND_DOWN));
    }

    public static String dividePercent(String v1, String v2) {
        if (StringUtils.isAnyBlank(v1, v2)) {
            return FsConst.Str.EMPTY;
        }
        return (new BigDecimal(v1).divide(new BigDecimal(v2), 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 四舍五入、保留2位小数
     *
     * @param value
     * @return
     */
    public static BigDecimal halfTwo(BigDecimal value) {
        return half(2, value);
    }

    /**
     * 四舍五入、保留newScale位小数
     *
     * @param newScale
     * @param value
     * @return
     */
    public static BigDecimal half(int newScale, BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.setScale(newScale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal max(BigDecimal price, BigDecimal nPrice) {
        return null;
    }
}
