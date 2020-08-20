package com.zf.yichat.service.impl;

import com.zf.yichat.mapper.AppNoticeMapper;
import com.zf.yichat.model.AppNotice;
import com.zf.yichat.service.AppNoticeService;
import com.zf.yichat.vo.AppNoticeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 11:00 2019/8/18 2019
 */
@Service
public class AppNoticeServiceImpl implements AppNoticeService {

    @Autowired
    private AppNoticeMapper appNoticeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(String title, String content, Long userId, Integer groupId) {

        //先删除之前所有的公告
        Example sel = new Example(AppNotice.class);
        sel.createCriteria().andEqualTo("groupId", groupId);
        AppNotice u = new AppNotice();
        u.setStatus(1);
        appNoticeMapper.updateByExampleSelective(u, sel);

        AppNotice appNotice = new AppNotice();
        appNotice.setContent(content);
        appNotice.setTitle(title);
        appNotice.setUserId(userId);
        appNotice.setType(AppNoticeType.GROUP.getVal());
        appNotice.setGroupId(groupId);

        appNoticeMapper.insertSelective(appNotice);
    }

    @Override
    public List<AppNotice> selectLastNotice(Integer groupId) {
        Example select = new Example(AppNotice.class);
        select.createCriteria().andEqualTo("status", 0)
                .andEqualTo("type", AppNoticeType.GROUP.getVal())
                .andEqualTo("groupId", groupId);
        select.setOrderByClause(" ctime desc limit 1");
        return appNoticeMapper.selectByExample(select);
    }

    @Override
    public void deleteNotice(Long noticeId) {
        AppNotice notice = new AppNotice();
        notice.setId(noticeId);
        notice.setStatus(1);
        appNoticeMapper.updateByPrimaryKeySelective(notice);
    }
}
