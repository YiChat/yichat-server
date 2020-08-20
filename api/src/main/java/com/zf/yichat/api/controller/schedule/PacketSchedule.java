package com.zf.yichat.api.controller.schedule;

import com.zf.yichat.service.PacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:51 2019/7/22 2019
 */
@Component
public class PacketSchedule {

    @Autowired
    private PacketService packetService;

    //创建者冻结余额中扣除金额 这个等定时任务统一操作 3分钟执行一次
    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void packetBack() {
        packetService.packetTimeoutBack();
    }
}
