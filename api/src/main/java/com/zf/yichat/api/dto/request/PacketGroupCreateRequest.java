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
public class PacketGroupCreateRequest extends FsRequest {

    private Integer num;

    private BigDecimal money;

    private String content;

    private Long groupId;

    private String password;

    private Integer type;//0余额 1微信 3支付宝

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


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

        Contracts.assertNotNull(num, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(num > 0, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(money, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertTrue(money.compareTo(BigDecimal.ZERO) > 0, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(content, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(groupId, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        Contracts.assertNotNull(type, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());
        if (type == 0) {
            Contracts.assertNotNull(password, YiChatMsgCode.SYSTEM_PARAM_ERROR.msg());

        }

    }
}
