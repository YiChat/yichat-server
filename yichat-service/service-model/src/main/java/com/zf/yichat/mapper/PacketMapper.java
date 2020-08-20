package com.zf.yichat.mapper;

import com.zf.yichat.base.FsMapper;
import com.zf.yichat.dto.PacketReceiveInfoDto;
import com.zf.yichat.dto.PacketReceiveListDto;
import com.zf.yichat.dto.PacketSendInfoDto;
import com.zf.yichat.dto.PacketSendListDto;
import com.zf.yichat.model.Packet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PacketMapper extends FsMapper<Packet> {
    PacketSendInfoDto selectSendCount(@Param("type") Integer val, @Param("userId") Long userId);

    PacketReceiveInfoDto selectReceiveCount(@Param("type") Integer val, @Param("userId") Long userId);

    Long countUserReceiveLuck(@Param("userId") Long userId);

    List<PacketSendListDto> selectSendList(@Param("type") Integer val, @Param("userId") Long userId);

    List<PacketReceiveListDto> selectReceiveList(@Param("type") Integer val, @Param("userId") Long userId);
}