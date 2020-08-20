package com.zf.yichat.api.controller.packet;

import com.zf.yichat.api.PacketDto;
import com.zf.yichat.api.controller.BaseController;
import com.zf.yichat.api.dto.request.*;
import com.zf.yichat.model.Packet;
import com.zf.yichat.service.BalanceService;
import com.zf.yichat.service.PacketService;
import com.zf.yichat.utils.YiChatMsgCode;
import com.zf.yichat.utils.response.FsPage;
import com.zf.yichat.utils.response.FsResponse;
import com.zf.yichat.utils.response.FsResponseGen;
import com.zf.yichat.vo.PacketType;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 红包功能
 *
 * @author yichat
 * @date create in 14:55 2019/6/11 2019
 */
@RestController
@RequestMapping("packet")
public class PacketController extends BaseController {

    @Autowired
    private PacketService packetService;

    @Autowired
    private BalanceService balanceService;

    /**
     * 创建单聊红包
     */
    @RequestMapping("create/single")
    public FsResponse single(@RequestBody PacketSingleCreateRequest params) {
        params.valid();
        /*if (params.getType() != 0) {
            balanceService.update(getUserId(), BalanceType.ADD, params.getMoney(), getUserId(), params.getType() == 1 ? "微信充值" : "支付宝充值");
        }*/
        Packet packet = packetService.create(params.getType(), getUserId(), params.getPassword(), params.getReceiveUserId(), 1, params.getMoney(), params.getContent(), null);

        PacketDto dto = new PacketDto();
        dto.setContent(packet.getContent());
        dto.setMoney(packet.getMoney());
        dto.setPacketId(packet.getId());
        dto.setStatus(packet.getStatus());
        return FsResponseGen.successData(dto);

    }

    /**
     * 创建群聊红包
     */
    @RequestMapping("create/group")
    public FsResponse group(@RequestBody PacketGroupCreateRequest params) {
        params.valid();
        /*if (params.getType() != 0) {
            balanceService.update(getUserId(), BalanceType.ADD, params.getMoney(), getUserId(), params.getType() == 1 ? "微信充值" : "支付宝充值");
        }*/
        Packet packet = packetService.create(params.getType(), getUserId(), params.getPassword(), null, params.getNum(), params.getMoney(), params.getContent(), params.getGroupId());

        PacketDto dto = new PacketDto();
        dto.setContent(packet.getContent());
        dto.setMoney(packet.getMoney());
        dto.setPacketId(packet.getId());
        dto.setStatus(packet.getStatus());
        return FsResponseGen.successData(dto);

    }

    /**
     * 领取红包
     */
    @RequestMapping("receive")
    public FsResponse receive(@RequestBody PacketReceiveRequest params) {
        params.valid();

        return packetService.receive(params.getPacketId(), getUserId());


    }


    /**
     * 红包详情
     */
    @RequestMapping("detail")
    public FsResponse detail(@RequestBody PacketReceiveRequest params) {
        params.valid();

        Contracts.assertNotNull(params.getPacketId(), YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        return FsResponseGen.successData(packetService.detail(params.getPacketId(), getUserId()));


    }

    /**
     * 发送红包汇总信息
     */
    @RequestMapping("send/info")
    public FsResponse sendInfo(@RequestBody PacketSendInfoRequest params) {
        params.valid();

        return FsResponseGen.successData(packetService.countSendMoney(PacketType.valOf(params.getType()), getUserId()));


    }

    /**
     * 发送红包列表
     */
    @RequestMapping("send/list")
    public FsResponse sendList(@RequestBody PacketSendListRequest params) {
        params.valid();

        return packetService.selectSendList(FsPage.init(params.getPageNo(), params.getPageSize()), PacketType.valOf(params.getType()), getUserId());


    }

    /**
     * 领取红包汇总信息
     */
    @RequestMapping("receive/info")
    public FsResponse receiveInfo(@RequestBody PacketReceiveInfoRequest params) {
        params.valid();

        return FsResponseGen.successData(packetService.countReceiveMoney(PacketType.valOf(params.getType()), getUserId()));


    }

    /**
     * 领取红包列表
     */
    @RequestMapping("receive/list")
    public FsResponse receiveList(@RequestBody PacketReceiveListRequest params) {
        params.valid();

        return packetService.selectReceiveList(FsPage.init(params.getPageNo(), params.getPageSize()), PacketType.valOf(params.getType()), getUserId());


    }
}
