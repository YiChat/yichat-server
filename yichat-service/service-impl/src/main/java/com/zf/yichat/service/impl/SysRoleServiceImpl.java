package com.zf.yichat.service.impl;


import com.github.pagehelper.PageHelper;
import com.zf.yichat.mapper.SysRoleMenuRelationMapper;
import com.zf.yichat.mapper.SysUserRoleMapper;
import com.zf.yichat.model.SysRoleMenuRelation;
import com.zf.yichat.model.SysUserRole;
import com.zf.yichat.service.SysRoleService;
import com.zf.yichat.utils.common.GeneralUtils;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsPageUtils;
import com.zf.yichat.utils.response.FsResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysUserRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public FsResponse<SysUserRole> selectIndexList(FsPage page, String name) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return FsPageUtils.genreateListResponse(roleMapper.selectByExample(example));
    }

    @Override
    public SysUserRole selectById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysUserRole role, String menus) {
        int i = 0;
        if (Objects.nonNull(role.getId())) {
            i = roleMapper.updateByPrimaryKeySelective(role);
        } else {
            i = roleMapper.insertUseGeneratedKeys(role);
        }

        //
        if (StringUtils.isNotBlank(menus)) {
            Example example = new Example(SysRoleMenuRelation.class);
            example.createCriteria().andEqualTo("roleId", role.getId());
            roleMenuRelationMapper.deleteByExample(example);
            roleMenuRelationMapper.insertList(GeneralUtils.string2ListLong(menus).stream().map(v -> {
                SysRoleMenuRelation relation = new SysRoleMenuRelation();
                relation.setMenuId(v.intValue());
                relation.setRoleId(role.getId());
                relation.setCtime(new Date());
                return relation;
            }).collect(Collectors.toList()));
        }

        return i;
    }
}
