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

 Date: 12/12/2023 12:09:33
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
    PRIMARY KEY (`id`),
    KEY `fk_ad_performance_advertisement` (`ad_id`),
    CONSTRAINT `fk_ad_performance_advertisement` FOREIGN KEY (`ad_id`) REFERENCES `t_advertisement` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '广告性能表';

-- ----------------------------
-- Records of t_ad_performance
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_ad_space
-- ----------------------------
DROP TABLE IF EXISTS `t_ad_space`;

CREATE TABLE `t_ad_space` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `description` varchar(255) DEFAULT NULL COMMENT '广告位描述（如页面位置、尺寸等）',
    `price` decimal(10, 2) DEFAULT NULL COMMENT '价格',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '广告位表';

-- ----------------------------
-- Records of t_ad_space
-- ----------------------------
BEGIN;
COMMIT;

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
    `status` varchar(50) DEFAULT NULL COMMENT '状态（如：激活、暂停、结束）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `advertisement_path` varchar(255) DEFAULT NULL COMMENT '广告链接',
    PRIMARY KEY (`id`),
    KEY `fk_advertisement_advertiser` (`advertiser_id`),
    KEY `fk_advertisement_content` (`content_id`),
    KEY `fk_advertisement_ad_space` (`ad_space_id`),
    CONSTRAINT `fk_advertisement_ad_space` FOREIGN KEY (`ad_space_id`) REFERENCES `t_ad_space` (`id`),
    CONSTRAINT `fk_advertisement_advertiser` FOREIGN KEY (`advertiser_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_advertisement_content` FOREIGN KEY (`content_id`) REFERENCES `t_file` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '广告表';

-- ----------------------------
-- Records of t_advertisement
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_auth_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_element_operation`;

CREATE TABLE `t_auth_element_operation` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `element_name` varchar(255) DEFAULT NULL COMMENT '页面元素名称',
    `element_code` varchar(50) DEFAULT NULL COMMENT '页面元素唯一编码',
    `operation_type` varchar(5) DEFAULT NULL COMMENT '操作类型：0可点击，1可见',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限控制--页面元素操作表';

-- ----------------------------
-- Records of t_auth_element_operation
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_element_operation` (`id`, `element_name`, `element_code`, `operation_type`, `create_time`, `update_time`)
    VALUES (1, '视频投稿按钮', 'postVideoButton', '0', '2023-01-24 09:37:06', '2023-01-24 09:37:06');
COMMIT;

-- ----------------------------
-- Table structure for t_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_menu`;

CREATE TABLE `t_auth_menu` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name` varchar(255) DEFAULT NULL COMMENT '菜单项目名称',
    `code` varchar(50) DEFAULT NULL COMMENT '唯一编码',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限控制--页面访问表';

-- ----------------------------
-- Records of t_auth_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_menu` (`id`, `name`, `code`, `create_time`, `update_time`)
    VALUES (1, '购买邀请码', 'buyInviteCode', '2023-01-24 13:02:03', '2023-01-24 13:02:03');
COMMIT;

-- ----------------------------
-- Table structure for t_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role`;

CREATE TABLE `t_auth_role` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `code` varchar(50) DEFAULT NULL COMMENT '唯一编码',
    PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限控制--角色表';

-- ----------------------------
-- Records of t_auth_role
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_role` (`id`, `name`, `create_time`, `update_time`, `code`)
    VALUES (1, '等级0', '2023-01-24 09:34:56', '2023-01-24 09:34:56', 'Lv0');
INSERT INTO `t_auth_role` (`id`, `name`, `create_time`, `update_time`, `code`)
    VALUES (2, '等级1', '2023-01-24 09:35:08', '2023-01-24 09:35:08', 'Lv1');
INSERT INTO `t_auth_role` (`id`, `name`, `create_time`, `update_time`, `code`)
    VALUES (3, '等级2', '2023-01-24 09:35:19', '2023-01-24 09:35:19', 'Lv2');
COMMIT;

-- ----------------------------
-- Table structure for t_auth_role_element_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_element_operation`;

CREATE TABLE `t_auth_role_element_operation` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `role_id` bigint DEFAULT NULL COMMENT '角色id',
    `element_operation_id` bigint DEFAULT NULL COMMENT '元素操作id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_role_element_operation_role_id` (`role_id`),
    KEY `fk_role_element_operation_element_operation_id` (`element_operation_id`),
    CONSTRAINT `fk_role_element_operation_element_operation_id` FOREIGN KEY (`element_operation_id`) REFERENCES `t_auth_element_operation` (`id`),
    CONSTRAINT `fk_role_element_operation_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_auth_role` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限控制--角色与元素操作关联表';

-- ----------------------------
-- Records of t_auth_role_element_operation
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_role_element_operation` (`id`, `role_id`, `element_operation_id`, `create_time`)
    VALUES (1, 2, 1, '2023-01-24 09:38:22');
COMMIT;

-- ----------------------------
-- Table structure for t_auth_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_menu`;

CREATE TABLE `t_auth_role_menu` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `role_id` bigint DEFAULT NULL COMMENT '角色id',
    `menu_id` bigint DEFAULT NULL COMMENT '页面菜单id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_role_menu_role_id` (`role_id`),
    KEY `fk_role_menu_menu_id` (`menu_id`),
    CONSTRAINT `fk_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `t_auth_menu` (`id`),
    CONSTRAINT `fk_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_auth_role` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限控制--角色页面菜单关联表';

-- ----------------------------
-- Records of t_auth_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_auth_role_menu` (`id`, `role_id`, `menu_id`, `create_time`)
    VALUES (1, 2, 1, '2023-01-24 13:03:54');
COMMIT;

-- ----------------------------
-- Table structure for t_collection_group
-- ----------------------------
DROP TABLE IF EXISTS `t_collection_group`;

CREATE TABLE `t_collection_group` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `name` varchar(50) DEFAULT NULL COMMENT '收藏分组名称',
    `type` varchar(5) DEFAULT NULL COMMENT '收藏分组类型：0默认收藏分组',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `fk_collection_group_user_id` (`user_id`),
    CONSTRAINT `fk_collection_group_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 1001 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户收藏分组表';

-- ----------------------------
-- Records of t_collection_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_danmu
-- ----------------------------
DROP TABLE IF EXISTS `t_danmu`;

CREATE TABLE `t_danmu` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `video_id` bigint DEFAULT NULL COMMENT '视频id',
    `content` text COMMENT '弹幕内容',
    `danmu_time` varchar(50) DEFAULT NULL COMMENT '弹幕出现时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_danmu_user_id` (`user_id`),
    KEY `fk_danmu_video_id` (`video_id`),
    CONSTRAINT `fk_danmu_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_danmu_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '弹幕表';

-- ----------------------------
-- Records of t_danmu
-- ----------------------------
BEGIN;
INSERT INTO `t_danmu` (`id`, `user_id`, `video_id`, `content`, `danmu_time`, `create_time`)
    VALUES (2, 3, 13, '123', NULL, '2023-11-28 16:23:23');
COMMIT;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;

CREATE TABLE `t_file` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `url` varchar(500) DEFAULT NULL COMMENT '文件存储路径',
    `type` varchar(50) DEFAULT NULL COMMENT '文件类型',
    `md5` varchar(500) DEFAULT NULL COMMENT '文件MD5唯一标识',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 8 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '上传文件信息表';

-- ----------------------------
-- Records of t_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_following_group
-- ----------------------------
DROP TABLE IF EXISTS `t_following_group`;

CREATE TABLE `t_following_group` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `name` varchar(50) DEFAULT NULL COMMENT '关注分组名称',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `type` varchar(5) DEFAULT NULL COMMENT '关注分组类型：0特别关注，1悄悄关注，2默认关注，3用户自定义关注',
    PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户关注分组表';

-- ----------------------------
-- Records of t_following_group
-- ----------------------------
BEGIN;
INSERT INTO `t_following_group` (`id`, `user_id`, `name`, `update_time`, `create_time`, `type`)
    VALUES (1, NULL, '特别关注', '2023-01-23 05:07:55', '2023-01-23 05:07:55', '0');
INSERT INTO `t_following_group` (`id`, `user_id`, `name`, `update_time`, `create_time`, `type`)
    VALUES (2, NULL, '悄悄关注', '2023-01-23 05:08:11', '2023-01-23 05:08:11', '1');
INSERT INTO `t_following_group` (`id`, `user_id`, `name`, `update_time`, `create_time`, `type`)
    VALUES (3, NULL, '默认关注', '2023-01-23 05:08:57', '2023-01-23 05:08:57', '2');
COMMIT;

-- ----------------------------
-- Table structure for t_messages
-- ----------------------------
DROP TABLE IF EXISTS `t_messages`;

CREATE TABLE `t_messages` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `sender_id` bigint NOT NULL COMMENT '发送者id',
    `receiver_id` bigint NOT NULL COMMENT '接收者id',
    `message` text NOT NULL COMMENT '消息内容',
    `send_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    PRIMARY KEY (`id`),
    KEY `fk_messages_sender` (`sender_id`),
    KEY `fk_messages_receiver` (`receiver_id`),
    CONSTRAINT `fk_messages_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_messages_sender` FOREIGN KEY (`sender_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '消息通信表';

-- ----------------------------
-- Records of t_messages
-- ----------------------------
BEGIN;
COMMIT;

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
    `content` text COMMENT '合作内容',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `fk_partnership_advertiser` (`advertiser_id`),
    KEY `fk_partnership_creator` (`creator_id`),
    CONSTRAINT `fk_partnership_advertiser` FOREIGN KEY (`advertiser_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_partnership_creator` FOREIGN KEY (`creator_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '合作关系表';

-- ----------------------------
-- Records of t_partnership
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_partnership_requests
-- ----------------------------
DROP TABLE IF EXISTS `t_partnership_requests`;

CREATE TABLE `t_partnership_requests` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '合作请求ID',
    `advertiser_id` bigint NOT NULL COMMENT '广告主ID',
    `content_creator_id` bigint NOT NULL COMMENT '内容创作者ID',
    `request_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
    `status` enum ('pending', 'accepted', 'rejected') NOT NULL DEFAULT 'pending' COMMENT '请求状态（等待、接受、拒绝）',
    `details` text COMMENT '合作详情',
    `expected_duration` int DEFAULT NULL COMMENT '预期的合作期限（以天为单位）',
    `expected_compensation` decimal(10, 2) DEFAULT NULL COMMENT '预期的报酬',
    PRIMARY KEY (`id`),
    KEY `fk_partnership_requests_advertiser` (`advertiser_id`),
    KEY `fk_partnership_requests_creator` (`content_creator_id`),
    CONSTRAINT `fk_partnership_requests_advertiser` FOREIGN KEY (`advertiser_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_partnership_requests_creator` FOREIGN KEY (`content_creator_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = latin1 COMMENT = '合作请求表';

-- ----------------------------
-- Records of t_partnership_requests
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `t_refresh_token`;

CREATE TABLE `t_refresh_token` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `refresh_token` varchar(500) DEFAULT NULL COMMENT '刷新令牌',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_refresh_token_user_id` (`user_id`),
    CONSTRAINT `fk_refresh_token_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 11 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '刷新令牌记录表';

-- ----------------------------
-- Records of t_refresh_token
-- ----------------------------
BEGIN;
INSERT INTO `t_refresh_token` (`id`, `user_id`, `refresh_token`, `create_time`)
    VALUES (9, 3, 'eyJraWQiOiIzIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiLnrb7lj5HogIUiLCJleHAiOjE3MDIwOTE4ODN9.JtALMd-laeS76MZp1xYVzqtHpAvRns__9esuyitw_u0H5rCGosQCGmwP10888rE9Tli4xWFTH5FZ7CVVaA4kSTSxD2w74Fg0PdJjeWESIBlFG6IPg7O8XSaA7Qli1fley539O_wJBQuCq0iA6QOPzzR06PNb4k70fV0Yi0SVoFg', '2023-12-02 11:18:03');
INSERT INTO `t_refresh_token` (`id`, `user_id`, `refresh_token`, `create_time`)
    VALUES (10, 3, 'eyJraWQiOiIzIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiLnrb7lj5HogIUiLCJleHAiOjE3MDIwOTM1NTR9.G6j2KpFp7vbFRNNjPr6fmws-AnxnembqUIl9xCtUrduJ-49_TrtpKgK9uACVdxzvB9aH7SfqU_L5V-OsYW6AS3ZFj2tYMwGerLYolqv9l9RjkmoXjgsMt_cHQpPohDbFBLZx6OYzF2SyRruUFrER-hCoFg1alfELCVpAshT_sBE', '2023-12-02 11:45:54');
COMMIT;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name` varchar(255) DEFAULT NULL COMMENT '名称',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表';

-- ----------------------------
-- Records of t_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `phone` varchar(100) DEFAULT NULL COMMENT '手机号',
    `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
    `user_password` varchar(255) DEFAULT NULL COMMENT '密码',
    `salt` varchar(50) DEFAULT NULL COMMENT '盐值',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`id`, `phone`, `email`, `user_password`, `salt`, `create_time`, `update_time`)
    VALUES (1, '13887654321', NULL, '62261c47d94c9986818709780e4eb6da', '1674445314548', '2023-01-23 03:41:54', '2023-01-23 04:16:07');
INSERT INTO `t_user` (`id`, `phone`, `email`, `user_password`, `salt`, `create_time`, `update_time`)
    VALUES (2, '13987654321', NULL, '9b603b98859fec10a5613ad0aa313ac2', '1674540758877', '2023-01-24 06:12:39', '2023-01-24 06:12:39');
INSERT INTO `t_user` (`id`, `phone`, `email`, `user_password`, `salt`, `create_time`, `update_time`)
    VALUES (3, '1', NULL, '137ade7680450d1148e7d1da802dbc34', '1700791570711', '2023-11-24 10:06:10', '2023-11-24 10:06:10');
COMMIT;

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
    PRIMARY KEY (`id`),
    KEY `fk_user_coin_user_id` (`user_id`),
    CONSTRAINT `fk_user_coin_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户硬币数量表';

-- ----------------------------
-- Records of t_user_coin
-- ----------------------------
BEGIN;
INSERT INTO `t_user_coin` (`id`, `user_id`, `amount`, `create_time`, `update_time`)
    VALUES (1, 3, 0, '2023-11-24 10:06:10', '2023-11-24 10:06:10');
COMMIT;

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
    PRIMARY KEY (`id`),
    KEY `fk_user_following_user_id` (`user_id`),
    KEY `fk_user_following_following_id` (`following_id`),
    KEY `fk_user_following_group_id` (`group_id`),
    CONSTRAINT `fk_user_following_following_id` FOREIGN KEY (`following_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_user_following_group_id` FOREIGN KEY (`group_id`) REFERENCES `t_following_group` (`id`),
    CONSTRAINT `fk_user_following_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户关注表';

-- ----------------------------
-- Records of t_user_following
-- ----------------------------
BEGIN;
INSERT INTO `t_user_following` (`id`, `user_id`, `following_id`, `group_id`, `create_time`, `update_time`)
    VALUES (2, 1, 2, 3, '2023-01-24 06:13:14', '2023-01-24 06:13:14');
COMMIT;

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;

CREATE TABLE `t_user_info` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id（关联）',
    `nick` varchar(100) DEFAULT NULL COMMENT '昵称',
    `avatar` varchar(1024) DEFAULT NULL COMMENT '头像',
    `sign` text COMMENT '签名',
    `gender` varchar(2) DEFAULT NULL COMMENT '性别：0男，1女，2未知',
    `birth` varchar(20) DEFAULT NULL COMMENT '生日',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `fk_user_info_user_id` (`user_id`),
    CONSTRAINT `fk_user_info_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户基本信息表';

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
BEGIN;
INSERT INTO `t_user_info` (`id`, `user_id`, `nick`, `avatar`, `sign`, `gender`, `birth`, `create_time`, `update_time`)
    VALUES (1, 1, '牛逼', NULL, NULL, '1', '2000-01-01', '2023-01-23 03:41:54', '2023-11-24 10:07:16');
INSERT INTO `t_user_info` (`id`, `user_id`, `nick`, `avatar`, `sign`, `gender`, `birth`, `create_time`, `update_time`)
    VALUES (2, 2, '萌新', NULL, NULL, '2', '2000-01-01', '2023-01-24 06:12:39', '2023-01-24 06:12:39');
INSERT INTO `t_user_info` (`id`, `user_id`, `nick`, `avatar`, `sign`, `gender`, `birth`, `create_time`, `update_time`)
    VALUES (3, 3, '萌新', NULL, NULL, '2', '2000-01-01', '2023-11-24 10:06:10', '2023-11-24 10:06:10');
COMMIT;

-- ----------------------------
-- Table structure for t_user_moments
-- ----------------------------
DROP TABLE IF EXISTS `t_user_moments`;

CREATE TABLE `t_user_moments` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `type` varchar(5) DEFAULT NULL COMMENT '动态类型：0视频，1直播，2动态专栏',
    `content_id` bigint DEFAULT NULL COMMENT '内容详情id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `fk_user_moments_user_id` (`user_id`),
    CONSTRAINT `fk_user_moments_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 13 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户动态表';

-- ----------------------------
-- Records of t_user_moments
-- ----------------------------
BEGIN;
INSERT INTO `t_user_moments` (`id`, `user_id`, `type`, `content_id`, `create_time`, `update_time`)
    VALUES (11, 2, '0', 2, '2023-01-24 13:58:05', '2023-01-24 13:58:05');
INSERT INTO `t_user_moments` (`id`, `user_id`, `type`, `content_id`, `create_time`, `update_time`)
    VALUES (12, 2, '0', 2, '2023-01-24 13:58:47', '2023-01-24 13:58:47');
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `role_id` bigint DEFAULT NULL COMMENT '角色id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_user_role_user_id` (`user_id`),
    KEY `fk_user_role_role_id` (`role_id`),
    CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_auth_role` (`id`),
    CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`, `create_time`)
    VALUES (1, 1, 1, '2023-01-24 09:39:07');
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`, `create_time`)
    VALUES (2, 2, 2, '2023-01-24 13:41:45');
INSERT INTO `t_user_role` (`id`, `user_id`, `role_id`, `create_time`)
    VALUES (3, 3, 1, '2023-11-24 10:06:10');
COMMIT;

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;

CREATE TABLE `t_video` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `url` varchar(500) NOT NULL COMMENT '视频链接',
    `thumbnail` varchar(500) NOT NULL COMMENT '封面链接',
    `title` varchar(255) NOT NULL COMMENT '视频标题',
    `type` varchar(5) NOT NULL COMMENT '视频类型：0原创，1转载',
    `duration` varchar(255) NOT NULL COMMENT '视频时长',
    `area` varchar(255) NOT NULL COMMENT '所在分区:0鬼畜，1音乐，2电影',
    `description` text COMMENT '视频简介',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `fk_video_user_id` (`user_id`),
    CONSTRAINT `fk_video_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 29 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频投稿记录表';

