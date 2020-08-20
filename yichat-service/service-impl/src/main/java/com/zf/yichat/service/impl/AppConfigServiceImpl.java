package com.zf.yichat.service.impl;

import com.zf.yichat.mapper.AppConfigMapper;
import com.zf.yichat.mapper.AppVersionMapper;
import com.zf.yichat.model.AppConfig;
import com.zf.yichat.model.AppVersion;
import com.zf.yichat.model.SysDict;
import com.zf.yichat.service.AppConfigService;
import com.zf.yichat.service.SysDictService;
import com.zf.yichat.vo.AppConfigType;
import com.zf.yichat.vo.DictKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;
import java.util.Optional;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:42 2019/7/18 2019
 */
@Service
public class AppConfigServiceImpl implements AppConfigService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Autowired
    private SysDictService sysDictService;

    @Override
    public void save(AppVersion appVersion) {
        if (Objects.isNull(appVersion.getId())) {
            appVersionMapper.insertSelective(appVersion);
        } else {
            appVersionMapper.updateByPrimaryKeySelective(appVersion);
        }
    }


    @Override
    public Integer getCreateGroupAuthStatus(Long userId) {
        //0无权限 1有权限
        Integer res = 1;
        Example select = new Example(AppConfig.class);
        Example.Criteria criteria = select.createCriteria().andEqualTo("type", 0);

        //有部分用户有权限
        if (appConfigMapper.selectCountByExample(select) > 0) {
            criteria.andEqualTo("userId", userId);
            return Objects.nonNull(appConfigMapper.selectOneByExample(select)) ? 1 : 0;
        } else {
            //全部用户都有权限
        }
        return res;
    }


    @Override
    public void saveHomeUrl(String url) {
        //先查询是否存在
        Example example = new Example(AppConfig.class);
        example.createCriteria().andEqualTo("type", AppConfigType.home_url.getVal());
        AppConfig config = appConfigMapper.selectOneByExample(example);

        if (Objects.nonNull(config)) {
            config.setMemo(url);
            appConfigMapper.updateByPrimaryKeySelective(config);
        } else {
            config = new AppConfig();
            config.setType(AppConfigType.home_url.getVal());
            config.setMemo(url);
            appConfigMapper.insertSelective(config);
        }
    }

    @Override
    public String getHomeUrl() {
        return Optional.ofNullable(sysDictService.selectOne(DictKey.app_home_url)).map(SysDict::getValue).orElse("");
    }

    @Override
    public boolean isFreezeIp(String ip) {
        Example example = new Example(AppConfig.class);
        example.createCriteria().andEqualTo("type", AppConfigType.freeze_ip.getVal());
        AppConfig config = appConfigMapper.selectOneByExample(example);
        return Objects.nonNull(config);
    }

    @Override
    public void freezeIp(String ip) {
        if (!isFreezeIp(ip)) {
            AppConfig config = new AppConfig();
            config.setType(AppConfigType.freeze_ip.getVal());
            config.setMemo(ip);
            appConfigMapper.insertSelective(config);
        }
    }

    @Override
    public void unfreezeIp(String ip) {
        Example example = new Example(AppConfig.class);
        example.createCriteria().andEqualTo("type", AppConfigType.freeze_ip.getVal()).andEqualTo("memo", ip);
        appConfigMapper.deleteByExample(example);
    }
}
