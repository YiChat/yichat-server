package com.zf.yichat.service.impl;

import com.github.pagehelper.PageHelper;
import com.zf.yichat.dto.MenuIndexDto;
import com.zf.yichat.mapper.SysMenuMapper;
import com.zf.yichat.model.SysMenu;
import com.zf.yichat.service.SysMenuService;
import com.zf.yichat.utils.common.DtoChangeUtils;
import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenu> selectListByUserId(Integer userId) {
        return menuMapper.selectByUserId(userId);
    }

    @Override
    public FsResponse<MenuIndexDto> selectIndexList(FsPage page) {
        Example example = new Example(SysMenu.class);
        example.setOrderByClause(" sort asc");
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return DtoChangeUtils.getPageList(menuMapper.selectByExample(example), v -> {
            MenuIndexDto dto = new MenuIndexDto();
            dto.setMenu(v);
            dto.setpName(Optional.ofNullable(v.getPid()).map(g -> g > 0).orElse(false) ? menuMapper.selectByPrimaryKey(v.getPid()).getName() : FsConst.Str.EMPTY);
            return dto;
        });
    }

    @Override
    public List<SysMenu> selectParent() {
        Example example = new Example(SysMenu.class);
        example.createCriteria().andEqualTo("pid", 0);
        example.setOrderByClause(" sort asc");
        return menuMapper.selectByExample(example);
    }

    @Override
    public int save(SysMenu menu) {
        if (Objects.nonNull(menu.getId())) {
            return menuMapper.updateByPrimaryKeySelective(menu);
        } else {
            return menuMapper.insertSelective(menu);
        }
    }

    @Override
    public SysMenu selectByName(String name) {
        Example example = new Example(SysMenu.class);
        example.createCriteria().andEqualTo(FsConst.SqlColumn.NAME, name);
        return menuMapper.selectOneByExample(example);
    }

    @Override
    public SysMenu selectById(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysMenu> selectAll() {
        Example example = new Example(SysMenu.class);
        example.createCriteria().andEqualTo(FsConst.SqlColumn.STATUS, FsConst.Status.EFFECT);
        example.setOrderByClause(" sort asc");
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<SysMenu> selectListByRoleId(Integer id) {
        return menuMapper.selectByRoleId(id);
    }
}
