package com.zf.yichat.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:05 2019/6/12 2019
 */
public class PacketInfoDto implements Serializable {
    private List<PacketReceiveDto> list;
    private Long packetId;
    private String content;
    private String nick;
    private Long userId;
    private String avatar;
    //0已创建 1有领取 2领取完 3已超时
    private Integer status;
    private Integer num;
    private Integer receiveNum;
    private BigDecimal money;
    private BigDecimal receiveMoney;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public List<PacketReceiveDto> getList() {
        return list;
    }

    public void setList(List<PacketReceiveDto> list) {
        this.list = list;
    }

    public Long getPacketId() {
        return packetId;
    }

    public void setPacketId(Long packetId) {
        this.packetId = packetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
