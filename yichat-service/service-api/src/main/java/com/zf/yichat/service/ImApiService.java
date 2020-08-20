package com.zf.yichat.service;

import com.zf.yichat.dto.GroupDto;
import com.zf.yichat.dto.im.ImCommand;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:00 2019/5/29 2019
 */
public interface ImApiService {

    /*
      * 创建IM用户
      */
    ImCommand create(String accountId, String password);

    List<Long> selectGroupUserIdList(Long userId, Long groupId);

    //获取单个群组信息
    GroupDto selectGroupById(Long id, Long userId);

    //删除群成员
    void deleteGroupMember(Integer gid, Long userId);

    void deleteUser(List<Long> v);

    String getMyGroup(String jsonStr);

    String addGroupMember(String jsonStr);

    String deleteGroupMember(String jsonStr);

    String deleteGroup(String jsonStr);


    boolean sendRobotMsg(Integer type, Long id, String text);
}
