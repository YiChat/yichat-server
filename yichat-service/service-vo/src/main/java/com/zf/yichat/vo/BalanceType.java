package com.zf.yichat.vo;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 16:45 2019/6/10 2019
 */
public enum BalanceType {

    NULL(-1, "空", ""),
    /**
     * 客户端余额充值
     */
    ADD(0, "余额充值", "+"),
    /**
     * 提现
     */
    WITHDRAW(1, "提现成功", "-"),
    /**
     * 创建红包
     */
    CREATE_PACKET(2, "余额创建红包", "-")
    /**
     * 领取红包
     */
    , RECEIVE_PACKET(3, "领取红包", "+"),

    /**
     * 扣除发红包人的冻结
     */
    RECEIVE_PACKET_CREATOR(4, "扣除红包冻结余额", "-"),

    /**
     * 扣除发红包人的冻结
     */
    RETURN_PACKET(5, "红包退回", "+"),

    /**
     * 签到获取
     */
    SIGN(6, "签到", "+"),
    /**
     * 后台直接充值
     */
    BACK_ADD(7, "系统充值", "+"),
    /**
     * 后台直接提现
     */
    BACK_WITHDRAW(8, "系统提现", "-"),

    /**
     * 红包充值  --已停用
     */
    PACKET_ADD(9, "红包充值", "+"),
    /**
     * 微信创建红包
     */
    WEIXIN_CREATE_PACKET(10, "微信充值创建红包", ""),
    /**
     * 微信创建红包
     */
    ALIPAY_CREATE_PACKET(11, "支付宝充值创建红包", ""),

    /**
     * 提现申请
     */
    WITHDRAW_APPLY(12, "提现申请", "-"),

    /**
     * 提现失败
     */
    WITHDRAW_FAIL(13, "提现失败", "+"),

    /**
     * 观看视频奖励
     */
    VIDEO_PLAY(110, "观看视频奖励", "+"),

    /**
     * 观看视频奖励
     */
    VIDEO_COMMENT(111, "评论视频奖励", "+"),

    /**
     * 推荐注册奖励
     */
    RECOMMEND_USER(112, "推荐注册奖励", "+"),

    VIDEO_PUBLISH(113, "发布视频奖励", "+"),

    VIDEO_PUBLISH_DELETE(114, "视频审核不通过扣除", "-"),
    VIDEO_PRAISE(115, "点赞视频奖励", "+");


    private int val;
    private String desc;
    //对可提现余额造成的加减
    private String direct;
    BalanceType(Integer val, String desc, String direct) {
        this.val = val;
        this.desc = desc;
        this.direct = direct;
    }

    public static BalanceType valOf(Integer type) {

        for (BalanceType b : BalanceType.values()) {
            if (b.getVal() == type) {
                return b;
            }
        }

        return BalanceType.NULL;

    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
