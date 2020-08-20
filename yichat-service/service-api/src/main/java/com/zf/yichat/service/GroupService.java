package com.zf.yichat.service;

import com.zf.yichat.dto.GroupUserDto;
import com.zf.yichat.model.GroupAdmin;
import com.zf.yichat.model.TigGroup;
import com.zf.yichat.model.TigGroupMember;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.GroupRoleType;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:16 2019/6/18 2019
 */
public interface GroupService {

    FsResponse selectUserList(FsPage page, Long userId, Integer groupId);


    void updateRoleType(Long userId, Integer groupId, GroupRoleType roleType, Integer status);

    List<GroupUserDto> selectAdminList(Integer groupId);

    List<GroupUserDto> selectSilentList(Integer groupId);

    GroupAdmin selectAdmin(Long userId, Integer groupId);

    //设置群禁言  0设置群成员 1设置群禁
    void updateSilentStatus(Long userId, Integer groupId, Integer type, Integer status);

    boolean isAdmin(Integer groupId, Long userId);

    Long getCreator(Integer groupId);

    TigGroup selectById(Integer id);

    GroupRoleType selectRoleType(Integer groupId, Long userId);

    List<TigGroupMember> selectGroupByUserId(Long userId);

    TigGroupMember selectGroupMember(Integer groupId, Long userId);

    Integer countMember(Integer groupId);

    Integer getGroupSilentStatus(Integer groupId);

    List<TigGroupMember> selectGroupByIds(List<Integer> groupId, Long userId);

    List<TigGroup> selectMyGroupList(Long userId);

    List<TigGroup> selectGroupListByCreater(Long userId);

    int selectProtectStatus(Integer groupId);

    void setProtectStatus(Long userId, Integer groupId, Integer status);
}