-- ----------------------------
-- Records of t_video
-- ----------------------------
BEGIN;
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (1, 1, 'url1', 'thumbnail1', 'Video 1', '0', '10min', '0', 'Description 1', '2023-12-02 11:45:16', '2023-12-02 11:45:16');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (2, 2, 'url2', 'thumbnail2', 'Video 2', '1', '20min', '1', 'Description 2', '2023-12-02 11:45:16', '2023-12-02 11:45:16');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (3, 3, 'url3', 'thumbnail3', 'Video 3', '0', '30min', '2', 'Description 3', '2023-12-02 11:45:16', '2023-12-02 11:45:16');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (13, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频2', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:54:46', '2023-01-28 06:54:46');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (14, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频1', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:54:53', '2023-01-28 06:54:53');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (15, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频3', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:54:57', '2023-01-28 06:54:57');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (16, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频5', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:55:01', '2023-01-28 06:55:01');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (17, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频8', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:55:04', '2023-01-28 06:55:04');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (18, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频9', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:55:08', '2023-01-28 06:55:08');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (19, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频10', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:55:13', '2023-01-28 06:55:13');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (20, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频11', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:55:16', '2023-01-28 06:55:16');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (21, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频1', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:59:45', '2023-01-28 06:59:45');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (22, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频2', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:59:49', '2023-01-28 06:59:49');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (23, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频3', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:59:53', '2023-01-28 06:59:53');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (24, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频4', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:59:56', '2023-01-28 06:59:56');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (25, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频5', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 06:59:59', '2023-01-28 06:59:59');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (26, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频6', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 07:00:03', '2023-01-28 07:00:03');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (27, 2, 'xxxxxxxxx.mp4', 'http://example.com', '示例视频7', '0', '199', '0', '示例描述。。。。。。。。。。', '2023-01-28 07:00:06', '2023-01-28 07:00:06');
INSERT INTO `t_video` (`id`, `user_id`, `url`, `thumbnail`, `title`, `type`, `duration`, `area`, `description`, `create_time`, `update_time`)
    VALUES (28, 3, 'abcde', 'abcde', 'good', '0', '199', '0', 'no', '2023-11-28 15:17:33', '2023-11-28 15:17:33');
