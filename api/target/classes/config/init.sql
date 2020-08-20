

ALTER TABLE `yixin`.`user` ADD COLUMN `recommend_code` varchar(20) AFTER `ucode`;

ALTER TABLE `yixin`.`user` ADD UNIQUE `ucode_uqk` USING BTREE (`ucode`) comment '';

ALTER TABLE `yixin`.`user` ADD COLUMN `recommend_time` datetime COMMENT '推荐时间' AFTER `recommend_code`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在线时间'