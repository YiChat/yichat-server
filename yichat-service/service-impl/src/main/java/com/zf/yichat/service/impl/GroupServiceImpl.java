package com.zf.yichat.service.impl;

import com.github.pagehelper.PageInfo;
import com.zf.yichat.dto.GroupUserDto;
import com.zf.yichat.im.mapper.TigGroupMapper;
import com.zf.yichat.im.mapper.TigGroupMemberMapper;
import com.zf.yichat.mapper.GroupAdminMapper;
import com.zf.yichat.mapper.GroupSilentMapper;
import com.zf.yichat.model.*;
import com.zf.yichat.service.GroupService;
import com.zf.yichat.service.UserService;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.GroupRoleType;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 10:21 2019/6/18 2019
 */
@Service
public class GroupServiceImpl implements GroupService {


    @Autowired
    private UserService userService;


    @Autowired
    private TigGroupMapper tigGroupMapper;

    @Autowired
    private TigGroupMemberMapper tigGroupMemberMapper;

    @Autowired
    private GroupAdminMapper groupAdminMapper;

    @Autowired
    private GroupSilentMapper groupSilentMapper;


    @Override
    public FsResponse selectUserList(FsPage page, Long userId, Integer groupId) {


   /*     Example select = new Example(TigGroupMember.class);
        select.createCriteria().andEqualTo("gid", groupId);*/

//        if (page.getPageNo() != -1) {
//            PageHelper.startPage(page.getPageNo(), page.getPageSize());
//        }

        //暂时限制返回1000个群用户  10.23日已取消
        List<TigGroupMember> list = tigGroupMemberMapper.selectList(groupId);
        PageInfo<TigGroupMember> pageInfo = new PageInfo<>(list);
        FsResponse res = DtoChangeUtils.getPageList(userService.selectList(pageInfo.getList().stream().map(v -> NumberUtils.toLong(v.getUid())).collect(Collectors.toList())), c -> {

            GroupUserDto dto = new GroupUserDto();
            dto.setAvatar(c.getAvatar());
            dto.setNick(c.getNick());
            dto.setUserId(c.getId());
            return dto;

        });

        res.setCount(pageInfo.getTotal());
        //第一页处理 管理员在第一个
        if (page.getPageNo() == 1 && res.getCount() > 0) {
            List<GroupUserDto> dtos = (List<GroupUserDto>) res.getData();
            //排序
            Long first = NumberUtils.toLong(list.get(0).getUid());
            GroupUserDto firstD = null;
            List<GroupUserDto> news = new ArrayList<>();
            news.add(new GroupUserDto());
            for (GroupUserDto d : dtos) {
                if (d.getUserId().compareTo(first) == 0) {
                    firstD = d;
                } else {
                    news.add(d);
                }
            }

            if (firstD != null) {
                news.set(0, firstD);
            }

            res.setData(news);
        }


        return res;
    }


    @Override
    public void updateRoleType(Long userId, Integer groupId, GroupRoleType roleType, Integer status) {

        GroupAdmin admin = selectAdmin(userId, groupId);

        //取消设置管理员
        if (Objects.nonNull(admin)) {
            admin.setStatus(status == 0 ? 1 : 0);
            admin.setUtime(new Date());
            groupAdminMapper.updateByPrimaryKeySelective(admin);
        } else {
            admin = new GroupAdmin();
            admin.setGroupId(groupId);
            admin.setUserId(userId);
            admin.setStatus(status == 0 ? 1 : 0);
            groupAdminMapper.insertSelective(admin);
        }

    }

    @Override
    public List<GroupUserDto> selectAdminList(Integer groupId) {
        Example example = new Example(GroupAdmin.class);
        example.createCriteria().andEqualTo("groupId", groupId).andEqualTo("status", 0);
        return DtoChangeUtils.getList(groupAdminMapper.selectByExample(example), v -> {
            GroupUserDto dto = new GroupUserDto();

            User user = userService.selectById(v.getUserId());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setUserId(v.getUserId());
            return dto;
        });
    }

    @Override
    public List<GroupUserDto> selectSilentList(Integer groupId) {
        Example example = new Example(GroupSilent.class);
        example.createCriteria().andEqualTo("groupId", groupId).andEqualTo("status", 1).andEqualTo("type", 0);
        return DtoChangeUtils.getList(groupSilentMapper.selectByExample(example), v -> {
            GroupUserDto dto = new GroupUserDto();
            User user = userService.selectById(v.getUserId());
            dto.setAvatar(user.getAvatar());
            dto.setNick(user.getNick());
            dto.setUserId(v.getUserId());
            return dto;
        });
    }

