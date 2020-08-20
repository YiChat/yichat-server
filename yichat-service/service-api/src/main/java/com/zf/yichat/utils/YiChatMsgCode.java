package com.zf.yichat.utils;

import com.zf.yichat.utils.common.FsConst;
import com.zf.yichat.utils.response.MsgCode;

/**
 * 一句话描述功能
 *
 * @author yichat
 * @date create in 17:51 2019/5/28 2019
 */
public interface YiChatMsgCode {

    String SUCCESS = "0";

    MsgCode SYSTEM_ERROR = new MsgCode("001", "系统异常");

    MsgCode SYSTEM_PARAM_ERROR = new MsgCode("002", "参数异常");
    MsgCode SYSTEM_PARAM_TYPE_ERROR = new MsgCode("010", "参数类型错误");
    MsgCode SYSTEM_PARAM_HEADER_ERROR = new MsgCode("011", "请求头部参数异常");
    MsgCode SYSTEM_AES_ERROR = new MsgCode("020", "AES解析异常");
    MsgCode SYSTEM_BODY_MISS = new MsgCode("021", "body体丢失");

    MsgCode SYSTEM_LOGIN_STATUS = new MsgCode("003", "重新登陆");

    MsgCode SYSTEM_USER_CLOSE = new MsgCode("004", "用户被封禁");

    MsgCode SYSTEM_USER_ERROR = new MsgCode("005", "用户异常");

    MsgCode SYSTEM_USER_BALANCE_ACCOUNT = new MsgCode("006", "余额账号未创建");

    MsgCode SYSTEM_USER_BALANCE_ACCOUNT_STATUS = new MsgCode("007", "余额账号被冻结");

    MsgCode SYSTEM_USER_BALANCE_ACCOUNT_PASSWORD_ERROR = new MsgCode("008", "支付密码错误");

    MsgCode SYSTEM_SMS_SEND_FAIL = new MsgCode("009", "短信发送失败");

    MsgCode SYSTEM_KEY_ERROR = new MsgCode("010", "key错误");

    //注册
    MsgCode REGISTER_MOBILE_EXIST = new MsgCode("102", "手机号已被注册");
    MsgCode REGISTER_MOBILE_APPID_EXIST = new MsgCode("102", "用户名已被使用");

    MsgCode REGISTER_FAIL = new MsgCode("103", "账号创建失败");
    MsgCode REGISTER_DEVICEID_EXIST = new MsgCode("104", "设备已被注册");


    //登录
    MsgCode LOGIN_DEVICE_CLOSE = new MsgCode("112", "设备被封禁");
    MsgCode LOGIN_USER_NONE = new MsgCode("114", "账号不存在");
    MsgCode LOGIN_PASSWORD_ERROR = new MsgCode("115", "密码错误");

    //用户信息
    MsgCode USER_INFO_NONE = new MsgCode("116", "用户不存在");
    MsgCode USER_INFO_MOBILE_EXIST = new MsgCode("117", "手机号已存在");
    MsgCode USER_INFO_RECOMMEND_CODE_NONE = new MsgCode("118", "推荐码无效");

    //密码重置
    MsgCode USE_PASSWORD_RESET_REPEAT = new MsgCode("121", "密码不能与原密码相同");

    MsgCode USE_PASSWORD_SRC_WRONG = new MsgCode("122", "原密码错误");


    //用户信息更新
    MsgCode USER_INFO_UPDATE_UCODE = new MsgCode("131", "用户识别码只能修改一次");


    //好友添加
    MsgCode FRIEND_APPLY_ADD_EXIST = new MsgCode("141", "已存在申请记录");
    MsgCode FRIEND_APPLY_ADD_EXIST_FRIEND = new MsgCode("142", "已是好友关系");
    MsgCode FRIEND_APPLY_ADD_NOT_MYSELF = new MsgCode("143", "不能添加自己");

