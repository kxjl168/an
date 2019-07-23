/*
Navicat MySQL Data Transfer

Source Server         : 192.168.100.126-kaifa-test
Source Server Version : 50726
Source Host           : 192.168.100.126:3306
Source Database       : video

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-23 15:45:05
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
INSERT INTO `manager` VALUES ('03d19bcabfb243428bef3fb88bf7b981', '13800000000', '7124eb7131c62a318211755d9a299983', '13800000000', '演示账号', null, null, null, '2019-07-21 22:41:47', '', null, '1');
INSERT INTO `manager` VALUES ('1', '13815429446', '7dacac90b853c7c57f40bd099a493a19', '13815429446', '管理员1', null, null, null, '2019-07-19 16:32:13', null, null, '1');
INSERT INTO `manager` VALUES ('eb4260703db94bfea41b7329f0431234', '13815429442', 'cc813d939508898a37eec617237d920e', '13815429442', '管理员2', null, null, null, '2019-07-19 17:04:13', '', null, '1');

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
INSERT INTO `sys_operlog` VALUES ('00b0335023f64521aabea1ceb0dd8a31', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:36:56', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"seatId\":\"542b8ff0548342a687ece17865437513\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"222222\",\"name\":\"接警人员1\",\"sex\":\"男\",\"idNo\":\"1001\",\"seatId\":\"542b8ff0548342a687ece17865437513\"}');
INSERT INTO `sys_operlog` VALUES ('0141b911dfde435398dac7aa34dbb6c7', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:42:46', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('01953739f25c4640a9503e5e9ba2f2ab', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:20:16', '0', '{\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"createTime\":\"2019-07-21 16:15:24.0\",\"uptimestamp\":\"2019-07-21 16:15:24.0\",\"dataState\":\"1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}  --> {\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}');
INSERT INTO `sys_operlog` VALUES ('02777da78d704ee3ab4e7f1416ac709c', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:27:17', '0', '--> {\"id\":\"\",\"name\":\"镇江1\",\"unitId\":\"aaabee7debd8441a8e865f3b4d3087a5\",\"des\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('08abb7373cbc492a969f95b3b171e378', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.UnitinfoController.saveOrUpdate', 'Unitinfo', '2019-07-19 22:38:20', '0', '--> {\"id\":\"\",\"name\":\"??????\",\"createTime\":\"2019-07-19 22:45:42\",\"uptimestamp\":\"2019-07-19 22:40:42\",\"dataState\":\"1\",\"des\":\"\",\"contactPerson\":\"zz\",\"contactPhone\":\"13815429446\",\"address\":\"????3333?\"}');
INSERT INTO `sys_operlog` VALUES ('08ec86c3ede44c90928c23f6694c7ad0', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:58:10', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"2\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('0b8f755074d2497dad0c02202fbfd3a3', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-20 10:14:57', '0', '--> {\"type\":2,\"onlineseatsId\":3123,\"area\":\"123\",\"latitude\":\"11\",\"longitude\":\"231\",\"address\":\"313\"}');
INSERT INTO `sys_operlog` VALUES ('11abdaa670674b7bb6071b8509ec1f3e', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 16:55:56', '0', '--> {\"id\":\"\",\"name\":\"CS\",\"areaId\":\"f751883ac69d4bb68a28a57aa6f10e09\"}');
INSERT INTO `sys_operlog` VALUES ('1385d06e319845249e60f5da934fce30', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 22:02:30', '0', '{\"id\":\"7e300761ab854300a2c1c9a31e2e60bb\",\"phone\":\"13815487433\",\"password\":\"222222\",\"name\":\"武三立\",\"createTime\":\"2019-07-21 21:46:06.0\",\"uptimestamp\":\"2019-07-21 21:46:06.0\",\"dataState\":\"1\",\"sex\":\"男\",\"idNo\":\"1002\",\"des\":\"11\",\"seatId\":\"b22440de071c4603999dd3f10a895bc8\"}  --> {\"id\":\"7e300761ab854300a2c1c9a31e2e60bb\",\"phone\":\"13815487433\",\"password\":\"\",\"name\":\"武三立\",\"sex\":\"男\",\"idNo\":\"1002\",\"des\":\"白班\",\"seatId\":\"542b8ff0548342a687ece17865437513\"}');
INSERT INTO `sys_operlog` VALUES ('1d781d1034cc4d09a0b891c25682ed7a', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:38:40', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('1d80dd08faef4283b34f53564ee2920f', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:27:42', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\"}');
INSERT INTO `sys_operlog` VALUES ('22baf8e2c2bb4f12bc826c3587a7fc47', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:27:29', '0', '--> {\"id\":\"\",\"name\":\"镇江2\",\"unitId\":\"aaabee7debd8441a8e865f3b4d3087a5\",\"des\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('2405e4f317da43c38b421991073d20cd', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:43:19', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('27bbc033d8a7463e862076c068cd0b45', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:15:24', '0', '--> {\"id\":\"\",\"name\":\"测试片区1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}');
INSERT INTO `sys_operlog` VALUES ('2f37b40618384034917f9368aa6758e6', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警手机信息', 'com.kxjl.video.controller.WebController.PhoneinfoController.saveOrUpdate', 'Phoneinfo', '2019-07-21 21:58:09', '0', '--> {\"id\":\"\",\"phone\":\"13812312311\",\"name\":\"手机2\",\"IMEI\":\"\",\"phoneType\":\"\",\"des\":\"1\",\"seatId\":\"542b8ff0548342a687ece17865437513\"}');
INSERT INTO `sys_operlog` VALUES ('33c4477e8c60476086887a000bc042e7', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改单位信息', 'com.kxjl.video.controller.WebController.UnitinfoController.saveOrUpdate', 'Unitinfo', '2019-07-21 12:02:05', '0', '--> {\"id\":\"\",\"name\":\"镇江公安\",\"createTime\":\"2019-07-22 12:00:55\",\"uptimestamp\":\"2019-07-21 13:00:55\",\"dataState\":\"1\",\"des\":\"\",\"contactPerson\":\"老吴\",\"contactPhone\":\"14827481721\",\"address\":\"镇江解放大道1-123号\"}');
INSERT INTO `sys_operlog` VALUES ('36030582b48e4c719a20ac4f7bdd581b', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警手机信息', 'com.kxjl.video.controller.WebController.PhoneinfoController.saveOrUpdate', 'Phoneinfo', '2019-07-21 21:55:50', '0', '{\"id\":\"84de8340676f42418d039a91d3b3526c\",\"phone\":\"13815491381\",\"name\":\"接警手机-小米8\",\"createTime\":\"2019-07-21 21:55:32.0\",\"uptimestamp\":\"2019-07-21 21:55:32.0\",\"dataState\":\"1\",\"IMEI\":\"112123111221\",\"phoneType\":\"小米8S\",\"des\":\"111\",\"seatId\":\"1f121966195047f5b6f8318550c3e7dc\"}  --> {\"id\":\"84de8340676f42418d039a91d3b3526c\",\"phone\":\"13815491381\",\"name\":\"接警手机-小米8\",\"IMEI\":\"112123111221\",\"phoneType\":\"小米8S\",\"des\":\"111\"}');
INSERT INTO `sys_operlog` VALUES ('3d30fb7f89ca425fa438764e059a8226', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 17:00:36', '0', '{\"id\":\"542b8ff0548342a687ece17865437513\",\"name\":\"CS-坐席1\",\"createTime\":\"2019-07-21 16:55:41.0\",\"uptimestamp\":\"2019-07-21 16:55:41.0\",\"dataState\":\"1\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}  --> {\"id\":\"542b8ff0548342a687ece17865437513\",\"name\":\"CS-坐席1\",\"areaId\":\"f751883ac69d4bb68a28a57aa6f10e09\"}');
INSERT INTO `sys_operlog` VALUES ('3dc2f37910464021a27be7b87c6f7ec9', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 21:05:05', '0', '{\"id\":\"1492b85f324c424e8aba3f5248c50539\",\"name\":\"c3\",\"createTime\":\"2019-07-21 17:01:01.0\",\"uptimestamp\":\"2019-07-21 21:04:59.0\",\"dataState\":\"1\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}  --> {\"id\":\"1492b85f324c424e8aba3f5248c50539\",\"name\":\"c3\",\"areaId\":\"64c13650ee4d4bc58550d55795c495e9\"}');
INSERT INTO `sys_operlog` VALUES ('4895fa5832184d90a739f6b15f2fa395', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:29:52', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"seatId\":\"542b8ff0548342a687ece17865437513\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\",\"seatId\":\"703b5f41e0234b2baa4d6a201a4e6cdc\"}');
INSERT INTO `sys_operlog` VALUES ('54c87351ca61421fbf4745acd2a33eb0', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 17:00:57', '0', '--> {\"id\":\"\",\"name\":\"c2\",\"areaId\":\"b69887ae73d04ad8b8f9268e21e5dbed\"}');
INSERT INTO `sys_operlog` VALUES ('561f3ad2eab54eb5b01c7e7e48ed0b6e', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:28:54', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\",\"seatId\":\"1f121966195047f5b6f8318550c3e7dc\"}');
INSERT INTO `sys_operlog` VALUES ('59a7ed13235a416e8ac85324d21d26e3', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改单位信息', 'com.kxjl.video.controller.WebController.UnitinfoController.saveOrUpdate', 'Unitinfo', '2019-07-21 13:08:48', '0', '{\"id\":\"480fe0d5ebb445c490e7c719c3898250\",\"name\":\"南京公安\",\"createTime\":\"2019-07-19 22:45:42.0\",\"uptimestamp\":\"2019-07-19 22:40:42.0\",\"dataState\":\"1\",\"des\":\"\",\"contactPerson\":\"王五\",\"contactPhone\":\"13815429446\",\"address\":\"雨花大道1010号\"}  --> {\"id\":\"480fe0d5ebb445c490e7c719c3898250\",\"name\":\"南京公安\",\"des\":\"饿肚肚多多多多多多多\",\"contactPerson\":\"王五\",\"contactPhone\":\"13815421111\",\"address\":\"雨花大道1010号\"}');
INSERT INTO `sys_operlog` VALUES ('59fa9f4ef898432ab7fac9c255650807', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 21:15:42', '0', '{\"id\":\"1492b85f324c424e8aba3f5248c50539\",\"name\":\"c3\",\"createTime\":\"2019-07-21 17:01:01.0\",\"uptimestamp\":\"2019-07-21 21:05:05.0\",\"dataState\":\"1\",\"areaId\":\"64c13650ee4d4bc58550d55795c495e9\"}  --> {\"id\":\"1492b85f324c424e8aba3f5248c50539\",\"name\":\"c3\",\"areaId\":\"f751883ac69d4bb68a28a57aa6f10e09\"}');
INSERT INTO `sys_operlog` VALUES ('5cebd7c75a7a4aa6b4199bcb50370bbe', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 17:01:05', '0', '--> {\"id\":\"\",\"name\":\"c4\",\"areaId\":\"f751883ac69d4bb68a28a57aa6f10e09\"}');
INSERT INTO `sys_operlog` VALUES ('5f7a2702502147acb0a7d4bb145625bf', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 21:04:59', '0', '{\"id\":\"1492b85f324c424e8aba3f5248c50539\",\"name\":\"c3\",\"createTime\":\"2019-07-21 17:01:01.0\",\"uptimestamp\":\"2019-07-21 17:01:01.0\",\"dataState\":\"1\",\"areaId\":\"b69887ae73d04ad8b8f9268e21e5dbed\"}  --> {\"id\":\"1492b85f324c424e8aba3f5248c50539\",\"name\":\"c3\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}');
INSERT INTO `sys_operlog` VALUES ('66164714fe944d50aa6cff90b83762f7', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:26:52', '0', '--> {\"id\":\"\",\"name\":\"测试片区2\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\",\"des\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('6623f18910ef4463afac76cd4951f7f9', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警用户信息', 'com.kxjl.video.controller.WebController.AlarmUserinfoController.saveOrUpdate', 'AlarmUserinfo', '2019-07-21 11:42:07', '0', '--> {\"id\":\"\",\"username\":\"阿三\",\"nickname\":\"三哥\",\"sex\":\"男\",\"idCard\":\"54912310123181231123\",\"address\":\"阿三老街222号1栋\",\"wechatId\":\"wer12\",\"wechatOpenId\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('71eceeababaf4f7fa6044f6fdaa92a55', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:45:28', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"222222\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"seatId\":\"542b8ff0548342a687ece17865437513\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\",\"sex\":\"男\",\"idNo\":\"1001\",\"des\":\"\",\"seatId\":\"542b8ff0548342a687ece17865437513\"}');
INSERT INTO `sys_operlog` VALUES ('75e969ab3bd0479a834d5c3542b66ae2', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:20:10', '0', '{\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"createTime\":\"2019-07-21 16:15:24.0\",\"uptimestamp\":\"2019-07-21 16:15:24.0\",\"dataState\":\"1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}  --> {\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}');
INSERT INTO `sys_operlog` VALUES ('7ba28792be6f403489f3a697fc7a946e', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:58:46', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"2\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('819376e6a86640baa1c231df4dfb8f3f', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 16:59:13', '0', '{\"id\":\"542b8ff0548342a687ece17865437513\",\"name\":\"CS-坐席1\",\"createTime\":\"2019-07-21 16:55:41.0\",\"uptimestamp\":\"2019-07-21 16:55:41.0\",\"dataState\":\"1\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}  --> {\"id\":\"542b8ff0548342a687ece17865437513\",\"name\":\"CS-坐席1\"}');
INSERT INTO `sys_operlog` VALUES ('8214b91a59f74444a4abea3bdd6038d2', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-20 10:14:32', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"1\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('8e08559325ce47639167a70a55a2457e', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 16:55:41', '0', '--> {\"id\":\"\",\"name\":\"CS-坐席1\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}');
INSERT INTO `sys_operlog` VALUES ('8e54290d088345f39ecde98f747d7f66', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 17:55:10', '0', '--> {\"id\":\"\",\"name\":\"c444\",\"areaId\":\"f751883ac69d4bb68a28a57aa6f10e09\"}');
INSERT INTO `sys_operlog` VALUES ('8ec72fed55d849d18ce4c66ab85e9c7f', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:16:40', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\"}');
INSERT INTO `sys_operlog` VALUES ('952a21239e0e408ba4ebadd9157e6cae', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '??????????', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-19 22:37:35', '0', '--> {\"id\":\"\",\"phone\":\"13815429446\",\"password\":\"111111\",\"sessionKey\":\"\",\"name\":\"111\",\"createTime\":\"2019-07-19 22:45:09\",\"uptimestamp\":\"2019-07-19 22:45:09\",\"createUser\":\"\",\"updateUser\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('9f028b21201a45b9989552344555032c', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:46:06', '0', '--> {\"id\":\"\",\"phone\":\"13815487433\",\"password\":\"222222\",\"name\":\"武三立\",\"sex\":\"男\",\"idNo\":\"1002\",\"des\":\"11\",\"seatId\":\"b22440de071c4603999dd3f10a895bc8\"}');
INSERT INTO `sys_operlog` VALUES ('a249c231066446cc9975a65bbe4923ab', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 22:03:10', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"des\":\"\",\"seatId\":\"542b8ff0548342a687ece17865437513\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\",\"sex\":\"男\",\"idNo\":\"1001\",\"des\":\"晚班\",\"seatId\":\"542b8ff0548342a687ece17865437513\"}');
INSERT INTO `sys_operlog` VALUES ('ab6854fea8ae4dcf92bd3d9cdd4766d0', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 22:37:28', '0', '--> {\"id\":\"\",\"name\":\"长沙1\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}');
INSERT INTO `sys_operlog` VALUES ('ae08be5059004f7fb3a80db6f9c5aeff', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 17:01:01', '0', '--> {\"id\":\"\",\"name\":\"c3\",\"areaId\":\"b69887ae73d04ad8b8f9268e21e5dbed\"}');
INSERT INTO `sys_operlog` VALUES ('b0c205df3ba140d8985bb150b7533e26', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-21 09:12:02', '0', '{\"id\":2,\"type\":2,\"onlineseats_id\":\"3123\",\"userName\":\"22\",\"idNumber\":\"3123\",\"area\":\"123\",\"latitude\":\"11\",\"longitude\":\"231\",\"address\":\"313\",\"phone\":\"111111\",\"occurrence_time\":\"2019-07-20 10:14:57.0\",\"occurrence_address\":\"13212312312\",\"description\":\"11111111111\",\"alarm_time\":\"2019-07-20 10:14:57.0\",\"status\":\"2\",\"case_type\":\"1\",\"case_level\":\"3\"}  --> {\"id\":2,\"type\":2,\"userName\":\"张三\",\"idNumber\":\"4220212398123812311\",\"area\":\"南京市玄武区\",\"latitude\":\"111.21\",\"longitude\":\"23.41\",\"address\":\"玄武湖风景区\"}');
INSERT INTO `sys_operlog` VALUES ('b1424fb510724c1987b2c44a00739790', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:34:33', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"seatId\":\"703b5f41e0234b2baa4d6a201a4e6cdc\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\",\"seatId\":\"542b8ff0548342a687ece17865437513\"}');
INSERT INTO `sys_operlog` VALUES ('b2b8bc4f84b94f7dbf625ab9705a0ec9', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-20 12:07:28', '0', '--> {\"type\":22,\"userName\":\"123\",\"idNumber\":\"1231\",\"area\":\"2312\",\"latitude\":\"312\",\"longitude\":\"3123\",\"address\":\"1231\"}');
INSERT INTO `sys_operlog` VALUES ('b5d1e59291fe420c974cad485823c427', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:27:08', '0', '--> {\"id\":\"\",\"name\":\"长沙片2\",\"unitId\":\"a9d7e202ca004df3a840db03f4e81c82\",\"des\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('bc2c7b3ddc87480e81f9d425a58bf6e4', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警手机信息', 'com.kxjl.video.controller.WebController.PhoneinfoController.saveOrUpdate', 'Phoneinfo', '2019-07-21 21:55:32', '0', '--> {\"id\":\"\",\"phone\":\"13815491381\",\"name\":\"接警手机-小米8\",\"IMEI\":\"112123111221\",\"phoneType\":\"小米8S\",\"des\":\"111\",\"seatId\":\"1f121966195047f5b6f8318550c3e7dc\"}');
INSERT INTO `sys_operlog` VALUES ('c4c53d95ac474a239317bed57bce9a23', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '????????', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 22:38:42', '0', '--> {\"type\":1,\"onlineseatsId\":111,\"area\":\"111\",\"latitude\":\"111\",\"longitude\":\"22\",\"address\":\"3123123\"}');
INSERT INTO `sys_operlog` VALUES ('c83009b22bc84e2494f08b63f593498c', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改单位信息', 'com.kxjl.video.controller.WebController.UnitinfoController.saveOrUpdate', 'Unitinfo', '2019-07-21 13:09:42', '0', '--> {\"id\":\"\",\"name\":\"长沙公安\",\"des\":\"哒哒哒哒哒哒多多多\",\"contactPerson\":\"李四\",\"contactPhone\":\"13981834121\",\"address\":\"长沙光辉大道10123号\"}');
INSERT INTO `sys_operlog` VALUES ('c866c21b43434e5abcac90dc0dc422e4', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:16:39', '0', '{\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"createTime\":\"2019-07-21 16:15:24.0\",\"uptimestamp\":\"2019-07-21 16:15:24.0\",\"dataState\":\"1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}  --> {\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}');
INSERT INTO `sys_operlog` VALUES ('ccd039b7489c478c906bbf3cc1c83a12', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:29:40', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"seatId\":\"1f121966195047f5b6f8318550c3e7dc\",\"unitId\":\"1f121966195047f5b6f8318550c3e7dc\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\",\"seatId\":\"542b8ff0548342a687ece17865437513\"}');
INSERT INTO `sys_operlog` VALUES ('cde0bce0121e416391bc73ee9fca9bde', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改接警人员信息', 'com.kxjl.video.controller.WebController.ReceivepoliceinfoController.saveOrUpdate', 'Receivepoliceinfo', '2019-07-21 21:15:28', '0', '{\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"111111\",\"sessionKey\":\"\",\"name\":\"接警人员1\",\"createTime\":\"2019-07-19 22:45:09.0\",\"uptimestamp\":\"2019-07-19 22:45:09.0\",\"createUser\":\"\",\"updateUser\":\"\",\"dataState\":\"1\",\"sex\":\"男\",\"idCard\":\"4231231211123\",\"idNo\":\"1001\",\"status\":\"1\"}  --> {\"id\":\"3123\",\"phone\":\"13815429446\",\"password\":\"\",\"name\":\"接警人员1\"}');
INSERT INTO `sys_operlog` VALUES ('d427ec138ac945ef820f24164f88be71', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改单位信息', 'com.kxjl.video.controller.WebController.UnitinfoController.saveOrUpdate', 'Unitinfo', '2019-07-21 10:37:18', '0', '{\"id\":\"480fe0d5ebb445c490e7c719c3898250\",\"name\":\"??????\",\"createTime\":\"2019-07-19 22:45:42.0\",\"uptimestamp\":\"2019-07-19 22:40:42.0\",\"dataState\":\"1\",\"des\":\"\",\"contactPerson\":\"zz\",\"contactPhone\":\"13815429446\",\"address\":\"????3333?\"}  --> {\"id\":\"480fe0d5ebb445c490e7c719c3898250\",\"name\":\"南京公安\",\"createTime\":\"2019-07-19 22:45:42.0\",\"uptimestamp\":\"2019-07-19 22:40:42.0\",\"dataState\":\"1\",\"des\":\"\",\"contactPerson\":\"王五\",\"contactPhone\":\"13815429446\",\"address\":\"雨花大道1010号\"}');
INSERT INTO `sys_operlog` VALUES ('d61e5277a9f847f8b2484fca4f96520d', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 17:00:44', '0', '{\"id\":\"b22440de071c4603999dd3f10a895bc8\",\"name\":\"CS\",\"createTime\":\"2019-07-21 16:55:56.0\",\"uptimestamp\":\"2019-07-21 16:55:56.0\",\"dataState\":\"1\",\"areaId\":\"f751883ac69d4bb68a28a57aa6f10e09\"}  --> {\"id\":\"b22440de071c4603999dd3f10a895bc8\",\"name\":\"CS\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}');
INSERT INTO `sys_operlog` VALUES ('dcb200fb724d462eb6d5c12c36e0c450', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:22:11', '0', '{\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"createTime\":\"2019-07-21 16:15:24.0\",\"uptimestamp\":\"2019-07-21 16:15:24.0\",\"dataState\":\"1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\"}  --> {\"id\":\"b69887ae73d04ad8b8f9268e21e5dbed\",\"name\":\"测试片区1\",\"unitId\":\"480fe0d5ebb445c490e7c719c3898250\",\"des\":\"1\"}');
INSERT INTO `sys_operlog` VALUES ('ee0a2e9d0c1346cbacb98db2d1b759fa', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改片区信息', 'com.kxjl.video.controller.WebController.AreainfoController.saveOrUpdate', 'Areainfo', '2019-07-21 16:27:02', '0', '--> {\"id\":\"\",\"name\":\"长沙片1\",\"unitId\":\"a9d7e202ca004df3a840db03f4e81c82\",\"des\":\"\"}');
INSERT INTO `sys_operlog` VALUES ('f2ede9d378f24a419f2959c131c4e1ce', '1', '13815429446', '1', '127.0.0.1', 'SaveOrUpdate', '保存修改报警事件', 'com.kxjl.video.controller.WebController.VideoalarmInfoController.saveOrUpdate', 'VideoalarmInfo', '2019-07-19 23:00:11', '0', '--> {\"type\":1,\"onlineseatsId\":1,\"area\":\"1\",\"latitude\":\"2\",\"longitude\":\"1\",\"address\":\"123\"}');
INSERT INTO `sys_operlog` VALUES ('f3076f22492a415c9805f27c9917b566', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 16:59:43', '0', '{\"id\":\"542b8ff0548342a687ece17865437513\",\"name\":\"CS-坐席1\",\"createTime\":\"2019-07-21 16:55:41.0\",\"uptimestamp\":\"2019-07-21 16:55:41.0\",\"dataState\":\"1\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}  --> {\"id\":\"542b8ff0548342a687ece17865437513\",\"name\":\"CS-坐席1\"}');
INSERT INTO `sys_operlog` VALUES ('f6d060ceb8dd464f944709ec35d8e661', '1', '13815429446', '管理员1', '127.0.0.1', 'SaveOrUpdate', '保存修改坐席信息', 'com.kxjl.video.controller.WebController.SeatinfoController.saveOrUpdate', 'Seatinfo', '2019-07-21 21:27:06', '0', '{\"id\":\"b22440de071c4603999dd3f10a895bc8\",\"name\":\"CS\",\"createTime\":\"2019-07-21 16:55:56.0\",\"uptimestamp\":\"2019-07-21 17:00:44.0\",\"dataState\":\"1\",\"areaId\":\"e95dfd3ff482402c9b43d9ebba8c2c8f\"}  --> {\"id\":\"b22440de071c4603999dd3f10a895bc8\",\"name\":\"CS\",\"areaId\":\"f751883ac69d4bb68a28a57aa6f10e09\"}');
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
INSERT INTO `sys_permission` VALUES ('1', '系统设置', '1', '/', 'sys', '0', null, '5', '1', 'fa fa-cogs');
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
INSERT INTO `sys_permission` VALUES ('menu_tseatinfo', '坐席管理', '2', '/manager/tseatinfo/manager', 'tseatinfo:view', 'jingwu', null, '6', '1', 'fa fa-cog');
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
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '1');
INSERT INTO `sys_role` VALUES ('superadmin', '超级管理员', '1');
INSERT INTO `sys_role` VALUES ('unitadmin', '单位管理员', '1');

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
INSERT INTO `sys_role_permission` VALUES ('02c1c8c43d9e45c69e430e2101cffd09', '1', 'menu_talarmuserinfo');
INSERT INTO `sys_role_permission` VALUES ('04f0db7d939e40879d4489251a31ddcf', '1', 'home_index');
INSERT INTO `sys_role_permission` VALUES ('06c6d5f2482641a8a268fd686a5b1206', '1', 'menu_treceivepoliceinfo');
INSERT INTO `sys_role_permission` VALUES ('0e378ac0b0f64cc9956e24312b35f416', 'superadmin', 'menu_tseatinfo');
INSERT INTO `sys_role_permission` VALUES ('1a49ff99bb084fe1897f4de2ecf55433', '1', '5');
INSERT INTO `sys_role_permission` VALUES ('1c9acf09e6b24a14b238d34c2768bea4', 'superadmin', 'menu_tconfig');
INSERT INTO `sys_role_permission` VALUES ('20c2c6a3dc124023985657507f42618f', 'superadmin', '5');
INSERT INTO `sys_role_permission` VALUES ('27e5e11287d9485cb45d637103233e4e', 'unitadmin', 'menu_tseatinfo');
INSERT INTO `sys_role_permission` VALUES ('33990d8ca5c74d5c837a9e4e7341bf48', 'unitadmin', 'menu_treceivepoliceinfo');
INSERT INTO `sys_role_permission` VALUES ('35f9554c18364c618f47e964c0011e61', 'superadmin', 'menu_tphoneinfo');
INSERT INTO `sys_role_permission` VALUES ('3953d1016dda4f0a94e01b48a862e366', 'superadmin', '102');
INSERT INTO `sys_role_permission` VALUES ('3a28c3c2cce44bd3865a7a425846a249', 'superadmin', 'menu_talarmuserinfo');
INSERT INTO `sys_role_permission` VALUES ('3c1e6e58fd2f4caa8be907ec5106dff1', 'superadmin', 'menu_tareainfoarea');
INSERT INTO `sys_role_permission` VALUES ('4cea3641673b42529d785c89574b779f', 'unitadmin', 'menu_videoalarminfo');
INSERT INTO `sys_role_permission` VALUES ('53ff6f700411419d829f6692add594d6', 'superadmin', 'home_index');
INSERT INTO `sys_role_permission` VALUES ('58d66cccbefd44b8b30eeb5793358b9c', 'superadmin', 'menu_tunitinfomanager');
INSERT INTO `sys_role_permission` VALUES ('5b6ecd1caee54157a5cc48534af44f26', '1', 'menu_tseatinfo');
INSERT INTO `sys_role_permission` VALUES ('5c2ef1d9fb7c4ea192ffb8fcb97e3159', '1', 'jingwu');
INSERT INTO `sys_role_permission` VALUES ('5cff51749bfb40e9909263d9a292ac16', 'unitadmin', 'menu_tunitinfo');
INSERT INTO `sys_role_permission` VALUES ('5d45c6012c0a44228bface8c73fdeae7', 'superadmin', 'jingwu');
INSERT INTO `sys_role_permission` VALUES ('5d984bd7f57e455f88453dbf236cbf6a', 'unitadmin', 'jingwu');
INSERT INTO `sys_role_permission` VALUES ('5ddbbc3b6af9491bbddb6fdf57ca5f5d', 'unitadmin', 'menu_tareainfo');
INSERT INTO `sys_role_permission` VALUES ('64aab64048fe4de0902ce746453a5dd2', 'superadmin', '1');
INSERT INTO `sys_role_permission` VALUES ('73b070abaeec4bcb89d5a957436952be', 'superadmin', 'menu_tunitinfo');
INSERT INTO `sys_role_permission` VALUES ('75727afd994340b293de6332cce24585', 'superadmin', 'menu_treceivepoliceinfo');
INSERT INTO `sys_role_permission` VALUES ('8d31a528684a416194a54b34dc0e6788', '1', '1');
INSERT INTO `sys_role_permission` VALUES ('96b05142fbba48a6a827fc8d2643fd71', '1', '102');
INSERT INTO `sys_role_permission` VALUES ('99a91a1b020d4065a7e2fe86c5cf2568', '1', 'menu_tphoneinfo');
INSERT INTO `sys_role_permission` VALUES ('9a9a833df1b74e928c42494b48e870eb', '1', 'menu_tunitinfo');
INSERT INTO `sys_role_permission` VALUES ('9af17bddad914e639eca5ae0e2606a10', 'superadmin', 'stastic');
INSERT INTO `sys_role_permission` VALUES ('9e7b2d5cf6f24452851828e9dfec15de', 'unitadmin', 'menu_talarmuserinfo');
INSERT INTO `sys_role_permission` VALUES ('9ec8ef8086994b958ff95c37af2767f9', '1', 'menu_videoalarminfo');
INSERT INTO `sys_role_permission` VALUES ('a19fa43309854050b3d1467bd1f7c9ac', 'unitadmin', 'menu_tphoneinfo');
INSERT INTO `sys_role_permission` VALUES ('bac643af3a004b5ea6ed7318a8740136', 'superadmin', 'menu_tvideoalarmtalkinfo');
INSERT INTO `sys_role_permission` VALUES ('bbc3f4ee0dcc4d61bce34f0397dbd8bb', 'superadmin', 'menu_sysdictinfo');
INSERT INTO `sys_role_permission` VALUES ('c54b8e46cb4c446f97983236d0037a22', 'superadmin', '100');
INSERT INTO `sys_role_permission` VALUES ('ca59c80fe183441b8ad775abf0caef7d', 'superadmin', 'menu_videoalarminfo');
INSERT INTO `sys_role_permission` VALUES ('ddd560b59d8d4413a70a8c87d847db53', '1', 'stastic');
INSERT INTO `sys_role_permission` VALUES ('e13d917889c0438083ec8636ae163e2a', '1', '100');
INSERT INTO `sys_role_permission` VALUES ('e38b05209459424886817014439b3e89', '1', 'menu_tareainfo');
INSERT INTO `sys_role_permission` VALUES ('eed809ad9b35402e992eeefe5e9f2956', 'superadmin', 'menu_tareainfo');
INSERT INTO `sys_role_permission` VALUES ('f90509262fcf454c86f1a0a7efda825c', 'superadmin', '20');
INSERT INTO `sys_role_permission` VALUES ('fa83c23ed52e46839d63d5aa99111e7e', 'superadmin', 'menu_auto');

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
INSERT INTO `sys_user_role` VALUES ('767381c5f6e24d0db7293d6a54d47530', 'superadmin', '1');
INSERT INTO `sys_user_role` VALUES ('aec61bd460d34235a686ea42f25d30bb', 'unitadmin', 'eb4260703db94bfea41b7329f0431234');
INSERT INTO `sys_user_role` VALUES ('ccedeb1cfba74ef29241e066fd2456f4', '1', '03d19bcabfb243428bef3fb88bf7b981');

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
  `regesterDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警用户信息';

-- ----------------------------
-- Records of t_alarm_userinfo
-- ----------------------------
INSERT INTO `t_alarm_userinfo` VALUES ('f1ca81c0a7ce4f7cae4cae352e55abcb', '阿三', '三哥', '男', '54912310123181231123', '阿三老街222号1栋', '13154912311', 'wer12', '', '2019-07-21 11:52:56');

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
  `Des` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='片区信息';

-- ----------------------------
-- Records of t_areainfo
-- ----------------------------
INSERT INTO `t_areainfo` VALUES ('64c13650ee4d4bc58550d55795c495e9', '镇江1', '2019-07-21 16:27:17', '2019-07-21 16:27:17', '1', 'aaabee7debd8441a8e865f3b4d3087a5', null);
INSERT INTO `t_areainfo` VALUES ('77ce5a08f8d04e7292adc1eb0b82888e', '镇江2', '2019-07-21 16:27:29', '2019-07-21 16:27:29', '1', 'aaabee7debd8441a8e865f3b4d3087a5', null);
INSERT INTO `t_areainfo` VALUES ('b69887ae73d04ad8b8f9268e21e5dbed', '测试片区1', '2019-07-21 16:15:24', '2019-07-21 16:22:11', '1', '480fe0d5ebb445c490e7c719c3898250', '1');
INSERT INTO `t_areainfo` VALUES ('e95dfd3ff482402c9b43d9ebba8c2c8f', '长沙片1', '2019-07-21 16:27:02', '2019-07-21 16:27:02', '1', 'a9d7e202ca004df3a840db03f4e81c82', null);
INSERT INTO `t_areainfo` VALUES ('f6f2838c520042adb21ce947731d666d', '长沙片2', '2019-07-21 16:27:08', '2019-07-21 16:27:08', '1', 'a9d7e202ca004df3a840db03f4e81c82', null);
INSERT INTO `t_areainfo` VALUES ('f751883ac69d4bb68a28a57aa6f10e09', '测试片区2', '2019-07-21 16:26:52', '2019-07-21 16:26:52', '1', '480fe0d5ebb445c490e7c719c3898250', null);

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
INSERT INTO `t_phoneinfo` VALUES ('684a1bd26ab0453aabb26e8e8a54adf7', '13812312311', '手机2', '2019-07-21 21:58:09', '2019-07-21 21:58:09', '1', '', '', '1', '542b8ff0548342a687ece17865437513', null, null);
INSERT INTO `t_phoneinfo` VALUES ('84de8340676f42418d039a91d3b3526c', '13815491381', '接警手机-小米8', '2019-07-21 21:55:32', '2019-07-21 21:55:32', '1', '112123111221', '小米8S', '111', '1f121966195047f5b6f8318550c3e7dc', null, null);

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
INSERT INTO `t_receivepoliceinfo` VALUES ('3123', '13815429446', '', '', '接警人员1', '2019-07-19 22:45:09', '2019-07-19 22:45:09', '', '', '1', '男', '4231231211123', '1001', '晚班', null, '542b8ff0548342a687ece17865437513', '1f121966195047f5b6f8318550c3e7dc', '1');
INSERT INTO `t_receivepoliceinfo` VALUES ('7e300761ab854300a2c1c9a31e2e60bb', '13815487433', '', null, '武三立', '2019-07-21 21:46:06', '2019-07-21 21:46:06', null, null, '1', '男', null, '1002', '白班', null, '542b8ff0548342a687ece17865437513', null, null);

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
  `PersonNum` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='坐席信息';

-- ----------------------------
-- Records of t_seatinfo
-- ----------------------------
INSERT INTO `t_seatinfo` VALUES ('1492b85f324c424e8aba3f5248c50539', 'c3', '2019-07-21 17:01:01', '2019-07-21 21:15:42', '1', 'f751883ac69d4bb68a28a57aa6f10e09', null, null);
INSERT INTO `t_seatinfo` VALUES ('1f121966195047f5b6f8318550c3e7dc', 'c2', '2019-07-21 17:00:57', '2019-07-21 17:00:57', '1', 'b69887ae73d04ad8b8f9268e21e5dbed', null, null);
INSERT INTO `t_seatinfo` VALUES ('542b8ff0548342a687ece17865437513', 'CS-坐席1', '2019-07-21 16:55:41', '2019-07-21 17:00:36', '1', 'f751883ac69d4bb68a28a57aa6f10e09', null, null);
INSERT INTO `t_seatinfo` VALUES ('703b5f41e0234b2baa4d6a201a4e6cdc', 'c444', '2019-07-21 17:55:10', '2019-07-21 17:55:10', '1', 'f751883ac69d4bb68a28a57aa6f10e09', null, null);
INSERT INTO `t_seatinfo` VALUES ('7d2bf147d8934ad68afe9faf0fbc34fa', 'c4', '2019-07-21 17:01:05', '2019-07-21 17:01:05', '1', 'f751883ac69d4bb68a28a57aa6f10e09', null, null);
INSERT INTO `t_seatinfo` VALUES ('b22440de071c4603999dd3f10a895bc8', 'CS', '2019-07-21 16:55:56', '2019-07-21 21:27:06', '1', 'f751883ac69d4bb68a28a57aa6f10e09', null, null);
INSERT INTO `t_seatinfo` VALUES ('c8e94c03beed495582fc81e7a907868d', '长沙1', '2019-07-21 22:37:28', '2019-07-21 22:37:28', '1', 'e95dfd3ff482402c9b43d9ebba8c2c8f', null, null);

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
INSERT INTO `t_unitinfo` VALUES ('480fe0d5ebb445c490e7c719c3898250', '南京公安', '2019-07-19 22:45:42', '2019-07-21 13:08:48', '1', '饿肚肚多多多多多多多', '王五', '13815421111', '雨花大道1010号');
INSERT INTO `t_unitinfo` VALUES ('a9d7e202ca004df3a840db03f4e81c82', '长沙公安', '2019-07-21 13:09:42', '2019-07-21 13:09:42', '1', '哒哒哒哒哒哒多多多', '李四', '13981834121', '长沙光辉大道10123号');
INSERT INTO `t_unitinfo` VALUES ('aaabee7debd8441a8e865f3b4d3087a5', '镇江公安', '2019-07-22 12:00:55', '2019-07-21 13:00:55', '1', '', '老吴', '14827481721', '镇江解放大道1-123号');

-- ----------------------------
-- Table structure for t_unitinfo_manager
-- ----------------------------
DROP TABLE IF EXISTS `t_unitinfo_manager`;
CREATE TABLE `t_unitinfo_manager` (
  `id` varchar(64) NOT NULL,
  `UnitId` varchar(64) NOT NULL COMMENT '单位id',
  `ManagerId` varchar(64) NOT NULL COMMENT 'manager id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位超级管理员 1对多';

-- ----------------------------
-- Records of t_unitinfo_manager
-- ----------------------------
INSERT INTO `t_unitinfo_manager` VALUES ('3a3acb7ed69b4e868c2deb37285bf091', 'aaabee7debd8441a8e865f3b4d3087a5', 'eb4260703db94bfea41b7329f0431234');
INSERT INTO `t_unitinfo_manager` VALUES ('49b66fa0db2b4c5db4924ad3e7c2f0a6', 'a9d7e202ca004df3a840db03f4e81c82', '03d19bcabfb243428bef3fb88bf7b981');
INSERT INTO `t_unitinfo_manager` VALUES ('6059165379aa418787aad37465406dda', '480fe0d5ebb445c490e7c719c3898250', '1');
INSERT INTO `t_unitinfo_manager` VALUES ('7b47078b4de0451d82ea3d9614bd214a', 'a9d7e202ca004df3a840db03f4e81c82', '1');
INSERT INTO `t_unitinfo_manager` VALUES ('c0ad3b76301f4d0b91cd47c990a3acc0', '480fe0d5ebb445c490e7c719c3898250', 'eb4260703db94bfea41b7329f0431234');
INSERT INTO `t_unitinfo_manager` VALUES ('f8a7119eeba5404bbf2acd77c52c0ed0', '480fe0d5ebb445c490e7c719c3898250', '03d19bcabfb243428bef3fb88bf7b981');

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
INSERT INTO `t_videoalarm_talkinfo` VALUES ('0a340fa9705f47ae853dc4212d0321a4', '2', '2', '2019-07-23 13:32:56.964', '1', '12312', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('0a481199ef4f4e499954c4d533ea152a', '2', '2', '2019-07-23 13:38:00.852', '1', '1', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('108f36c2641f47ff820dce32d8c22e6c', '2', '2', '2019-07-20 18:29:23.617', '1', '稍安勿躁，我们马上出动', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('10f59d1bed3c45edb220ab8fbe8fa3d9', '2', '2', '2019-07-23 13:33:31.012', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1347c3c4f486480693cefafe294b6ee0', '3', '2', '2019-07-20 17:03:44.000', '1', '23', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('13701575910e452da214551a18092ec5', '2', '2', '2019-07-20 18:13:38.035', '1', '111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1454d28cbe644a8e89859b0d4f3f248d', '3', '2', '2019-07-20 17:00:15.000', '1', '10', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('15a4c153384e43fba8645def4502b784', '3', '2', '2019-07-20 17:00:05.000', '1', '4', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('16b17aee394d4c9384417de43e5c267c', '2', '2', '2019-07-20 16:15:10.000', '1', '1111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('188a22115fd84f29b5c19ddc1f2c9abc', '3', '2', '2019-07-20 17:07:14.000', '1', '25', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1e219fe273ca4b26b0748f0d2a22bdc8', '2', '1', '2019-07-20 17:46:35.389', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('1f255dc0a07d49d09873ec46fe60c0fb', '3', '2', '2019-07-20 17:03:45.000', '1', '24', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('20d68c1c3a1a4feab440b9046eb2fe14', '2', '2', '2019-07-20 16:35:58.000', '1', '你好！！！', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('24bf82d53cf44e72bf27ea7b8f14ef89', '2', '2', '2019-07-23 13:34:04.652', '1', '123', null);
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
INSERT INTO `t_videoalarm_talkinfo` VALUES ('49d6716dc24c4fc18ba510d886ddc5e2', '2', '2', '2019-07-23 13:34:07.819', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('4f289dc118d24325a7e79a0a5040a9d5', '2', '2', '2019-07-23 13:34:06.420', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('54b3db4df6c44070a3f7511d65737109', '2', '2', '2019-07-20 16:59:25.000', '1', '', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('56936e83c8694d76af07fea98cb86b82', '3', '2', '2019-07-20 17:07:16.000', '1', '26', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('58bad0fc35c14631bbf90a2204c48d5c', '2', '2', '2019-07-20 16:59:30.000', '1', '12312 胜多负少d', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('5be2834384d34543bcf584162c04ae15', '3', '1', '2019-07-20 17:34:36.658', '1', '我怎么处理111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('5d6e04289ec24b58a3f9b8ace0e7c95f', '3', '1', '2019-07-20 17:30:44.124', '1', '你好，我发现有人在偷车，要报警', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('60a5ccc438a144da96db05b8c77ab363', '2', '2', '2019-07-20 16:16:00.000', '1', '2123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('6f1d3b61789d4981887953a2dc3b4b91', '2', '2', '2019-07-20 16:36:04.000', '1', '胜多负少d', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('6f28473dcb204417bcee7ea7faae12c9', '3', '2', '2019-07-21 08:14:00.635', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('6f4b0e18ec71451f96dc04f6244d9c37', '2', '2', '2019-07-20 16:39:16.000', '1', '1213', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('71b0b6b799c24fcaa9325ee42f3ff3ac', '2', '2', '2019-07-20 18:11:21.989', '1', '', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('726ec95714e441f6afb5f76967d0f9be', '3', '2', '2019-07-20 17:28:00.936', '1', '1231', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('7681cf79941f4c49803623181653c3b8', '2', '2', '2019-07-20 16:59:38.000', '1', '1231', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('79cd66494c224d639f4387288f594fd0', '2', '2', '2019-07-20 16:39:45.000', '1', '2', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('833b8e79351b4c4ebc09e00a1253472b', '3', '2', '2019-07-20 17:00:29.000', '1', '22', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('8680028564f54ba6bcf1adae076566ef', '2', '2', '2019-07-23 14:55:02.443', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('871000bc825e42ddbe44cc6862c7a2f1', '3', '1', '2019-07-20 17:52:34.203', '1', '我怎么处理33', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('871f8f23b98a4a7a816b98e511b57d6c', '3', '2', '2019-07-20 17:00:25.000', '1', '18', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('89cf4bb0e6654c5aa767bd1bbcf22e59', '2', '2', '2019-07-23 13:34:03.068', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('8b0d8a88b54b4619acb0e13660d10648', '3', '2', '2019-07-20 17:08:33.000', '1', '11', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('8cacfcb73331464d8add8828997e94e6', '3', '2', '2019-07-20 17:00:23.000', '1', '17', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('8d0ef8590f604f1dab9e75e175377318', '3', '2', '2019-07-20 17:05:53.000', '1', '24', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('90c3219dced2491fa4beac46ff780c28', '3', '2', '2019-07-20 17:00:07.000', '1', '5', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('98ca3d15350744b5a3ab6cf0844d6890', '2', '2', '2019-07-23 13:34:05.379', '1', '23', null);
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
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b4f5964503ba464984d6988dbe5f7e45', '3', '1', '2019-07-23 15:26:21.035', '1', '好的，明白了', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b6f83e606c2e4859a75b24074921b7e7', '2', '2', '2019-07-20 16:36:02.000', '1', '大道有人吗？', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b7898fae7c954ee78720b9e039e41987', '3', '1', '2019-07-20 18:04:17.681', '1', '我怎么处理33444', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b800dc9806b546e58b9eca1f1a866a37', '3', '2', '2019-07-20 17:28:01.598', '1', '2314', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b86f0ebae1134b4f843ad4f9874e2166', '2', '2', '2019-07-20 16:36:26.000', '1', '1111', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('b9bffe5ea1924082978a7fbb0cbe068d', '3', '1', '2019-07-20 17:34:00.088', '1', '我怎么处理', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('ba90b046082047d681c3cdb0b25215f5', '3', '2', '2019-07-20 17:00:19.000', '1', '14', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('bd1b53d0b02349b895f77293ab7574e4', '2', '1', '2019-07-23 14:55:49.393', '1', '好的，明白了', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c3938545984340359f54cf62ebf87ab3', '2', '2', '2019-07-23 13:34:03.884', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c3dfa5e757404caaa29d2252e9b3d6a1', '3', '2', '2019-07-20 17:00:27.000', '1', '20', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c4aaaa1ebc534dbea939c4bdd765e84e', '3', '2', '2019-07-20 17:28:03.372', '1', '31231', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c5ad07b7c2d34e8fb9309e056acca44e', '2', '2', '2019-07-20 16:23:25.000', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c8889f90e56347aa99d1a69b434235c3', '2', '2', '2019-07-20 18:44:51.583', '1', '有什么可以帮您的？', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('c97615d5f5624630aaf01429e3207458', '2', '2', '2019-07-20 16:59:37.000', '1', '', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('d20e5798fad2422e91b36ab43d1fca7d', '3', '2', '2019-07-20 17:00:08.000', '1', '6', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('d25b033ed21944308329de9f2de49f15', '2', '2', '2019-07-23 14:55:03.774', '1', '2', null);
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
INSERT INTO `t_videoalarm_talkinfo` VALUES ('ebf30ea3a65d43e8b9ba81a9c74a6558', '2', '2', '2019-07-23 13:32:54.518', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('f20b2362ffa14888a479a5bc74cab571', '2', '2', '2019-07-20 16:39:47.000', '1', '4', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('f20cd475d8334cfcba74c74ef5c6a19c', '2', '2', '2019-07-20 16:59:25.000', '1', '水电费s', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('f350e0f5650f49e4b23d227a6148bef9', '2', '2', '2019-07-23 13:33:31.964', '1', '123', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('f622562c56704531a307eb8e972c5847', '3', '2', '2019-07-20 17:00:02.000', '1', '2', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('fcd44462f9bd46e587271a45b41444f9', '2', '2', '2019-07-20 16:39:46.000', '1', '3', null);
INSERT INTO `t_videoalarm_talkinfo` VALUES ('fe22287c2c79479c8d38ba207886b1b9', '3', '1', '2019-07-20 17:53:24.013', '1', '我怎么处理33', null);

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
  `hasNewInfo` varchar(5) DEFAULT '0' COMMENT '新消息  ,   1:有  ，0无',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of videoalarm_info
-- ----------------------------
INSERT INTO `videoalarm_info` VALUES ('2', '2', '3123', '阿三', '54912310123181231123', '南京市玄武区', '111.21', '23.41', '玄武湖风景区', null, null, '13815429446', '2019-07-20 10:14:57', '13212312312', '11111111111', '2019-07-20 10:14:57', '2', 'https://avatar-static.segmentfault.com/229/023/2290239497-56776b67a4fdd_big64,https://avatar-static.segmentfault.com/229/023/2290239497-56776b67a4fdd_big64', 'http://vjs.zencdn.net/v/oceans.webm,%3D&vkey=A7d711ab1fc13585b62bccd83f92993bb', 'http://127.0.0.1:7779/an/img/an/audio.mp3,http://127.0.0.1:7779/an/img/an/audio.mp3', '1', '3', null, '0');
INSERT INTO `videoalarm_info` VALUES ('3', '22', '3123', '123', '1231', '2312', '312', '3123', '1231', null, null, '13815421231', '2019-07-20 12:07:28', '123', '123', '2019-07-20 12:07:28', '1', 'https://avatar-static.segmentfault.com/229/023/2290239497-56776b67a4fdd_big64,https://avatar-static.segmentfault.com/229/023/2290239497-56776b67a4fdd_big64', 'http://valipl.cp31.ott.cibntv.net/6572dfa0d303c718709616fa9/03000801005CDE290717FC8003E8805D2F36AA-2E35-46AF-B1D0-871C0E422BAA.mp4?ccode=0502&duration=7820&expire=18000&psid=59bbeccc44d1cdc7727f2fd44ace4dca&ups_client_netip=b7d00516&ups_ts=1563679956&ups_userid=&utid=2tnWErEO9xkCAbfODKFiWHNT&vid=XNDI3NzU3NTIxNg%3D%3D&vkey=A7d711ab1fc13585b62bccd83f92993bb', 'http://127.0.0.1:7779/an/img/an/audio.mp3,http://127.0.0.1:7779/an/img/an/audio.mp3', null, null, null, '0');
INSERT INTO `videoalarm_info` VALUES ('4', '2', '3123', '阿三', '54912310123181231123', '123', '123', '123', '123', '123', '123', '138153328254', '2019-07-20 15:23:13', '123', '123123', '2019-07-20 15:23:13', '1', 'https://avatar-static.segmentfault.com/229/023/2290239497-56776b67a4fdd_big64,https://avatar-static.segmentfault.com/229/023/2290239497-56776b67a4fdd_big64', 'http://valipl.cp31.ott.cibntv.net/65727228ce04b71ea73ea379c/03000801005D0B285FD1322003E880AF718FA6-037A-4A74-99A5-97BCB05E988A.mp4?ccode=0502&duration=327&expire=18000&psid=29f1a90f6bfa487417c78efd1866bbf6&ups_client_netip=b7d00516&ups_ts=1563680145&ups_userid=&utid=2tnWErEO9xkCAbfODKFiWHNT&vid=XNDI3NTk4NjYzMg%3D%3D&vkey=A497aa0a90041ce920f22987e5ff00cf6,%3D&vkey=A497aa0a90041ce920f22987e5ff00cf6', 'http://127.0.0.1:7779/an/img/an/audio.mp3,http://127.0.0.1:7779/an/img/an/audio.mp3', null, null, null, '0');
