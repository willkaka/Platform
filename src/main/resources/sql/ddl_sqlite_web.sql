
-- 菜单
-- menu	menu_desc	menu_group
DROP TABLE IF EXISTS web_menu;
CREATE TABLE IF NOT EXISTS web_menu (
  web_menu_id integer primary key,
  menu_group varchar(32),
  menu_seq integer default 0,
  menu varchar(32) NOT NULL,
  menu_desc varchar(32) );
CREATE INDEX wm_ind_01 on web_menu (menu_group,menu_seq);
CREATE INDEX wm_ind_02 on web_menu (menu);

-- 页面元素
drop table web_element;
CREATE TABLE web_element (
  web_element_id integer primary key,
  menu varchar(32) NOT NULL,
  page varchar(32) NOT NULL,
  element_parent varchar(32),
  element_seq integer default 0,
	element varchar(32),
  element_type varchar(32),
  element_desc varchar(200),
  element_attr varchar(200) );
CREATE INDEX we_ind_01 on web_element (menu,page,element_seq);
CREATE INDEX we_ind_02 on web_element (element);

-- 事件
-- menu	element	event_type	request_type	request_no	param
DROP TABLE IF EXISTS web_event;
CREATE TABLE IF NOT EXISTS web_event (
  web_event_id integer primary key,
  menu varchar(32) NOT NULL,
  page varchar(32) NOT NULL,
  element varchar(32),
  event_type varchar(32),
  request_type varchar(32),
  request_bean varchar(32),
  trigger_type varchar(32),
  trigger_element varchar(32),
  trigger_element_type varchar(32),
  param varchar(500) );
CREATE INDEX wev_ind_01 on web_event (menu,element);

DROP TABLE IF EXISTS web_trigger;
CREATE TABLE web_trigger (
  web_trigger_id integer primary key,
  source_menu varchar(32) NOT NULL,
  source_page varchar(20),
  source_element varchar(32),
  trigger_type varchar(32),
  trigger_element varchar(32),
  trigger_element_type varchar(32),
  param varchar(500) );
CREATE INDEX wt_ind_01 on web_trigger (source_menu,source_element);

DROP TABLE IF EXISTS web_data;
CREATE TABLE web_data (
    web_data_id integer primary key,
    menu varchar(20),
    page varchar(20),
    element varchar(20),
    data_type varchar(20),
    data_attr varchar(20),
    express varchar(20) );
CREATE INDEX wd_ind_01 on web_data (menu,page,element);