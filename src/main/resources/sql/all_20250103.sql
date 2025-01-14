-- web_menu definition

CREATE TABLE web_menu (
  web_menu_id integer primary key, -- 主键
  menu_no varchar(20), -- 菜单号
  sort_no integer, -- 排序号
  menu varchar(32) NOT NULL,
  menu_parent varchar(32),
  menu_desc varchar(32) );

CREATE INDEX wm_ind_01 on web_menu (menu_parent,sort_no);
CREATE INDEX wm_ind_02 on web_menu (menu);

-- web_element definition

CREATE TABLE web_element (
  web_element_id integer primary key,
  element_no varchar(20) NOT NULL,
  sort_no integer, -- 排序号
  menu varchar(32) NOT NULL,
  page varchar(32) NOT NULL,
  element_parent varchar(32),
  element varchar(32),
  element_type varchar(32),
  element_desc varchar(200),
  element_attr varchar(200) , param varchar(500));

CREATE INDEX we_ind_01 on web_element (element_no);
CREATE INDEX we_ind_02 on web_element (menu,page);


CREATE TABLE web_event (
  web_event_id integer primary key,
  menu varchar(32) NOT NULL,
  page varchar(32) NOT NULL,
  element varchar(32),
  event_type varchar(32),
  request_type varchar(32),
  request_bean varchar(32),
  next_page varchar(40),
  param varchar(500) );

CREATE INDEX wev_ind_01 on web_event (menu,element);

CREATE TABLE web_call_after (
  web_call_after_id integer primary key,
  menu varchar(32) NOT NULL,
  page varchar(32) NOT NULL,
  process_bean varchar(32),
  -- bean处理成功失败状态
  process_status varchar(20),
  -- 页面需要操作的动作类型
  opr_type varchar(32),
  request_type varchar(32),
  request_bean varchar(32),
  param varchar(500) );

CREATE INDEX wca_ind_01 on web_call_after (menu,page,process_bean);

CREATE TABLE web_data (
    web_data_id integer primary key,
    menu varchar(20),
    page varchar(20),
    element varchar(20),
    data_type varchar(20),
    data_attr varchar(20),
    express varchar(20) );

CREATE INDEX wd_ind_01 on web_data (menu,page,element);

-- ---------------------------------------------------
DELETE FROM web_menu;
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0001', 1, 'SystemMaintain', 'root', '系统维护'),
('MN0002', 2, 'MenuMaintain', 'SystemMaintain', '菜单维护'),
('MN0003', 7, 'lcs', 'root', '核算系统'),
('MN0004', 8, 'putoutLoan', 'lcs', '放款'),
('MN0007', 3, 'tools', 'root', '工具'),
('MN0008', 4, 'SearchText', 'tools', '运维记录查询'),
('MN0009', 5, 'splitString', 'tools', '拆分字符串'),
('MN0010', 6, 'ResetSeq', 'tools', '重置表序号');


