package com.zf.yichat.service;

/**
 * 安全
 *
 * @author yichat
 * @date create in 16:06 2019/6/3 2019
 */
public interface SecurityService {

    /**
     * 获取token 同时存储redis
     *
     * @param salt   盐值
     * @param userId 用户ID
     */
    String token(String salt, Long userId) throws Exception;

    /**
     * 验证 token
     *
     * @param token token
     */
    boolean validToken(String token);

    /**
     * 清除token
     *
     * @param userId 用户ID
     */
    void clearToken(Long userId);

    /**
     * 验证 token 是否有效
     *
     * @param token token
     */
    boolean checkToken(String token);
}
