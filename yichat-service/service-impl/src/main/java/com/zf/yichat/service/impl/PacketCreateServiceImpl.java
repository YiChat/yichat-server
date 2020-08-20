package com.zf.yichat.service.impl;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.service.PacketCreateService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.common.NumberUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 09:55 2019/9/25 2019
 */
@Service
public class PacketCreateServiceImpl implements PacketCreateService {

    /**
     * 每个红包最小金额，单位为分
     */
    private static final int MIN_MONEY = 1;

    /**
     * 红包金额的离散程度，值越大红包金额越分散
     */
    private static final double DISPERSE = 8;
    private static final Random random = new Random();

    public static void main(String[] args) throws Exception {
        //红包金额
        BigDecimal amount = new BigDecimal("0.1");
        //红包个数
        int count = 5;

        /*for (int i = 0; i < 10; i++) {
            System.out.println(new PacketCreateServiceImpl().construct(amount, count));
        }*/

        List<BigDecimal> list = new PacketCreateServiceImpl().construct(amount, count);

        System.out.println(JSON.toJSONString(list));

        System.out.println(JSON.toJSONString(GeneralUtils.conList(list, 1)));
        BigDecimal nn = new BigDecimal("1.2");

        String ss = nn.toString();
        //System.out.println(ss.substring(ss.length()-1));

    }

    /**
     * 构建抢红包数据分布
     *
     * @param amount 总金额
     * @param num    份数
     */
    @Override
    public List<BigDecimal> construct(BigDecimal amount, Integer num) {


        //红包个数
        amount = NumberUtils.halfTwo(amount);

        //累计单个红包的金额,最后这个数要和amount一致才对
        BigDecimal total = new BigDecimal(0);


        int min = 1;//最小0.01 单位分
        int max = 20000;//最大200元 单位分
        BigDecimal rate = new BigDecimal(100);
        List<Integer> genRandList = genRandList(amount.multiply(new BigDecimal(100)).intValue(), num, min, max);


        //将分转化为元

        List<BigDecimal> list = genRandList.stream().map(v -> NumberUtils.halfTwo(new BigDecimal(v).divide(rate))).collect(Collectors.toList());

        total = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        //总金额是否相等
        Contracts.assertTrue(total.compareTo(amount) == 0, YiChatMsgCode.PACKET_CREATE_FAILED.msg());

        return list;
    }

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
    @Override
    public List<Integer> genRandList(int total, int splitNum, int min, int max) {
        return genRandList(total, splitNum, min, max, 0.99f);
    }

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
    @Override
    public List<Integer> genRandList(int total, int splitNum, int min, int max, float thresh) {
        assert total >= splitNum * min && total <= splitNum * max;
        assert thresh >= 0.0f && thresh <= 1.0f;
        // 平均分配
        int average = total / splitNum;
        List<Integer> list = new ArrayList<>(splitNum);
        int rest = total - average * splitNum;
        for (int i = 0; i < splitNum; i++) {
            if (i < rest) {
                list.add(average + 1);
            } else {
                list.add(average);
            }
        }
        // 如果浮动阀值为0则不进行数据随机处理
        if (thresh == 0) {
            return list;
        }
        // 根据阀值进行数据随机处理
        for (int i = 0; i < splitNum - 1; i++) {
            int nextIndex = i + 1;
            int itemThis = list.get(i);
            int itemNext = list.get(nextIndex);
            boolean isLt = itemThis < itemNext;
            int rangeThis = isLt ? max - itemThis : itemThis - min;
            int rangeNext = isLt ? itemNext - min : max - itemNext;
            int rangeFinal = (int) Math.ceil(thresh * (Math.min(rangeThis, rangeNext) + 1));
            int randOfRange = random.nextInt(rangeFinal);
            int randRom = isLt ? 1 : -1;
            list.set(i, list.get(i) + randRom * randOfRange);
            list.set(nextIndex, list.get(nextIndex) + randRom * randOfRange * -1);
        }
        return list;
    }
}
