
-- web_menu definition
-- 旧
CREATE TABLE web_menu (
  web_menu_id integer primary key,
  menu_parent varchar(32),
	menu_seq integer default 0,
	menu varchar(32) NOT NULL,
  menu_desc varchar(32) );

CREATE INDEX wm_ind_01 on web_menu (menu_parent,menu_seq);
CREATE INDEX wm_ind_02 on web_menu (menu);

INSERT INTO web_menu(web_menu_id, menu_parent, menu_seq, menu, menu_desc)VALUES
(1, 'root', 1, 'SystemMaintain', '系统维护'),
(2, 'SystemMaintain', 1, 'MenuMaintain', '菜单维护'),
(3, 'root', 3, 'lcs', '核算系统'),
(4, 'lcs', 1, 'putoutLoan', '放款'),
(7, 'root', 2, 'tools', '工具'),
(8, 'tools', 1, 'SearchText', '运维记录查询'),
(9, 'tools', 2, 'splitString', '拆分字符串');

DROP TABLE web_menu;
CREATE TABLE web_menu (
  web_menu_id integer primary key, -- 主键
  menu_no varchar(20), -- 菜单号
  sort_no integer, -- 排序号
  menu varchar(32) NOT NULL,
  menu_parent varchar(32),
  menu_desc varchar(32) );

CREATE INDEX wm_ind_01 on web_menu (menu_parent,sort_no);
CREATE INDEX wm_ind_02 on web_menu (menu);
-- 新
INSERT INTO web_menu(menu_no, sort_no, menu_parent, menu, menu_desc)VALUES
('M001', 1, 'root', 'SystemMaintain', '系统维护'),
('M002', 2, 'SystemMaintain', 'MenuMaintain', '菜单维护'),
('M003', 3, 'root', 'lcs', '核算系统'),
('M004', 4, 'lcs', 'putoutLoan', '放款'),
('M007', 7, 'root', 'tools', '工具'),
('M008', 8, 'tools', 'SearchText', '运维记录查询'),
('M009', 9, 'tools', 'splitString', '拆分字符串');


-- 旧
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


