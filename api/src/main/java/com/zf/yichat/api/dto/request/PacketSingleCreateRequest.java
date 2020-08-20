package com.zf.yichat.api.dto.request;

import com.zf.yichat.utils.YiChatMsgCode;
import org.hibernate.validator.internal.util.Contracts;

import java.math.BigDecimal;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 15:04 2019/6/11 2019
 */
public class PacketSingleCreateRequest extends FsRequest {

    private Long receiveUserId;

    private BigDecimal money;

    private String content;

    private String password;

    private Integer type;//0余额 1微信 2支付宝

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void valid() {

        Contracts.assertNotNull(receiveUserId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(money, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(money.compareTo(BigDecimal.ZERO) > 0, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(content, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(type, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        if (type == 0) {
            Contracts.assertNotNull(password, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

        }

    }
}
