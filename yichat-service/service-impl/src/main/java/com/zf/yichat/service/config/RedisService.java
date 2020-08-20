package com.zf.yichat.service.config;

import com.alibaba.fastjson.JSON;
import com.zf.yichat.dto.PacketCtrlItem;
import com.zf.yichat.utils.YiChatMsgCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:59 2019/1/3 2019
 */
@Component
public class RedisService {

    private static String token_pre = "token_";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long getId(String key) {
        return redisTemplate.boundValueOps(key).increment(1);
    }

    public String setKeyVal(String key) {
        String val = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(key, val);
        return val;
    }

    public boolean validKey(String key, String val) {
        String store = redisTemplate.opsForValue().get(key).toString();
        return StringUtils.equals(val, store);
    }

    public String getVal(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setVal(String key, String val) {
        stringRedisTemplate.opsForValue().set(key, val);
    }

    public void setVal(String key, String val, long seconds) {
        stringRedisTemplate.opsForValue().set(key, val, seconds, TimeUnit.SECONDS);
    }


    public boolean valid(Long userId, String token) {
        String result = getVal(token_pre + userId);
        return StringUtils.isNotBlank(result) && result.equals(token);
    }

    //token 默认设置失效一周
    public void setToken(Long userId, String token) {
        setVal(token_pre + userId, token, 7 * 24 * 60 * 60);

    }


    public void deleteToken(Long userId) {
        stringRedisTemplate.delete(token_pre + userId);
    }

    public void setPacketList(Long packetId, List<BigDecimal> list, long hours) {
        redisTemplate.boundListOps("packet_" + packetId).expire(hours, TimeUnit.HOURS);
        list.forEach(v -> {
            redisTemplate.boundListOps("packet_" + packetId).rightPush(v);
        });

        //设置抢红包用户存储
        stringRedisTemplate.opsForValue().set("packet_" + packetId + "_user_", "packet", 24, TimeUnit.HOURS);
    }

    public BigDecimal getPacket(Long packetId, Long userId) {

        String key = "packet_" + packetId + "_user_";


        String res = stringRedisTemplate.opsForValue().get(key);
        //标识下设置失效时间  用于清理数据 不必担心  超过时效时间 接口已过滤
        Contracts.assertNotNull(res, YiChatMsgCode.PACKET_RECEIVE_OVER_TIME.msg());

        //标识已抢过
        if (res.contains(String.valueOf(userId))) {
            return BigDecimal.ZERO;
        }

        BigDecimal bigDecimal = (BigDecimal) redisTemplate.boundListOps("packet_" + packetId).leftPop();

        if (Objects.nonNull(bigDecimal)) {
            Long expire = stringRedisTemplate.getExpire(key);
            stringRedisTemplate.opsForValue().set(key, (StringUtils.isBlank(res) ? "" : res) + "," + userId, (Objects.isNull(expire) || expire == 0) ? 24 * 3600 : expire, TimeUnit.SECONDS);
        }

        return bigDecimal;
    }


    public void deleteKey(Long id) {
        stringRedisTemplate.delete("packet_" + id);
        stringRedisTemplate.delete("packet_" + id + "_user_");
    }

    public void deleteKey(String key) {
        stringRedisTemplate.delete(key);
    }


    public void setCtrlKey(Integer groupId, PacketCtrlItem item) {
        setVal("ctrl_" + groupId, JSON.toJSONString(item));
    }

    public void deleteCtrlKey(Integer groupId) {
        stringRedisTemplate.delete("ctrl_" + groupId);
    }

    public PacketCtrlItem getCtrl(Integer groupId) {
        String res = getVal("ctrl_" + groupId);
        if (StringUtils.isNotBlank(res)) {

            return JSON.parseObject(res, PacketCtrlItem.class);
        }

        return null;
    }


    //预先设置某人红包抢到金额
    public void setReceive(Long packetId, Long userId, String money) {
        setVal("prec" + packetId + "_", userId + "_" + money);
    }

    public BigDecimal getReceive(Long packetId, Long userId) {
        String key = "prec" + packetId + "_";
        String res = getVal(key);
        if (StringUtils.isNotBlank(res) && res.contains(String.valueOf(userId))) {
            stringRedisTemplate.delete(key);
            return new BigDecimal(res.split("_")[1]);
        }

        return null;
    }

    public BigDecimal getReceiveAbs(Long packetId) {
        String key = "prec" + packetId + "_";
        String res = getVal(key);
        if (StringUtils.isNotBlank(res)) {
            stringRedisTemplate.delete(key);
            return new BigDecimal(res.split("_")[1]);
        }

        return null;
    }


}
