package com.zf.yichat.api.controller;

import com.zf.yichat.api.dto.request.AppVersionRequest;
import com.zf.yichat.api.dto.resp.AppVersionDto;
import com.zf.yichat.mapper.AppVersionMapper;
import com.zf.yichat.model.AppVersion;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:05 2019/7/30 2019
 */
@RestController
public class VersionController extends BaseController {

    @Autowired
    public AppVersionMapper appVersionMapper;

    @PostMapping("version")
    public FsResponse index(@RequestBody AppVersionRequest params) {

        params.valid();

        Example example = new Example(AppVersion.class);

        example.createCriteria().andEqualTo("type", params.getType());
        example.setOrderByClause(" ctime desc limit 1");
        AppVersion version = appVersionMapper.selectOneByExample(example);
        if (Objects.isNull(version)) {
            return FsResponseGen.successData(YiChatMsgCode.APP_VERSION_NONE, null);
        }
        return FsResponseGen.successData(DtoChangeUtils.getDto(appVersionMapper.selectOneByExample(example), v -> {
            AppVersionDto dto = new AppVersionDto();
            dto.setMemo(v.getMemo());
            dto.setVersion(Integer.parseInt(v.getVersion()));
            //如果中间隔的版本存在强制更新则必须为强制更新
            dto.setUpdateStatus(v.getUpdateStatus());
            if (Objects.nonNull(params.getCurrentVersion())) {
                Example se = new Example(AppVersion.class);
                se.createCriteria().andBetween("version", params.getCurrentVersion(), v.getVersion()).andEqualTo("type", params.getType());
                List<AppVersion> list = appVersionMapper.selectByExample(se);
                if (GeneralUtils.isNotNullOrEmpty(list) && list.stream().anyMatch(c -> c.getUpdateStatus() == 1)) {
                    dto.setUpdateStatus(1);
                }
            }
            dto.setDownloadUrl(v.getUrl());
            return dto;
        }));
    }

}
