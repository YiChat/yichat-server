package com.zf.yichat.service;

import com.zf.yichat.utils.response.FsResponse;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:00 2019/7/31 2019
 */
public interface SmsService {

    void save(String mobile, String code);

    FsResponse check(String mobile, String code);

    boolean send(String mobile, String code);
}
