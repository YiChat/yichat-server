package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.dto.PacketReceiveDto;
import com.zf.yichat.model.PacketReceive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PacketReceiveMapper extends FsMapper<PacketReceive> {
    List<PacketReceiveDto> selectList(@Param("packetId") Long packetId);
}