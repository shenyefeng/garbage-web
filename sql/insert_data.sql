INSERT INTO `t_menu_details` VALUES
 ('1', '1', '1', 'Overview', null, sysdate(), null, null, null),
 ('2', '1', '2', '总览', null, sysdate(), null, null, null),
 ('3', '2', '1', 'Management', null, sysdate(), null, null, null),
 ('4', '2', '2', '系统管理', null, sysdate(), null, null, null),
 ('5', '3', '1', 'Settings', null, sysdate(), null, null, null),
 ('6', '3', '2', '设置', null, sysdate(), null, null, null),
 ('7', '4', '1', 'Users', null, sysdate(), null, null, null),
 ('8', '4', '2', '用户管理', null, sysdate(), null, null, null),
 ('9', '5', '1', 'QR Codes', null, sysdate(), null, null, null),
 ('10', '5', '2', '二维码管理', null, sysdate(), null, null, null),
 ('11', '6', '1', 'Garbages', null, sysdate(), null, null, null),
 ('12', '6', '2', '垃圾管理', null, sysdate(), null, null, null),
 ('13', '7', '1', 'Stations', null, sysdate(), null, null, null),
 ('14', '7', '2', '分拣中心管理', null, sysdate(), null, null, null),
 ('15', '8', '1', 'Garbages Cans', null, sysdate(), null, null, null),
 ('16', '8', '2', '垃圾桶管理', null, sysdate(), null, null, null),
 ('17', '9', '1', 'Garbage Types', null, sysdate(), null, null, null),
 ('18', '9', '2', '垃圾类别管理', null, sysdate(), null, null, null),
 ('19', '10', '1', 'Gifts', null, sysdate(), null, null, null),
 ('20', '10', '2', '礼品管理', null, sysdate(), null, null, null),
 ('21', '11', '1', 'Gift Grants', null, sysdate(), null, null, null),
 ('22', '11', '2', '礼品发放管理', null, sysdate(), null, null, null),
 ('27', '12', '1', 'Configurations', null, sysdate(), null, null, null),
 ('28', '12', '2', '系统配置', null, sysdate(), null, null, null),
 ('29', '13', '1', 'Report', null, sysdate(), null, null, null),
 ('30', '13', '2', '报表管理', null, sysdate(), null, null, null),
 ('31', '14', '1', 'Type report', null, sysdate(), null, null, null),
 ('32', '14', '2', '垃圾类别报表', null, sysdate(), null, null, null),
 ('33', '15', '1', 'User report', null, sysdate(), null, null, null),
 ('34', '15', '2', '用户垃圾报表', null, sysdate(), null, null, null);



INSERT INTO `t_menu_languages` VALUES
 ('1', 'en_US', null, sysdate(), null, null, null),
 ('2', 'zh_CN', null, sysdate(), null, null, null);


INSERT INTO `t_menus` VALUES 
 ('1', 'overview', null, '1', 'icon-roof', 'tab_overview', null, sysdate(), null, null, null),
 ('2', 'management', null, '2', 'icon-manage', null, null, sysdate(), null, null, null),
 ('3', 'settings', null, '3', 'icon-system', null, null, sysdate(), null, null, null),
 ('4', 'users', '2', '1', null, 'tab_users', null, sysdate(), null, null, null),
 ('5', 'qrcodes', '2', '2', null, 'tab_qrcodes', null, sysdate(), null, null, null),
 ('6', 'garbages', '2', '3', null, 'tab_garbages', null, sysdate(), null, null, null),
 ('7', 'stations', '2', '4', null, 'tab_stations', null, sysdate(), null, null, null),
 ('8', 'garbagecans', '2', '5', null, 'tab_garbagecans', null, sysdate(), null, null, null),
 ('9', 'garbagetypes', '2', '6', null, 'tab_garbagetypes', null, sysdate(), null, null, null),
 ('10', 'gifts', '2', '7', null, 'tab_gifts', null, sysdate(), null, null, null),
 ('11', 'giftgrants', '2', '8', null, 'tab_giftgrants', null, sysdate(), null, null, null),
 ('12', 'config', '3', '1', null, 'tab_config', null, sysdate(), null, null, null),
 ('13', 'report', null, '3', 'icon-manage', null, null, sysdate(), null, null, null),
 ('14', 'type_report', '13', '1', null, 'tab_type_report', null, sysdate(), null, null, null),
 ('15', 'user_report', '13', '2', null, 'tab_user_report', null, sysdate(), null, null, null);

INSERT INTO `T_USERS` (`id`, `user_name`, `password`, `is_admin`, `created_at`) VALUES
 (1, 'admin', '86a6e0033acc487dec8052190b4d6eeb', 'Y', sysdate()),
 (2, 'garbagemgr', '86a6e0033acc487dec8052190b4d6eeb', 'Y', sysdate());

INSERT INTO `t_system_config` (`created_at`, `config_name`, `config_value`, `remark`) VALUES 
(sysdate(), 'page_size', '10', NULL),
(sysdate(), 'timezone', 'Asia/Shanghai', 'user timezone:Asia/Shanghai');

INSERT INTO `t_roles` VALUES 
('1', 'admin', null, sysdate(), null, null, null)
,('2', 'garbagemgr', null, sysdate(), null, null, null);

INSERT INTO `t_role_menus` VALUES
  ('1', '1', '1', 'Y', null, sysdate(), null, null, null)
, ('2', '1', '2', 'Y', null, sysdate(), null, null, null)
, ('3', '1', '3', 'Y', null, sysdate(), null, null, null)
, ('4', '1', '4', 'Y', null, sysdate(), null, null, null)
, ('5', '1', '5', 'Y', null, sysdate(), null, null, null)
, ('6', '1', '6', 'Y', null, sysdate(), null, null, null)
, ('7', '1', '7', 'Y', null, sysdate(), null, null, null)
, ('8', '1', '8', 'Y', null, sysdate(), null, null, null)
, ('9', '1', '9', 'Y', null, sysdate(), null, null, null)
, ('10', '1', '10', 'Y', null, sysdate(), null, null, null)
, ('11', '1', '11', 'Y', null, sysdate(), null, null, null)
, ('12', '1', '12', 'Y', null, sysdate(), null, null, null)
, ('13', '2', '1', 'N', null, sysdate(), null, null, null)
, ('14', '2', '2', 'Y', null, sysdate(), null, null, null)
, ('15', '2', '6', 'Y', null, sysdate(), null, null, null)
, ('16', '1', '13', 'Y', null, sysdate(), null, null, null)
, ('17', '1', '14', 'Y', null, sysdate(), null, null, null)
, ('18', '1', '15', 'Y', null, sysdate(), null, null, null)
;

INSERT INTO `t_role_users` VALUES 
 ('1', '1', '1', null, sysdate(), null, null, null)
,('2', '2', '2', null, sysdate(), null, null, null);
