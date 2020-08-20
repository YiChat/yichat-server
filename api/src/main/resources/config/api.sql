/*
 Navicat Premium Data Transfer

 Source Server         : 易聊IM
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 47.111.129.48
 Source Database       : api

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : utf-8

 Date: 08/19/2020 11:45:15 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `app_config`
-- ----------------------------
DROP TABLE IF EXISTS `app_config`;
CREATE TABLE `app_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `type` int(2) DEFAULT '0' COMMENT '0创建群权限',
  `memo` varchar(200) DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `config_user_id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `app_notice`
-- ----------------------------
DROP TABLE IF EXISTS `app_notice`;
CREATE TABLE `app_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT '0' COMMENT '0公告 1系统消息',
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `group_id` int(11) DEFAULT NULL COMMENT '群ID',
  `user_id` bigint(20) DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- ----------------------------
--  Table structure for `app_robot`
-- ----------------------------
DROP TABLE IF EXISTS `app_robot`;
CREATE TABLE `app_robot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) DEFAULT NULL COMMENT '机器人ID',
  `callback_url` varchar(500) NOT NULL COMMENT '回调地址',
  `refer_ids` varchar(2000) DEFAULT NULL COMMENT '关联数据 逗号分隔',
  `refer_type` int(2) DEFAULT '0' COMMENT '关联类型 0个人 1群组',
  `token` varchar(50) NOT NULL,
  `status` int(2) DEFAULT '0',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机器人';

-- ----------------------------
--  Table structure for `app_robot_record`
-- ----------------------------
DROP TABLE IF EXISTS `app_robot_record`;
CREATE TABLE `app_robot_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(50) NOT NULL COMMENT '消息ID',
  `status` int(2) DEFAULT '0' COMMENT '0 发送失败  1已再次发送成功',
  `success_time` datetime DEFAULT NULL,
  `robot_id` bigint(20) NOT NULL COMMENT '机器人ID',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机器人回调消息记录';

-- ----------------------------
--  Table structure for `app_robot_relation`
-- ----------------------------
DROP TABLE IF EXISTS `app_robot_relation`;
CREATE TABLE `app_robot_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `robot_id` bigint(20) DEFAULT NULL COMMENT '机器人ID',
  `type` int(2) DEFAULT '2' COMMENT '1单人 2群聊',
  `refer_id` bigint(20) DEFAULT NULL COMMENT '类型不同针对的对象',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `refer_id_idx` (`refer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机器人关联数据表';

-- ----------------------------
--  Table structure for `app_small`
-- ----------------------------
DROP TABLE IF EXISTS `app_small`;
CREATE TABLE `app_small` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL COMMENT '小程序标题',
  `icon` varchar(300) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序表';

-- ----------------------------
--  Table structure for `app_version`
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL COMMENT '版本',
  `memo` varchar(500) DEFAULT NULL,
  `status` int(2) DEFAULT '0' COMMENT '0 发布  1取消发布',
  `url` varchar(300) DEFAULT NULL COMMENT '下载链接',
  `update_status` int(2) DEFAULT '0' COMMENT '是否强制更新 0否 1是',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='app版本更新';

-- ----------------------------
--  Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `friend_id` bigint(20) NOT NULL COMMENT '用户好友ID ',
  `status` int(2) DEFAULT '0' COMMENT '0 正常 1删除',
  `view_status` int(11) DEFAULT '0' COMMENT '0新 1旧',
  `user_mark` varchar(100) DEFAULT NULL COMMENT 'userId对应的好友备注',
  `friend_mark` varchar(100) DEFAULT NULL COMMENT 'friendId用户对应的备注',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `friend_friend_user_uqk` (`user_id`,`friend_id`) USING BTREE,
  KEY `friend_friend_id_idx` (`friend_id`) USING BTREE,
  KEY `friend_user_id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `friend_apply`
-- ----------------------------
DROP TABLE IF EXISTS `friend_apply`;
CREATE TABLE `friend_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '申请好友用户',
  `friend_id` bigint(20) NOT NULL COMMENT '被申请用户',
  `status` int(2) DEFAULT '0' COMMENT '0 当前状态 1删除状态',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `check_status` int(2) DEFAULT '0' COMMENT '0申请中 1通过 2拒绝 ',
  `delete_user_status` int(2) DEFAULT '0' COMMENT 'userID对应的删除状态',
  `delete_friend_status` int(2) DEFAULT '0' COMMENT 'friendId对应的删除状态',
  `reason` varchar(500) DEFAULT NULL COMMENT '申请理由',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='好友申请记录表';

-- ----------------------------
--  Table structure for `group_admin`
-- ----------------------------
DROP TABLE IF EXISTS `group_admin`;
CREATE TABLE `group_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` int(2) DEFAULT '0' COMMENT '0有效 1删除',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='群主管理员';

-- ----------------------------
--  Table structure for `group_silent`
-- ----------------------------
DROP TABLE IF EXISTS `group_silent`;
CREATE TABLE `group_silent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `type` int(2) DEFAULT '0' COMMENT '0群成员禁言 1群禁言',
  `status` int(2) DEFAULT '0',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='群禁言及群成员禁言';

-- ----------------------------
--  Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `content` text COMMENT '2000',
  `refer_type` int(2) DEFAULT '0' COMMENT '0user 1group',
  `refer_id` bigint(20) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL COMMENT '消息时间',
  `status` int(2) DEFAULT '0',
  `message_id` varchar(50) NOT NULL,
  `text` text,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_id_uqk` (`message_id`) USING BTREE,
  KEY `message_user_id_idx` (`user_id`) USING BTREE,
  KEY `message_refer_id_idx` (`refer_id`) USING BTREE,
  KEY `message_id_idx` (`message_id`) USING BTREE,
  KEY `message_time_idx` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `message_del_time`
-- ----------------------------
DROP TABLE IF EXISTS `message_del_time`;
CREATE TABLE `message_del_time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `refer_id` bigint(20) DEFAULT NULL COMMENT '根据类型 显示是群还是用户',
  `refer_type` int(2) DEFAULT '0' COMMENT '0用户 1群',
  `del_time` bigint(20) DEFAULT NULL COMMENT '删除时间节点',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_type_refer_uqk` (`user_id`,`refer_id`,`refer_type`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE,
  KEY `refer_id` (`refer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `message_store`
-- ----------------------------
DROP TABLE IF EXISTS `message_store`;
CREATE TABLE `message_store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `content` text COMMENT '2000',
  `refer_type` int(2) DEFAULT '0' COMMENT '0user 1group',
  `refer_id` bigint(20) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL COMMENT '消息时间',
  `status` int(2) DEFAULT '0',
  `message_id` varchar(50) NOT NULL,
  `text` text,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_id_uqk` (`message_id`) USING BTREE,
  KEY `message_user_id_idx` (`user_id`) USING BTREE,
  KEY `message_refer_id_idx` (`refer_id`) USING BTREE,
  KEY `message_id_idx` (`message_id`) USING BTREE,
  KEY `message_time_idx` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `mobile_captcha`
-- ----------------------------
DROP TABLE IF EXISTS `mobile_captcha`;
CREATE TABLE `mobile_captcha` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `content` varchar(64) DEFAULT NULL COMMENT '验证码',
  `invalid_time` datetime DEFAULT NULL COMMENT '过期时间',
  `type` int(11) DEFAULT '100' COMMENT '100 短信验证码',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码';

-- ----------------------------
--  Table structure for `packet`
-- ----------------------------
DROP TABLE IF EXISTS `packet`;
CREATE TABLE `packet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT '0' COMMENT '0 单聊 1群聊',
  `num` int(11) NOT NULL COMMENT '红包个数',
  `user_count` int(11) DEFAULT '0' COMMENT '人数',
  `status` int(2) DEFAULT '0' COMMENT '0 创建  1已领取  2已领完',
  `over_status` int(2) DEFAULT '0' COMMENT '0为抢完  1已抢完',
  `money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '红包金额',
  `content` varchar(500) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '创建人',
  `group_id` bigint(20) DEFAULT NULL COMMENT '群ID',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `packet_user_id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='红包主表';

-- ----------------------------
--  Table structure for `packet_receive`
-- ----------------------------
DROP TABLE IF EXISTS `packet_receive`;
CREATE TABLE `packet_receive` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '领取人',
  `packet_id` bigint(20) NOT NULL COMMENT '红包ID',
  `status` int(2) DEFAULT '0' COMMENT '0未领取  1已领取',
  `money` decimal(11,2) DEFAULT '0.00' COMMENT '领取钱数',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `packet_id_user_id_uqk` (`user_id`,`packet_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='红包领取详情';

-- ----------------------------
--  Table structure for `sms`
-- ----------------------------
DROP TABLE IF EXISTS `sms`;
CREATE TABLE `sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT '100' COMMENT '100 注册  200修改支付密码',
  `mobile` varchar(20) DEFAULT NULL,
  `status` int(2) DEFAULT '0' COMMENT '0未验证  1已验证',
  `check_time` int(2) DEFAULT '0' COMMENT '已校验次数',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `third_login`
-- ----------------------------
DROP TABLE IF EXISTS `third_login`;
CREATE TABLE `third_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT '0' COMMENT '0微信  1qq',
  `unique_code` varchar(128) DEFAULT NULL COMMENT '识别码',
  `union_id` varchar(128) DEFAULT NULL COMMENT '微信unionId',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `status` int(2) DEFAULT '0',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `union_id` (`union_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `trend`
-- ----------------------------
DROP TABLE IF EXISTS `trend`;
CREATE TABLE `trend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) DEFAULT NULL,
  `imgs` varchar(2000) DEFAULT NULL COMMENT '图片地址逗号分隔',
  `videos` varchar(500) DEFAULT NULL COMMENT '视频地址 多个逗号分隔',
  `location` varchar(100) DEFAULT NULL COMMENT '位置信息',
  `user_id` bigint(20) NOT NULL,
  `status` int(2) DEFAULT '0',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `praise_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`) USING BTREE,
  KEY `trend_status_idx` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='动态';

-- ----------------------------
--  Table structure for `trend_comment`
-- ----------------------------
DROP TABLE IF EXISTS `trend_comment`;
CREATE TABLE `trend_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trend_id` bigint(20) DEFAULT NULL COMMENT '动态ID',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '被回复的评论',
  `src_user_id` bigint(20) DEFAULT NULL COMMENT '被回复人ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '评论人ID',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `status` int(2) DEFAULT '0',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `trend_id_idx` (`trend_id`) USING BTREE,
  KEY `trend_comment_user_id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `trend_config`
-- ----------------------------
DROP TABLE IF EXISTS `trend_config`;
CREATE TABLE `trend_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `img` varchar(500) DEFAULT NULL COMMENT '背景图',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='朋友圈相关配置';

-- ----------------------------
--  Table structure for `trend_praise`
-- ----------------------------
DROP TABLE IF EXISTS `trend_praise`;
CREATE TABLE `trend_praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trend_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT '0',
  `status` int(2) DEFAULT '0',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `praise_trend_uqk` (`trend_id`,`user_id`) USING BTREE,
  KEY `praise_user_id` (`user_id`) USING BTREE,
  KEY `praise_trend_id` (`trend_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='动态点赞表';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nick` varchar(500) DEFAULT NULL COMMENT '昵称',
  `area_code` varchar(32) DEFAULT '' COMMENT '区号  前缀',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL,
  `token` varchar(128) DEFAULT NULL COMMENT '加密后token',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像路径',
  `platform` varchar(64) DEFAULT NULL COMMENT '用户注册平台  iOs  android',
  `status` int(2) DEFAULT '0' COMMENT '0正常 1删除/冻结',
  `appId` varchar(128) DEFAULT NULL COMMENT '微信ID',
  `gender` int(2) DEFAULT NULL COMMENT '0 女 1男',
  `ucode` varchar(128) DEFAULT NULL COMMENT '唯一码',
  `recommend_code` varchar(20) DEFAULT NULL,
  `recommend_time` datetime DEFAULT NULL COMMENT '推荐时间',
  `timestamp` bigint(20) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  `create_group_auth` int(2) DEFAULT '0' COMMENT '0无权限  1有权限  是否有建群权限',
  `qrcode` varchar(20) DEFAULT NULL COMMENT '二维码ID',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `appId` (`appId`) USING BTREE,
  UNIQUE KEY `mobile_uqk` (`mobile`) USING BTREE,
  UNIQUE KEY `ucode_uqk` (`ucode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
--  Table structure for `user_balance`
-- ----------------------------
DROP TABLE IF EXISTS `user_balance`;
CREATE TABLE `user_balance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `balance` decimal(11,2) DEFAULT '0.00' COMMENT '余额',
  `income_balance` decimal(11,2) DEFAULT '0.00' COMMENT '可支配余额',
  `freeze_balance` decimal(11,2) DEFAULT '0.00' COMMENT '冻结余额',
  `status` int(2) DEFAULT '0' COMMENT '0 正常 1冻结',
  `password` varchar(100) DEFAULT NULL COMMENT '支付密码',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `balance_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户余额表';

-- ----------------------------
--  Table structure for `user_balance_detail`
-- ----------------------------
DROP TABLE IF EXISTS `user_balance_detail`;
CREATE TABLE `user_balance_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `balance_id` bigint(20) NOT NULL COMMENT '余额表主键ID',
  `user_id` bigint(20) NOT NULL,
  `money` decimal(11,2) NOT NULL COMMENT '发生金额',
  `b_balance` decimal(11,2) NOT NULL,
  `b_income` decimal(11,2) NOT NULL COMMENT '之前可支配金额',
  `b_freeze` decimal(11,2) NOT NULL COMMENT '之前可冻结余额',
  `a_income` decimal(11,2) NOT NULL COMMENT '之后可支配金额',
  `a_freeze` decimal(11,2) NOT NULL COMMENT '之后可冻结余额',
  `a_balance` decimal(11,2) NOT NULL,
  `type` int(2) DEFAULT '0' COMMENT '类型   0 充值 1提现 2 创建红包 3领取红包 ',
  `refer_id` bigint(20) DEFAULT NULL COMMENT '类型对应的数据ID ',
  `memo` varchar(200) DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_bank`
-- ----------------------------
DROP TABLE IF EXISTS `user_bank`;
CREATE TABLE `user_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `password` varchar(100) DEFAULT NULL,
  `id_number` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行',
  `bank_number` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `status` int(2) DEFAULT '0' COMMENT '0存在  1删除',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_device`
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `device_id` varchar(100) NOT NULL,
  `status` int(2) DEFAULT '0' COMMENT '0 可用 1不可用',
  `ctime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_device_uqk` (`user_id`,`device_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_im`
-- ----------------------------
DROP TABLE IF EXISTS `user_im`;
CREATE TABLE `user_im` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `im_password` varchar(64) NOT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_im_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户IM号';

-- ----------------------------
--  Table structure for `user_ip`
-- ----------------------------
DROP TABLE IF EXISTS `user_ip`;
CREATE TABLE `user_ip` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip信息',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_ip_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户IP表';

-- ----------------------------
--  Table structure for `user_online`
-- ----------------------------
DROP TABLE IF EXISTS `user_online`;
CREATE TABLE `user_online` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `seconds` bigint(20) NOT NULL DEFAULT '0' COMMENT '在线秒数',
  `today_status` int(2) DEFAULT '0' COMMENT '今天是否开始记录 0否 1是',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uqk` (`user_id`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='在线时间';

-- ----------------------------
--  Table structure for `user_sign`
-- ----------------------------
DROP TABLE IF EXISTS `user_sign`;
CREATE TABLE `user_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `sign_date` date DEFAULT NULL,
  `score` int(11) DEFAULT '0' COMMENT '获取的积分',
  `ctime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sing_userId_date_uqk` (`user_id`,`sign_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_trade`
-- ----------------------------
DROP TABLE IF EXISTS `user_trade`;
CREATE TABLE `user_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `trade_no` varchar(100) DEFAULT NULL COMMENT '交易订单号',
  `type` int(2) DEFAULT '0' COMMENT '0充值',
  `pay_type` int(2) DEFAULT '0' COMMENT '0微信 1支付宝',
  `status` int(2) DEFAULT '0' COMMENT '0未支付 1已支付 ',
  `money` decimal(11,2) DEFAULT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_trade_id_uqk` (`trade_no`) USING BTREE,
  KEY `user_trade_id_idx` (`trade_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户充值交易记录表';

-- ----------------------------
--  Table structure for `user_withdraw`
-- ----------------------------
DROP TABLE IF EXISTS `user_withdraw`;
CREATE TABLE `user_withdraw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `status` int(2) DEFAULT '0' COMMENT '0提交申请  1审核通过  2拒绝申请',
  `bank_number` varchar(50) DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL,
  `money` decimal(11,2) DEFAULT NULL,
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL COMMENT '文字内容',
  `path` varchar(200) NOT NULL COMMENT '视频地址',
  `thumbnail` varchar(200) DEFAULT NULL COMMENT '缩略图',
  `rate` decimal(11,2) DEFAULT NULL COMMENT '视频宽高比',
  `seconds` int(11) DEFAULT '0' COMMENT '视频秒数',
  `praise_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `view_count` int(11) DEFAULT '0',
  `status` int(2) DEFAULT '0' COMMENT '0 待审核  1通过 2删除',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `balance_status` int(2) DEFAULT '0' COMMENT '0未奖励 1已奖励',
  `balance_money` decimal(11,2) DEFAULT '0.00' COMMENT '奖励金额',
  `user_id` bigint(20) NOT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='视频';

-- ----------------------------
--  Table structure for `video_comment`
-- ----------------------------
DROP TABLE IF EXISTS `video_comment`;
CREATE TABLE `video_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `src_comment_id` bigint(20) DEFAULT '0' COMMENT '顶级评论ID',
  `comment_id` bigint(20) DEFAULT '0',
  `comment_user_id` bigint(20) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `praise_count` int(11) DEFAULT '0',
  `reply_count` int(11) DEFAULT '0' COMMENT '回复总数',
  `balance_status` int(2) DEFAULT '0' COMMENT '是否被奖励过',
  `status` int(2) DEFAULT '0',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `praise_video_id` (`video_id`) USING BTREE,
  KEY `praise_user_id` (`user_id`) USING BTREE,
  KEY `comment_id_idx` (`comment_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `video_comment_praise`
-- ----------------------------
DROP TABLE IF EXISTS `video_comment_praise`;
CREATE TABLE `video_comment_praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_id` bigint(20) NOT NULL COMMENT '评论ID',
  `user_id` bigint(20) DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_video_uqk` (`comment_id`,`user_id`) USING BTREE,
  KEY `praise_video_id` (`comment_id`) USING BTREE,
  KEY `praise_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='评论点赞表';

-- ----------------------------
--  Table structure for `video_play_record`
-- ----------------------------
DROP TABLE IF EXISTS `video_play_record`;
CREATE TABLE `video_play_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `balance_status` int(2) DEFAULT '0' COMMENT '0未奖励  1奖励',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_viddeo_uqk` (`video_id`,`user_id`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE,
  KEY `video_id_idx` (`video_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='视频播放记录表';

-- ----------------------------
--  Table structure for `video_praise`
-- ----------------------------
DROP TABLE IF EXISTS `video_praise`;
CREATE TABLE `video_praise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `balance_status` int(2) DEFAULT '0',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_video_uqk` (`video_id`,`user_id`) USING BTREE,
  KEY `praise_video_id` (`video_id`) USING BTREE,
  KEY `praise_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `video_user_reward`
-- ----------------------------
DROP TABLE IF EXISTS `video_user_reward`;
CREATE TABLE `video_user_reward` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `reward` decimal(11,2) DEFAULT NULL COMMENT '总奖励',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uqk` (`user_id`) USING BTREE,
  KEY `user_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户视频奖励汇总';

-- ----------------------------
--  Table structure for `video_view`
-- ----------------------------
DROP TABLE IF EXISTS `video_view`;
CREATE TABLE `video_view` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `count` bigint(20) DEFAULT '0' COMMENT '浏览次数',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `video_vide_uqk` (`video_id`,`user_id`) USING BTREE,
  KEY `video_id_uqk` (`video_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;


/*
 Navicat Premium Data Transfer

 Source Server         : yichat
 Source Server Type    : MySQL
 Source Server Version : 50645
 Source Host           : 47.56.123.244
 Source Database       : api

 Target Server Type    : MySQL
 Target Server Version : 50645
 File Encoding         : utf-8

 Date: 08/19/2020 14:34:09 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '字典名称',
  `code` varchar(64) NOT NULL COMMENT '字典编码',
  `value` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '值',
  `status` int(2) DEFAULT '0',
  `memo` varchar(511) CHARACTER SET utf8 DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_uk` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_dict`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES ('16', 'IM服务器地址', 'im_server', '127.0.0.1', '0', '', '2019-07-18 14:52:31', '2019-08-29 19:49:15'), ('17', '积分单次签到分值', 'user_sign_get', '0.1', '0', '', '2019-08-08 11:08:55', '2019-09-03 16:57:24'), ('18', '积分连续一周签到获取值', 'user_sign_continue_get', '1', '0', '', '2019-08-08 11:10:17', '2019-09-03 16:56:54'), ('19', '签到说明', 'user_sign_desc', '', '0', '1.每天签到奖励0.1元\n2.每周连续签到7天可得到1元奖励\n3.具体规则解释权归运营方所有', '2019-08-21 17:29:20', '2019-09-03 16:56:40'), ('20', '接口返回加密key', 'aes_api_encript', 'A286D372M63HFUQW', '0', '服务重要参数，请勿随意改动', '2019-08-26 12:15:40', null), ('21', '最低提现金额', 'sys_withdraw_money', '50', '0', '金额填浮点数  精确到小数后2位即可', '2019-09-03 15:41:28', null), ('22', '提现手续费百分比', 'sys_withdraw_rate', '2', '0', '单位是百分比  填1就是1%，填0.1就是0.1%', '2019-09-03 15:42:43', '2019-09-06 15:49:26'), ('23', '提现页面文案', 'sys_withdraw_text', '', '0', '1.提现最低金额为50元\n2.单次提现的手续费为2%\n3.提现到账时间为T+1', '2019-09-03 15:43:29', '2019-09-06 15:49:17'), ('24', '分享安卓链接', 'share_andriod', 'http://www.yichatsystem.com/', '0', 'http://www.yichatsystem.com/', '2019-09-03 18:38:40', '2020-02-03 18:39:28'), ('25', '分享ios链接', 'share_ios', 'http://www.yichatsystem.com/', '0', 'http://www.yichatsystem.com/', '2019-09-03 18:38:59', '2020-02-03 18:39:20'), ('26', '分享文本', 'share_text', '我正在使用YiChat App聊天，赶紧一起来吧！', '0', '我正在使用YiChat App聊天，赶紧一起来吧！', '2019-09-03 18:39:25', '2019-09-03 22:47:37'), ('27', '首页地址', 'app_home_url', 'https://sina.cn/?wm=4007', '0', '', '2019-10-17 15:59:42', '2019-10-17 16:34:50'), ('28', '群红包单个金额最小限制', 'group_packet_min_money', '2', '0', '', '2019-11-20 14:52:40', '2019-11-20 14:53:20'), ('29', '加好友红包最小金额限制', 'friend_packet_min_money', '1', '0', '', '2019-11-20 14:53:09', null), ('30', '动态标签', 'friend_trend_sign', '', '0', '', '2020-02-13 12:49:17', null), ('31', '积分签到每次获取', 'integral_sign', '10', '0', '', '2020-03-26 15:15:46', null), ('32', '积分分享每次获取', 'integral_share', '10', '0', '', '2020-03-26 15:19:03', null), ('33', '积分邀请每次获取', 'integral_invite', '10', '0', '', '2020-03-26 15:19:16', null), ('34', '积分分享每天限制获取次数', 'integral_share_limit', '10', '0', '', '2020-03-26 15:19:32', null), ('35', '积分等级', 'integral_level', '', '0', '每行数据备注 是营销红包每天领取的个数', '2020-03-26 15:42:11', '2020-03-31 09:40:45'), ('36', '默认头像地址', 'user_default_avatar', 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1260305692,2555177882&fm=26&gp=0.jpg', '0', '', '2020-05-21 14:11:52', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_dict_data`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pcode` varchar(100) NOT NULL COMMENT '父级code',
  `name` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '名称',
  `code` varchar(64) NOT NULL COMMENT '编码',
  `value` varchar(128) NOT NULL,
  `status` int(2) DEFAULT '0',
  `sort` int(2) DEFAULT '0',
  `memo` varchar(256) DEFAULT '',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_uk` (`pcode`,`code`) USING BTREE COMMENT 'code唯一'
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='数据字典数据表';

-- ----------------------------
--  Records of `sys_dict_data`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` VALUES ('1', 'friend_trend_sign', '1', '1', '合肥美景', null, '1', '', null, null), ('2', 'friend_trend_sign', '2', '2', '网红美女', null, '1', '', null, null), ('3', 'integral_level', '青铜', 'qingtong', '100', null, '1', '2', null, '2020-04-07 13:44:12'), ('4', 'integral_level', '白银', 'baiyin', '500', null, '1', '5', null, '2020-03-31 09:41:32'), ('5', 'integral_level', '黄金', 'huangjin', '1000', null, '1', '8', null, '2020-03-31 09:41:32'), ('6', 'integral_level', '铂金', 'bojin', '3000', null, '1', '12', null, '2020-03-31 09:41:32'), ('7', 'integral_level', '钻石', 'zuanshi', '10000', null, '1', '15', null, '2020-03-31 09:41:32'), ('8', 'integral_level', '星耀', 'xingyao', '50000', null, '1', '20', null, '2020-03-31 09:41:32'), ('9', 'integral_level', '王者', 'wanghze', '10000000000', null, '1', '30', null, '2020-03-31 09:41:32');
COMMIT;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT '0' COMMENT '父级ID',
  `name` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '菜单名称',
  `status` int(2) DEFAULT '0',
  `sort` int(2) DEFAULT '0' COMMENT '排序',
  `path` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '菜单路径',
  `icon` varchar(64) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1 COMMENT='菜单表';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '0', '系统设置', '0', '1000', '/user/index', 'layui-icon-set-sm', null, '2018-10-25 19:00:53', '2018-10-31 17:33:19'), ('2', '1', '系统用户', '0', '1030', '/user/index', 'layui-icon-friends', '', '2018-10-25 19:16:04', '2019-07-18 16:55:23'), ('3', '1', '菜单管理', '0', '1040', '/menu/index', 'layui-icon-shrink-right', '', '2018-10-31 15:13:41', '2019-07-18 16:55:34'), ('4', '1', '角色管理', '0', '1010', '/role/index', 'layui-icon-username', '', '2018-10-31 17:37:04', '2019-07-18 16:54:56'), ('8', '1', '参数配置', '0', '1020', '/dict/index', 'layui-icon-template-1', '', '2018-11-02 10:50:04', '2019-08-21 17:29:36'), ('33', '0', '用户管理', '0', '100', '', 'layui-icon-username', '', '2019-07-09 11:04:50', null), ('34', '33', '用户列表', '0', '102', '/member/list/index', 'layui-icon-list', '', '2019-07-09 11:05:59', null), ('35', '0', '聊天管理', '0', '200', '', 'layui-icon-dialogue', '', '2019-07-09 14:50:02', null), ('36', '35', '单聊记录', '0', '202', '/message/index', 'layui-icon-dialogue', '', '2019-07-09 14:50:53', '2019-07-11 14:12:57'), ('37', '35', '群聊记录', '0', '205', '/message/group/index', 'layui-icon-dialogue', '', '2019-07-11 14:13:32', '2019-07-11 14:14:33'), ('38', '33', '版本设置', '0', '1005', '/app/version/index', 'layui-icon-log', '', '2019-07-18 16:56:47', '2019-07-18 17:10:36'), ('39', '0', '群组管理', '0', '500', '/group/index', 'layui-icon-group', '', '2019-07-29 11:39:12', null), ('40', '39', '群组列表', '0', '502', '/group/index', 'layui-icon-group', '', '2019-07-29 11:39:57', '2019-07-29 11:54:59'), ('41', '0', '财务管理', '0', '800', '', 'layui-icon-rmb', '', '2019-07-30 10:32:23', null), ('42', '41', '提现审核', '0', '802', '/balance/withdraw/index', 'layui-icon-log', '', '2019-07-30 10:33:47', null), ('43', '39', '权限设置', '1', '505', '/group/create/auth/index', 'layui-icon-set-sm', '', '2019-08-01 17:32:40', '2019-08-23 16:31:23'), ('44', '33', '用户清除', '0', '105', '/member/clean/index', 'layui-icon-delete', '', '2019-08-09 10:03:44', '2019-08-09 10:16:24'), ('45', '41', '提现充值', '0', '801', '/balance/user/index', 'layui-icon-dollar', '', '2019-08-13 15:01:50', null), ('46', '1', '小程序', '0', '1004', '/app/small/index', 'layui-icon-cellphone', '', '2019-08-21 15:16:47', null), ('47', '1', '系统通知', '0', '1003', '/sys/push/index', 'layui-icon-notice', '', '2019-08-26 16:21:21', null), ('48', '0', '红包列表', '0', '700', '', 'layui-icon-rmb', '', '2019-09-02 18:33:55', null), ('49', '48', '红包查询', '0', '702', '/packet/index', 'layui-icon-list', '', '2019-09-02 18:35:32', null), ('50', '1', '注册协议', '0', '1080', '/user/agent/index', 'layui-icon-form', '', '2019-09-04 15:32:59', null), ('51', '33', '用户反馈', '0', '107', '/feedback/index', 'layui-icon-survey', '', '2019-09-09 14:24:17', null), ('52', '48', '红包控制', '0', '704', '/packet/ctrl/index', 'layui-icon-rmb', '', '2019-09-26 11:07:10', '2019-09-26 11:07:28'), ('53', '41', '三方充值', '0', '805', '/balance/third/index', 'layui-icon-list', '', '2019-11-12 13:48:44', null), ('54', '33', '冻结IP', '0', '110', '/member/ip/index', 'layui-icon-list', '', '2020-02-11 22:14:55', null), ('55', '0', '动态管理', '0', '600', '', 'layui-icon-log', '', '2020-02-28 11:35:51', null), ('56', '55', '动态列表', '0', '602', '/trend/index', 'layui-icon-log', '', '2020-02-28 11:36:16', null), ('57', '0', '任务管理', '0', '850', '', 'layui-icon-engine', '', '2020-03-18 16:47:31', null), ('58', '57', '任务列表', '0', '852', '/mission/index', 'layui-icon-list', '', '2020-03-18 16:48:08', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_menu_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_relation`;
CREATE TABLE `sys_role_menu_relation` (
  `menu_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL COMMENT '角色',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='角色菜单关联表';

-- ----------------------------
--  Records of `sys_role_menu_relation`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu_relation` VALUES ('5', '3', '2018-12-26 11:24:05', null), ('12', '3', '2018-12-26 11:24:05', null), ('14', '3', '2018-12-26 11:24:05', null), ('17', '3', '2018-12-26 11:24:05', null), ('18', '3', '2018-12-26 11:24:05', null), ('16', '3', '2018-12-26 11:24:05', null), ('10', '3', '2018-12-26 11:24:05', null), ('11', '3', '2018-12-26 11:24:05', null), ('5', '4', '2019-01-24 18:53:24', null), ('12', '4', '2019-01-24 18:53:24', null), ('14', '4', '2019-01-24 18:53:24', null), ('17', '4', '2019-01-24 18:53:24', null), ('18', '4', '2019-01-24 18:53:24', null), ('16', '4', '2019-01-24 18:53:24', null), ('10', '4', '2019-01-24 18:53:24', null), ('11', '4', '2019-01-24 18:53:24', null), ('5', '5', '2019-01-24 18:54:03', null), ('12', '5', '2019-01-24 18:54:03', null), ('14', '5', '2019-01-24 18:54:03', null), ('17', '5', '2019-01-24 18:54:03', null), ('18', '5', '2019-01-24 18:54:03', null), ('16', '5', '2019-01-24 18:54:03', null), ('10', '5', '2019-01-24 18:54:03', null), ('11', '5', '2019-01-24 18:54:03', null), ('33', '1', '2020-03-18 16:48:25', null), ('34', '1', '2020-03-18 16:48:25', null), ('44', '1', '2020-03-18 16:48:25', null), ('51', '1', '2020-03-18 16:48:25', null), ('54', '1', '2020-03-18 16:48:25', null), ('35', '1', '2020-03-18 16:48:25', null), ('36', '1', '2020-03-18 16:48:25', null), ('37', '1', '2020-03-18 16:48:25', null), ('39', '1', '2020-03-18 16:48:25', null), ('40', '1', '2020-03-18 16:48:25', null), ('55', '1', '2020-03-18 16:48:25', null), ('56', '1', '2020-03-18 16:48:25', null), ('48', '1', '2020-03-18 16:48:25', null), ('49', '1', '2020-03-18 16:48:25', null), ('41', '1', '2020-03-18 16:48:25', null), ('45', '1', '2020-03-18 16:48:25', null), ('42', '1', '2020-03-18 16:48:25', null), ('53', '1', '2020-03-18 16:48:25', null), ('57', '1', '2020-03-18 16:48:25', null), ('58', '1', '2020-03-18 16:48:25', null), ('1', '1', '2020-03-18 16:48:25', null), ('47', '1', '2020-03-18 16:48:25', null), ('46', '1', '2020-03-18 16:48:25', null), ('38', '1', '2020-03-18 16:48:25', null), ('4', '1', '2020-03-18 16:48:25', null), ('8', '1', '2020-03-18 16:48:25', null), ('2', '1', '2020-03-18 16:48:25', null), ('3', '1', '2020-03-18 16:48:25', null), ('50', '1', '2020-03-18 16:48:25', null), ('33', '2', '2020-04-16 09:33:55', null), ('34', '2', '2020-04-16 09:33:55', null), ('44', '2', '2020-04-16 09:33:55', null), ('51', '2', '2020-04-16 09:33:55', null), ('35', '2', '2020-04-16 09:33:55', null), ('36', '2', '2020-04-16 09:33:55', null), ('37', '2', '2020-04-16 09:33:55', null), ('39', '2', '2020-04-16 09:33:55', null), ('40', '2', '2020-04-16 09:33:55', null), ('57', '2', '2020-04-16 09:33:55', null), ('58', '2', '2020-04-16 09:33:55', null), ('1', '2', '2020-04-16 09:33:55', null), ('47', '2', '2020-04-16 09:33:55', null), ('46', '2', '2020-04-16 09:33:55', null), ('38', '2', '2020-04-16 09:33:55', null), ('8', '2', '2020-04-16 09:33:55', null), ('3', '2', '2020-04-16 09:33:55', null), ('50', '2', '2020-04-16 09:33:55', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `roleId` int(11) DEFAULT NULL COMMENT '关联角色',
  `companyId` int(11) DEFAULT NULL COMMENT '关联公司',
  `referee` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '推荐人',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `status` int(2) DEFAULT '0' COMMENT '0正常  1删除',
  `memo` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `userId` int(11) DEFAULT NULL COMMENT '创建人ID',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_uk` (`username`) USING BTREE COMMENT '用户名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('18', 'admin', '000000', '超级管理员', '1', null, '', '', '', '0', '', '18', '2019-07-09 10:25:10', '2019-09-26 14:52:14'), ('19', 'test', '000000', '测试', '2', null, '', '', '', '0', '', '18', '2020-01-07 16:12:51', '2020-01-07 16:13:38');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `name` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  `memo` varchar(255) DEFAULT NULL COMMENT '描述',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP,
  `utime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='用户角色表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '100', '管理员', '0', '', '2019-07-09 10:32:46', '2019-07-09 11:02:17'), ('2', null, '测试', '0', '', null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
