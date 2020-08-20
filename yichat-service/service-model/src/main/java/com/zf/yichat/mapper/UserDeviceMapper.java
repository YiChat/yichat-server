package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.model.UserDevice;
import org.apache.ibatis.annotations.Param;

public interface UserDeviceMapper extends FsMapper<UserDevice> {
    void updateInsert(@Param("userId") Long id, @Param("deviceId") String deviceId);
}