CREATE TABLE t_users (
id bigint(20) NOT NULL AUTO_INCREMENT,
user_name varchar(200) NOT NULL,
password varchar(200),
real_name varchar(200),
score float(20,2),
gender varchar(10),
birthday date,
village varchar(200),
phone varchar(200),
address varchar(2000),
timezone varchar(30),
is_admin varchar(10) NOT NULL,
current_login datetime,
last_login datetime,
current_login_ip varchar(100),
last_login_ip varchar(100),
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_T_USERS_1 (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_qr_codes (
id bigint(20) NOT NULL AUTO_INCREMENT,
user_id bigint(20),
qr_code_start varchar(100) NOT NULL,
qr_code_end varchar(100),
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_garbage_stations (
id bigint(20) NOT NULL AUTO_INCREMENT,
station_name varchar(200) NOT NULL,
phone varchar(200),
address varchar(2000),
station_op varchar(200),
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_T_GARBAGE_STATIONS_1 (station_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE t_garbage_cans (
id bigint(20) NOT NULL AUTO_INCREMENT,
can_name varchar(200) NOT NULL,
village varchar(200),
address varchar(2000),
station_op varchar(200),
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_T_GARBAGE_CANS_1 (can_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_garbage_types (
id bigint(20) NOT NULL AUTO_INCREMENT,
type_name varchar(200) NOT NULL,
score_weight float(20,2) NOT NULL,
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_T_GARBAGE_TYPES_1 (type_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_garbages (
id bigint(20) NOT NULL AUTO_INCREMENT,
garbage_station_id bigint(20) NOT NULL,
garbage_can_id bigint(20),
qr_code_id bigint(20) NOT NULL,
qr_code varchar(100) NOT NULL,
user_id bigint(20) NOT NULL,
garbage_type_id bigint(20) NOT NULL,
weight float(20,2) NOT NULL,
score float(20,2) NOT NULL,
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_T_GARBAGES_1 (qr_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE t_gifts (
id bigint(20) NOT NULL AUTO_INCREMENT,
gift_name varchar(200) NOT NULL,
gift_num float(20,2) NOT NULL,
gift_grant_num float(20,2) NOT NULL,
score float(20,2) NOT NULL,
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_T_GIFTS_1 (gift_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_gift_grants (
id bigint(20) NOT NULL AUTO_INCREMENT,
gift_id bigint(20) NOT NULL,
user_id bigint(20) NOT NULL,
gift_num float(20,2) NOT NULL,
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_menu_actions (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
menu_id bigint(20) NOT NULL COMMENT 'menu id',
menu_action varchar(255) COMMENT 'menu action',
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_menu_actions_1 (menu_id,menu_action)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_menu_details (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
menu_id bigint(20) NOT NULL COMMENT 'menu id',
lang_id bigint(20) NOT NULL COMMENT 'language ID',
menu_value varchar(1000) COMMENT '菜单显示名',
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_menu_details_1 (menu_id, lang_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_menu_languages (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
lang_name varchar(255) NOT NULL COMMENT 'language name',
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_menu_languages_1 (lang_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_menus (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
menu_name varchar(255) NOT NULL COMMENT 'menu name',
parent_menu_id bigint(20) COMMENT '父菜单编号ID',
menu_order bigint(20) COMMENT '顺序号',
style_class varchar(255) COMMENT '菜单样式',
menu_item_id varchar(255) COMMENT '菜单id，用于js触发',
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY ix_menus_1 (menu_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_system_config (
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
config_name varchar(100) NOT NULL COMMENT '配置名称',
config_value varchar(10000) NOT NULL COMMENT '配置值',
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id),
UNIQUE KEY i_system_config_1 (config_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_operation_logs (
id bigint(20) NOT NULL AUTO_INCREMENT,
user_id bigint(20) NOT NULL,
ip_address varchar(100),
class_method varchar(1000) NOT NULL,
params varchar(4000),
exception_msg varchar(4000),
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_score_details (
id bigint(20) NOT NULL AUTO_INCREMENT,
gift_id bigint(20),
user_id bigint(20) NOT NULL,
garbage_id bigint(20),
score float(20,2) NOT NULL,
remark varchar(1000),
created_at datetime,
created_by bigint(20),
updated_at datetime,
updated_by bigint(20),
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_roles (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  role_name varchar(255) NOT NULL COMMENT 'role name',
  remark varchar(1000) COMMENT '备注',
  created_at datetime COMMENT '创建日期',
  created_by bigint(20) COMMENT '创建者',
  updated_at datetime COMMENT '更新日期',
  updated_by bigint(20) COMMENT '更新者',
  PRIMARY KEY (id),
  UNIQUE KEY ix_roles_1 (role_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_role_menus (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  role_id bigint(20) NOT NULL COMMENT 'role id',
  menu_id bigint(20) NOT NULL COMMENT 'menu id',
  has_right varchar(2) COMMENT '是否有权限,Y/N',
  remark varchar(1000) COMMENT '备注',
  created_at datetime COMMENT '创建日期',
  created_by bigint(20) COMMENT '创建者',
  updated_at datetime COMMENT '更新日期',
  updated_by bigint(20) COMMENT '更新者',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_role_users (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  role_id bigint(20) NOT NULL COMMENT 'role id',
  user_id bigint(20) NOT NULL COMMENT 'user id',
  remark varchar(1000) COMMENT '备注',
  created_at datetime COMMENT '创建日期',
  created_by bigint(20) COMMENT '创建者',
  updated_at datetime COMMENT '更新日期',
  updated_by bigint(20) COMMENT '更新者',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_user_menus (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号ID',
  user_id bigint(20) NOT NULL COMMENT 'user id',
  menu_id bigint(20) NOT NULL COMMENT 'menu id',
  has_right varchar(2) COMMENT '是否有权限,Y/N',
  remark varchar(1000) COMMENT '备注',
  created_at datetime COMMENT '创建日期',
  created_by bigint(20) COMMENT '创建者',
  updated_at datetime COMMENT '更新日期',
  updated_by bigint(20) COMMENT '更新者',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
