CREATE TABLE `t_userinfo` (
  
  `Id` varchar(64) NOT NULL COMMENT '接警人员id(主键)，不能为0',
  `Phone` varchar(11) NOT NULL COMMENT '接警人员手机号，也是登陆接警人员名（唯一约束）',
  `Password` varchar(50) NOT NULL COMMENT '接警人员密码',
  `SessionKey` varchar(50) DEFAULT NULL COMMENT '会话id',
  `Name` varchar(50) NOT NULL COMMENT '名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
  `CreateUser` varchar(64) DEFAULT NULL COMMENT '创建人（外键manager）',
  `UpdateUser` varchar(64) DEFAULT NULL COMMENT '上次更新人（外键manager）',
  
  `DataState` varchar(2) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',

  `Sex` varchar(2) DEFAULT NULL COMMENT '性别（男，女），默认 男',
  `IdCard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `IdNo` varchar(20) DEFAULT NULL COMMENT '工号',
  
  `Des` varchar(500) DEFAULT NULL COMMENT '备注',
  `WxOpenId` varchar(64) DEFAULT NULL COMMENT '微信openid',
  `QRUrl` varchar(100) DEFAULT NULL COMMENT '二维码md5',
  `AvatarUrl` varchar(100) DEFAULT NULL COMMENT '头像md5',
  `SeatId` varchar(64) DEFAULT NULL COMMENT '关联的坐席id',
  `UnitId` varchar(64) DEFAULT NULL COMMENT '所属单位id',
    `Status`              varchar(2) DEFAULT NULL comment '接警人员状态  0:空闲 1:繁忙 2:离开 3:下线',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `t_userinfo_Phone` (`Phone`),
  UNIQUE KEY `t_userinfo_IdCard` (`IdCard`),
   UNIQUE KEY `t_userinfo_IdNo` (`IdNo`)
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '接警人员信息';


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
    `Status`              varchar(2) DEFAULT NULL comment '接警手机状态  0:空闲 1:繁忙 2:离开 3:下线',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `t_phoneinfo_Phone` (`Phone`)
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '接警手机信息';



CREATE TABLE `t_seatinfo` (
  
  `Id` varchar(64) NOT NULL COMMENT '坐席id(主键)',
  `Name` varchar(50) NOT NULL COMMENT '坐席名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
 
  `DataState` varchar(2) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',

  `AreaId` varchar(64) DEFAULT NULL COMMENT '所属片区id',
  `UnitId` varchar(64) DEFAULT NULL COMMENT '所属单位id',
  PRIMARY KEY (`Id`)
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '坐席信息';



CREATE TABLE `t_areainfo` (
  
  `Id` varchar(64) NOT NULL COMMENT '片区id(主键)',
  `Name` varchar(50) NOT NULL COMMENT '片区名称',
  `CreateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（insert 触发器 确定）',
  `Uptimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新时间（update 触发器 确定）',
 
  `DataState` varchar(2) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',

  `UnitId` varchar(64) DEFAULT NULL COMMENT '所属单位id',
  PRIMARY KEY (`Id`)
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '片区信息';

CREATE TABLE `t_areainfo_area` (
  `id` varchar(64) NOT NULL,
  `AreaId` varchar(64)  NOT NULL COMMENT '片区id',
  `districtId` int(11) NOT NULL COMMENT '行政区域id -t_area_district-id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='片区对应的物理行政区域 1对多';




CREATE TABLE `t_area_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ProvinceName` varchar(64) DEFAULT NULL COMMENT '省份名称',
  `ProvinceCode` varchar(10) DEFAULT NULL COMMENT '省份编码',
  PRIMARY KEY (`id`),
  KEY `t_area_province_ProvinceName_index` (`ProvinceName`),
  KEY `t_area_province_ProvinceCode_index` (`ProvinceCode`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='高德地图省份编码表';

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
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '单位信息';

CREATE TABLE `t_unitinfo_manager` (
  `id` varchar(64) NOT NULL,
  `UnitId` varchar(64)  NOT NULL COMMENT '单位id',
  `ManagerId` int(11) NOT NULL COMMENT 'manager id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位超级管理员 1对多';




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
  `UnitId` varchar(64)DEFAULT NULL COMMENT '所属单位id,若为本平台管理员则为null',


  `DataState` int(11) NOT NULL DEFAULT '1' COMMENT '数据状态，1：可用，0：禁用，2：删除',

  PRIMARY KEY (`ID`),
    UNIQUE KEY `t_manager_Phone` (`TELEPHONE`),
    UNIQUE KEY `t_manager_UserIDe` (`UserID`),

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台登录用户管理员';


CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL,
  `available` char(1) DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';


CREATE TABLE `sys_user_role` (
  `id` varchar(64) NOT NULL,
  `sys_role_id` varchar(64) DEFAULT NULL,
  `sys_user_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_55` (`sys_role_id`),
  KEY `FK_Reference_56` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

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


CREATE TABLE `sys_role_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `sys_role_id` varchar(64) NOT NULL COMMENT '角色id',
  `sys_permission_id` varchar(64) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_53` (`sys_permission_id`),
  KEY `FK_Reference_54` (`sys_role_id`)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';


CREATE TABLE `t_alarm_userinfo` (

  id              varchar(64)  not null  comment '报警者ID',
   username             varchar(64) DEFAULT NULL  comment '报警者姓名',
  nickname             varchar(64) DEFAULT NULL  comment '报警者昵称',

  `Sex` varchar(2) DEFAULT NULL COMMENT '性别（男，女），默认 男',
  `IdCard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  

   address              varchar(128) DEFAULT NULL  comment '住址',
   mobile_phone         varchar(12) DEFAULT NULL  comment '手机账号',
   wechatId            varchar(64)  DEFAULT NULL comment '微信号',
   wechatOpenId            varchar(64) DEFAULT NULL  comment '微信号openid',
   primary key (id)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警用户信息';


create table videoalarm_info
(
   id                   int not null auto_increment comment '序号',
   type                 int comment '类型  1 图文报警  2在线坐席上传',
   onlineseats_id       int comment '接警人员ID',
   userName             varchar(64) comment '报警者姓名',
   idNumber             varchar(20) comment '身份证号',
   area   varchar(200) comment '区域',
   address              varchar(256) comment '地理位置',

   wechat_id            varchar(64) comment '微信账号ID',
   wechat_OpenId            varchar(64) comment '微信账号openID',
   phone                varchar(12) comment '报警用户电话号码',
   occurrence_time      timestamp comment '警情发生时间',
   description          varchar(5120) comment '警情描述',
   alarm_time           timestamp comment '报警时间',
   status   varchar(2) comment '警情状态 1已报警、2已受理、3已出警、4已关闭',
   picture_url          varchar(2048) comment '图片Url地址 多个用，分割',
   video_url            varchar(2048) comment '视频Url地址 多个用，分割',
   audio_url            varchar(2048) comment '语音Url地址 多个用，分割',
   primary key (id)
);



create table t_videoalarm_talkinfo
(
   id                   int varchar(64)  comment '序号',
   alarmId                 int comment '报警id',
   talkType varchar(2)  comment '聊天状态 1:报警人-》接警人， 2:接警人-》报警人',
  ctime      timestamp comment '时间',
  msgType   varchar(2) comment '消息类型 1:文本',
  msgContent varchar(2048) comment '消息内容',
  fileUrl varchar(512) commet '非文本文件路径',
   
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警事件聊天记录';


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

