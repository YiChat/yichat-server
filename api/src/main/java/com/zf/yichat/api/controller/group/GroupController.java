package com.zf.yichat.api.controller.group;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.FsGroupResponse;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.api.dto.resp.GroupDetailDto;
import com.zf.yichat.api.dto.resp.GroupNoticeDto;
import com.zf.yichat.dto.GroupDto;
import com.zf.yichat.model.TigGroup;
import com.zf.yichat.model.User;
import com.zf.yichat.service.AppNoticeService;
import com.zf.yichat.service.GroupService;
import com.zf.yichat.service.MessageService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.common.DateUtils;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.GroupRoleType;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 14:12 2019/6/17 2019
 */
@RestController
@RequestMapping("group")
public class GroupController extends BaseController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AppNoticeService appNoticeService;

    @Autowired
    private UserService userService;


    @PostMapping("/user/list")
    public FsResponse list(@RequestBody GroupUserListRequest params) {
        params.valid();
        FsPage page = FsPage.init(params.getPageNo(), params.getPageSize());
        FsResponse copy = groupService.selectUserList(page, getUserId(), params.getGroupId());

        FsGroupResponse res = new FsGroupResponse();
        res.setGroupId(params.getGroupId());
        res.setCount(copy.getCount());
        res.setPageSize(copy.getPageSize());
        res.setPageNo(copy.getPageNo());
        res.setMsg(copy.getMsg());
        res.setCode(copy.getCode());
        res.setData(copy.getData());
        res.setSuccess(copy.isSuccess());

        return res;
    }


    /**
     * 信息
     */
    @PostMapping("/info")
    public FsResponse info(@RequestBody GroupInfoRequest params) {
        params.valid();

        //群基本信息

        TigGroup group = groupService.selectById(params.getGroupId());
        Contracts.assertNotNull(group, YiChatMsgCode.GROUP_INFO_NONE.msg());

        GroupDto dto = new GroupDto();
        dto.setGroupId(group.getGid());
        dto.setCrateUnixTime(group.getCreationDate());
        dto.setGroupAvatar(group.getImgurl());
        dto.setGroupDescription(group.getDescri());
        dto.setVersion(String.valueOf(group.getVersion()));
        dto.setGroupSilentStatus(groupService.getGroupSilentStatus(params.getGroupId()));
        dto.setGroupName(group.getName());
        dto.setOwner(Long.parseLong(group.getCreator()));
        dto.setMemberCount(groupService.countMember(params.getGroupId()));
        dto.setRoleType(getUserId().compareTo(dto.getOwner()) == 0 ? 2 : (groupService.isAdmin(params.getGroupId(), getUserId()) ? 1 : 0));

        dto.setAdminList(groupService.selectAdminList(params.getGroupId()));
        dto.setSilentList(groupService.selectSilentList(params.getGroupId()));
        dto.setRoleType(groupService.selectRoleType(params.getGroupId(), getUserId()).getVal());
        dto.setLastList(messageService.selecLastList(params.getGroupId(), getUserId(), params.getTimestamp()));
        dto.setProtectStatus(groupService.selectProtectStatus(params.getGroupId()));

        return FsResponseGen.successData(dto);
    }


    @PostMapping("/protect/status/set")
    public FsResponse protectStatusSet(@RequestBody GroupProtectStatusRequest params) {

        params.valid();
        groupService.setProtectStatus(getUserId(), params.getGroupId(), params.getStatus());
        return FsResponseGen.success();
    }


    /**
     * 设置管理员
     */
    @PostMapping("/admin/set")
    public FsResponse setAdmin(@RequestBody GroupAdminSetRequest params) {
        params.valid();

        Contracts.assertTrue(groupService.getCreator(params.getGroupId()).compareTo(getUserId()) == 0, YiChatMsgCode.GROUP_SET.msg());
        GeneralUtils.splitExcludeEmpty(params.getUserIds()).forEach(v -> {
            groupService.updateRoleType(Long.parseLong(v), params.getGroupId(), GroupRoleType.amdin, params.getStatus());
        });
        return FsResponseGen.success();
    }

    /**
     * 管理员list
     */
    @PostMapping("/admin/list")
    public FsResponse adminList(@RequestBody GroupAdminListRequest params) {
        params.valid();
        return FsResponseGen.successData(groupService.selectAdminList(params.getGroupId()));
    }


    /**
     * 禁言设置/取消
     */
    @PostMapping("/silent/set")
    public FsResponse silentSet(@RequestBody GroupSilentSetRequest params) {
        params.valid();
        groupService.updateSilentStatus(getUserId(), params.getGroupId(), 1, params.getStatus());
        return FsResponseGen.success();
    }


    /**
     * 群成员禁言设置/取消
     */
    @PostMapping("/member/silent/set")
    public FsResponse memberSilentSet(@RequestBody GroupSilentSetRequest params) {
        params.valid();
        groupService.updateSilentStatus(params.getUserId(), params.getGroupId(), 0, params.getStatus());
        return FsResponseGen.success();
    }


    /**
     * 群禁言列表
     */
    @PostMapping("/silent/list")
    public FsResponse silentList(@RequestBody GroupInfoRequest params) {
        params.valid();

        return FsResponseGen.successData(groupService.selectSilentList(params.getGroupId()));
    }


    /**
     * 发布群公告
     */
    @PostMapping("/notice/publish")
    public FsResponse noticePublish(@RequestBody GroupNoticeRequest params) {
        params.valid();
        appNoticeService.add(params.getTitle(), params.getContent(), getUserId(), params.getGroupId());
        return FsResponseGen.successData(DtoChangeUtils.getList(appNoticeService.selectLastNotice(params.getGroupId()), v -> {
            GroupNoticeDto dto = new GroupNoticeDto();
            dto.setContent(v.getContent());
            dto.setTitle(v.getTitle());
            dto.setTime(v.getCtime().getTime());
            dto.setNoticeId(v.getId());
            dto.setTimeDesc(DateUtils.formatDate(v.getCtime()));

            User user = userService.selectById(v.getUserId());
            dto.setAvatar(user.getAvatar());
            dto.setUserId(v.getUserId());
            dto.setTitle(v.getTitle());
            return dto;
        }));
    }

    /**
     * 获取最新公告
     */
    @PostMapping("/notice/last")
    public FsResponse getNotice(@RequestBody GroupNoticeGetRequest params) {
        params.valid();
        return FsResponseGen.successData(DtoChangeUtils.getList(appNoticeService.selectLastNotice(params.getGroupId()), v -> {
            GroupNoticeDto dto = new GroupNoticeDto();
            dto.setContent(v.getContent());
            dto.setTitle(v.getTitle());
            dto.setTime(v.getCtime().getTime());
            dto.setTimeDesc(DateUtils.formatDate(v.getCtime()));

            User user = userService.selectById(v.getUserId());
            dto.setAvatar(user.getAvatar());
            dto.setUserId(v.getUserId());
            dto.setTitle(v.getTitle());
            dto.setNoticeId(v.getId());
            return dto;
        }));
    }

    /**
     * 删除公告
     */
    @PostMapping("/notice/delete")
    public FsResponse deleteNotice(@RequestBody GroupNoticeDeleteRequest params) {
        params.valid();
        appNoticeService.deleteNotice(params.getNoticeId());
        return FsResponseGen.success();

    }

    /**
     * 群详细信息
     */
    @PostMapping("/detail")
    public FsResponse detailGroup(@RequestBody GroupInfoRequest params) {

        TigGroup group = groupService.selectById(params.getGroupId());
        GroupDetailDto dto = new GroupDetailDto();
        dto.setCreate_date(group.getCreationDate());
        dto.setCreator(Long.parseLong(group.getCreator()));
        dto.setName(group.getName());
        dto.setGid(group.getGid());
        dto.setImgurlde(group.getImgurl());
        dto.setVersion(group.getVersion());
        dto.setDesc(group.getDescri());
        return FsResponseGen.successData(dto);
    }

    /**
     * 我的群列表
     */
    @PostMapping("/my/list")
    public FsResponse myGroupList() {

        return FsResponseGen.successData(DtoChangeUtils.getList(groupService.selectMyGroupList(getUserId()), v -> {
            GroupDetailDto dto = new GroupDetailDto();
            dto.setCreate_date(v.getCreationDate());
            dto.setCreator(Long.parseLong(v.getCreator()));
            dto.setName(v.getName());
            dto.setGid(v.getGid());
            dto.setImgurlde(v.getImgurl());
            dto.setVersion(v.getVersion());
            dto.setDesc(v.getDescri());

            return dto;
        }));
    }

}