COMMIT;

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
    PRIMARY KEY (`id`),
    KEY `fk_video_coin_user_id` (`user_id`),
    KEY `fk_video_coin_video_id` (`video_id`),
    CONSTRAINT `fk_video_coin_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_video_coin_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频投币记录表';

-- ----------------------------
-- Records of t_video_coin
-- ----------------------------
BEGIN;
COMMIT;

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
    PRIMARY KEY (`id`),
    KEY `fk_video_collection_video_id` (`video_id`),
    KEY `fk_video_collection_user_id` (`user_id`),
    KEY `fk_video_collection_group_id` (`group_id`),
    CONSTRAINT `fk_video_collection_group_id` FOREIGN KEY (`group_id`) REFERENCES `t_collection_group` (`id`),
    CONSTRAINT `fk_video_collection_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_video_collection_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频收藏表';

-- ----------------------------
-- Records of t_video_collection
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_video_comment`;

CREATE TABLE `t_video_comment` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `video_id` bigint NOT NULL COMMENT '视频id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `comment` text NOT NULL COMMENT '评论',
    `reply_user_id` bigint DEFAULT NULL COMMENT '回复用户id',
    `root_id` bigint DEFAULT NULL COMMENT '根结点评论id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `fk_video_comment_video_id` (`video_id`),
    KEY `fk_video_comment_user_id` (`user_id`),
    KEY `fk_video_comment_reply_user_id` (`reply_user_id`),
    CONSTRAINT `fk_video_comment_reply_user_id` FOREIGN KEY (`reply_user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_video_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_video_comment_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频评论表';

