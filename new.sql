INSERT INTO `sys_permission` (`id`, `name`, `type`, `url`, `percode`, `parentid`, `parentids`, `sortstring`, `available`, `icon`) VALUES ('menu_videoalarmerrorinfo', '统计分析管理', '2', '/manager/videoalarmerrorinfo/manager', 'videoalarmerrorinfo:view', 'menu_auto', NULL, '1', '1', 'fa fa-cog');

alter table t_unitinfo add COLUMN
`parentUnit` varchar(64) DEFAULT NULL COMMENT '上级单位id';