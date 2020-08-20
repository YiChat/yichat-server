package com.zf.yichat.service;

import com.zf.yichat.dto.UserIndexDto;
import com.zf.yichat.dto.UserRoleSelectCondition;
import com.zf.yichat.model.SysUser;
import com.zf.yichat.model.SysUserRole;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.UserRoleEnum;

import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 18:09 2019/7/8 2019
 */
public interface SysUserService {
    SysUser selectUserByUsername(String username);

    FsResponse<UserIndexDto> selectIndexList(FsPage page, String name);

    List<SysUserRole> selectRoleList();

    int saveUser(SysUser user);

    SysUser selectUserById(Integer id);

    int updateUserStatus(Integer id);

    List<SysUser> selectListByCompanyId(Integer userId);

    List<SysUser> selectListByRole(UserRoleEnum roleEnum);

    /**
     * code查询角色
     *
     * @param code
     * @return
     */
    SysUserRole selectRoleByCode(String code);

    UserRoleSelectCondition genCondition(Integer currentUserId);

    List<SysUser> selectList(String name);
}
