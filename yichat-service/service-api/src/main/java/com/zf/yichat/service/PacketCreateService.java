package com.zf.yichat.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:55 2019/9/25 2019
 */
public interface PacketCreateService {


    /**
     * 构建抢红包数据分布
     *
     * @param amount 总金额
     * @param num    份数
     */
    List<BigDecimal> construct(BigDecimal amount, Integer num);


    /**
     * 根据总数分割个数及限定区间进行数据随机处理
     * 数列浮动阀值为0.85
     *
     * @param total    - 被分割的总数
     * @param splitNum - 分割的个数
     * @param min      - 单个数字下限
     * @param max      - 单个数字上限
     * @return - 返回符合要求的数字列表
     */
    List<Integer> genRandList(int total, int splitNum, int min, int max);

    /**
     * 根据总数分割个数及限定区间进行数据随机处理
     *
     * @param total    - 被分割的总数
     * @param splitNum - 分割的个数
     * @param min      - 单个数字下限
     * @param max      - 单个数字上限
     * @param thresh   - 数列浮动阀值[0.0, 1.0]
     * @return List<Integer>
     */
    List<Integer> genRandList(int total, int splitNum, int min, int max, float thresh);


}
