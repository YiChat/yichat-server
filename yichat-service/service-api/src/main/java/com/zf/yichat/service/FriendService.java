package com.zf.yichat.service;

import com.zf.yichat.model.Friend;
import com.zf.yichat.model.FriendApply;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.FriendCheckStatus;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:50 2019/6/3 2019
 */
public interface FriendService {

    /**
     * 好友申请
     *
     * @param userId       申请人
     * @param friendUserId 被申请人
     */
    void apply(Long userId, Long friendUserId, String reason);

    /**
     * 交互关系记录
     *
     * @param userId   审核人
     * @param friendId 被审核记录ID
     * @param status   状态设置
     */
    void check(Long userId, Long friendId, FriendCheckStatus status);

    /*
      * 删除好友
      * @param userId 当前操作人
       * @param friendId 被删除好友
      */
    void delete(Long userId, Long friendId);

    Friend selectOne(Long userId, Long friendId);

    FriendApply selectOneApply(Long userId, Long friendId);

    /**
     * 查看当前被申请列表
     *
     * @param pageNo   页码
     * @param pageSize 页尺
     * @param userId   被申请人
     */
    FsResponse selectApplyList(Integer pageNo, Integer pageSize, Long userId);

    FsResponse selectList(Integer pageNo, Integer pageSize, Long userId);

    boolean isFriend(Long userId, Long friendId);

    void remarkSet(Long userId, Long friendId, String remark);

    /**
     * 删除好友申请记录
     */
    void deleteApply(Long userId, Long fid);

    int applyUnreadCount(Long userId);

    void saveApplyReadTimestamp(Long userId);

    void duang(Long id, Long id1);
}
