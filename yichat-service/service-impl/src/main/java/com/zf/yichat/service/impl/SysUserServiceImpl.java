package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.UserIndexDto;
import com.zf.yichat.dto.UserRoleSelectCondition;
import com.zf.yichat.mapper.SysUserMapper;
import com.zf.yichat.mapper.SysUserRoleMapper;
import com.zf.yichat.model.SysUser;
import com.zf.yichat.model.SysUserRole;
import com.zf.yichat.service.SysUserService;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.UserRoleEnum;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper roleMapper;


    @Override
    public SysUser selectUserByUsername(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", username);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public FsResponse<UserIndexDto> selectIndexList(FsPage page, String name) {
        Example example = new Example(SysUser.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        example.setOrderByClause(" ctime desc");
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(userMapper.selectByExample(example), v -> {
            UserIndexDto dto = new UserIndexDto();
            dto.setUser(v);
            dto.setRole(roleMapper.selectByPrimaryKey(v.getRoleid()));
            return dto;
        });
    }

    @Override
    public List<SysUserRole> selectRoleList() {
        return roleMapper.selectAll();
    }


    @Override
    public int saveUser(SysUser user) {
        if (Objects.nonNull(user.getId())) {
            return userMapper.updateByPrimaryKeySelective(user);
        } else {
            return userMapper.insertSelective(user);
        }
    }

    @Override
    public SysUser selectUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateUserStatus(Integer id) {
        SysUser user = userMapper.selectByPrimaryKey(id);
        user.setStatus(user.getStatus() == 0 ? 1 : 0);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<SysUser> selectListByCompanyId(Integer userId) {
        UserRoleSelectCondition condition = genCondition(userId);
        if (Objects.nonNull(condition.getCompanyId())) {
            Example select = new Example(SysUser.class);
            select.createCriteria().andEqualTo("companyid", condition.getCompanyId()).andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT);
            return userMapper.selectByExample(select);
        } else if (Objects.nonNull(condition.getUserId())) {
            Example select = new Example(SysUser.class);
            select.createCriteria().andEqualTo("id", condition.getUserId()).andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT);
            return userMapper.selectByExample(select);
        } else {
            Example select = new Example(SysUser.class);
            select.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT);
            return userMapper.selectByExample(select);

        }
    }

    @Override
    public List<SysUser> selectListByRole(UserRoleEnum roleEnum) {
        Example select = new Example(SysUser.class);
        select.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT).andEqualTo("roleid", selectRoleByCode(roleEnum.getCode()).getId());
        return userMapper.selectByExample(select);
    }

    /**
     * code查询角色
     *
     * @param code
     * @return
     */
    @Override
    public SysUserRole selectRoleByCode(String code) {
        Example select = new Example(SysUserRole.class);
        select.createCriteria().andEqualTo("code", code);
        return roleMapper.selectOneByExample(select);
    }

    @Override
    public UserRoleSelectCondition genCondition(Integer currentUserId) {
        SysUser user = userMapper.selectByPrimaryKey(currentUserId);
        Contracts.assertNotNull(user, "用户不存在");
        String code = roleMapper.selectByPrimaryKey(user.getRoleid()).getCode();
        UserRoleSelectCondition condition = new UserRoleSelectCondition();
        boolean roleValid = false;
        if (StringUtils.equals(UserRoleEnum.ADMIN.getCode(), code)) {
            condition.setCompanyId(null);
            condition.setUserId(null);
            condition.setRole(UserRoleEnum.ADMIN);
            roleValid = true;
        } else if (StringUtils.equals(UserRoleEnum.COMPANY_ADMIN.getCode(), code)) {
            condition.setCompanyId(user.getCompanyid());
            condition.setUserId(null);
            condition.setRole(UserRoleEnum.COMPANY_ADMIN);
            roleValid = true;
        } else if (StringUtils.equals(UserRoleEnum.SALESMAN.getCode(), code)) {
            condition.setCompanyId(null);
            condition.setUserId(currentUserId);
            condition.setRole(UserRoleEnum.SALESMAN);
            roleValid = true;
        } else {
            condition.setCompanyId(null);
            condition.setUserId(currentUserId);
            condition.setRole(UserRoleEnum.NULL);
            roleValid = true;
        }

        Contracts.assertTrue(roleValid, "角色不存在");
        return condition;
    }

    @Override
    public List<SysUser> selectList(String name) {
        Example select = new Example(SysUser.class);
        select.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT);
        return userMapper.selectByExample(select);
    }
}