    //好友审核
    MsgCode FRIEND_CHECK_NONE = new MsgCode("151", "审核失败");
    MsgCode FRIEND_CHECK_AUTH = new MsgCode("152", "无权限审核");
    MsgCode FRIEND_CHECK_REPEAT = new MsgCode("153", "已审核");

    //好友删除
    MsgCode FRIEND_DELETTE_NONE = new MsgCode("161", "删除失败");

    //
    MsgCode FRIEND_NONE = new MsgCode("165", "不是好友关系");

    //动态删除
    MsgCode TREND_DELETE_AUTH = new MsgCode("171", "无权限删除动态");

    //动态列表
    MsgCode TREND_LIST_AUTH = new MsgCode("181", "无权限查看动态");

    //动态评论
    MsgCode TREND_COMMENT_FAILED = new MsgCode("191", "评论失败");

    //删除评论
    MsgCode TREND_COMMENT_DELETE_FAIL = new MsgCode("201", "删除评论失败");

    //创建红包
    MsgCode PACKET_CREATE_NO_ENOUGH_BALANCE = new MsgCode("211", "余额不足");

    MsgCode PACKET_CREATE_MONEY_LIMIT = new MsgCode("212", "单个红包不能低于0.01" + FsConst.UNIT);

    MsgCode PACKET_CREATE_SPLIT_NUM_LIMIT = new MsgCode("213", "红包个数不能超过100");

    MsgCode PACKET_CREATE_FAILED = new MsgCode("214", "创建红包失败");

    MsgCode PACKET_CREATE_MAX_LIMIT = new MsgCode("215", "群红包拆分单个不能超过200" + FsConst.UNIT);

    //领取红包
    MsgCode PACKET_RECEIVE_FAILED = new MsgCode("221", "领取失败");
    MsgCode PACKET_RECEIVE_OVER_TIME = new MsgCode("222", "红包过期");
    MsgCode PACKET_RECEIVE_NONE = new MsgCode("223", "红包已抢完");
    MsgCode PACKET_RECEIVE_REPEAT = new MsgCode("224", "已抢过红包");
    MsgCode PACKET_RECEIVE_NONEMINE = new MsgCode("225", "单聊红包不能自己领");


    //群设置
    MsgCode GROUP_ADMIN_SET_MYSELF = new MsgCode("231", "不能设置自己");
    MsgCode GROUP_ADMIN_SET_NONE = new MsgCode("232", "群成员不存在");
    MsgCode GROUP_SET = new MsgCode("233", "无权限操作");
    MsgCode GROUP_MEMBER_NONE = new MsgCode("234", "不是该群成员");

    //群组
    MsgCode GROUP_INFO_NONE = new MsgCode("240", "群组不存在");
    //消息撤销
    MsgCode MESSAGE_CANCLE_AUTH = new MsgCode("245", "无权限删除");
    MsgCode MESSAGE_UPLOAD_CONTENT_FAILED = new MsgCode("246", "上传消息[content]Base64解析失败");

    //提现申请
    MsgCode WITHDRAW_APPLY_EXIST = new MsgCode("250", "已存在待处理提现申请");
    MsgCode WITHDRAW_APPLY_BALANCE_LITTLE = new MsgCode("251", "余额不足");

    //APP版本
    MsgCode APP_VERSION_NONE = new MsgCode("255", "尚未设置APP版本");

    //短信验证码
    MsgCode SMS_OVERTIME = new MsgCode("260", "验证码已超时");
    MsgCode SMS_CHECK_VALID = new MsgCode("261", "短信验证码错误");
    MsgCode SMS_CHECK_TIME = new MsgCode("262", "校验次数已用完，请重新发验证码");
    MsgCode SMS_CHECK_FAILED = new MsgCode("263", "校验失败");

    //签到
    MsgCode USER_SIGN_FAIL = new MsgCode("265", "签到失败");

    //银行卡
    MsgCode USER_BANK_VALID_FAILED = new MsgCode("270", "银行卡信息验证失败");

    //视频

    MsgCode VIDEO_NONE_MINE = new MsgCode("300", "无权限删除");

}
