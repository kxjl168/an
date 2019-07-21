/*
Navicat MySQL Data Transfer

Source Server         : 192.168.31.239_ubuntu
Source Server Version : 50726
Source Host           : 192.168.31.239:3306
Source Database       : video

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-20 18:50:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `ID` varchar(64) NOT NULL,
  `UserID` varchar(32) DEFAULT NULL COMMENT '用户账号',
  `PASSWORD` varchar(32) DEFAULT NULL COMMENT '用户密码',
  `TELEPHONE` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `NICKNAME` varchar(45) DEFAULT NULL,
  `HEAD_IMG` blob COMMENT '头像',
  `TOKEN` varchar(36) DEFAULT NULL COMMENT '用户访问令牌',
  `TIME` varchar(36) DEFAULT NULL COMMENT '令牌创建时间',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `icon` varchar(64) DEFAULT NULL COMMENT '头像md5',
  `UnitId` varchar(64) DEFAULT NULL COMMENT '所属单位id,若为本平台管理员则为null',
  `DataState` int(11) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `t_manager_Phone` (`TELEPHONE`),
  UNIQUE KEY `t_manager_UserIDe` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台登录用户管理员';

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', '13815429446', '7dacac90b853c7c57f40bd099a493a19', '13815429446', '1', null, null, null, '2019-07-19 16:32:13', null, null, '1');
INSERT INTO `manager` VALUES ('eb4260703db94bfea41b7329f0431234', '13815429442', 'cc813d939508898a37eec617237d920e', '13815429442', '22', null, null, null, '2019-07-19 17:04:13', '', null, '1');

-- ----------------------------
-- Table structure for sys_dict_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_info`;
CREATE TABLE `sys_dict_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) DEFAULT NULL COMMENT '类型   1案件类型  2案件等级 3常用语',
  `dict_name` varchar(16) DEFAULT NULL COMMENT '字典名称',
  `dict_value` varchar(128) DEFAULT NULL COMMENT '字典值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

-- ----------------------------
-- Records of sys_dict_info
-- ----------------------------
INSERT INTO `sys_dict_info` VALUES ('1', '1', '盗窃事件', '1');
INSERT INTO `sys_dict_info` VALUES ('2', '1', '抢劫事件', '2');
INSERT INTO `sys_dict_info` VALUES ('3', '1', '团伙黑帮', '3');
INSERT INTO `sys_dict_info` VALUES ('4', '2', '轻微', '1');
INSERT INTO `sys_dict_info` VALUES ('5', '2', '重大', '3');
INSERT INTO `sys_dict_info` VALUES ('6', '2', '特大级别', '0');
INSERT INTO `sys_dict_info` VALUES ('7', '3', '你好', '1');
INSERT INTO `sys_dict_info` VALUES ('8', '3', '有什么可以帮您的？', '2');
INSERT INTO `sys_dict_info` VALUES ('9', '3', '稍安勿躁，我们马上出动', '3');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `old_name` varchar(100) DEFAULT NULL COMMENT '文件原始名称',
  `save_name` varchar(150) DEFAULT NULL COMMENT '文件存储名称',
  `full_path` varchar(400) DEFAULT NULL COMMENT '文件本地存储全路径',
  `http_relative_path` varchar(500) DEFAULT NULL COMMENT '文件访问相对路径  (不带http://xxxx/)',
  `http_down_url` varchar(500) DEFAULT NULL COMMENT '文件下载路径.action-计算下载次数(不带http://xxxx/)',
  `file_size` int(11) DEFAULT NULL,
  `save_date` varchar(50) DEFAULT NULL,
  `file_md5` varchar(100) DEFAULT NULL,
  `down_nums` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_operlog
-- ----------------------------
DROP TABLE IF EXISTS `sys_operlog`;
CREATE TABLE `sys_operlog` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '登录用户ID',
  `login_name` varchar(50) DEFAULT NULL COMMENT '登录名',
  `display_name` varchar(50) DEFAULT NULL COMMENT '显示名',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `oper_type` varchar(20) DEFAULT NULL COMMENT '操作类型',
  `oper_desc` varchar(2000) DEFAULT NULL COMMENT '操作描述',
  `fun_name` varchar(200) DEFAULT NULL COMMENT '方法名：中文描述',
  `bean_class_name` varchar(200) DEFAULT NULL COMMENT 'bean class名称',
  `oper_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `delete_flag` int(11) DEFAULT '0' COMMENT '删除标记n                        1 删除n                        0 未删除',
  `data` text COMMENT '操作数据',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_sys_id` (`user_id`),
  CONSTRAINT `sys_operlog_manager_ID_fk` FOREIGN KEY (`user_id`) REFERENCES `manager` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统操作日志表';

-- ----------------------------
-- Records of sys_operlog
-- ----------------------------
INSERT INTO `sys_operlog` VALUES ('0141b911dfde435398dac7aa34dbb6c7', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:42:46', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('08abb7373cbc492a969f95b3b171e378', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.UnitinfoController.saveOrUpdate', 'Unitinfo', '2019-07-19 22:38:20', '0', '--> {\"id\":\"\",\"name\":\"??????\",\"createTime\":\"2019-07-19 22:45:42\",\"uptimestamp\":\"2019-07-19 22:40:42\",\"dataState\":\"1\",\"des\":\"\",\"contactPerson\":\"zz\",\"contactPhone\":\"13815429446\",\"address\":\"????3333?\"}');
INSERT INTO `sys_operlog` VALUES ('08ec86c3ede44c90928c23f6694c7ad0', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:58:10', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"2\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('0b8f755074d2497dad0c02202fbfd3a3', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-20 10:14:57', '0', '--> {\"type\":2,\"onlineseatsId\":3123,\"area\":\"123\",\"latitude\":\"11\",\"longitude\":\"231\",\"address\":\"313\"}');
INSERT INTO `sys_operlog` VALUES ('1d781d1034cc4d09a0b891c25682ed7a', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:38:40', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('2405e4f317da43c38b421991073d20cd', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:43:19', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('7ba28792be6f403489f3a697fc7a946e', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:58:46', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"2\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('8214b91a59f74444a4abea3bdd6038d2', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-20 10:14:32', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"1\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('952a21239e0e408ba4ebadd9157e6cae', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '??????????', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-19 22:37:35', '0', '--> {\"id\":\"\",\"phone\":\"13815429446\",\"password\":\"111111\",\"sessionKey\":\"\",\"name\":\"111\",\"createTime\":\"2019-07-19 22:45:09\",\"uptimestamp\":\"2019-07-19 22:45:09\",\"createUser\":\"\",\"updateUser\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('b2b8bc4f84b94f7dbf625ab9705a0ec9', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-20 12:07:28', '0', '--> {\"type\":22,\"userName\":\"123\",\"idNumber\":\"1231\",\"area\":\"2312\",\"latitude\":\"312\",\"longitude\":\"3123\",\"address\":\"1231\"}');
INSERT INTO `sys_operlog` VALUES ('c4c53d95ac474a239317bed57bce9a23', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:38:42', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('f2ede9d378f24a419f2959c131c4e1ce', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 23:00:11', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"2\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('f9838dc1b27243b28257cafed943d5d0', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '??????????', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-19 22:37:24', '0', '--> {\"id\":\"\",\"phone\":\"13815429446\",\"password\":\"111111\",\"sessionKey\":\"\",\"name\":\"111\",\"createTime\":\"\",\"uptimestamp\":\"\",\"createUser\":\"\",\"updateUser\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('ff8e7fe7c5574f678b4b938242ce3e7b', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:42:34', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(128) NOT NULL COMMENT '资源名称',
  `type` varchar(32) NOT NULL COMMENT '权限类型：1，一级菜单；  2，二级菜单； 3，二级菜单功能下的按钮',
  `url` varchar(128) DEFAULT NULL COMMENT '访问url地址',
  `percode` varchar(128) DEFAULT NULL COMMENT '权限代码字符串',
  `parentid` varchar(60) DEFAULT NULL COMMENT '父结点id',
  `parentids` varchar(128) DEFAULT NULL COMMENT '父结点id列表串',
  `sortstring` varchar(128) DEFAULT NULL COMMENT '排序号',
  `available` char(1) DEFAULT '1' COMMENT '是否可用,1：可用，0不可用',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标或者icon class',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限-菜单-按钮';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '系统设置', '1', '/', 'sys', '0', null, '1', '1', 'fa fa-cogs');
INSERT INTO `sys_permission` VALUES ('100', '系统权限管理', '2', '/privilege/permission/index', 'pri:view', '1', null, '1', '1', 'xuanzecj.png');
INSERT INTO `sys_permission` VALUES ('102', '角色管理', '2', '/privilege/role/index', 'role:view', '1', null, '2', '1', 'xuanzecj.png');
INSERT INTO `sys_permission` VALUES ('20', '自动生成管理', '2', '/generator/index', 'generator：view', '1', null, '6', '1', '123457');
INSERT INTO `sys_permission` VALUES ('5', '用户管理', '2', '/manager/user/manager', 'user:view', '1', null, '1', '1', 'xuanzecj.png');
INSERT INTO `sys_permission` VALUES ('home_index', '接警大厅', '2', '/query', 'home_index:view', 'jingwu', null, '1', '1', null);
INSERT INTO `sys_permission` VALUES ('jingwu', '警务管理', '1', '/', 'jingwu:view', '0', null, '1', '1', null);
INSERT INTO `sys_permission` VALUES ('menu_auto', '警务', '1', '/', 'menu_auto:view', '0', null, '100', '1', null);
INSERT INTO `sys_permission` VALUES ('menu_sysdictinfo', '字典管理', '2', '/manager/sysdictinfo/manager', 'sysdictinfo:view', 'menu_auto', null, '1', '1', null);
INSERT INTO `sys_permission` VALUES ('menu_talarmuserinfo', '实名认证用户', '2', '/manager/talarmuserinfo/manager', 'talarmuserinfo:view', 'jingwu', null, '3', '1', '');
INSERT INTO `sys_permission` VALUES ('menu_tareainfo', '片区管理', '2', '/manager/tareainfo/manager', 'tareainfo:view', 'jingwu', null, '5', '1', '');
INSERT INTO `sys_permission` VALUES ('menu_tareainfoarea', '片区-行政区划', '2', '/manager/tareainfoarea/manager', 'tareainfoarea:view', 'menu_auto', null, '1', '1', null);
INSERT INTO `sys_permission` VALUES ('menu_tconfig', '配置项管理', '2', '/manager/tconfig/manager', 'tconfig:view', 'menu_auto', null, '1', '1', 'fa fa-cog');
INSERT INTO `sys_permission` VALUES ('menu_tphoneinfo', '接警手机管理', '2', '/manager/tphoneinfo/manager', 'tphoneinfo:view', 'jingwu', null, '8', '1', '');
INSERT INTO `sys_permission` VALUES ('menu_treceivepoliceinfo', '接警人员管理', '2', '/manager/treceivepoliceinfo/manager', 'treceivepoliceinfo:view', 'jingwu', null, '7', '1', '');
INSERT INTO `sys_permission` VALUES ('menu_tseatinfo', '坐席管理', '2', '/manager/tseatinfo/manager', 'tseatinfo:view', 'jingwu', null, '6', '1', '');
INSERT INTO `sys_permission` VALUES ('menu_tunitinfo', '接警单位管理', '2', '/manager/tunitinfo/manager', 'tunitinfo:view', 'jingwu', null, '4', '1', '');
INSERT INTO `sys_permission` VALUES ('menu_tunitinfomanager', '单位管理员配置', '2', '/manager/tunitinfomanager/manager', 'tunitinfomanager:view', 'menu_auto', null, '1', '1', null);
INSERT INTO `sys_permission` VALUES ('menu_tvideoalarmtalkinfo', '聊天记录管理', '2', '/manager/tvideoalarmtalkinfo/manager', 'tvideoalarmtalkinfo:view', 'menu_auto', null, '1', '1', null);
INSERT INTO `sys_permission` VALUES ('menu_videoalarminfo', '接警记录', '2', '/manager/videoalarminfo/manager', 'videoalarminfo:view', 'jingwu', null, '3', '1', '');
INSERT INTO `sys_permission` VALUES ('stastic', '统计分析', '2', '/query', 'stastic:view', 'jingwu', null, '2', '1', null);
INSERT INTO `sys_permission` VALUES ('sys_log', '系统日志', '2', '/manager/sysOperLog/manager', 'sys_log', '1', null, '9', '0', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL,
  `available` char(1) DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `sys_role_id` varchar(64) NOT NULL COMMENT '角色id',
  `sys_permission_id` varchar(64) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_53` (`sys_permission_id`),
  KEY `FK_Reference_54` (`sys_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('31ecba19fc524ef5aca01cf927f26411', '1', 'menu_auto');
INSERT INTO `sys_role_permission` VALUES ('41b5a0a7afb5435f8ae681da0d6be793', '1', 'menu_tphoneinfo');
INSERT INTO `sys_role_permission` VALUES ('43258df2e0a94ff69eda314f2341c666', '1', 'menu_tseatinfo');
INSERT INTO `sys_role_permission` VALUES ('5530c3ed9a174a569767af964781cbad', '1', '1');
INSERT INTO `sys_role_permission` VALUES ('5af510f2f98f4ff59cdf142154f9eff6', '1', '102');
INSERT INTO `sys_role_permission` VALUES ('6458d2856d6840efb828691592d7dc3e', '1', 'menu_tunitinfo');
INSERT INTO `sys_role_permission` VALUES ('65ce2a5db7044295bb4cbf8525a072f7', '1', '100');
INSERT INTO `sys_role_permission` VALUES ('87d1b45c2a3d49aaa9d60e2c256e08e0', '1', 'menu_tunitinfomanager');
INSERT INTO `sys_role_permission` VALUES ('9390ef498786460ebe4621ff3de1ae13', '1', 'jingwu');
INSERT INTO `sys_role_permission` VALUES ('a4275f1fa5914c75a10d61a28ed3ba3a', '1', 'menu_sysdictinfo');
INSERT INTO `sys_role_permission` VALUES ('a89794aaa8d64a25a3b362f1453d5fd7', '1', 'menu_tareainfoarea');
INSERT INTO `sys_role_permission` VALUES ('afc548c84b7c42f4bf4d3e327b572a20', '1', 'menu_talarmuserinfo');
INSERT INTO `sys_role_permission` VALUES ('b85f2a6e7c9f41acb17eea99dfee4534', '1', 'menu_videoalarminfo');
INSERT INTO `sys_role_permission` VALUES ('c0902a852b1c415785c4f764b3181655', '1', 'menu_tvideoalarmtalkinfo');
INSERT INTO `sys_role_permission` VALUES ('c852847ed58c4ab5b8df532251a8afe8', '1', 'menu_tconfig');
INSERT INTO `sys_role_permission` VALUES ('cc9cd0a69314447db9534b2469e480f6', '1', '5');
INSERT INTO `sys_role_permission` VALUES ('cda9a5aba3c04992ae443ffe4624274b', '1', 'menu_treceivepoliceinfo');
INSERT INTO `sys_role_permission` VALUES ('d1854f5157e24408a474452050d0e37c', '1', 'menu_tareainfo');
INSERT INTO `sys_role_permission` VALUES ('da594cb74aaf43a5902d9df7f50000f2', '1', '20');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(64) NOT NULL,
  `sys_role_id` varchar(64) DEFAULT NULL,
  `sys_user_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_55` (`sys_role_id`),
  KEY `FK_Reference_56` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('afa3318016914563adf92f38e2543617', '1', 'eb4260703db94bfea41b7329f0431234');

-- ----------------------------
-- Table structure for t_alarm_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_alarm_userinfo`;
CREATE TABLE `t_alarm_userinfo` (
  `id` varchar(64) NOT NULL COMMENT '报警者ID',
  `username` varchar(64) DEFAULT NULL COMMENT '报警者姓名',
  `nickname` varchar(64) DEFAULT NULL COMMENT '报警者昵称',
  `Sex` varchar(2) DEFAULT NULL COMMENT '性别（男，女），默认 男',
  `IdCard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `address` varchar(128) DEFAULT NULL COMMENT '住址',
  `mobile_phone` varchar(12) DEFAULT NULL COMMENT '手机账号',
  `wechatId` varchar(64) DEFAULT NULL COMMENT '微信号',
  `wechatOpenId` varchar(64) DEFAULT NULL COMMENT '微信号openid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警用户信息';

-- ----------------------------
-- Records of t_alarm_userinfo
-- ----------------------------

-- ----------------------------
-- Table structure for t_area_city
-- ----------------------------
DROP TABLE IF EXISTS `t_area_city`;
CREATE TABLE `t_area_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CityName` varchar(64) DEFAULT NULL COMMENT '城市名称\n',
  `CityCode` varchar(10) DEFAULT NULL COMMENT '城市编码',
  `ProvinceId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_area_city_CityName_index` (`CityName`),
  KEY `t_area_city_CityCode_index` (`CityCode`),
  KEY `t_area_city_ProvinceId_index` (`ProvinceId`),
  CONSTRAINT `t_area_city_t_area_province_id_fk` FOREIGN KEY (`ProvinceId`) REFERENCES `t_area_province` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='高德地图城市编码表';

-- ----------------------------
-- Records of t_area_city
-- ----------------------------

-- ----------------------------
-- Table structure for t_area_district
-- ----------------------------
DROP TABLE IF EXISTS `t_area_district`;
CREATE TABLE `t_area_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DistrictName` varchar(64) DEFAULT NULL COMMENT '区域名称',
  `DistrictCode` varchar(10) DEFAULT NULL COMMENT '区域编码',
  `CityId` int(11) DEFAULT NULL COMMENT '城市id',
  PRIMARY KEY (`id`),
  KEY `t_area_district_DistrictCode_index` (`DistrictCode`),
  KEY `t_area_district_DistrictName_index` (`DistrictName`),
  KEY `t_area_district_CityId_index` (`CityId`),
  KEY `index_area_district_city_id` (`CityId`),
  CONSTRAINT `t_area_district_t_area_city_id_fk` FOREIGN KEY (`CityId`) REFERENCES `t_area_city` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='高德地图区域编码表';

-- ----------------------------
-- Records of t_area_district
-- ----------------------------

-- ----------------------------
-- Table structure for t_area_province
-- ----------------------------
DROP TABLE IF EXISTS `t_area_province`;
CREATE TABLE `t_area_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ProvinceName` varchar(64) DEFAULT NULL COMMENT '省份名称',
  `ProvinceCode` varchar(10) DEFAULT NULL COMMENT '省份编码',
  PRIMARY KEY (`id`),
  KEY `t_area_province_ProvinceName_index` (`ProvinceName`),
  KEY `t_area_province_ProvinceCode_index` (`ProvinceCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='高德地图省份编码表';

-- ----------------------------
-- Records of t_area_province
-- ----------------------------

-- ----------------------------
-- Table structure for t_areainfo
-- ----------------------------
DROP TABLE IF EXISTS `t_areainfo`;
CREATE TABLE `t_areainfo` (
  `Id` varchar(64) NOT NULL COMMENT '片区id(主键)',
  `Name` varchar(50) NOT NULL COMMENT '片区名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
  `DataState` varchar(2) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',
  `UnitId` varchar(64) DEFAULT NULL COMMENT '所属单位id',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='片区信息';

-- ----------------------------
-- Records of t_areainfo
-- ----------------------------

-- ----------------------------
-- Table structure for t_areainfo_area
-- ----------------------------
DROP TABLE IF EXISTS `t_areainfo_area`;
CREATE TABLE `t_areainfo_area` (
  `id` varchar(64) NOT NULL,
  `AreaId` varchar(64) NOT NULL COMMENT '片区id',
  `districtId` int(11) NOT NULL COMMENT '行政区域id -t_area_district-id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='片区对应的物理行政区域 1对多';

-- ----------------------------
-- Records of t_areainfo_area
-- ----------------------------

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `ckey` varchar(50) NOT NULL COMMENT '配置表key,主键，非空',
  `cvalue` varchar(500) NOT NULL COMMENT '配置表value，非空',
  `des` varchar(150) DEFAULT NULL COMMENT '备注说明本配置项的作用，非空',
  PRIMARY KEY (`ckey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES ('31960b9b59374121a0fc180a10a8c922', '11', '11');
INSERT INTO `t_config` VALUES ('LongAuto', '4', '自增Long');

-- ----------------------------
-- Table structure for t_phoneinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_phoneinfo`;
CREATE TABLE `t_phoneinfo` (
  `Id` varchar(64) NOT NULL COMMENT '接警手机id(主键)，不能为0',
  `Phone` varchar(11) NOT NULL COMMENT '接警手机手机号，也是接警手机（唯一约束）',
  `Name` varchar(50) NOT NULL COMMENT '接警手机名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
  `DataState` varchar(2) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',
  `IMEI` varchar(40) DEFAULT NULL COMMENT '手机标识号',
  `PhoneType` varchar(500) DEFAULT NULL COMMENT '手机型号',
  `Des` varchar(500) DEFAULT NULL COMMENT '备注',
  `SeatId` varchar(64) DEFAULT NULL COMMENT '关联的坐席id',
  `UnitId` varchar(64) DEFAULT NULL COMMENT '所属单位id',
  `Status` varchar(2) DEFAULT NULL COMMENT '接警手机状态  0:空闲 1:繁忙 2:离开 3:下线',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `t_phoneinfo_Phone` (`Phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接警手机信息';

-- ----------------------------
-- Records of t_phoneinfo
-- ----------------------------

-- ----------------------------
-- Table structure for t_receivepoliceinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_receivepoliceinfo`;
CREATE TABLE `t_receivepoliceinfo` (
  `Id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '接警人员id(主键)，不能为0',
  `Phone` varchar(11) CHARACTER SET utf8 NOT NULL COMMENT '接警人员手机号，也是登陆接警人员名（唯一约束）',
  `Password` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '接警人员密码',
  `SessionKey` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '会话id',
  `Name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
  `CreateUser` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人（外键manager）',
  `UpdateUser` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '上次更新人（外键manager）',
  `DataState` varchar(2) CHARACTER SET utf8 NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',
  `Sex` varchar(2) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别（男，女），默认 男',
  `IdCard` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号',
  `IdNo` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '工号',
  `Des` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `AvatarUrl` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像md5',
  `SeatId` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '关联的坐席id',
  `UnitId` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '所属单位id',
  `Status` varchar(2) CHARACTER SET utf8 DEFAULT NULL COMMENT '接警人员状态  0:空闲 1:繁忙 2:离开 3:下线',
  PRIMARY KEY (`Id`),
  KEY `t_userinfo_IdCard` (`IdCard`),
  KEY `t_userinfo_IdNo` (`IdNo`),
  KEY `t_userinfo_Phone` (`Phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接警人员信息';

-- ----------------------------
-- Records of t_receivepoliceinfo
-- ----------------------------
INSERT INTO `t_receivepoliceinfo` VALUES ('642046b466c647968f995c22c001b92f', '13815429446', '111111', '', '111', '2019-07-19 22:45:09', '2019-07-19 22:45:09', '', '', '1', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_seatinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_seatinfo`;
CREATE TABLE `t_seatinfo` (
  `Id` varchar(64) NOT NULL COMMENT '坐席id(主键)',
  `Name` varchar(50) NOT NULL COMMENT '坐席名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
  `DataState` varchar(2) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',
  `AreaId` varchar(64) DEFAULT NULL COMMENT '所属片区id',
  `UnitId` varchar(64) DEFAULT NULL COMMENT '所属单位id',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='坐席信息';

-- ----------------------------
-- Records of t_seatinfo
-- ----------------------------

-- ----------------------------
-- Table structure for t_unitinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_unitinfo`;
CREATE TABLE `t_unitinfo` (
  `Id` varchar(64) NOT NULL COMMENT '单位id(主键)',
  `Name` varchar(50) NOT NULL COMMENT '单位名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
  `DataState` varchar(2) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',
  `Des` varchar(500) DEFAULT NULL COMMENT '备注',
  `ContactPerson` varchar(64) DEFAULT NULL COMMENT '单位联系人',
  `ContactPhone` varchar(64) DEFAULT NULL COMMENT '单位联系电话',
  `Address` varchar(64) DEFAULT NULL COMMENT '单位地址',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位信息';

-- ----------------------------
-- Records of t_unitinfo
-- ----------------------------
INSERT INTO `t_unitinfo` VALUES ('480fe0d5ebb445c490e7c719c3898250', '??????', '2019-07-19 22:45:42', '2019-07-19 22:40:42', '1', '', 'zz', '13815429446', '????3333?');

-- ----------------------------
-- Table structure for t_unitinfo_manager
-- ----------------------------
DROP TABLE IF EXISTS `t_unitinfo_manager`;
CREATE TABLE `t_unitinfo_manager` (
  `id` varchar(64) NOT NULL,
  `UnitId` varchar(64) NOT NULL COMMENT '单位id',
  `ManagerId` int(11) NOT NULL COMMENT 'manager id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位超级管理员 1对多';

-- ----------------------------
-- Records of t_unitinfo_manager
-- ----------------------------

-- ----------------------------
-- Table structure for t_videoalarm_talkinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_videoalarm_talkinfo`;
CREATE TABLE `t_videoalarm_talkinfo` (
  `id` varchar(64) NOT NULL COMMENT '序号',
  `alarmId` int(11) DEFAULT NULL COMMENT '报警id',
  `talkType` varchar(2) DEFAULT NULL COMMENT '聊天状态 1:报警人-》接警人， 2:接警人-》报警人',
  `ctime` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '时间',
  `msgType` varchar(2) DEFAULT NULL COMMENT '消息类型 1:文本',
  `msgContent` varchar(2048) DEFAULT NULL COMMENT '消息内容',
  `fileUrl` varchar(512) DEFAULT NULL COMMENT '非文本文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警事件聊天记录';

-- ----------------------------
-- Records of t_videoalarm_talkinfo
-- ----------------------------
INSERT INTO `t_videoalarm_talkinfo` VALUES ('00b7bba776b84d1a8473a62b56e19e18', '3', '2', '2019-07-20 17:09:29.000', '1', '22', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('035080391c5d4e7cbe5c299b58d98558', '3', '1', '2019-07-20 17:51:49.345', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('06c2b33222ad412ca12ba2c0583bc1d6', '3', '2', '2019-07-20 17:00:30.000', '1', '23', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('089ff6519a6c436c8157a37ae0ed4e18', '2', '2', '2019-07-20 18:30:22.206', '1', '有什么可以帮您的？', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('108f36c2641f47ff820dce32d8c22e6c', '2', '2', '2019-07-20 18:29:23.617', '1', '稍安勿躁，我们马上出动', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1347c3c4f486480693cefafe294b6ee0', '3', '2', '2019-07-20 17:03:44.000', '1', '23', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('13701575910e452da214551a18092ec5', '2', '2', '2019-07-20 18:13:38.035', '1', '111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1454d28cbe644a8e89859b0d4f3f248d', '3', '2', '2019-07-20 17:00:15.000', '1', '10', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('15a4c153384e43fba8645def4502b784', '3', '2', '2019-07-20 17:00:05.000', '1', '4', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('16b17aee394d4c9384417de43e5c267c', '2', '2', '2019-07-20 16:15:10.000', '1', '1111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('188a22115fd84f29b5c19ddc1f2c9abc', '3', '2', '2019-07-20 17:07:14.000', '1', '25', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1e219fe273ca4b26b0748f0d2a22bdc8', '2', '1', '2019-07-20 17:46:35.389', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1f255dc0a07d49d09873ec46fe60c0fb', '3', '2', '2019-07-20 17:03:45.000', '1', '24', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('20d68c1c3a1a4feab440b9046eb2fe14', '2', '2', '2019-07-20 16:35:58.000', '1', '你好！！！', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('25c0126859144a84906be76db863c711', '2', '2', '2019-07-20 16:59:26.000', '1', '水电费水电费', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('2f51a3c9076b4b73911cfff1678d8332', '3', '2', '2019-07-20 17:00:03.000', '1', '3', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('31036d3d98884c01978b88d16e1b55f7', '2', '2', '2019-07-20 18:29:26.093', '1', '有什么可以帮您的？', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('373deb38b0a34948a861f7f2ba6a7f38', '3', '1', '2019-07-20 17:55:05.650', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('3784216a37934be6bb43a5f58b62540c', '3', '2', '2019-07-20 17:00:20.000', '1', '15', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('3cddc037bd46470b9fbd0912af4473a1', '2', '2', '2019-07-20 16:39:48.000', '1', '5', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('3d367ea4964d4e049d845f0055d53b78', '2', '2', '2019-07-20 18:11:46.192', '1', '11', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('3e9eec6955df4b8ba2efa060b0f3e235', '2', '2', '2019-07-20 16:59:29.000', '1', '', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('3fc80608fec749de8e732fc6f1753b6d', '3', '2', '2019-07-20 17:09:55.000', '1', '33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('44034b942a164504b1f85c2488d35531', '3', '1', '2019-07-20 17:36:17.745', '1', '我怎么处理222', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('45c2cf8d06b84582be7ab367a274f474', '2', '2', '2019-07-20 16:41:32.000', '1', '5', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('45cd781d82d5499097688acdb7117a82', '2', '2', '2019-07-20 16:39:44.000', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('48082f85583944da88073ad781a4c0c4', '2', '1', '2019-07-20 17:47:00.184', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('54b3db4df6c44070a3f7511d65737109', '2', '2', '2019-07-20 16:59:25.000', '1', '', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('56936e83c8694d76af07fea98cb86b82', '3', '2', '2019-07-20 17:07:16.000', '1', '26', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('58bad0fc35c14631bbf90a2204c48d5c', '2', '2', '2019-07-20 16:59:30.000', '1', '12312 胜多负少d', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('5be2834384d34543bcf584162c04ae15', '3', '1', '2019-07-20 17:34:36.658', '1', '我怎么处理111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('5d6e04289ec24b58a3f9b8ace0e7c95f', '3', '1', '2019-07-20 17:30:44.124', '1', '你好，我发现有人在偷车，要报警', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('60a5ccc438a144da96db05b8c77ab363', '2', '2', '2019-07-20 16:16:00.000', '1', '2123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('6f1d3b61789d4981887953a2dc3b4b91', '2', '2', '2019-07-20 16:36:04.000', '1', '胜多负少d', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('6f4b0e18ec71451f96dc04f6244d9c37', '2', '2', '2019-07-20 16:39:16.000', '1', '1213', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('71b0b6b799c24fcaa9325ee42f3ff3ac', '2', '2', '2019-07-20 18:11:21.989', '1', '', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('726ec95714e441f6afb5f76967d0f9be', '3', '2', '2019-07-20 17:28:00.936', '1', '1231', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('7681cf79941f4c49803623181653c3b8', '2', '2', '2019-07-20 16:59:38.000', '1', '1231', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('79cd66494c224d639f4387288f594fd0', '2', '2', '2019-07-20 16:39:45.000', '1', '2', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('833b8e79351b4c4ebc09e00a1253472b', '3', '2', '2019-07-20 17:00:29.000', '1', '22', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('871000bc825e42ddbe44cc6862c7a2f1', '3', '1', '2019-07-20 17:52:34.203', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('871f8f23b98a4a7a816b98e511b57d6c', '3', '2', '2019-07-20 17:00:25.000', '1', '18', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('8b0d8a88b54b4619acb0e13660d10648', '3', '2', '2019-07-20 17:08:33.000', '1', '11', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('8cacfcb73331464d8add8828997e94e6', '3', '2', '2019-07-20 17:00:23.000', '1', '17', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('8d0ef8590f604f1dab9e75e175377318', '3', '2', '2019-07-20 17:05:53.000', '1', '24', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('90c3219dced2491fa4beac46ff780c28', '3', '2', '2019-07-20 17:00:07.000', '1', '5', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('9bd95f667b194934b26482b211543dd9', '3', '2', '2019-07-20 17:00:13.000', '1', '9', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('9d9b80411388458dbe3d2ad4290ee836', '3', '2', '2019-07-20 17:00:11.000', '1', '8', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('9fade306e70c4ea8ae17c5053212d8e2', '3', '2', '2019-07-20 17:00:16.000', '1', '11', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('a035a3d201e74fa39316cf5d4668ebe5', '3', '1', '2019-07-20 17:52:08.563', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('a20b736fa8fd4bbdbc239073627632b7', '2', '2', '2019-07-20 16:39:21.000', '1', 'ddddddddddddddddddddddddddddddd', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('a3e5be72411c4563a3b25f75996f8cfe', '2', '2', '2019-07-20 16:59:23.000', '1', '发送到发送到', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('a47071235f2148b7889256d4ded3455a', '3', '1', '2019-07-20 17:36:41.667', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('a4c540c8547240db849cabc95c4115ff', '3', '2', '2019-07-20 17:05:55.000', '1', '25', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('a95bfe244cde4d6c85f8afa294dc2668', '3', '2', '2019-07-20 17:00:22.000', '1', '16', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('aae536911e8449f1955a042f576d7413', '3', '1', '2019-07-20 17:52:22.469', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('abfe62c767c84c7694bfd3696362413e', '3', '2', '2019-07-20 17:00:28.000', '1', '21', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('adb96262d9b245a284b2502cc95d8b17', '2', '2', '2019-07-20 16:59:24.000', '1', '1水电费s', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('adc166d9704c4ddebffad80fcb24f790', '2', '2', '2019-07-20 16:59:28.000', '1', '水电费水电费sd', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('ae46a1e1fcd64820b24f0b7290d1a7a9', '3', '2', '2019-07-20 17:10:19.000', '1', '23123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('af2e7fc8886d4f719618ab8d58af8438', '3', '2', '2019-07-20 17:10:18.000', '1', '1231', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b14735cf72714dcd9fa55e348bab0448', '2', '2', '2019-07-20 18:44:47.651', '1', '有什么可以帮您的？', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b6f83e606c2e4859a75b24074921b7e7', '2', '2', '2019-07-20 16:36:02.000', '1', '大道有人吗？', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b7898fae7c954ee78720b9e039e41987', '3', '1', '2019-07-20 18:04:17.681', '1', '我怎么处理33444', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b800dc9806b546e58b9eca1f1a866a37', '3', '2', '2019-07-20 17:28:01.598', '1', '2314', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b86f0ebae1134b4f843ad4f9874e2166', '2', '2', '2019-07-20 16:36:26.000', '1', '1111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b9bffe5ea1924082978a7fbb0cbe068d', '3', '1', '2019-07-20 17:34:00.088', '1', '我怎么处理', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('ba90b046082047d681c3cdb0b25215f5', '3', '2', '2019-07-20 17:00:19.000', '1', '14', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c3dfa5e757404caaa29d2252e9b3d6a1', '3', '2', '2019-07-20 17:00:27.000', '1', '20', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c4aaaa1ebc534dbea939c4bdd765e84e', '3', '2', '2019-07-20 17:28:03.372', '1', '31231', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c5ad07b7c2d34e8fb9309e056acca44e', '2', '2', '2019-07-20 16:23:25.000', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c8889f90e56347aa99d1a69b434235c3', '2', '2', '2019-07-20 18:44:51.583', '1', '有什么可以帮您的？', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c97615d5f5624630aaf01429e3207458', '2', '2', '2019-07-20 16:59:37.000', '1', '', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('d20e5798fad2422e91b36ab43d1fca7d', '3', '2', '2019-07-20 17:00:08.000', '1', '6', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('d3b2b6469b7440c2aff270bfccd9a383', '3', '2', '2019-07-20 17:00:26.000', '1', '19', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('d3bc6e164a0e4750bcaa938179ccc48b', '3', '2', '2019-07-20 17:00:10.000', '1', '7', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('d84b241eca1c41cdba57a406e8b95dd4', '2', '2', '2019-07-20 16:35:52.000', '1', '1213', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('da10177891d143beae49daf4da800225', '3', '1', '2019-07-20 17:57:15.916', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('dcce973e8ecd4226a370746f5a3e4cea', '3', '2', '2019-07-20 17:38:07.587', '1', '稍安勿躁，我们马上出警', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('dea69283b62a432bbabac4efa8cedfe5', '3', '2', '2019-07-20 17:00:17.000', '1', '12', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('debe203618984800a7604cfb57bf280c', '3', '2', '2019-07-20 17:28:02.544', '1', '4134', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('e3797de4c0904ccf9fcf58cde1f3bb3c', '2', '2', '2019-07-20 16:39:18.000', '1', '333333333333333', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('e589355363ca436aae956d271d17d10c', '3', '2', '2019-07-20 17:00:01.000', '1', '1', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('e765a821328440358e3408591dca4a47', '2', '2', '2019-07-20 16:59:27.000', '1', '胜多负少电风扇df', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('e7bb18e7f57a486591e43a5133c09408', '3', '2', '2019-07-20 17:00:18.000', '1', '13', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('f20b2362ffa14888a479a5bc74cab571', '2', '2', '2019-07-20 16:39:47.000', '1', '4', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('f20cd475d8334cfcba74c74ef5c6a19c', '2', '2', '2019-07-20 16:59:25.000', '1', '水电费s', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('f622562c56704531a307eb8e972c5847', '3', '2', '2019-07-20 17:00:02.000', '1', '2', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('fcd44462f9bd46e587271a45b41444f9', '2', '2', '2019-07-20 16:39:46.000', '1', '3', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('fe22287c2c79479c8d38ba207886b1b9', '3', '1', '2019-07-20 17:53:24.013', '1', '我怎么处理33', null);

-- ----------------------------
-- Table structure for tmp_videoalarm_info
-- ----------------------------
DROP TABLE IF EXISTS `tmp_videoalarm_info`;
CREATE TABLE `tmp_videoalarm_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `type` int(11) DEFAULT NULL COMMENT '类型  1 图文报警  2在线坐席上传',
  `onlineseats_id` int(11) DEFAULT NULL COMMENT '接警人员ID',
  `userName` varchar(64) DEFAULT NULL COMMENT '报警者姓名',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `area` varchar(200) DEFAULT NULL COMMENT '区域',
  `address` varchar(256) DEFAULT NULL COMMENT '地理位置',
  `wechat_id` varchar(64) DEFAULT NULL COMMENT '微信账号ID',
  `wechat_OpenId` varchar(64) DEFAULT NULL COMMENT '微信账号openID',
  `phone` varchar(12) DEFAULT NULL COMMENT '报警用户电话号码',
  `occurrence_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '警情发生时间',
  `description` varchar(5120) DEFAULT NULL COMMENT '警情描述',
  `alarm_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报警时间',
  `status` varchar(2) DEFAULT NULL COMMENT '警情状态 1已报警、2已受理、3已出警、4已关闭',
  `picture_url` varchar(2048) DEFAULT NULL COMMENT '图片Url地址 多个用，分割',
  `video_url` varchar(2048) DEFAULT NULL COMMENT '视频Url地址 多个用，分割',
  `audio_url` varchar(2048) DEFAULT NULL COMMENT '语音Url地址 多个用，分割',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tmp_videoalarm_info
-- ----------------------------

-- ----------------------------
-- Table structure for videoalarm_info
-- ----------------------------
DROP TABLE IF EXISTS `videoalarm_info`;
CREATE TABLE `videoalarm_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `type` int(11) DEFAULT NULL COMMENT '类型  1 图文报警  2在线坐席上传',
  `onlineseats_id` varchar(64) DEFAULT NULL COMMENT '接警人员ID',
  `userName` varchar(64) DEFAULT NULL COMMENT '报警者姓名',
  `idNumber` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `area` varchar(200) DEFAULT NULL COMMENT '区域',
  `latitude` varchar(64) DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(64) DEFAULT NULL COMMENT '经度',
  `address` varchar(256) DEFAULT NULL COMMENT '地理位置',
  `wechat_id` varchar(64) DEFAULT NULL COMMENT '微信账号ID',
  `wechat_OpenId` varchar(64) DEFAULT NULL COMMENT '微信账号openID',
  `phone` varchar(12) DEFAULT NULL COMMENT '报警用户电话号码',
  `occurrence_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '警情发生时间',
  `occurrence_address` varchar(256) DEFAULT NULL COMMENT '警情发生地址',
  `description` varchar(5120) DEFAULT NULL COMMENT '警情描述',
  `alarm_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报警时间',
  `status` varchar(2) DEFAULT NULL COMMENT '警情状态 1已报警、2已受理、3已出警、4已关闭',
  `picture_url` varchar(2048) DEFAULT NULL COMMENT '图片Url地址 多个用，分割',
  `video_url` varchar(2048) DEFAULT NULL COMMENT '视频Url地址 多个用，分割',
  `audio_url` varchar(2048) DEFAULT NULL COMMENT '语音Url地址 多个用，分割',
  `case_type` varchar(16) DEFAULT NULL COMMENT '案件类别',
  `case_level` varchar(16) DEFAULT NULL COMMENT '案件等级',
  `Sex` varchar(2) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别（男，女），默认 男',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of videoalarm_info
-- ----------------------------
INSERT INTO `videoalarm_info` VALUES ('2', '2', '3123', '22', '3123', '123', '11', '231', '313', null, null, '111111', '2019-07-20 10:14:57', '13212312312', '11111111111', '2019-07-20 10:14:57', '2', null, null, null, '1', '3', null);
INSERT INTO `videoalarm_info` VALUES ('3', '22', '3123', '123', '1231', '2312', '312', '3123', '1231', null, null, '2314', '2019-07-20 12:07:28', '123', '123', '2019-07-20 12:07:28', '1', null, null, null, null, null, null);
INSERT INTO `videoalarm_info` VALUES ('4', '2', '3123', '12', '34', '123', '123', '123', '123', '123', '123', '123', '2019-07-20 15:23:13', '123', '123123', '2019-07-20 15:23:13', '1', null, null, null, null, null, null);
