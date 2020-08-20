package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends FsMapper<SysMenu> {
    List<SysMenu> selectByUserId(@Param("userId") Integer userId);

    List<SysMenu> selectByRoleId(@Param("roleId") Integer id);
}