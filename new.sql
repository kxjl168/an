INSERT INTO `sys_permission` (`id`, `name`, `type`, `url`, `percode`, `parentid`, `parentids`, `sortstring`, `available`, `icon`) VALUES ('menu_videoalarmerrorinfo', '统计分析管理', '2', '/manager/videoalarmerrorinfo/manager', 'videoalarmerrorinfo:view', 'menu_auto', NULL, '1', '1', 'fa fa-cog');

alter table t_unitinfo add COLUMN
`parentUnit` varchar(64) DEFAULT NULL COMMENT '上级单位id';


INSERT INTO `sys_dict_info` (`id`, `type`, `dict_name`, `dict_value`) VALUES ('10', '4', '坐席繁忙', '1');
INSERT INTO `sys_dict_info` (`id`, `type`, `dict_name`, `dict_value`) VALUES ('11', '4', '超时未接', '2');

INSERT INTO `t_receivepoliceinfo` (`Id`, `Phone`, `Password`, `SessionKey`, `Name`, `CreateTime`, `Uptimestamp`, `CreateUser`, `UpdateUser`, `DataState`, `Sex`, `IdCard`, `IdNo`, `Des`, `AvatarUrl`, `SeatId`, `UnitId`, `Status`) VALUES ('ac30dece0bbc4e2d9691a8fc1ad2583b', '13815429446', '111111', NULL, '关1', '2019-07-31 22:36:56', '2019-07-31 22:36:56', NULL, NULL, '1', '男', NULL, '1005', '111', NULL, 'd65dbd3b3f074517aa9c7873148213b8', NULL, '2');
INSERT INTO `t_receivepoliceinfo` (`Id`, `Phone`, `Password`, `SessionKey`, `Name`, `CreateTime`, `Uptimestamp`, `CreateUser`, `UpdateUser`, `DataState`, `Sex`, `IdCard`, `IdNo`, `Des`, `AvatarUrl`, `SeatId`, `UnitId`, `Status`) VALUES ('b319973adb5e4b288cfa08b96616482c', '13833333312', '111111', NULL, '张飞', '2019-07-31 22:24:20', '2019-07-31 22:24:20', NULL, NULL, '1', '男', NULL, '10001', '111', NULL, 'bcb15b8e73694e028bafa3905015c068', NULL, '0');
INSERT INTO `t_receivepoliceinfo` (`Id`, `Phone`, `Password`, `SessionKey`, `Name`, `CreateTime`, `Uptimestamp`, `CreateUser`, `UpdateUser`, `DataState`, `Sex`, `IdCard`, `IdNo`, `Des`, `AvatarUrl`, `SeatId`, `UnitId`, `Status`) VALUES ('e608821ce16c4c5db099d1b5dbb8ce3f', '13815429445', '111111', NULL, '吴22', '2019-07-31 22:37:33', '2019-07-31 22:37:33', NULL, NULL, '1', '男', NULL, '1006', '1', NULL, '87d32f2485c44f6482d13fd728022ec4', NULL, '1');


INSERT INTO `t_unitinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `Des`, `ContactPerson`, `ContactPhone`, `Address`, `unitType`, `parentUnit`) VALUES ('480fe0d5ebb445c490e7c719c3898250', '南京公安', '2019-07-19 22:45:42', '2019-07-27 15:40:54', '1', '饿肚肚多多多多多多多', '王五', '13815421111', '雨花大道1010号', '1', '-1');
INSERT INTO `t_unitinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `Des`, `ContactPerson`, `ContactPhone`, `Address`, `unitType`, `parentUnit`) VALUES ('7256d0a2e6814db7b5441b2301d47c49', '六合分局', '2019-07-31 22:20:13', '2019-07-31 22:20:13', '1', '1231', '11', '13815429445', '123', '2', '480fe0d5ebb445c490e7c719c3898250');
INSERT INTO `t_unitinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `Des`, `ContactPerson`, `ContactPhone`, `Address`, `unitType`, `parentUnit`) VALUES ('a9d7e202ca004df3a840db03f4e81c82', '江宁分局', '2019-07-21 13:09:42', '2019-07-27 16:15:51', '1', '哒哒哒哒哒哒多多多', '李四', '13981834121', '长沙光辉大道10123号', '2', '480fe0d5ebb445c490e7c719c3898250');
INSERT INTO `t_unitinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `Des`, `ContactPerson`, `ContactPhone`, `Address`, `unitType`, `parentUnit`) VALUES ('aaabee7debd8441a8e865f3b4d3087a5', '镇江公安', '2019-07-22 12:00:55', '2019-07-27 16:39:17', '1', '', '老吴', '14827481721', '镇江解放大道1-123号', '1', '-1');

INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('26fc93d7683d45bfade5375c74ce75fb', '662', '2019-07-31 22:20:49', '2019-07-31 22:20:49', '1', '7256d0a2e6814db7b5441b2301d47c49', NULL);
INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('64c13650ee4d4bc58550d55795c495e9', '镇江1', '2019-07-21 16:27:17', '2019-07-21 16:27:17', '1', 'aaabee7debd8441a8e865f3b4d3087a5', NULL);
INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('77ce5a08f8d04e7292adc1eb0b82888e', '镇江2', '2019-07-21 16:27:29', '2019-07-21 16:27:29', '1', 'aaabee7debd8441a8e865f3b4d3087a5', NULL);
INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('8f8fe1940bae41fb9c943d211923d88d', '6611', '2019-07-31 22:20:44', '2019-07-31 22:20:44', '1', '7256d0a2e6814db7b5441b2301d47c49', NULL);
INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('b69887ae73d04ad8b8f9268e21e5dbed', '测试片区1', '2019-07-21 16:15:24', '2019-07-31 22:19:10', '1', 'a9d7e202ca004df3a840db03f4e81c82', '1');
INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('e95dfd3ff482402c9b43d9ebba8c2c8f', 'p1', '2019-07-21 16:27:02', '2019-07-31 22:06:09', '1', 'a9d7e202ca004df3a840db03f4e81c82', '');
INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('f6f2838c520042adb21ce947731d666d', 'p2', '2019-07-21 16:27:08', '2019-07-31 22:06:13', '1', 'a9d7e202ca004df3a840db03f4e81c82', '');
INSERT INTO `t_areainfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `UnitId`, `Des`) VALUES ('f751883ac69d4bb68a28a57aa6f10e09', '测试片区2', '2019-07-21 16:26:52', '2019-07-31 22:19:15', '1', 'a9d7e202ca004df3a840db03f4e81c82', '');


INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('1492b85f324c424e8aba3f5248c50539', 'c3', '2019-07-21 17:01:01', '2019-07-21 21:15:42', '1', 'f751883ac69d4bb68a28a57aa6f10e09', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('1f121966195047f5b6f8318550c3e7dc', 'c2', '2019-07-21 17:00:57', '2019-07-21 17:00:57', '1', 'b69887ae73d04ad8b8f9268e21e5dbed', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('542b8ff0548342a687ece17865437513', 'CS-坐席1', '2019-07-21 16:55:41', '2019-07-21 17:00:36', '1', 'f751883ac69d4bb68a28a57aa6f10e09', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('5df44971ab3a429caf70d4fbeee16c43', 'C-04', '2019-07-31 23:52:40', '2019-07-31 23:52:40', '1', 'e95dfd3ff482402c9b43d9ebba8c2c8f', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('6a8dcb9d9af641b09bc3c0032190fdba', 'A-01', '2019-07-31 23:52:23', '2019-07-31 23:52:23', '1', 'f6f2838c520042adb21ce947731d666d', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('703b5f41e0234b2baa4d6a201a4e6cdc', 'c444', '2019-07-21 17:55:10', '2019-07-21 17:55:10', '1', 'f751883ac69d4bb68a28a57aa6f10e09', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('7d2bf147d8934ad68afe9faf0fbc34fa', 'c4', '2019-07-21 17:01:05', '2019-07-21 17:01:05', '1', 'f751883ac69d4bb68a28a57aa6f10e09', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('87d32f2485c44f6482d13fd728022ec4', '005', '2019-07-31 22:22:16', '2019-07-31 22:22:16', '1', '8f8fe1940bae41fb9c943d211923d88d', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('98d1679a003a45c896a3d05312a7f181', 'B-03', '2019-07-31 23:52:31', '2019-07-31 23:52:31', '1', 'f6f2838c520042adb21ce947731d666d', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('b22440de071c4603999dd3f10a895bc8', 'CS', '2019-07-21 16:55:56', '2019-07-21 21:27:06', '1', 'f751883ac69d4bb68a28a57aa6f10e09', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('bcb15b8e73694e028bafa3905015c068', '003', '2019-07-31 22:22:03', '2019-07-31 22:22:03', '1', '26fc93d7683d45bfade5375c74ce75fb', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('c8e94c03beed495582fc81e7a907868d', '长沙1', '2019-07-21 22:37:28', '2019-07-21 22:37:28', '1', 'e95dfd3ff482402c9b43d9ebba8c2c8f', NULL, NULL);
INSERT INTO `t_seatinfo` (`Id`, `Name`, `CreateTime`, `Uptimestamp`, `DataState`, `AreaId`, `UnitId`, `PersonNum`) VALUES ('d65dbd3b3f074517aa9c7873148213b8', '004', '2019-07-31 22:22:10', '2019-07-31 22:22:10', '1', '26fc93d7683d45bfade5375c74ce75fb', NULL, NULL);


INSERT INTO `videoalarm_errorinfo` (`id`, `type`, `onlineseats_id`, `userName`, `idNumber`, `area`, `latitude`, `longitude`, `address`, `wechat_id`, `phone`, `alarm_time`) VALUES ('2', '2', '3123', '22', '2222', '马路11', '111', '22', '玄武湖1', '', '', '2019-08-01 18:52:44');
INSERT INTO `videoalarm_errorinfo` (`id`, `type`, `onlineseats_id`, `userName`, `idNumber`, `area`, `latitude`, `longitude`, `address`, `wechat_id`, `phone`, `alarm_time`) VALUES ('4', '1', '3123', '111', '11111', '马路11', '111', '22', '玄武湖1', '', '', '2019-07-31 18:52:44');
INSERT INTO `videoalarm_errorinfo` (`id`, `type`, `onlineseats_id`, `userName`, `idNumber`, `area`, `latitude`, `longitude`, `address`, `wechat_id`, `phone`, `alarm_time`) VALUES ('5', '1', '3123', '123', '333', '马路11', '111', '22', '玄武湖1', '', '', '2019-07-30 18:52:44');
INSERT INTO `videoalarm_errorinfo` (`id`, `type`, `onlineseats_id`, `userName`, `idNumber`, `area`, `latitude`, `longitude`, `address`, `wechat_id`, `phone`, `alarm_time`) VALUES ('6', '2', '3123', '123', '33', '马路11', '111', '22', '玄武湖1', '', '', '2019-08-01 10:52:44');
INSERT INTO `videoalarm_errorinfo` (`id`, `type`, `onlineseats_id`, `userName`, `idNumber`, `area`, `latitude`, `longitude`, `address`, `wechat_id`, `phone`, `alarm_time`) VALUES ('7', '1', '3123', '4123', '444', '马路11', '111', '22', '玄武湖1', '', '', '2019-08-01 08:52:44');