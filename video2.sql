/*
Navicat MySQL Data Transfer

Source Server         : 192.168.100.126-kaifa-test
Source Server Version : 50726
Source Host           : 192.168.100.126:3306
Source Database       : video

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-19 17:04:53
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
  `type` int(11) DEFAULT NULL COMMENT '类型   1案件类型  2案件等级',
  `dict_name` varchar(16) DEFAULT NULL COMMENT '字典名称',
  `dict_value` varchar(128) DEFAULT NULL COMMENT '字典值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

-- ----------------------------
-- Records of sys_dict_info
-- ----------------------------

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
INSERT INTO `sys_role_permission` VALUES ('19064448', '1', 'order_manager');
INSERT INTO `sys_role_permission` VALUES ('19064449', '1', 'order_create');
INSERT INTO `sys_role_permission` VALUES ('19064450', '1', 'order_create_money');
INSERT INTO `sys_role_permission` VALUES ('19064451', '1', 'order_create_query');
INSERT INTO `sys_role_permission` VALUES ('19064452', '1', 'order_to_assgin');
INSERT INTO `sys_role_permission` VALUES ('19064453', '1', 'order_to_assgin_money');
INSERT INTO `sys_role_permission` VALUES ('19064454', '1', 'order_to_assgin_query');
INSERT INTO `sys_role_permission` VALUES ('19064455', '1', 'order_doing');
INSERT INTO `sys_role_permission` VALUES ('19064456', '1', 'order_doing_money');
INSERT INTO `sys_role_permission` VALUES ('19064457', '1', 'menu_torderreview');
INSERT INTO `sys_role_permission` VALUES ('19064458', '1', 'order_review_query');
INSERT INTO `sys_role_permission` VALUES ('19064459', '1', 'menu_torderreview_money');
INSERT INTO `sys_role_permission` VALUES ('19064460', '1', 'order_huifang');
INSERT INTO `sys_role_permission` VALUES ('19064461', '1', 'menu_orderCompleted');
INSERT INTO `sys_role_permission` VALUES ('19064462', '1', 'order_log');
INSERT INTO `sys_role_permission` VALUES ('19064463', '1', 'order_delete_list');
INSERT INTO `sys_role_permission` VALUES ('19064464', '1', 'order_delete_list_del');
INSERT INTO `sys_role_permission` VALUES ('19064465', '1', 'order_delete_list_query');
INSERT INTO `sys_role_permission` VALUES ('19064466', '1', 'order_delete_list_refresh');
INSERT INTO `sys_role_permission` VALUES ('19064467', '1', 'all_query');
INSERT INTO `sys_role_permission` VALUES ('19064468', '1', 'menu_tuserbackquestion');
INSERT INTO `sys_role_permission` VALUES ('19064469', '1', '1');
INSERT INTO `sys_role_permission` VALUES ('19064470', '1', '5');
INSERT INTO `sys_role_permission` VALUES ('19064471', '1', '100');
INSERT INTO `sys_role_permission` VALUES ('19064472', '1', '102');
INSERT INTO `sys_role_permission` VALUES ('19064473', '1', '20');
INSERT INTO `sys_role_permission` VALUES ('19064474', '1', 'm_user_manager');
INSERT INTO `sys_role_permission` VALUES ('19064475', '1', 'menu_tuserinfo');
INSERT INTO `sys_role_permission` VALUES ('19064476', '1', 'menu_tuserinfo_q');
INSERT INTO `sys_role_permission` VALUES ('19064477', '1', 'menu_tuserinfo_query');
INSERT INTO `sys_role_permission` VALUES ('19064478', '1', 'menu_tuserinfo_changeType');
INSERT INTO `sys_role_permission` VALUES ('19064479', '1', 'menu_tuserinfo_delete');
INSERT INTO `sys_role_permission` VALUES ('19064480', '1', 'menu_tuserinfo_hei');
INSERT INTO `sys_role_permission` VALUES ('19064481', '1', 'user_to_auidt');
INSERT INTO `sys_role_permission` VALUES ('19064482', '1', 'usertype_change');
INSERT INTO `sys_role_permission` VALUES ('19064483', '1', 'price_locksmith_build');
INSERT INTO `sys_role_permission` VALUES ('19064484', '1', 'price_locksmith_other');
INSERT INTO `sys_role_permission` VALUES ('19064485', '1', 'blacklist');
INSERT INTO `sys_role_permission` VALUES ('19064486', '1', 'score_manager');
INSERT INTO `sys_role_permission` VALUES ('19064487', '1', 'usertype_change');
INSERT INTO `sys_role_permission` VALUES ('19064488', '1', 'lock_company');
INSERT INTO `sys_role_permission` VALUES ('19064489', '1', 'menu_tlockcompany');
INSERT INTO `sys_role_permission` VALUES ('19064490', '1', 'menu_lockCompanyStatistics');
INSERT INTO `sys_role_permission` VALUES ('19064491', '1', 'menu_tlockproductinfo');
INSERT INTO `sys_role_permission` VALUES ('19064492', '1', 'price_lock_company_build');
INSERT INTO `sys_role_permission` VALUES ('19064493', '1', 'menu_tlockcompanymanager');
INSERT INTO `sys_role_permission` VALUES ('19064494', '1', 'menu_lockCompanyBillingStatistics');
INSERT INTO `sys_role_permission` VALUES ('19064495', '1', 'menu_afterService');
INSERT INTO `sys_role_permission` VALUES ('19064496', '1', 'order_after_service');
INSERT INTO `sys_role_permission` VALUES ('19064497', '1', 'price_manager');
INSERT INTO `sys_role_permission` VALUES ('19064498', '1', 'user_money');
INSERT INTO `sys_role_permission` VALUES ('19064499', '1', 'menu_lock_money_detail');
INSERT INTO `sys_role_permission` VALUES ('19064500', '1', 'userDeposit');
INSERT INTO `sys_role_permission` VALUES ('19064501', '1', 'userDeposit_money');
INSERT INTO `sys_role_permission` VALUES ('19064502', '1', 'enterprise_pay');
INSERT INTO `sys_role_permission` VALUES ('19064503', '1', 'btn_companypay_query');
INSERT INTO `sys_role_permission` VALUES ('19064504', '1', 'btn_companypay_pay');
INSERT INTO `sys_role_permission` VALUES ('19064505', '1', 'menu_torderinfomoneyquestion');
INSERT INTO `sys_role_permission` VALUES ('19064506', '1', 'stastic_manager');
INSERT INTO `sys_role_permission` VALUES ('19064507', '1', 'mouth_order_count');
INSERT INTO `sys_role_permission` VALUES ('19064508', '1', 'province_order_count');
INSERT INTO `sys_role_permission` VALUES ('19064509', '1', 'turnover');
INSERT INTO `sys_role_permission` VALUES ('19064510', '1', 'lockMouthOrderCount');
INSERT INTO `sys_role_permission` VALUES ('19064511', '1', 'customer_service');
INSERT INTO `sys_role_permission` VALUES ('19064512', '1', 'menu_enterprise_statistics');
INSERT INTO `sys_role_permission` VALUES ('19064513', '1', 'suggest_manager');
INSERT INTO `sys_role_permission` VALUES ('19064514', '1', 'menu_tuseropinion');
INSERT INTO `sys_role_permission` VALUES ('19064515', '1', 'menu_tcustomercomplain');
INSERT INTO `sys_role_permission` VALUES ('19064516', '1', 'menu_enterprisecomplain');
INSERT INTO `sys_role_permission` VALUES ('19064517', '1', 'menu_tcusopinion');
INSERT INTO `sys_role_permission` VALUES ('19064518', '1', 'menu_customer');
INSERT INTO `sys_role_permission` VALUES ('19064519', '1', 'menu_gradeMoney');
INSERT INTO `sys_role_permission` VALUES ('19064520', '1', 'menu_custormAssessQ');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警用户信息';

-- ----------------------------
-- Records of t_alarm_userinfo
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
) ENGINE=InnoDB AUTO_INCREMENT=345 DEFAULT CHARSET=utf8 COMMENT='高德地图城市编码表';

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
) ENGINE=InnoDB AUTO_INCREMENT=2854 DEFAULT CHARSET=utf8 COMMENT='高德地图区域编码表';

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='高德地图省份编码表';

-- ----------------------------
-- Records of t_area_province
-- ----------------------------

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
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `msgType` varchar(2) DEFAULT NULL COMMENT '消息类型 1:文本',
  `msgContent` varchar(2048) DEFAULT NULL COMMENT '消息内容',
  `fileUrl` varchar(512) DEFAULT NULL COMMENT '非文本文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警事件聊天记录';

-- ----------------------------
-- Records of t_videoalarm_talkinfo
-- ----------------------------

-- ----------------------------
-- Table structure for videoalarm_info
-- ----------------------------
DROP TABLE IF EXISTS `videoalarm_info`;
CREATE TABLE `videoalarm_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `type` int(11) DEFAULT NULL COMMENT '类型  1 图文报警  2在线坐席上传',
  `onlineseats_id` int(11) DEFAULT NULL COMMENT '接警人员ID',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of videoalarm_info
-- ----------------------------