-- ----------------------------
-- Records of t_video_comment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_like
-- ----------------------------
DROP TABLE IF EXISTS `t_video_like`;

CREATE TABLE `t_video_like` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `video_id` bigint NOT NULL COMMENT '视频投稿id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_video_like_user_id` (`user_id`),
    KEY `fk_video_like_video_id` (`video_id`),
    CONSTRAINT `fk_video_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_video_like_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频点赞表';

-- ----------------------------
-- Records of t_video_like
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_operation
-- ----------------------------
DROP TABLE IF EXISTS `t_video_operation`;

CREATE TABLE `t_video_operation` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `video_id` bigint DEFAULT NULL COMMENT '视频id',
    `operation_type` varchar(5) DEFAULT NULL COMMENT '操作类型:0点赞，1收藏，2投币',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_video_operation_user_id` (`user_id`),
    KEY `fk_video_operation_video_id` (`video_id`),
    CONSTRAINT `fk_video_operation_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_video_operation_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB AUTO_INCREMENT = 17 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户操作表';

-- ----------------------------
-- Records of t_video_operation
-- ----------------------------
BEGIN;
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (1, 1, 17, '0', '2009-12-18 17:23:16');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (2, 2, 21, '1', '2021-06-07 03:08:25');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (3, 2, 23, '2', '2009-04-21 16:47:22');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (4, 3, 15, '3', '2010-11-14 17:37:33');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (5, 1, 26, '1', '2008-06-21 00:18:27');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (6, 3, 15, '2', '2000-08-12 08:44:08');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (7, 1, 15, '1', '2016-10-04 21:36:15');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (8, 1, 16, '2', '2018-11-13 21:29:13');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (9, 3, 19, '1', '2018-02-23 08:15:09');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (10, 1, 28, '2', '2017-10-22 16:02:33');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (11, 1, 1, '0', '2023-12-02 11:45:25');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (12, 1, 2, '1', '2023-12-02 11:45:25');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (13, 2, 1, '2', '2023-12-02 11:45:25');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (14, 2, 3, '0', '2023-12-02 11:45:25');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (15, 3, 2, '1', '2023-12-02 11:45:25');
INSERT INTO `t_video_operation` (`id`, `user_id`, `video_id`, `operation_type`, `create_time`)
    VALUES (16, 3, 3, '2', '2023-12-02 11:45:25');
COMMIT;

-- ----------------------------
-- Table structure for t_video_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_video_tag`;

CREATE TABLE `t_video_tag` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `video_id` bigint NOT NULL COMMENT '视频id',
    `tag_id` bigint NOT NULL COMMENT '标签id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_video_tag_video_id` (`video_id`),
    KEY `fk_video_tag_tag_id` (`tag_id`),
    CONSTRAINT `fk_video_tag_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `t_tag` (`id`),
    CONSTRAINT `fk_video_tag_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频标签关联表';

-- ----------------------------
-- Records of t_video_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_video_view
-- ----------------------------
DROP TABLE IF EXISTS `t_video_view`;

CREATE TABLE `t_video_view` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `video_id` bigint NOT NULL COMMENT '视频id',
    `user_id` bigint DEFAULT NULL COMMENT '用户id',
    `client_id` varchar(500) DEFAULT NULL COMMENT '客户端id',
    `ip` varchar(50) DEFAULT NULL COMMENT 'ip',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `fk_video_view_video_id` (`video_id`),
    KEY `fk_video_view_user_id` (`user_id`),
    CONSTRAINT `fk_video_view_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
    CONSTRAINT `fk_video_view_video_id` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频观看记录表';

-- ----------------------------
-- Records of t_video_view
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

