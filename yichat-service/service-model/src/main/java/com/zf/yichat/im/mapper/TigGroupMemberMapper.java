package com.zf.yichat.im.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.TigGroupMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TigGroupMemberMapper extends FsMapper<TigGroupMember> {

    List<TigGroupMember> selectList(@Param("gid") Integer groupId);
}