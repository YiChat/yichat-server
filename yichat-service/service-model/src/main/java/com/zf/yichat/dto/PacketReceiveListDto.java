package com.zf.yichat.dto;

import com.zf.yichat.utils.common.FsConst;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:23 2019/6/12 2019
 */
public class PacketReceiveListDto implements Serializable {

    private Long userId;
    private String nick;
    private String avatar;
    private BigDecimal money;
    private String moneyDesc;
    private String receiveTime;
    private Integer type;
    private Long packetId;

    public Long getPacketId() {
        return packetId;
    }

    public void setPacketId(Long packetId) {
        this.packetId = packetId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
        setMoneyDesc(money + FsConst.UNIT);
    }

    public String getMoneyDesc() {
        return moneyDesc;
    }

    public void setMoneyDesc(String moneyDesc) {
        this.moneyDesc = moneyDesc;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
}
