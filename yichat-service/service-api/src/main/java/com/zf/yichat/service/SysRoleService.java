package com.zf.yichat.service;


import com.zf.yichat.model.SysUserRole;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;

public interface SysRoleService {

    FsResponse<SysUserRole> selectIndexList(FsPage page, String name);

    SysUserRole selectById(Integer id);

    int save(SysUserRole role, String menus);
}