-- 新
CREATE TABLE web_element (
  web_element_id integer primary key,
  element_no varchar(20) NOT NULL,
  sort_no integer, -- 排序号
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


delete from web_element where 1=1;
INSERT INTO web_element(element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
('EM000001', 0, 'MenuMaintain', 'start_page', 'contentArea', 'inputArea', 'div', '输入区域', 'class="inputArea"', NULL),
('EM000002', 0, 'MenuMaintain', 'start_page', 'inputArea', 'funDiv', 'div', '输入区域', 'class="inputArea"', NULL),
('EM000003', 0, 'MenuMaintain', 'start_page', 'funDiv', 'addMenu', 'button', '新增菜单', 'class="inputArea_sub_button"', NULL),
('EM000004', 0, 'MenuMaintain', 'start_page', 'inputArea', 'menuTable', 'table', '菜单列表', 'class="output_table"', '{"hideFields":["web_menu_id"]}'),
('EM000005', 0, 'MenuMaintain', 'start_page', 'menuTable', 'menu_move_up', 'table_record_button', '上移', 'class="label_button"', NULL),
('EM000005', 0, 'MenuMaintain', 'start_page', 'menuTable', 'menu_move_down', 'table_record_button', '下移', 'class="label_button"', NULL),
('EM000005', 0, 'MenuMaintain', 'start_page', 'menuTable', 'edt_menu_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000006', 0, 'MenuMaintain', 'start_page', 'menuTable', 'del_menu_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000007', 0, 'MenuMaintain', 'start_page', 'menuTable', 'dsp_ele_button', 'table_record_button', '元素', 'class="label_button"', NULL),
('EM000008', 0, 'MenuMaintain', 'start_page', 'menuTable', 'dsp_eve_button', 'table_record_button', '事件', 'class="label_button"', NULL),
('EM000009', 0, 'MenuMaintain', 'start_page', 'menuTable', 'dsp_dta_button', 'table_record_button', '数据', 'class="label_button"', NULL),
('EM000010', 0, 'MenuMaintain', 'start_page', 'menuTable', 'dsp_aft_button', 'table_record_button', '事后处理步骤', 'class="label_button"', NULL),
('EM000011', 0, 'MenuMaintain', 'add_menu_sbw', 'body', 'add_menu_sub_window', 'subWindow', '新增菜单', '', NULL),
('EM000012', 0, 'MenuMaintain', 'add_menu_sbw', 'add_menu_sub_window_swBody', 'add_menu_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000015', 0, 'MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000013', 0, 'MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 'menuParent', 'input', '父级菜单(顶级为root)', '', NULL),
('EM000016', 0, 'MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 'menuDesc', 'input', '菜单名称', '', NULL),
('EM000017', 0, 'MenuMaintain', 'add_menu_sbw', 'add_menu_sub_window_swFooter', 'addMenuButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000018', 0, 'MenuMaintain', 'edt_menu_sbw', 'body', 'edt_menu_sub_window', 'subWindow', '编辑菜单', '', NULL),
('EM000019', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sub_window_swBody', 'edt_menu_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000023', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'web_menu_id', 'input', 'id', '', '{"hide":true}'),
('EM000023', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu_no', 'input', '菜单编号', '', NULL),
('EM000022', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'sort_no', 'input', '菜单序号', '', NULL),
('EM000023', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000021', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu_parent', 'input', '父级菜单(顶级为root)', '', NULL),
('EM000024', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu_desc', 'input', '菜单名称', '', NULL),
('EM000025', 0, 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sub_window_swFooter', 'edtMenuButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000026', 0, 'MenuMaintain', 'ele_page', 'inputArea', 'eleTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000027', 0, 'MenuMaintain', 'ele_page', 'eleTableDiv', 'addEle', 'button', '新增页面元素', 'class="inputArea_sub_button"', NULL),
('EM000028', 0, 'MenuMaintain', 'ele_page', 'eleTableDiv', 'eleTable', 'table', '元素列表', 'class="output_table"', '{"hideFields":["web_element_id"]}'),
('EM000028', 0, 'MenuMaintain', 'ele_page', 'eleTable', 'ele_move_up', 'table_record_button', '上移', 'class="label_button"', NULL),
('EM000028', 0, 'MenuMaintain', 'ele_page', 'eleTable', 'ele_move_down', 'table_record_button', '下移', 'class="label_button"', NULL),
('EM000029', 0, 'MenuMaintain', 'ele_page', 'eleTable', 'edt_ele_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000030', 0, 'MenuMaintain', 'ele_page', 'eleTable', 'del_ele_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000031', 0, 'MenuMaintain', 'add_ele_sbw', 'body', 'add_ele_sub_window', 'subWindow', '新增页面元素', '', NULL),
('EM000032', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sub_window_swBody', 'add_ele_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000034', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000035', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000036', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementParent', 'input', '父级元素', '', NULL),
('EM000038', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000039', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementType', 'input', '元素类型', '', NULL),
('EM000040', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementDesc', 'input', '元素名称', '', NULL),
('EM000041', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementAttr', 'input', '属性', '', NULL),
('EM000041', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'param', 'input', '参数', '', NULL),
('EM000042', 0, 'MenuMaintain', 'add_ele_sbw', 'add_ele_sub_window_swFooter', 'addEleButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000043', 0, 'MenuMaintain', 'edt_ele_sbw', 'body', 'edt_ele_sub_window', 'subWindow', '编辑页面元素', '', NULL),
('EM000044', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sub_window_swBody', 'edt_ele_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000186', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'web_element_id', 'input', 'id', '', '{"hide":true}'),
('EM000050', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_no', 'input', '元素编号', '', NULL),
('EM000049', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'sort_no', 'input', '元素序号', '', NULL),
('EM000046', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000047', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000048', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_parent', 'input', '父级元素', '', NULL),
('EM000050', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000051', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_type', 'input', '元素类型', '', NULL),
('EM000052', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_desc', 'input', '菜单名称', '', NULL),
('EM000053', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_attr', 'input', '属性', '', NULL),
('EM000053', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'param', 'input', '参数', '', NULL),
('EM000054', 0, 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sub_window_swFooter', 'edtEleButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000055', 0, 'MenuMaintain', 'evn_page', 'inputArea', 'evnTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000056', 0, 'MenuMaintain', 'evn_page', 'evnTableDiv', 'addEvn', 'button', '新增页面事件', 'class="inputArea_sub_button"', NULL),
('EM000057', 0, 'MenuMaintain', 'evn_page', 'evnTableDiv', 'evnTable', 'table', '元素事件列表', 'class="output_table"', '{"hideFields":["web_event_id"]}'),
('EM000058', 0, 'MenuMaintain', 'evn_page', 'evnTable', 'edt_evn_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000059', 0, 'MenuMaintain', 'evn_page', 'evnTable', 'del_evn_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000060', 0, 'MenuMaintain', 'add_evn_sbw', 'body', 'add_evn_sub_window', 'subWindow', '新增页面元素', '', NULL),
('EM000061', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sub_window_swBody', 'add_evn_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000062', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'webEventId', 'input', 'web_event_id', '', NULL),
('EM000063', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000064', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000065', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000066', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'eventType', 'input', '事件类型', '', NULL),
('EM000067', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'requestType', 'input', '请求类型', '', NULL),
('EM000068', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'requestBean', 'input', '请求处理的bean', '', NULL),
('EM000069', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'nextPage', 'input', '跳转显示页', '', NULL),
('EM000070', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'param', 'input', '参数(JSON)', '', NULL),
('EM000071', 0, 'MenuMaintain', 'add_evn_sbw', 'add_evn_sub_window_swFooter', 'addEvnButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000072', 0, 'MenuMaintain', 'edt_evn_sbw', 'body', 'edt_evn_sub_window', 'subWindow', '编辑页面元素', '', NULL),
('EM000073', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sub_window_swBody', 'edt_evn_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000074', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'web_event_id', 'input', 'web_event_id', '', NULL),
('EM000075', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000076', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000077', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000078', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'event_type', 'input', '事件类型', '', NULL),
('EM000079', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'request_type', 'input', '请求类型', '', NULL),
('EM000080', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'request_bean', 'input', '请求处理的bean', '', NULL),
('EM000081', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'next_page', 'input', '跳转显示页', '', NULL),
('EM000082', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'param', 'input', '参数(JSON)', '', NULL),
('EM000083', 0, 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sub_window_swFooter', 'edtEvnButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000084', 0, 'MenuMaintain', 'dta_page', 'inputArea', 'dtaTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000085', 0, 'MenuMaintain', 'dta_page', 'dtaTableDiv', 'addDta', 'button', '新增页面数据', 'class="inputArea_sub_button"', NULL),
('EM000086', 0, 'MenuMaintain', 'dta_page', 'dtaTableDiv', 'dtaTable', 'table', '元素数据列表', 'class="output_table"', '{"hideFields":["web_data_id"]}'),
('EM000087', 0, 'MenuMaintain', 'dta_page', 'dtaTable', 'edt_dta_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000088', 0, 'MenuMaintain', 'dta_page', 'dtaTable', 'del_dta_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000089', 0, 'MenuMaintain', 'add_dta_sbw', 'body', 'add_dta_sub_window', 'subWindow', '新增页面数据', '', NULL),
('EM000090', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sub_window_swBody', 'add_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000091', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'webDataId', 'input', 'web_data_id', '', NULL),
('EM000092', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000093', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000094', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000095', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'dataType', 'input', '数据类型', '', NULL),
('EM000096', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'dataAttr', 'input', '数据属性', '', NULL),
('EM000097', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'express', 'input', '表达式', '', NULL),
('EM000098', 0, 'MenuMaintain', 'add_dta_sbw', 'add_dta_sub_window_swFooter', 'addDtaButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000099', 0, 'MenuMaintain', 'edt_dta_sbw', 'body', 'edt_dta_sub_window', 'subWindow', '编辑页面数据', '', NULL),
('EM000100', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sub_window_swBody', 'edt_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000101', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'web_data_id', 'input', 'web_data_id', '', NULL),
('EM000102', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000103', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000104', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000105', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'data_type', 'input', '数据类型', '', NULL),
('EM000106', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'data_attr', 'input', '数据属性', '', NULL),
('EM000107', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'express', 'input', '表达式', '', NULL),
('EM000108', 0, 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sub_window_swFooter', 'edtDtaButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000109', 0, 'MenuMaintain', 'aft_page', 'inputArea', 'aftTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000110', 0, 'MenuMaintain', 'aft_page', 'aftTableDiv', 'addAft', 'button', '新增页面事后处理步骤', 'class="inputArea_sub_button"', NULL),
('EM000111', 0, 'MenuMaintain', 'aft_page', 'aftTableDiv', 'aftTable', 'table', '事后处理步骤列表', 'class="output_table"', '{"hideFields":["web_call_after_id"]}'),
('EM000112', 0, 'MenuMaintain', 'aft_page', 'aftTable', 'edt_aft_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000113', 0, 'MenuMaintain', 'aft_page', 'aftTable', 'del_aft_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000114', 0, 'MenuMaintain', 'add_aft_sbw', 'body', 'add_aft_sub_window', 'subWindow', '新增页面事后处理步骤', '', NULL),
('EM000115', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sub_window_swBody', 'add_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000116', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'webCallAfterId', 'input', 'web_call_after_id', '', NULL),
('EM000117', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000118', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000119', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'processBean', 'input', '处理bean', '', NULL),
('EM000120', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'processStatus', 'input', '处理状态', '', NULL),
('EM000121', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'oprType', 'input', '操作类型', '', NULL),
('EM000122', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'requestType', 'input', '请求类型', '', NULL),
('EM000123', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'requestBean', 'input', '请求Bean', '', NULL),
('EM000124', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'param', 'input', '参数', '', NULL),
('EM000125', 0, 'MenuMaintain', 'add_aft_sbw', 'add_aft_sub_window_swFooter', 'addAftButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000126', 0, 'MenuMaintain', 'edt_aft_sbw', 'body', 'edt_aft_sub_window', 'subWindow', '编辑页面事后处理步骤', '', NULL),
('EM000127', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sub_window_swBody', 'edt_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000128', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'web_data_id', 'input', 'web_data_id', '', NULL),
('EM000129', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000130', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000131', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000132', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'data_type', 'input', '数据类型', '', NULL),
('EM000133', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'data_attr', 'input', '数据属性', '', NULL),
('EM000134', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'express', 'input', '表达式', '', NULL),
('EM000135', 0, 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sub_window_swFooter', 'edtAftButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000136', 0, 'putoutLoan', 'start_page', 'contentArea', 'inputArea', 'div', '输入区域', 'class="inputArea showWithRowDivFlex"', NULL),
('EM000137', 0, 'putoutLoan', 'start_page', 'inputArea', 'transInfoDiv', 'div', '交易信息div', 'class="inputArea background_color_1"', NULL),
('EM000138', 0, 'putoutLoan', 'start_page', 'transInfoDiv', 'transInfoDivTitle', 'divTitle', '交易信息', 'class="content_title"', NULL),
('EM000139', 0, 'putoutLoan', 'start_page', 'transInfoDiv', 'transSeq', 'input', '交易流水号', '', NULL),
('EM000140', 0, 'putoutLoan', 'start_page', 'transInfoDiv', 'transDate', 'input', '交易日期', '', NULL),
('EM000141', 0, 'putoutLoan', 'start_page', 'transInfoDiv', 'transChannel', 'input', '交易渠道', '', NULL),
('EM000142', 0, 'putoutLoan', 'start_page', 'transInfoDiv', 'businessSource', 'input', '业务来源', '', NULL),
('EM000143', 0, 'putoutLoan', 'start_page', 'transInfoDiv', 'businessChannel', 'input', '业务渠道', '', NULL),
('EM000144', 0, 'putoutLoan', 'start_page', 'inputArea', 'customerInfoDiv', 'div', '客户信息div', 'class="inputArea background_color_1"', NULL),
('EM000145', 0, 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerInfoDivTitle', 'divTitle', '客户信息', 'class="content_title"', NULL),
('EM000146', 0, 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerName', 'input', '客户姓名', '', NULL),
('EM000147', 0, 'putoutLoan', 'start_page', 'customerInfoDiv', 'certNo', 'input', '身份证号', '', NULL),
('EM000148', 0, 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerPhone', 'input', '客户手机号', '', NULL),
('EM000149', 0, 'putoutLoan', 'start_page', 'inputArea', 'produceInfoDiv', 'div', '产品信息输入区域', 'class="inputArea background_color_1"', NULL),
('EM000150', 0, 'putoutLoan', 'start_page', 'produceInfoDiv', 'produceInfoDivTitle', 'divTitle', '产品信息', 'class="content_title"', NULL),
('EM000151', 0, 'putoutLoan', 'start_page', 'produceInfoDiv', 'lineId', 'selectOption', '资金方ID', '', NULL),
('EM000152', 0, 'putoutLoan', 'start_page', 'produceInfoDiv', 'productId', 'selectOption', '产品ID', '', NULL),
('EM000153', 0, 'putoutLoan', 'start_page', 'produceInfoDiv', 'productType', 'input', '产品类型', '', NULL),
('EM000154', 0, 'putoutLoan', 'start_page', 'produceInfoDiv', 'collaborate', 'input', '合作模式', '', NULL),
('EM000155', 0, 'putoutLoan', 'start_page', 'inputArea', 'loanInfoDiv', 'div', '贷款信息div', 'class="inputArea background_color_1"', NULL),
('EM000156', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanInfoDivTitle', 'divTitle', '贷款信息', 'class="content_title"', NULL),
('EM000157', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'applyNo', 'input', '申请编号', '', NULL),
('EM000158', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanNo', 'input', '出账编号', '', NULL),
('EM000159', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanTerm', 'input', '贷款期次', '', NULL),
('EM000160', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'businessSum', 'input', '放款金额', '', NULL),
('EM000161', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'putoutDate', 'input', '放款日期', '', NULL),
('EM000162', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'maturityDate', 'input', '到期日期', '', NULL),
('EM000163', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'beginDate', 'input', '首次还款日', '', NULL),
('EM000164', 0, 'putoutLoan', 'start_page', 'loanInfoDiv', 'returnMethod', 'input', '还款方式', '', NULL),
('EM000165', 0, 'putoutLoan', 'start_page', 'inputArea', 'rateInfoDiv', 'div', '利率信息div', 'class="inputArea background_color_1"', NULL),
('EM000166', 0, 'putoutLoan', 'start_page', 'rateInfoDiv', 'rateInfoDivTitle', 'divTitle', '利率信息', 'class="content_title"', NULL),
('EM000167', 0, 'putoutLoan', 'start_page', 'rateInfoDiv', 'executeRate', 'input', '执行利率', '', NULL),
('EM000168', 0, 'putoutLoan', 'start_page', 'rateInfoDiv', 'baseDays', 'input', '利率基准天数', '', NULL),
('EM000169', 0, 'putoutLoan', 'start_page', 'rateInfoDiv', 'graceTerm', 'input', '宽限期', '', NULL),
('EM000170', 0, 'putoutLoan', 'start_page', 'rateInfoDiv', 'fineRate', 'input', '罚息利率', '', NULL),
('EM000171', 0, 'putoutLoan', 'start_page', 'rateInfoDiv', 'compoundRate', 'input', '复利利率', '', NULL),
('EM000172', 0, 'putoutLoan', 'start_page', 'inputArea', 'ownerInfoDiv', 'div', '归属信息div', 'class="inputArea background_color_1"', NULL),
('EM000173', 0, 'putoutLoan', 'start_page', 'ownerInfoDiv', 'ownerInfoDivTitle', 'divTitle', '归属信息', 'class="content_title"', NULL),
('EM000174', 0, 'putoutLoan', 'start_page', 'ownerInfoDiv', 'operateOrgId', 'input', '入账机构', '', NULL),
('EM000175', 0, 'putoutLoan', 'start_page', 'ownerInfoDiv', 'accountOwnerCd', 'input', '账务归属', '', NULL),
('EM000176', 0, 'putoutLoan', 'start_page', 'inputArea', 'buttonDiv', 'div', '按钮div', 'class="inputArea background_color_1 center"', NULL),
('EM000177', 0, 'putoutLoan', 'start_page', 'buttonDiv', 'submitButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000178', 0, 'SearchText', 'start_page', 'contentArea', 'inputArea', 'div', '', 'class="inputArea background_color_1"', NULL),
('EM000179', 0, 'SearchText', 'start_page', 'inputArea', 'dirPath', 'input', '文件夹路径', '', NULL),
('EM000180', 0, 'SearchText', 'start_page', 'inputArea', 'searchText', 'input', '查找关键词', '', NULL),
('EM000181', 0, 'SearchText', 'start_page', 'inputArea', 'searchButton', 'button', '查找', 'class="inputArea_sub_button"', NULL),
('EM000182', 0, 'SearchText', 'start_page', 'contentArea', 'outputArea', 'div', '', 'class="inputArea background_color_1 showWithRowDivFlex"', NULL),
('EM000183', 0, 'SearchText', 'start_page', 'outputArea', 'dataTable', 'table', '输出数据table', 'class="output_table"', NULL),
('EM000184', 0, 'ResetSeq', 'start_page', 'contentArea', 'inputArea', 'div', 'div', '', NULL),
('EM000185', 0, 'ResetSeq', 'start_page', 'inputArea', 'resetButton', 'button', '重置表序号', 'class="inputArea_sub_button"', NULL);


UPDATE web_element SET sort_no =web_element_id WHERE 1=1;
UPDATE web_element SET element_no ='EM'||'00000'||web_element_id WHERE web_element_id <10;
UPDATE web_element SET element_no ='EM'||'0000'||web_element_id WHERE web_element_id >=10 AND web_element_id <100;
UPDATE web_element SET element_no ='EM'||'000'||web_element_id WHERE web_element_id >=100;

UPDATE web_call_after
SET param = '{"refreshFlag":"Y","refreshPage":"' || (SELECT we.element_no FROM web_element we WHERE menu ='MenuMaintain' AND page ='ele_page' AND element_parent ='inputArea') || '"}'
WHERE param LIKE '%refreshPage%'
AND process_bean IN ('addNewEle','edtEle','delNewEle','ele_move_up','ele_move_down');

UPDATE web_call_after
SET param = '{"refreshFlag":"Y","refreshPage":"' || (SELECT we.element_no FROM web_element we WHERE menu ='MenuMaintain' AND page ='evn_page' AND element_parent ='inputArea') || '"}'
WHERE param LIKE '%refreshPage%'
AND process_bean IN ('addNewEvn','edtNewEvn','delNewEvn');

UPDATE web_call_after
SET param = '{"refreshFlag":"Y","refreshPage":"' || (SELECT we.element_no FROM web_element we WHERE menu ='MenuMaintain' AND page ='dta_page' AND element_parent ='inputArea') || '"}'
WHERE param LIKE '%refreshPage%'
AND process_bean IN ('addNewDta','edtNewDta','delNewDta');

UPDATE web_call_after
SET param = '{"refreshFlag":"Y","refreshPage":"' || (SELECT we.element_no FROM web_element we WHERE menu ='MenuMaintain' AND page ='aft_page' AND element_parent ='inputArea') || '"}'
WHERE param LIKE '%refreshPage%'
AND process_bean IN ('addNewAft','edtNewAft','delNewAft');

