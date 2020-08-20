package com.zf.yichat.api.controller;

import com.zf.yichat.api.dto.request.AppVersionRequest;
import com.zf.yichat.api.dto.resp.AppVersionDto;
import com.zf.yichat.api.dto.resp.ConfigDto;
import com.zf.yichat.api.dto.resp.ShareDto;
import com.zf.yichat.api.dto.resp.SmallAppDto;
import com.zf.yichat.dto.MessageListDto;
import com.zf.yichat.dto.MessageUnreadDto;
import com.zf.yichat.mapper.AppSmallMapper;
import com.zf.yichat.mapper.AppVersionMapper;
import com.zf.yichat.model.*;
import com.zf.yichat.service.*;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.DictKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 全局配置
 *
 * @author yichat
 * @date create in 15:31 2019/8/1 2019
 */
@RestController
public class ConfigController extends BaseController {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AppSmallMapper appSmallMapper;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private AppConfigService appConfigService;


    @PostMapping("config")
    public FsResponse config(@RequestBody AppVersionRequest params, org.apache.catalina.servlet4preview.http.HttpServletRequest request) {

        Example example = new Example(AppVersion.class);
        example.createCriteria().andEqualTo("type", 0);
        example.setOrderByClause(" ctime desc limit 1");

        Example example1 = new Example(AppVersion.class);
        example1.createCriteria().andEqualTo("type", 1);
        example1.setOrderByClause(" ctime desc limit 1");

        User user = userService.selectById(getUserId());

        ConfigDto res = new ConfigDto();
        res.setAndroidVersion(DtoChangeUtils.getDto(appVersionMapper.selectOneByExample(example), v -> {
            AppVersionDto dto = new AppVersionDto();
            dto.setMemo(v.getMemo());
            dto.setVersion(Integer.parseInt(v.getVersion()));
            dto.setUpdateStatus(v.getUpdateStatus());
            if (Objects.nonNull(params.getCurrentVersion()) && params.getType() == 0) {
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

        res.setIosVersion(DtoChangeUtils.getDto(appVersionMapper.selectOneByExample(example), v -> {
            AppVersionDto dto = new AppVersionDto();
            dto.setMemo(v.getMemo());
            dto.setVersion(Integer.parseInt(v.getVersion()));
            dto.setUpdateStatus(v.getUpdateStatus());
            if (Objects.nonNull(params.getCurrentVersion()) && params.getType() == 1) {
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


        List<TigGroupMember> gids = groupService.selectGroupByUserId(getUserId());
        Long time = userService.getTimestamp(getUserId());

        res.setUnreadCountList(DtoChangeUtils.getList(gids, v -> {
            MessageUnreadDto dto = new MessageUnreadDto();
            FsResponse response = messageService.selectList(FsPage.init(1, 20), 2, String.valueOf(v.getGid()), time, getUserId(), false);
            dto.setUnreadCount(response.getCount().intValue());
            dto.setGroupId(v.getGid());
            if (dto.getUnreadCount() > 0) {
                dto.setLastmessage(((List<MessageListDto>) response.getData()).get(0));
            }
            return dto;
        }));

        //分享设置
        ShareDto dto = new ShareDto();

        dto.setAndroidLink(Optional.ofNullable(sysDictService.selectOne(DictKey.share_andriod)).map(SysDict::getValue).orElse(""));
        dto.setIosLink(Optional.ofNullable(sysDictService.selectOne(DictKey.share_ios)).map(SysDict::getValue).orElse(""));
        dto.setContent(Optional.ofNullable(sysDictService.selectOne(DictKey.share_text)).map(SysDict::getValue).orElse(""));
        res.setShare(dto);


        userService.updateTimestamp(getUserId(), System.currentTimeMillis());

        //建群权限
        res.setCreateGroupAuthStatus(user.getCreateGroupAuth());

        //保存IP地址
        userService.saveIp(GeneralUtils.getIpAddress(request), getUserId());

        return FsResponseGen.successData(res);
    }


    //xia小程序列表
    @PostMapping("app/small/list")
    public FsResponse smallList() {

        Example example = new Example(AppSmall.class);
        example.setOrderByClause(" id desc");
        return FsResponseGen.successData(DtoChangeUtils.getList(appSmallMapper.selectByExample(example), v -> {
            SmallAppDto smallAppDto = new SmallAppDto();
            smallAppDto.setIcon(v.getIcon());
            smallAppDto.setTitle(v.getTitle());
            smallAppDto.setUrl(v.getUrl());
            return smallAppDto;
        }));
    }

    @PostMapping("home/url")
    public FsResponse homeUrl() {

        return FsResponseGen.successData(appConfigService.getHomeUrl());
    }


    @PostMapping("config/status")
    public FsResponse config() {
        return FsResponseGen.successData(1);
    }


    @PostMapping("config/link")
    public FsResponse configLink() {
        return FsResponseGen.successData(sysDictService.selectOne(DictKey.config_link).getValue());
    }


}
