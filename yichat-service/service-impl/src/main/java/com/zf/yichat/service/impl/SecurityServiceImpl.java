package com.zf.yichat.service.impl;

import com.zf.yichat.service.SecurityService;
import com.zf.yichat.service.config.ConfigSet;
import com.zf.yichat.service.config.RedisService;
import com.zf.yichat.utils.common.AesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:07 2019/6/3 2019
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private ConfigSet configSet;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String token(String salt, Long userId) throws Exception {
        String token = AesUtil.encrypt(salt + ":" + userId, configSet.getSecurity().getAesKey().getToken());
        redisService.setToken(userId, token);
        return token;
    }

    @Override
    public boolean validToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            String de = AesUtil.decrypt(token, configSet.getSecurity().getAesKey().getToken());
            if (StringUtils.isNotBlank(de)) {
                Long userId = NumberUtils.toLong(de.split(":")[1]);
                boolean result = redisService.valid(userId, token);
                if (result) {
                    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    HttpSession session = requestAttributes.getRequest().getSession();
                    session.setAttribute("userId", userId);
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void clearToken(Long userId) {
        redisService.deleteToken(userId);
    }

    @Override
    public boolean checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            String de = AesUtil.decrypt(token, configSet.getSecurity().getAesKey().getToken());
            if (StringUtils.isNotBlank(de)) {
                Long userId = NumberUtils.toLong(de.split(":")[1]);
                return redisService.valid(userId, token);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
