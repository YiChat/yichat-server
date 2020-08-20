package com.zf.yichat.service;

import com.zf.yichat.model.AppNotice;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:59 2019/8/18 2019
 */
public interface AppNoticeService {

    void add(String title, String content, Long userId, Integer groupId);

    List<AppNotice> selectLastNotice(Integer groupId);

    void deleteNotice(Long noticeId);
}
