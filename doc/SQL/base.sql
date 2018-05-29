/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : 

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-03-16 10:00:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mana_classify
-- ----------------------------
DROP TABLE IF EXISTS `mana_classify`;
CREATE TABLE `mana_classify` (
  `classify_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `classify_code` varchar(50) DEFAULT NULL,
  `parent_id` bigint(50) DEFAULT NULL,
  `parent_name` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `layer` int(11) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，1：显示，0：隐藏',
  `remark` varchar(50) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`classify_id`),
  KEY `classify_code` (`classify_code`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mana_devices
-- ----------------------------
DROP TABLE IF EXISTS `mana_devices`;
CREATE TABLE `mana_devices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(255) NOT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mana_material
-- ----------------------------
DROP TABLE IF EXISTS `mana_material`;
CREATE TABLE `mana_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `material_name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `suffix` varchar(255) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `classify_id` bigint(20) DEFAULT NULL,
  `file_size` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `classify_id` (`classify_id`),
  CONSTRAINT `mana_material_ibfk_1` FOREIGN KEY (`classify_id`) REFERENCES `mana_classify` (`classify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mana_program
-- ----------------------------
DROP TABLE IF EXISTS `mana_program`;
CREATE TABLE `mana_program` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '节目名',
  `create_user_id` bigint(20) DEFAULT NULL,
  `width` varchar(255) DEFAULT NULL,
  `height` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `items` text COMMENT '储存节目布局信息',
  `status` int(11) unsigned zerofill DEFAULT NULL,
  `ratio` varchar(255) DEFAULT NULL COMMENT '节目分辨率字段，只做展示用',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_index` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mana_program_material
-- ----------------------------
DROP TABLE IF EXISTS `mana_program_material`;
CREATE TABLE `mana_program_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` varchar(255) DEFAULT NULL COMMENT '节目的素材的位置',
  `sort_num` int(11) DEFAULT NULL,
  `material_id` bigint(20) DEFAULT NULL,
  `program_id` bigint(20) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `program_id` (`program_id`),
  KEY `material_id` (`material_id`),
  CONSTRAINT `mana_program_material_ibfk_2` FOREIGN KEY (`program_id`) REFERENCES `mana_program` (`id`),
  CONSTRAINT `mana_program_material_ibfk_3` FOREIGN KEY (`material_id`) REFERENCES `mana_material` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mana_program_programtask_relation
-- ----------------------------
DROP TABLE IF EXISTS `mana_program_programtask_relation`;
CREATE TABLE `mana_program_programtask_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `program_id` bigint(20) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  `player_num` int(11) DEFAULT NULL COMMENT '节目播发顺序',
  `begin_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `begin_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `end_date` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `end_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `week` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_pi7bqcqg8u0niepnkgp333par` (`program_id`),
  KEY `FK_93iipxa8f9vtfslloa866j8hu` (`task_id`),
  CONSTRAINT `mana_program_programtask_relation_ibfk_1` FOREIGN KEY (`program_id`) REFERENCES `mana_program` (`id`),
  CONSTRAINT `mana_program_programtask_relation_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `mana_program_task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for mana_program_task
-- ----------------------------
DROP TABLE IF EXISTS `mana_program_task`;
CREATE TABLE `mana_program_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `play_model` int(11) DEFAULT NULL COMMENT '播放顺序',
  `width` varchar(255) DEFAULT NULL,
  `height` varchar(255) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '节目单审核状态： -2未审核  -1待审核  1已审核 0审核通过',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `publish_path` varchar(255) DEFAULT NULL COMMENT '节目单路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mana_terminal
-- ----------------------------
DROP TABLE IF EXISTS `mana_terminal`;
CREATE TABLE `mana_terminal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `terminal_id` varchar(255) DEFAULT NULL,
  `terminal_name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_user_id` bigint(20) DEFAULT NULL,
  `last_online_date` datetime DEFAULT NULL,
  `network_status` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sync_status` int(11) DEFAULT '-1',
  `root_node` int(11) DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `classify_id` bigint(50) DEFAULT NULL,
  `classify_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `classify_id` (`classify_id`),
  CONSTRAINT `mana_terminal_ibfk_1` FOREIGN KEY (`classify_id`) REFERENCES `mana_classify` (`classify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `area_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '区域id',
  `area_code` varchar(50) NOT NULL COMMENT '行政区划代码',
  `parent_code` varchar(50) NOT NULL COMMENT '父级id',
  `name` varchar(20) DEFAULT NULL COMMENT '地区名称',
  `layer` int(11) DEFAULT NULL COMMENT '层级',
  `order_num` int(11) DEFAULT NULL COMMENT '排序号,1:省级,2:地市,3:区县',
  `status` tinyint(4) DEFAULT NULL COMMENT '显示,1:显示,0:隐藏',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3509 DEFAULT CHARSET=utf8 COMMENT='行政区划';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=638 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for sys_macro
-- ----------------------------
DROP TABLE IF EXISTS `sys_macro`;
CREATE TABLE `sys_macro` (
  `macro_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_id` bigint(255) DEFAULT NULL COMMENT '父级id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `value` varchar(2000) DEFAULT NULL COMMENT '值',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态，0：隐藏   1：显示',
  `type` tinyint(20) DEFAULT NULL COMMENT '类型,0:目录，1:参数配置',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`macro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='通用字典表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2433 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';
SET FOREIGN_KEY_CHECKS=1;
