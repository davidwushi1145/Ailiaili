/*
 Navicat Premium Data Transfer

 Source Server         : navicat
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : localhost:3306
 Source Schema         : bilibili

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 02/01/2024 17:58:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_ad_performance
-- ----------------------------
DROP TABLE IF EXISTS `t_ad_performance`;
CREATE TABLE `t_ad_performance` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ad_id` bigint NOT NULL COMMENT '广告id（关联到t_advertisement表）',
  `clicks` int DEFAULT '0' COMMENT '点击次数',
  `impressions` int DEFAULT '0' COMMENT '展示次数',
  `conversions` int DEFAULT '0' COMMENT '转化次数',
  `date` date NOT NULL COMMENT '日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_ad_performance_advertisement` (`ad_id`) USING BTREE,
  CONSTRAINT `fk_ad_performance_advertisement` FOREIGN KEY (`ad_id`) REFERENCES `t_advertisement` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='广告性能表';

-- ----------------------------
-- Table structure for t_ad_space
-- ----------------------------
DROP TABLE IF EXISTS `t_ad_space`;
CREATE TABLE `t_ad_space` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '广告位描述（如页面位置、尺寸等）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='广告位表';

-- ----------------------------
-- Table structure for t_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement`;
CREATE TABLE `t_advertisement` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `advertiser_id` bigint NOT NULL COMMENT '广告主id',
  `content_id` bigint NOT NULL COMMENT '广告内容id（关联到t_file表）',
  `ad_space_id` bigint NOT NULL COMMENT '广告位id（关联到t_ad_space表）',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '投放开始时间',
  `end_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '投放结束时间',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态（如：激活、暂停、结束）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `advertisement_path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_advertisement_advertiser` (`advertiser_id`) USING BTREE,
  KEY `fk_advertisement_content` (`content_id`) USING BTREE,
  KEY `fk_advertisement_ad_space` (`ad_space_id`) USING BTREE,
  CONSTRAINT `fk_advertisement_ad_space` FOREIGN KEY (`ad_space_id`) REFERENCES `t_ad_space` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_advertisement_advertiser` FOREIGN KEY (`advertiser_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_advertisement_content` FOREIGN KEY (`content_id`) REFERENCES `t_file` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='广告表';

-- ----------------------------
-- Table structure for t_auth_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_element_operation`;
CREATE TABLE `t_auth_element_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `element_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '页面元素名称',
  `element_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '页面元素唯一编码',
  `operation_type` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '操作类型：0可点击，1可见',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='权限控制--页面元素操作表';

-- ----------------------------
-- Table structure for t_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_menu`;
CREATE TABLE `t_auth_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单项目名称',
  `code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '唯一编码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='权限控制--页面访问表';

-- ----------------------------
-- Table structure for t_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role`;
CREATE TABLE `t_auth_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '唯一编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='权限控制--角色表';

-- ----------------------------
-- Table structure for t_auth_role_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_element_operation`;
CREATE TABLE `t_auth_role_element_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint DEFAULT NULL COMMENT '角色id',
  `element_operation_id` bigint DEFAULT NULL COMMENT '元素操作id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_role_element_operation_role_id` (`role_id`) USING BTREE,
  KEY `fk_role_element_operation_element_operation_id` (`element_operation_id`) USING BTREE,
  CONSTRAINT `fk_role_element_operation_element_operation_id` FOREIGN KEY (`element_operation_id`) REFERENCES `t_auth_element_operation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_element_operation_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_auth_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='权限控制--角色与元素操作关联表';

-- ----------------------------
-- Table structure for t_auth_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_menu`;
CREATE TABLE `t_auth_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint DEFAULT NULL COMMENT '角色id',
  `menu_id` bigint DEFAULT NULL COMMENT '页面菜单id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_role_menu_role_id` (`role_id`) USING BTREE,
  KEY `fk_role_menu_menu_id` (`menu_id`) USING BTREE,
  CONSTRAINT `fk_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `t_auth_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_auth_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='权限控制--角色页面菜单关联表';

-- ----------------------------
-- Table structure for t_collection_group
-- ----------------------------
DROP TABLE IF EXISTS `t_collection_group`;
CREATE TABLE `t_collection_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '收藏分组名称',
  `type` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '收藏分组类型：0默认收藏分组',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_collection_group_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_collection_group_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户收藏分组表';

-- ----------------------------
-- Table structure for t_danmu
-- ----------------------------
DROP TABLE IF EXISTS `t_danmu`;
CREATE TABLE `t_danmu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `video_id` bigint DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `danmu_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `pass` int DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC
/*!50100 PARTITION BY HASH (`id`)
(PARTITION p0 ENGINE = InnoDB,
 PARTITION p1 ENGINE = InnoDB,
 PARTITION p2 ENGINE = InnoDB,
 PARTITION p3 ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件存储路径',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件类型',
  `md5` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件MD5唯一标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='上传文件信息表';

-- ----------------------------
-- Table structure for t_following_group
-- ----------------------------
DROP TABLE IF EXISTS `t_following_group`;
CREATE TABLE `t_following_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '关注分组名称',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '关注分组类型：0特别关注，1悄悄关注，2默认关注，3用户自定义关注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户关注分组表';

-- ----------------------------
-- Table structure for t_messages
-- ----------------------------
DROP TABLE IF EXISTS `t_messages`;
CREATE TABLE `t_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sender_id` bigint NOT NULL COMMENT '发送者id',
  `receiver_id` bigint NOT NULL COMMENT '接收者id',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `send_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_messages_sender` (`sender_id`) USING BTREE,
  KEY `fk_messages_receiver` (`receiver_id`) USING BTREE,
  CONSTRAINT `fk_messages_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_messages_sender` FOREIGN KEY (`sender_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='消息通信表';

-- ----------------------------
-- Table structure for t_partnership
-- ----------------------------
DROP TABLE IF EXISTS `t_partnership`;
CREATE TABLE `t_partnership` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `advertiser_id` bigint NOT NULL COMMENT '广告主id',
  `creator_id` bigint NOT NULL COMMENT '内容创作者id',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '合作开始时间',
  `end_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '合作结束时间',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '合作内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_partnership_advertiser` (`advertiser_id`) USING BTREE,
  KEY `fk_partnership_creator` (`creator_id`) USING BTREE,
  CONSTRAINT `fk_partnership_advertiser` FOREIGN KEY (`advertiser_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_partnership_creator` FOREIGN KEY (`creator_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='合作关系表';

-- ----------------------------
-- Table structure for t_partnership_requests
-- ----------------------------
DROP TABLE IF EXISTS `t_partnership_requests`;
CREATE TABLE `t_partnership_requests` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '合作请求ID',
  `advertiser_id` bigint NOT NULL COMMENT '广告主ID',
  `content_creator_id` bigint NOT NULL COMMENT '内容创作者ID',
  `request_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
  `status` enum('pending','accepted','rejected') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT 'pending' COMMENT '请求状态（等待、接受、拒绝）',
  `details` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '合作详情',
  `expected_duration` int DEFAULT NULL COMMENT '预期的合作期限（以天为单位）',
  `expected_compensation` decimal(10,2) DEFAULT NULL COMMENT '预期的报酬',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_partnership_requests_advertiser` (`advertiser_id`) USING BTREE,
  KEY `fk_partnership_requests_creator` (`content_creator_id`) USING BTREE,
  CONSTRAINT `fk_partnership_requests_advertiser` FOREIGN KEY (`advertiser_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_partnership_requests_creator` FOREIGN KEY (`content_creator_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='合作请求表';

-- ----------------------------
-- Table structure for t_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `t_refresh_token`;
CREATE TABLE `t_refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `refresh_token` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '刷新令牌',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_refresh_token_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_refresh_token_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='刷新令牌记录表';

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='标签表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `phone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '邮箱',
  `user_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '盐值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pass` int DEFAULT '1' COMMENT '封禁状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Table structure for t_user_coin
-- ----------------------------
DROP TABLE IF EXISTS `t_user_coin`;
CREATE TABLE `t_user_coin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `amount` bigint DEFAULT NULL COMMENT '硬币总数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_coin_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_user_coin_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户硬币数量表';

-- ----------------------------
-- Table structure for t_user_following
-- ----------------------------
DROP TABLE IF EXISTS `t_user_following`;
CREATE TABLE `t_user_following` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `following_id` bigint DEFAULT NULL COMMENT '关注用户id',
  `group_id` bigint DEFAULT NULL COMMENT '关注分组id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_following_user_id` (`user_id`) USING BTREE,
  KEY `fk_user_following_following_id` (`following_id`) USING BTREE,
  KEY `fk_user_following_group_id` (`group_id`) USING BTREE,
  CONSTRAINT `fk_user_following_following_id` FOREIGN KEY (`following_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_following_group_id` FOREIGN KEY (`group_id`) REFERENCES `t_following_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_following_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户关注表';

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id（关联）',
  `nick` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头像',
  `sign` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '签名',
  `gender` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '性别：0男，1女，2未知',
  `birth` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '生日',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_info_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_user_info_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户基本信息表';

-- ----------------------------
-- Table structure for t_user_moments
-- ----------------------------
DROP TABLE IF EXISTS `t_user_moments`;
CREATE TABLE `t_user_moments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `type` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '动态类型：0视频，1直播，2动态专栏',
  `content_id` bigint DEFAULT NULL COMMENT '内容详情id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_moments_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_user_moments_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户动态表';

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `role_id` bigint DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_role_user_id` (`user_id`) USING BTREE,
  KEY `fk_user_role_role_id` (`role_id`) USING BTREE,
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_auth_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频链接',
  `thumbnail` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '封面链接',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频标题',
  `type` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频类型：0原创，1转载',
  `duration` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频时长',
  `area` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所在分区:0鬼畜，1音乐，2电影',
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '视频简介',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `coins` int DEFAULT NULL COMMENT 'comment ''投币数量''',
  `likes` int DEFAULT NULL COMMENT 'comment ''点赞数量''',
  `collections` int DEFAULT NULL COMMENT 'comment ''收藏数量''',
  `views` int DEFAULT NULL COMMENT 'comment ''播放数量''',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_video_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='视频投稿记录表';

-- ----------------------------
-- Table structure for t_video_coin
-- ----------------------------
DROP TABLE IF EXISTS `t_video_coin`;
CREATE TABLE `t_video_coin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `video_id` bigint DEFAULT NULL COMMENT '视频投稿id',
  `amount` int DEFAULT NULL COMMENT '投币数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_coin_user_id` (`user_id`) USING BTREE,
  KEY `fk_video_coin_video_id` (`video_id`) USING BTREE,
  CONSTRAINT `fk_video_coin_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_coin_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='视频投币记录表';

-- ----------------------------
-- Table structure for t_video_collection
-- ----------------------------
DROP TABLE IF EXISTS `t_video_collection`;
CREATE TABLE `t_video_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `video_id` bigint DEFAULT NULL COMMENT '视频投稿id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `group_id` bigint DEFAULT NULL COMMENT '收藏分组',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_collection_video_id` (`video_id`) USING BTREE,
  KEY `fk_video_collection_user_id` (`user_id`) USING BTREE,
  KEY `fk_video_collection_group_id` (`group_id`) USING BTREE,
  CONSTRAINT `fk_video_collection_group_id` FOREIGN KEY (`group_id`) REFERENCES `t_collection_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_collection_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_collection_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='视频收藏表';

-- ----------------------------
-- Table structure for t_video_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_video_comment`;
CREATE TABLE `t_video_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `video_id` bigint NOT NULL COMMENT '视频id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `comment` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '评论',
  `reply_user_id` bigint DEFAULT NULL COMMENT '回复用户id',
  `root_id` bigint DEFAULT NULL COMMENT '根结点评论id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pass` int DEFAULT '1' COMMENT '封禁状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_comment_video_id` (`video_id`) USING BTREE,
  KEY `fk_video_comment_user_id` (`user_id`) USING BTREE,
  KEY `fk_video_comment_reply_user_id` (`reply_user_id`) USING BTREE,
  CONSTRAINT `fk_video_comment_reply_user_id` FOREIGN KEY (`reply_user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_comment_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='视频评论表';

-- ----------------------------
-- Table structure for t_video_like
-- ----------------------------
DROP TABLE IF EXISTS `t_video_like`;
CREATE TABLE `t_video_like` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `video_id` bigint NOT NULL COMMENT '视频投稿id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_like_user_id` (`user_id`) USING BTREE,
  KEY `fk_video_like_video_id` (`video_id`) USING BTREE,
  CONSTRAINT `fk_video_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_like_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='视频点赞表';

-- ----------------------------
-- Table structure for t_video_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_video_operation`;
CREATE TABLE `t_video_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `video_id` bigint DEFAULT NULL COMMENT '视频id',
  `operation_type` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '操作类型:0点赞，1收藏，2投币',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_operation_user_id` (`user_id`) USING BTREE,
  KEY `fk_video_operation_video_id` (`video_id`) USING BTREE,
  CONSTRAINT `fk_video_operation_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_operation_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户操作表';

-- ----------------------------
-- Table structure for t_video_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_video_tag`;
CREATE TABLE `t_video_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `video_id` bigint NOT NULL COMMENT '视频id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_tag_video_id` (`video_id`) USING BTREE,
  KEY `fk_video_tag_tag_id` (`tag_id`) USING BTREE,
  CONSTRAINT `fk_video_tag_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `t_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_tag_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='视频标签关联表';

-- ----------------------------
-- Table structure for t_video_view
-- ----------------------------
DROP TABLE IF EXISTS `t_video_view`;
CREATE TABLE `t_video_view` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `video_id` bigint NOT NULL COMMENT '视频id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `client_id` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '客户端id',
  `ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'ip',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_video_view_video_id` (`video_id`) USING BTREE,
  KEY `fk_video_view_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_video_view_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_video_view_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='视频观看记录表';

-- ----------------------------
-- Event structure for expire_ads_event
-- ----------------------------
DROP EVENT IF EXISTS `expire_ads_event`;
delimiter ;;
CREATE EVENT `expire_ads_event`
ON SCHEDULE
EVERY '1' MINUTE STARTS '2023-12-26 04:12:55'
DO UPDATE t_advertisement
    SET status = '结束'
    WHERE end_time <= NOW() AND status = '激活'
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
