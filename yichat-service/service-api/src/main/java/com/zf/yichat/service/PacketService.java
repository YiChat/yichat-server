package com.zf.yichat.service;

import com.zf.yichat.dto.PacketInfoDto;
import com.zf.yichat.dto.PacketReceiveInfoDto;
import com.zf.yichat.dto.PacketSendInfoDto;
import com.zf.yichat.model.Packet;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.vo.PacketType;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:08 2019/6/11 2019
 */
public interface PacketService {

    Packet create(Integer srcType, Long userId, String password, Long receiveUserId, Integer num, BigDecimal money, String content, Long groupId);

    Packet selectById(Long id);

    /**
     * 领取红包
     *
     * @param packetId 红包
     * @param userId   领取人
     */
    FsResponse receive(Long packetId, Long userId);

    /**
     * 红包领取信息
     *
     * @param packetId 红包ID
     */
    PacketInfoDto selectList(Long packetId, Long userId);

    /**
     * 红包详情
     *
     * @param packetId 红包ID
     */
    PacketInfoDto detail(Long packetId, Long userId);

    /**
     * 红包领取信息
     **/
    PacketSendInfoDto countSendMoney(PacketType type, Long userId);

    PacketReceiveInfoDto countReceiveMoney(PacketType type, Long userId);

    FsResponse selectSendList(FsPage page, PacketType type, Long userId);

    FsResponse selectReceiveList(FsPage page, PacketType type, Long userId);

    void packetTimeoutBack();
}