    @Override
    public GroupAdmin selectAdmin(Long userId, Integer groupId) {
        Example example = new Example(GroupAdmin.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("groupId", groupId);
        return groupAdminMapper.selectOneByExample(example);
    }

    @Override
    public void updateSilentStatus(Long userId, Integer groupId, Integer silentType, Integer status) {

        Example example = new Example(GroupSilent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("groupId", groupId).andEqualTo("type", silentType);

        //如果是群成员禁言
        if (Objects.nonNull(silentType) && silentType == 0) {
            criteria.andEqualTo("userId", userId);
        }

        GroupSilent silent = groupSilentMapper.selectOneByExample(example);

        if (Objects.nonNull(silent)) {
            silent.setStatus(status);
            silent.setUtime(new Date());
            groupSilentMapper.updateByPrimaryKeySelective(silent);

        } else {
            silent = new GroupSilent();
            silent.setGroupId(groupId);
            silent.setStatus(1);
            silent.setUserId(userId);
            silent.setType(silentType);
            groupSilentMapper.insertSelective(silent);
        }

    }

    @Override
    public boolean isAdmin(Integer groupId, Long userId) {
        GroupAdmin admin = selectAdmin(userId, groupId);
        return Objects.nonNull(admin) && admin.getStatus() == 0;
    }

    @Override
    public Long getCreator(Integer groupId) {
        return Long.parseLong(selectById(groupId).getCreator());
    }

    @Override
    public TigGroup selectById(Integer id) {
        return tigGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public GroupRoleType selectRoleType(Integer groupId, Long userId) {
        return isAdmin(groupId, userId) ? GroupRoleType.amdin : (getCreator(groupId).compareTo(userId) == 0 ? GroupRoleType.creator : GroupRoleType.common);
    }


    @Override
    public List<TigGroupMember> selectGroupByUserId(Long userId) {
        Example example = new Example(TigGroupMember.class);
        example.createCriteria().andEqualTo("uid", userId);
        return tigGroupMemberMapper.selectByExample(example);
    }

    @Override
    public TigGroupMember selectGroupMember(Integer groupId, Long userId) {
        Example example = new Example(TigGroupMember.class);
        example.createCriteria().andEqualTo("uid", userId).andEqualTo("gid", groupId);
        return tigGroupMemberMapper.selectOneByExample(example);
    }

    @Override
    public Integer countMember(Integer groupId) {
        Example example = new Example(TigGroupMember.class);
        example.createCriteria().andEqualTo("gid", groupId);
        return tigGroupMemberMapper.selectCountByExample(example);
    }

    @Override
    public Integer getGroupSilentStatus(Integer groupId) {

        Example example = new Example(GroupSilent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("groupId", groupId).andEqualTo("type", 1);

        GroupSilent silent = groupSilentMapper.selectOneByExample(example);

        return Optional.ofNullable(silent).map(GroupSilent::getStatus).orElse(0);


    }

    @Override
    public List<TigGroupMember> selectGroupByIds(List<Integer> groupId, Long userId) {
        Example example = new Example(TigGroupMember.class);
        example.createCriteria().andEqualTo("uid", userId).andIn("gid", groupId);
        return tigGroupMemberMapper.selectByExample(example);
    }


    @Override
    public List<TigGroup> selectMyGroupList(Long userId) {

        return tigGroupMapper.selectList(userId);
    }

    @Override
    public List<TigGroup> selectGroupListByCreater(Long userId) {
        Example example = new Example(TigGroup.class);
        example.createCriteria().andEqualTo("creator", userId);
        return tigGroupMapper.selectByExample(example);
    }

    @Override
    public int selectProtectStatus(Integer groupId) {

        Example example = new Example(GroupSilent.class);
        Example.Criteria criteria = example.createCriteria();
        //状态为群保护状态设置
        criteria.andEqualTo("groupId", groupId).andEqualTo("type", 2);
        GroupSilent silent = groupSilentMapper.selectOneByExample(example);
        return Optional.ofNullable(silent).map(GroupSilent::getStatus).orElse(0);
    }

    @Override
    public void setProtectStatus(Long userId, Integer groupId, Integer status) {
        Example example = new Example(GroupSilent.class);
        Example.Criteria criteria = example.createCriteria();
        //状态为群保护状态设置
        criteria.andEqualTo("groupId", groupId).andEqualTo("type", 2);
        GroupSilent silent = groupSilentMapper.selectOneByExample(example);
        if (Objects.nonNull(silent)) {
            silent.setStatus(status);
            silent.setUserId(userId);
            groupSilentMapper.updateByPrimaryKeySelective(silent);
        } else {
            silent = new GroupSilent();
            silent.setStatus(status);
            silent.setUserId(userId);
            silent.setGroupId(groupId);
            silent.setType(2);
            groupSilentMapper.insertSelective(silent);
        }
    }
}
