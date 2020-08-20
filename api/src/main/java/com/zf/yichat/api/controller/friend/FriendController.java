package com.zf.yichat.api.controller.friend;

import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.api.dto.resp.UserInfoDto;
import com.zf.yichat.model.User;
import com.zf.yichat.service.FriendService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.FriendCheckStatus;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 好友功能点
 *
 * @author yichat
 * @date create in 09:42 2019/5/31 2019
 */
@RestController
@RequestMapping("friend")
public class FriendController extends BaseController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    /**
     * 添加好友
     */
    @PostMapping("/apply")
    public FsResponse add(@RequestBody FriendApplyRequest params) {
        params.valid();

        Contracts.assertTrue(getUserId().compareTo(params.getFriendId()) != 0, YiChatMsgCode.FRIEND_APPLY_ADD_NOT_MYSELF.msg());

        friendService.apply(getUserId(), params.getFriendId(), params.getReason());

        return FsResponseGen.success();
    }


    /**
     * 好友申请列表
     */
    @PostMapping("/apply/list")
    public FsResponse applyList(@RequestBody FriendApplyListRequest params) {
        params.valid();

        return friendService.selectApplyList(params.getPageNo(), params.getPageSize(), getUserId());

    }


    /**
     * 好友申请未读数量
     */
    @PostMapping("/apply/unread")
    public FsResponse add() {


        return FsResponseGen.successData(friendService.applyUnreadCount(getUserId()));
    }

    /**
     * 审核状态
     */
    @PostMapping("apply/check")
    public FsResponse check(@RequestBody FriendCheckRequest params) {
        params.valid();

        friendService.check(getUserId(), params.getFid(), FriendCheckStatus.valOf(params.getStatus()));

        return FsResponseGen.success();
    }

    /**
     * 删除申请记录
     */
    @PostMapping("apply/delete")
    public FsResponse applyDelete(@RequestBody FriendApplyDeleteRequest params) {
        params.valid();

        friendService.deleteApply(getUserId(), params.getFid());

        return FsResponseGen.success();
    }

    /**
     * 删除好友
     */
    @PostMapping("/delete")
    public FsResponse delete(@RequestBody FriendDeleteRequest params) {
        params.valid();

        friendService.delete(getUserId(), params.getFriendId());

        return FsResponseGen.success();
    }

    /**
     * 好友列表
     */
    @PostMapping("/list")
    public FsResponse list(@RequestBody FriendListRequest params) {

        params.valid();

        return friendService.selectList(params.getPageNo(), params.getPageSize(), getUserId());

    }

    /**
     * 好友备注设置
     */
    @PostMapping("/remark/set")
    public FsResponse remarkSet(@RequestBody FriendRemarkSetRequest params) {
        params.valid();

        friendService.remarkSet(getUserId(), params.getFriendId(), params.getRemark());

        User user = userService.selectById(params.getFriendId());
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setAppId(user.getAppid());
        userInfoDto.setAvatar(user.getAvatar());
        userInfoDto.setNick(user.getNick());
        userInfoDto.setUserId(String.valueOf(user.getId()));
        userInfoDto.setGender(user.getGender());
        userInfoDto.setMobile(user.getMobile());
        userInfoDto.setRemark(params.getRemark());
        return FsResponseGen.successData(userInfoDto);

    }

    /**
     * 是否是好友
     */
    @PostMapping("/status")
    public FsResponse list(@RequestBody FriendApplyRequest params) {
        params.valid();

        return FsResponseGen.successData(friendService.isFriend(getUserId(), params.getFriendId()) ? "1" : "0");

    }


}
