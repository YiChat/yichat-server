package com.zf.yichat.service;


import com.zf.yichat.model.SysMenu;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> selectListByUserId(Integer userId);

    FsResponse selectIndexList(FsPage init);

    List<SysMenu> selectParent();

    int save(SysMenu menu);

    SysMenu selectByName(String name);

    SysMenu selectById(Integer id);

    List<SysMenu> selectAll();

    List<SysMenu> selectListByRoleId(Integer id);
}