DELETE FROM web_element;
INSERT INTO web_element(element_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
('EM000001', 'MenuMaintain', 'start_page', 'contentArea', 'inputArea', 'div', '输入区域', 'class="inputArea"', NULL),
('EM000002', 'MenuMaintain', 'start_page', 'inputArea', 'funDiv', 'div', '输入区域', 'class="inputArea"', NULL),
('EM000003', 'MenuMaintain', 'start_page', 'funDiv', 'addMenu', 'button', '新增菜单', 'class="inputArea_sub_button"', NULL),
('EM000004', 'MenuMaintain', 'start_page', 'inputArea', 'menuTable', 'table', '菜单列表', 'class="output_table"', '{"hideFields":["web_menu_id"]}'),
('EM000005', 'MenuMaintain', 'start_page', 'menuTable', 'menu_move_up', 'table_record_button', '上移', 'class="label_button"', NULL),
('EM000006', 'MenuMaintain', 'start_page', 'menuTable', 'menu_move_down', 'table_record_button', '下移', 'class="label_button"', NULL),
('EM000007', 'MenuMaintain', 'start_page', 'menuTable', 'edt_menu_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000008', 'MenuMaintain', 'start_page', 'menuTable', 'del_menu_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000009', 'MenuMaintain', 'start_page', 'menuTable', 'dsp_ele_button', 'table_record_button', '元素', 'class="label_button"', NULL),
('EM000010', 'MenuMaintain', 'start_page', 'menuTable', 'dsp_eve_button', 'table_record_button', '事件', 'class="label_button"', NULL),
('EM000011', 'MenuMaintain', 'start_page', 'menuTable', 'dsp_dta_button', 'table_record_button', '数据', 'class="label_button"', NULL),
('EM000012', 'MenuMaintain', 'start_page', 'menuTable', 'dsp_aft_button', 'table_record_button', '事后处理步骤', 'class="label_button"', NULL),
('EM000013', 'MenuMaintain', 'add_menu_sbw', 'body', 'add_menu_sub_window', 'subWindow', '新增菜单', '', NULL),
('EM000014', 'MenuMaintain', 'add_menu_sbw', 'add_menu_sub_window_swBody', 'add_menu_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000015', 'MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000016', 'MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 'menuParent', 'input', '父级菜单(顶级为root)', '', NULL),
('EM000017', 'MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 'menuDesc', 'input', '菜单名称', '', NULL),
('EM000018', 'MenuMaintain', 'add_menu_sbw', 'add_menu_sub_window_swFooter', 'addMenuButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000019', 'MenuMaintain', 'edt_menu_sbw', 'body', 'edt_menu_sub_window', 'subWindow', '编辑菜单', '', NULL),
('EM000020', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sub_window_swBody', 'edt_menu_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000021', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'web_menu_id', 'input', 'id', '', '{"hide":true}'),
('EM000022', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu_no', 'input', '菜单编号', '', NULL),
('EM000023', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'sort_no', 'input', '菜单序号', '', NULL),
('EM000024', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000025', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu_parent', 'input', '父级菜单(顶级为root)', '', NULL),
('EM000026', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 'menu_desc', 'input', '菜单名称', '', NULL),
('EM000027', 'MenuMaintain', 'edt_menu_sbw', 'edt_menu_sub_window_swFooter', 'edtMenuButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000028', 'MenuMaintain', 'ele_page', 'inputArea', 'eleTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000029', 'MenuMaintain', 'ele_page', 'eleTableDiv', 'addEle', 'button', '新增页面元素', 'class="inputArea_sub_button"', NULL),
('EM000030', 'MenuMaintain', 'ele_page', 'eleTableDiv', 'eleTable', 'table', '元素列表', 'class="output_table"', '{"hideFields":["web_element_id"]}'),
('EM000031', 'MenuMaintain', 'ele_page', 'eleTable', 'ele_move_up', 'table_record_button', '上移', 'class="label_button"', NULL),
('EM000032', 'MenuMaintain', 'ele_page', 'eleTable', 'ele_move_down', 'table_record_button', '下移', 'class="label_button"', NULL),
('EM000033', 'MenuMaintain', 'ele_page', 'eleTable', 'edt_ele_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000034', 'MenuMaintain', 'ele_page', 'eleTable', 'del_ele_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000035', 'MenuMaintain', 'add_ele_sbw', 'body', 'add_ele_sub_window', 'subWindow', '新增页面元素', '', NULL),
('EM000036', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sub_window_swBody', 'add_ele_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000037', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000038', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000039', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementParent', 'input', '父级元素', '', NULL),
('EM000040', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000041', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementType', 'input', '元素类型', '', NULL),
('EM000042', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementDesc', 'input', '元素名称', '', NULL),
('EM000043', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'elementAttr', 'input', '属性', '', NULL),
('EM000044', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 'param', 'input', '参数', '', NULL),
('EM000045', 'MenuMaintain', 'add_ele_sbw', 'add_ele_sub_window_swFooter', 'addEleButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000046', 'MenuMaintain', 'edt_ele_sbw', 'body', 'edt_ele_sub_window', 'subWindow', '编辑页面元素', '', NULL),
('EM000047', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sub_window_swBody', 'edt_ele_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000048', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'web_element_id', 'input', 'id', '', '{"hide":true}'),
('EM000049', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_no', 'input', '元素编号', '', NULL),
('EM000050', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'sort_no', 'input', '元素序号', '', NULL),
('EM000051', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000052', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000053', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_parent', 'input', '父级元素', '', NULL),
('EM000054', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000055', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_type', 'input', '元素类型', '', NULL),
('EM000056', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_desc', 'input', '元素名称', '', ''),
('EM000057', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'element_attr', 'input', '属性', '', NULL),
('EM000058', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 'param', 'input', '参数', '', NULL),
('EM000059', 'MenuMaintain', 'edt_ele_sbw', 'edt_ele_sub_window_swFooter', 'edtEleButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000060', 'MenuMaintain', 'evn_page', 'inputArea', 'evnTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000061', 'MenuMaintain', 'evn_page', 'evnTableDiv', 'addEvn', 'button', '新增页面事件', 'class="inputArea_sub_button"', NULL),
('EM000062', 'MenuMaintain', 'evn_page', 'evnTableDiv', 'evnTable', 'table', '元素事件列表', 'class="output_table"', '{"hideFields":["web_event_id"]}'),
('EM000063', 'MenuMaintain', 'evn_page', 'evnTable', 'edt_evn_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000064', 'MenuMaintain', 'evn_page', 'evnTable', 'del_evn_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000065', 'MenuMaintain', 'add_evn_sbw', 'body', 'add_evn_sub_window', 'subWindow', '新增页面事件', '', ''),
('EM000066', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sub_window_swBody', 'add_evn_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000068', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000069', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000070', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000071', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'eventType', 'input', '事件类型', '', NULL),
('EM000072', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'requestType', 'input', '请求类型', '', NULL),
('EM000073', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'requestBean', 'input', '请求处理的bean', '', NULL),
('EM000074', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'nextPage', 'input', '跳转显示页', '', NULL),
('EM000075', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 'param', 'input', '参数(JSON)', '', NULL),
('EM000076', 'MenuMaintain', 'add_evn_sbw', 'add_evn_sub_window_swFooter', 'addEvnButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000077', 'MenuMaintain', 'edt_evn_sbw', 'body', 'edt_evn_sub_window', 'subWindow', '编辑页面事件', '', ''),
('EM000078', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sub_window_swBody', 'edt_evn_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000079', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'web_event_id', 'input', 'web_event_id', '', '{"hide":true}'),
('EM000080', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000081', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000082', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000083', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'event_type', 'input', '事件类型', '', NULL),
('EM000084', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'request_type', 'input', '请求类型', '', NULL),
('EM000085', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'request_bean', 'input', '请求处理的bean', '', NULL),
('EM000086', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'next_page', 'input', '跳转显示页', '', NULL),
('EM000087', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 'param', 'input', '参数(JSON)', '', NULL),
('EM000088', 'MenuMaintain', 'edt_evn_sbw', 'edt_evn_sub_window_swFooter', 'edtEvnButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000089', 'MenuMaintain', 'dta_page', 'inputArea', 'dtaTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000090', 'MenuMaintain', 'dta_page', 'dtaTableDiv', 'addDta', 'button', '新增页面数据', 'class="inputArea_sub_button"', NULL),
('EM000091', 'MenuMaintain', 'dta_page', 'dtaTableDiv', 'dtaTable', 'table', '元素数据列表', 'class="output_table"', '{"hideFields":["web_data_id"]}'),
('EM000092', 'MenuMaintain', 'dta_page', 'dtaTable', 'edt_dta_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000093', 'MenuMaintain', 'dta_page', 'dtaTable', 'del_dta_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000094', 'MenuMaintain', 'add_dta_sbw', 'body', 'add_dta_sub_window', 'subWindow', '新增页面数据', '', NULL),
('EM000095', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sub_window_swBody', 'add_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000097', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000098', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000099', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000100', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'dataType', 'input', '数据类型', '', NULL),
('EM000101', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'dataAttr', 'input', '数据属性', '', NULL),
('EM000102', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 'express', 'input', '表达式', '', NULL),
('EM000103', 'MenuMaintain', 'add_dta_sbw', 'add_dta_sub_window_swFooter', 'addDtaButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000104', 'MenuMaintain', 'edt_dta_sbw', 'body', 'edt_dta_sub_window', 'subWindow', '编辑页面数据', '', NULL),
('EM000105', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sub_window_swBody', 'edt_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000106', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'web_data_id', 'input', 'web_data_id', '', '{"hide":true}'),
('EM000107', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000108', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000109', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'element', 'input', '元素', '', NULL),
('EM000110', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'data_type', 'input', '数据类型', '', NULL),
('EM000111', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'data_attr', 'input', '数据属性', '', NULL),
('EM000112', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 'express', 'input', '表达式', '', NULL),
('EM000113', 'MenuMaintain', 'edt_dta_sbw', 'edt_dta_sub_window_swFooter', 'edtDtaButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000114', 'MenuMaintain', 'aft_page', 'inputArea', 'aftTableDiv', 'div', '', 'class="inputArea"', NULL),
('EM000115', 'MenuMaintain', 'aft_page', 'aftTableDiv', 'addAft', 'button', '新增页面事后处理步骤', 'class="inputArea_sub_button"', NULL),
('EM000116', 'MenuMaintain', 'aft_page', 'aftTableDiv', 'aftTable', 'table', '事后处理步骤列表', 'class="output_table"', '{"hideFields":["web_call_after_id"]}'),
('EM000117', 'MenuMaintain', 'aft_page', 'aftTable', 'edt_aft_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
('EM000118', 'MenuMaintain', 'aft_page', 'aftTable', 'del_aft_button', 'table_record_button', '删除', 'class="label_button"', NULL),
('EM000119', 'MenuMaintain', 'add_aft_sbw', 'body', 'add_aft_sub_window', 'subWindow', '新增页面事后处理步骤', '', NULL),
('EM000120', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sub_window_swBody', 'add_aft_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000122', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000123', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000124', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'processBean', 'input', '处理bean', '', NULL),
('EM000125', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'processStatus', 'input', '处理状态', '', NULL),
('EM000126', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'oprType', 'input', '操作类型', '', NULL),
('EM000127', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'requestType', 'input', '请求类型', '', NULL),
('EM000128', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'requestBean', 'input', '请求Bean', '', NULL),
('EM000129', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 'param', 'input', '参数', '', NULL),
('EM000130', 'MenuMaintain', 'add_aft_sbw', 'add_aft_sub_window_swFooter', 'addAftButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
('EM000131', 'MenuMaintain', 'edt_aft_sbw', 'body', 'edt_aft_sub_window', 'subWindow', '编辑页面事后处理步骤', '', NULL),
('EM000132', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sub_window_swBody', 'edt_aft_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
('EM000133', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'web_call_after_id', 'input', 'web_call_after_id', '', '{"hide":true}'),
('EM000134', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'menu', 'input', '菜单', '', NULL),
('EM000135', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'page', 'input', '页面', '', NULL),
('EM000136', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'process_bean', 'input', '处理bean', '', ''),
('EM000137', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'process_status', 'input', '处理状态', '', ''),
('EM000138', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'opr_type', 'input', '操作类型', '', ''),
('EM000139', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 'request_type', 'input', '请求类型', '', ''),
('EM000140', 'MenuMaintain', 'edt_aft_sbw', 'edt_aft_sub_window_swFooter', 'edtAftButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),

('EM000141', 'putoutLoan', 'start_page', 'contentArea', 'inputArea', 'div', '输入区域', 'class="inputArea showWithRowDivFlex"', NULL),
('EM000142', 'putoutLoan', 'start_page', 'inputArea', 'transInfoDiv', 'div', '交易信息div', 'class="inputArea background_color_1"', NULL),
('EM000143', 'putoutLoan', 'start_page', 'transInfoDiv', 'transInfoDivTitle', 'divTitle', '交易信息', 'class="content_title"', NULL),
('EM000144', 'putoutLoan', 'start_page', 'transInfoDiv', 'transSeq', 'input', '交易流水号', '', NULL),
('EM000145', 'putoutLoan', 'start_page', 'transInfoDiv', 'transDate', 'input', '交易日期', 'type="date";value="2025-01-02"', ''),
('EM000146', 'putoutLoan', 'start_page', 'transInfoDiv', 'transChannel', 'input', '交易渠道', '', NULL),
('EM000147', 'putoutLoan', 'start_page', 'transInfoDiv', 'businessSource', 'input', '业务来源', '', NULL),
('EM000148', 'putoutLoan', 'start_page', 'transInfoDiv', 'businessChannel', 'input', '业务渠道', '', NULL),
('EM000149', 'putoutLoan', 'start_page', 'inputArea', 'customerInfoDiv', 'div', '客户信息div', 'class="inputArea background_color_1"', NULL),
('EM000150', 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerInfoDivTitle', 'divTitle', '客户信息', 'class="content_title"', NULL),
('EM000151', 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerName', 'input', '客户姓名', '', NULL),
('EM000152', 'putoutLoan', 'start_page', 'customerInfoDiv', 'certNo', 'input', '身份证号', '', NULL),
('EM000153', 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerPhone', 'input', '客户手机号', '', NULL),
('EM000154', 'putoutLoan', 'start_page', 'inputArea', 'produceInfoDiv', 'div', '产品信息输入区域', 'class="inputArea background_color_1"', NULL),
('EM000155', 'putoutLoan', 'start_page', 'produceInfoDiv', 'produceInfoDivTitle', 'divTitle', '产品信息', 'class="content_title"', NULL),
('EM000156', 'putoutLoan', 'start_page', 'produceInfoDiv', 'lineId', 'selectOption', '资金方ID', '', NULL),
('EM000157', 'putoutLoan', 'start_page', 'produceInfoDiv', 'productId', 'selectOption', '产品ID', '', NULL),
('EM000158', 'putoutLoan', 'start_page', 'produceInfoDiv', 'productType', 'input', '产品类型', '', NULL),
('EM000159', 'putoutLoan', 'start_page', 'produceInfoDiv', 'collaborate', 'input', '合作模式', '', NULL),
('EM000160', 'putoutLoan', 'start_page', 'inputArea', 'loanInfoDiv', 'div', '贷款信息div', 'class="inputArea background_color_1"', NULL),
('EM000161', 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanInfoDivTitle', 'divTitle', '贷款信息', 'class="content_title"', NULL),
('EM000162', 'putoutLoan', 'start_page', 'loanInfoDiv', 'applyNo', 'input', '申请编号', '', NULL),
('EM000163', 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanNo', 'input', '出账编号', '', NULL),
('EM000164', 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanTerm', 'input', '贷款期次', '', NULL),
('EM000165', 'putoutLoan', 'start_page', 'loanInfoDiv', 'businessSum', 'input', '放款金额', '', NULL),
('EM000166', 'putoutLoan', 'start_page', 'loanInfoDiv', 'putoutDate', 'input', '放款日期', '', NULL),
('EM000167', 'putoutLoan', 'start_page', 'loanInfoDiv', 'maturityDate', 'input', '到期日期', '', NULL),
('EM000168', 'putoutLoan', 'start_page', 'loanInfoDiv', 'beginDate', 'input', '首次还款日', '', NULL),
('EM000169', 'putoutLoan', 'start_page', 'loanInfoDiv', 'returnMethod', 'input', '还款方式', '', NULL),
('EM000170', 'putoutLoan', 'start_page', 'inputArea', 'rateInfoDiv', 'div', '利率信息div', 'class="inputArea background_color_1"', NULL),
('EM000171', 'putoutLoan', 'start_page', 'rateInfoDiv', 'rateInfoDivTitle', 'divTitle', '利率信息', 'class="content_title"', NULL),
('EM000172', 'putoutLoan', 'start_page', 'rateInfoDiv', 'executeRate', 'input', '执行利率', '', NULL),
('EM000173', 'putoutLoan', 'start_page', 'rateInfoDiv', 'baseDays', 'input', '利率基准天数', '', NULL),
('EM000174', 'putoutLoan', 'start_page', 'rateInfoDiv', 'graceTerm', 'input', '宽限期', '', NULL),
('EM000175', 'putoutLoan', 'start_page', 'rateInfoDiv', 'fineRate', 'input', '罚息利率', '', NULL),
('EM000176', 'putoutLoan', 'start_page', 'rateInfoDiv', 'compoundRate', 'input', '复利利率', '', NULL),
('EM000177', 'putoutLoan', 'start_page', 'inputArea', 'ownerInfoDiv', 'div', '归属信息div', 'class="inputArea background_color_1"', NULL),
('EM000178', 'putoutLoan', 'start_page', 'ownerInfoDiv', 'ownerInfoDivTitle', 'divTitle', '归属信息', 'class="content_title"', NULL),
('EM000179', 'putoutLoan', 'start_page', 'ownerInfoDiv', 'operateOrgId', 'input', '入账机构', '', NULL),
('EM000180', 'putoutLoan', 'start_page', 'ownerInfoDiv', 'accountOwnerCd', 'input', '账务归属', '', NULL),
('EM000181', 'putoutLoan', 'start_page', 'inputArea', 'buttonDiv', 'div', '按钮div', 'class="inputArea background_color_1 center"', NULL),
('EM000182', 'putoutLoan', 'start_page', 'buttonDiv', 'submitButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),

('EM000183', 'SearchText', 'start_page', 'contentArea', 'inputArea', 'div', '', 'class="inputArea background_color_1"', NULL),
('EM000184', 'SearchText', 'start_page', 'inputArea', 'dirPath', 'input', '文件夹路径', '', NULL),
('EM000185', 'SearchText', 'start_page', 'inputArea', 'searchText', 'input', '查找关键词', '', NULL),
('EM000186', 'SearchText', 'start_page', 'inputArea', 'searchButton', 'button', '查找', 'class="inputArea_sub_button"', NULL),
('EM000187', 'SearchText', 'start_page', 'contentArea', 'outputArea', 'div', '', 'class="inputArea background_color_1 showWithRowDivFlex"', NULL),
('EM000188', 'SearchText', 'start_page', 'outputArea', 'dataTable', 'table', '输出数据table', 'class="output_table"', '{"showSeq":true}'),

('EM000200', 'SearchText', 'dsp_text_sbw', 'body', 'dsp_text_sub_window_subWindowBackGround', 'div', 'subWindowBackGround', 'class="subWindowBackGround"', ''),
('EM000202', 'SearchText', 'dsp_text_sbw', 'dsp_text_sub_window_subWindowBackGround', 'dsp_text_sub_window_subWindow', 'div', 'subWindow', 'class="subWindow ui-draggable ui-draggable-handle"', ''),
('EM000203', 'SearchText', 'dsp_text_sbw', 'dsp_text_sub_window_subWindow', 'dsp_text_sub_window_subWidowContent', 'div', 'WidowContent', 'class="subWidowContent"', ''),
('EM000204', 'SearchText', 'dsp_text_sbw', 'dsp_text_sub_window_subWidowContent', 'dsp_text_sub_window_swHeader', 'div', 'swHeader', 'class="subWidowHeader"', ''),
('EM000204', 'SearchText', 'dsp_text_sbw', 'dsp_text_sub_window_swHeader', 'titleText', 'span', '编辑菜单', '', ''),
('EM000204', 'SearchText', 'dsp_text_sbw', 'dsp_text_sub_window_swHeader', 'edt_menu_sub_window_header-x-div', 'div', 'x', 'class="subWidowHeaderCloseBtn";onclick="hide(dsp_text_sub_window_subWindowBackGround)"', '{"showText":true}'),
('EM000204', 'SearchText', 'dsp_text_sbw', 'dsp_text_sub_window_subWidowContent', 'dsp_text_sub_window_swBody', 'div', 'swBody', 'class="subWidowBody"', ''),
('EM000201', 'SearchText', 'dsp_text_sbw', 'dsp_text_sub_window_swBody', 'textTextarea', 'textarea', '内容', '', ''),

('EM000189', 'ResetSeq', 'start_page', 'contentArea', 'inputArea', 'div', 'div', '', NULL),
('EM000190', 'ResetSeq', 'start_page', 'inputArea', 'resetButton', 'button', '重置表序号', 'class="inputArea_sub_button"', NULL),

('EM000191', 'splitString', 'start_page', 'contentArea', 'inputArea', 'div', '输入区域', 'class="inputArea"', NULL),
('EM000192', 'splitString', 'start_page', 'inputArea', 'inputStr', 'input', '字符', '', NULL),
('EM000193', 'splitString', 'start_page', 'inputArea', 'addString', 'selectOption', '加', '', NULL),
('EM000194', 'splitString', 'start_page', 'inputArea', 'separator', 'selectOption', '分隔符', '', NULL),
('EM000195', 'splitString', 'start_page', 'inputArea', 'colNum', 'input', '分几列', '', NULL),
('EM000196', 'splitString', 'start_page', 'inputArea', 'processButton', 'button', '处理', 'class="inputArea_sub_button"', NULL),
('EM000197', 'splitString', 'start_page', 'contentArea', 'outputArea', 'div', '输出区域', 'class="inputArea background_color_1 showWithRowDivFlex"', NULL),
('EM000198', 'splitString', 'start_page', 'outputArea', 'outputEle', 'textarea', '输出信息', '', NULL);


DELETE FROM web_event;
INSERT INTO web_event(menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
('MenuMaintain', 'menuEvent', '1', 'click', 'menuReq', 'MenuMaintain', NULL, NULL),

('MenuMaintain', 'start_page', 'addMenu', 'click', 'swDataReq', 'MenuMaintain', 'add_menu_sbw', NULL),
('MenuMaintain', 'start_page', 'menu_move_up', 'click', 'buttonReq', 'menuMoveUp', NULL, NULL),
('MenuMaintain', 'start_page', 'menu_move_down', 'click', 'buttonReq', 'menuMoveDown', NULL, NULL),
('MenuMaintain', 'start_page', 'edt_menu_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_menu_sbw', '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'start_page', 'del_menu_button', 'click', 'buttonReq', 'menuDel', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),

('MenuMaintain', 'add_menu_sbw', 'addMenuButton', 'click', 'buttonReq', 'menuAdd', NULL, NULL),
('MenuMaintain', 'edt_menu_sbw', 'edtMenuButton', 'click', 'buttonReq', 'menuEdt', NULL, NULL),

('MenuMaintain', 'start_page', 'dsp_ele_button', 'click', 'webDataReq', 'getWebElement', 'EM000028', NULL),
('MenuMaintain', 'start_page', 'dsp_eve_button', 'click', 'webDataReq', 'getWebElement', 'EM000060', NULL),
('MenuMaintain', 'start_page', 'dsp_dta_button', 'click', 'webDataReq', 'getWebElement', 'EM000089', NULL),
('MenuMaintain', 'start_page', 'dsp_aft_button', 'click', 'webDataReq', 'getWebElement', 'EM000114', NULL),

('MenuMaintain', 'ele_page', 'addEle', 'click', 'swDataReq', 'xxxx', 'add_ele_sbw', NULL),
('MenuMaintain', 'ele_page', 'ele_move_up', 'click', 'buttonReq', 'elementMoveUp', '', NULL),
('MenuMaintain', 'ele_page', 'ele_move_down', 'click', 'buttonReq', 'elementMoveDown', '', NULL),
('MenuMaintain', 'ele_page', 'edt_ele_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_ele_sbw', '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'ele_page', 'del_ele_button', 'click', 'buttonReq', 'eleDel', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),
('MenuMaintain', 'add_ele_sbw', 'addEleButton', 'click', 'buttonReq', 'eleAdd', NULL, NULL),
('MenuMaintain', 'edt_ele_sbw', 'edtEleButton', 'click', 'buttonReq', 'eleEdt', NULL, NULL),
('MenuMaintain', 'evn_page', 'addEvn', 'click', 'swDataReq', 'xxxx', 'add_evn_sbw', NULL),
('MenuMaintain', 'evn_page', 'edt_evn_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_evn_sbw', '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'evn_page', 'del_evn_button', 'click', 'buttonReq', 'evnDel', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),
('MenuMaintain', 'add_evn_sbw', 'addEvnButton', 'click', 'buttonReq', 'evnAdd', NULL, NULL),
('MenuMaintain', 'edt_evn_sbw', 'edtEvnButton', 'click', 'buttonReq', 'evnEdt', NULL, NULL),
('MenuMaintain', 'dta_page', 'addDta', 'click', 'swDataReq', 'xxxx', 'add_dta_sbw', NULL),
('MenuMaintain', 'dta_page', 'edt_dta_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_dta_sbw', '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'dta_page', 'del_dta_button', 'click', 'buttonReq', 'dtaDel', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),
('MenuMaintain', 'add_dta_sbw', 'addDtaButton', 'click', 'buttonReq', 'dtaAdd', NULL, NULL),
('MenuMaintain', 'edt_dta_sbw', 'edtDtaButton', 'click', 'buttonReq', 'dtaEdt', NULL, NULL),
('MenuMaintain', 'aft_page', 'addAft', 'click', 'swDataReq', 'xxxx', 'add_aft_sbw', NULL),
('MenuMaintain', 'aft_page', 'edt_aft_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_aft_sbw', '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'aft_page', 'del_aft_button', 'click', 'buttonReq', 'callAfterDel', '', '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),
('MenuMaintain', 'add_aft_sbw', 'addAftButton', 'click', 'buttonReq', 'callAfterAdd', '', ''),
('MenuMaintain', 'edt_aft_sbw', 'edtAftButton', 'click', 'buttonReq', 'callAfterEdt', '', ''),

('putoutLoan', 'menuEvent', '1', 'click', 'menuReq', 'putoutLoan', '', NULL),
('putoutLoan', 'start_page', 'submitButton', 'click', 'buttonReq', 'loanPutout', NULL, NULL),

('SearchText', 'menuEvent', '1', 'click', 'menuReq', 'SearchText', '', ''),
('SearchText', 'start_page', 'searchButton', 'click', 'webDataReq', 'getWebElement', '', '{"refreshFlag":"Y","refreshPage":"EM000187"}'),
('SearchText', 'start_page', 'dataTable', 'record_click', 'buttonReq', 'getTextContent', 'EM000200', '{"dataFillToEle":"textTextarea"}'),

('splitString', 'menuEvent', '1', 'click', 'menuReq', 'splitString', '', ''),
('splitString', 'start_page', 'processButton', 'click', 'buttonReq', 'prcStringUtil', 'EM000198', '');

DELETE FROM web_call_after;
INSERT INTO web_call_after(menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
('MenuMaintain', 'add_menu_sbw', 'addMenuButton', 'false', 'showMessage', NULL, NULL, '{"msg":"新增菜单失败"}'),
('MenuMaintain', 'add_menu_sbw', 'addMenuButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增菜单"}'),
('MenuMaintain', 'add_menu_sbw', 'addMenuButton', 'success', 'closeSw', '', '', '{"subWindowId":"add_menu_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_menu_sbw', 'addMenuButton', 'success', 'request', 'menuReq', 'MenuMaintain', ''),
('MenuMaintain', 'edt_menu_sbw', 'edtMenuButton', 'false', 'showMessage', NULL, NULL, '{"msg":"更新菜单失败"}'),
('MenuMaintain', 'edt_menu_sbw', 'edtMenuButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新菜单"}'),
('MenuMaintain', 'edt_menu_sbw', 'edtMenuButton', 'success', 'closeSw', '', '', '{"subWindowId":"edt_menu_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_menu_sbw', 'edtMenuButton', 'success', 'request', 'menuReq', 'MenuMaintain', ''),
('MenuMaintain', 'start_page', 'del_menu_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除菜单失败"}'),
('MenuMaintain', 'start_page', 'del_menu_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除菜单"}'),
('MenuMaintain', 'start_page', 'del_menu_button', 'success', 'request', 'menuReq', 'MenuMaintain', ''),
('MenuMaintain', 'add_ele_sbw', 'addEleButton', 'false', 'showMessage', NULL, NULL, '{"msg":"新增元素失败"}'),
('MenuMaintain', 'add_ele_sbw', 'addEleButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增元素"}'),
('MenuMaintain', 'add_ele_sbw', 'addEleButton', 'success', 'closeSw', '', '', '{"subWindowId":"add_ele_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_ele_sbw', 'addEleButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000028"}'),
('MenuMaintain', 'edt_ele_sbw', 'edtEleButton', 'false', 'showMessage', NULL, NULL, '{"msg":"更新元素失败"}'),
('MenuMaintain', 'edt_ele_sbw', 'edtEleButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新元素"}'),
('MenuMaintain', 'edt_ele_sbw', 'edtEleButton', 'success', 'closeSw', '', '', '{"subWindowId":"edt_ele_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_ele_sbw', 'edtEleButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000028"}'),
('MenuMaintain', 'ele_page', 'del_ele_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除元素失败"}'),
('MenuMaintain', 'ele_page', 'del_ele_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除元素"}'),
('MenuMaintain', 'ele_page', 'del_ele_button', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000028"}'),
('MenuMaintain', 'add_evn_sbw', 'addEvnButton', 'false', 'showMessage', NULL, NULL, '{"msg":"新增事件失败"}'),
('MenuMaintain', 'add_evn_sbw', 'addEvnButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增事件"}'),
('MenuMaintain', 'add_evn_sbw', 'addEvnButton', 'success', 'closeSw', '', '', '{"subWindowId":"add_evn_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_evn_sbw', 'addEvnButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000060"}'),
('MenuMaintain', 'edt_evn_sbw', 'edtEvnButton', 'false', 'showMessage', NULL, NULL, '{"msg":"更新事件失败"}'),
('MenuMaintain', 'edt_evn_sbw', 'edtEvnButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新事件"}'),
('MenuMaintain', 'edt_evn_sbw', 'edtEvnButton', 'success', 'closeSw', '', '', '{"subWindowId":"edt_evn_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_evn_sbw', 'edtEvnButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000060"}'),
('MenuMaintain', 'evn_page', 'del_evn_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除事件失败"}'),
('MenuMaintain', 'evn_page', 'del_evn_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除事件"}'),
('MenuMaintain', 'evn_page', 'del_evn_button', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000060"}'),
('MenuMaintain', 'add_dta_sbw', 'addDtaButton', 'false', 'showMessage', NULL, NULL, '{"msg":"新增数据配置失败"}'),
('MenuMaintain', 'add_dta_sbw', 'addDtaButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增数据配置"}'),
('MenuMaintain', 'add_dta_sbw', 'addDtaButton', 'success', 'closeSw', '', '', '{"subWindowId":"add_dta_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_dta_sbw', 'addDtaButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000089"}'),
('MenuMaintain', 'edt_dta_sbw', 'edtDtaButton', 'false', 'showMessage', NULL, NULL, '{"msg":"更新数据配置失败"}'),
('MenuMaintain', 'edt_dta_sbw', 'edtDtaButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新数据配置"}'),
('MenuMaintain', 'edt_dta_sbw', 'edtDtaButton', 'success', 'closeSw', '', '', '{"subWindowId":"edt_dta_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_dta_sbw', 'edtDtaButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000089"}'),
('MenuMaintain', 'dta_page', 'del_dta_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除数据配置失败"}'),
('MenuMaintain', 'dta_page', 'del_dta_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除数据配置"}'),
('MenuMaintain', 'dta_page', 'del_dta_button', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000089"}'),
('MenuMaintain', 'add_aft_sbw', 'addAftButton', 'false', 'showMessage', NULL, NULL, '{"msg":"新增事件处理后步骤配置失败"}'),
('MenuMaintain', 'add_aft_sbw', 'addAftButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增事件处理后步骤配置"}'),
('MenuMaintain', 'add_aft_sbw', 'addAftButton', 'success', 'closeSw', '', '', '{"subWindowId":"add_aft_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_aft_sbw', 'addAftButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000114"}'),
('MenuMaintain', 'edt_aft_sbw', 'edtAftButton', 'false', 'showMessage', NULL, NULL, '{"msg":"更新事件处理后步骤配置失败"}'),
('MenuMaintain', 'edt_aft_sbw', 'edtAftButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新事件处理后步骤配置"}'),
('MenuMaintain', 'edt_aft_sbw', 'edtAftButton', 'success', 'closeSw', '', '', '{"subWindowId":"edt_aft_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_aft_sbw', 'edtAftButton', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000114"}'),
('MenuMaintain', 'aft_page', 'del_aft_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除事件处理后步骤配置失败"}'),
('MenuMaintain', 'aft_page', 'del_aft_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除事件处理后步骤配置"}'),
('MenuMaintain', 'aft_page', 'del_aft_button', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000114"}'),
('MenuMaintain', 'start_page', 'dsp_ele_button', 'success', 'removeEle', '', '', '{"removeEleId":"evnTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_ele_button', 'success', 'removeEle', '', '', '{"removeEleId":"dtaTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_ele_button', 'success', 'removeEle', '', '', '{"removeEleId":"aftTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_eve_button', 'success', 'removeEle', '', '', '{"removeEleId":"eleTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_eve_button', 'success', 'removeEle', '', '', '{"removeEleId":"dtaTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_eve_button', 'success', 'removeEle', '', '', '{"removeEleId":"aftTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_dta_button', 'success', 'removeEle', '', '', '{"removeEleId":"eleTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_dta_button', 'success', 'removeEle', '', '', '{"removeEleId":"evnTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_dta_button', 'success', 'removeEle', '', '', '{"removeEleId":"aftTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_aft_button', 'success', 'removeEle', '', '', '{"removeEleId":"eleTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_aft_button', 'success', 'removeEle', '', '', '{"removeEleId":"evnTableDiv"}'),
('MenuMaintain', 'start_page', 'dsp_aft_button', 'success', 'removeEle', '', '', '{"removeEleId":"dtaTableDiv"}'),
('MenuMaintain', 'start_page', 'menu_move_up', 'success', 'request', 'menuReq', 'MenuMaintain', ''),
('MenuMaintain', 'start_page', 'menu_move_down', 'success', 'request', 'menuReq', 'MenuMaintain', ''),
('MenuMaintain', 'ele_page', 'ele_move_up', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000028"}'),
('MenuMaintain', 'ele_page', 'ele_move_down', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"EM000028"}');

DELETE FROM web_data;
INSERT INTO web_data(menu, page, "element", data_type, data_attr, express)VALUES
('MenuMaintain', 'start_page', 'menuTable', 'sql', NULL, 'select * from web_menu order by sort_no'),
('MenuMaintain', 'start_page', 'body_record_list', 'sql', NULL, 'select * from web_menu'),
('MenuMaintain', 'ele_page', 'eleTable', 'sql', NULL, 'select * from web_element where menu=#menu# order by sort_no'),
('MenuMaintain', 'evn_page', 'evnTable', 'sql', NULL, 'select * from web_event where menu=#menu# order by web_event_id'),
('MenuMaintain', 'dta_page', 'dtaTable', 'sql', NULL, 'select * from web_data where menu=#menu# order by web_data_id'),
('MenuMaintain', 'aft_page', 'aftTable', 'sql', NULL, 'select * from web_call_after where menu=#menu# order by web_call_after_id'),
('putoutLoan', 'start_page', 'lineId', '', 'optionList', '[{"value":"RL001","text":"中关村"},{"value":"RL002","text":"平安银行"}]'),
('SearchText', 'start_page', 'dataTable', 'fun', '', 'searchFromText'),
('splitString', 'start_page', 'addString', '', 'optionList', '[{"value":"single","text":"单引号"},{"value":"double","text":"双引号"}]'),
('splitString', 'start_page', 'separator', '', 'optionList', '[{"value":"comma","text":"逗号"},{"value":"semicolon","text":"分号"}]');



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
AND process_bean IN ('addEvnButton','edtEvnButton','del_evn_button');

UPDATE web_call_after
SET param = '{"refreshFlag":"Y","refreshPage":"' || (SELECT we.element_no FROM web_element we WHERE menu ='MenuMaintain' AND page ='dta_page' AND element_parent ='inputArea') || '"}'
WHERE param LIKE '%refreshPage%'
AND process_bean IN ('addDtaButton','edtDtaButton','del_dta_button');

UPDATE web_call_after
SET param = '{"refreshFlag":"Y","refreshPage":"' || (SELECT we.element_no FROM web_element we WHERE menu ='MenuMaintain' AND page ='aft_page' AND element_parent ='inputArea') || '"}'
WHERE param LIKE '%refreshPage%'
AND process_bean IN ('addAftButton','edtAftButton','del_aft_button');
