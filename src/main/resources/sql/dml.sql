
DELETE FROM web_element where menu='MenuMaintain';
INSERT INTO web_element(menu, page, element_parent, element_seq, "element", element_type, element_desc, element_attr)VALUES
('MenuMaintain', 'start_page', 'contentArea', 1, 'inputArea', 'div', '输入区域', 'class="inputArea"'),
('MenuMaintain', 'start_page', 'inputArea', 1, 'funDiv', 'div', '输入区域', 'class="inputArea"'),
('MenuMaintain', 'start_page', 'funDiv', 1, 'addMenu', 'button', '新增菜单', 'class="inputArea_sub_button"'),
('MenuMaintain', 'start_page', 'inputArea', 2, 'menuTable', 'table', '菜单列表', 'class="output_table"'),
('MenuMaintain', 'start_page', 'menuTable', 90, 'edt_menu_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 91, 'del_menu_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 92, 'dsp_ele_button', 'table_record_button', '元素', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 93, 'dsp_eve_button', 'table_record_button', '事件', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 94, 'dsp_dta_button', 'table_record_button', '数据', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 95, 'dsp_aft_button', 'table_record_button', '事后处理步骤', 'class="label_button"'),
-- 点击“新增菜单”按钮后的弹窗内容
('MenuMaintain', 'add_menu_sbw', 'body', 1, 'add_menu_sub_window', 'subWindow', '新增菜单', ''),
('MenuMaintain', 'add_menu_sbw', 'add_menu_sub_window_swBody', 1, 'add_menu_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 1, 'menuParent', 'input', '父级菜单(顶级为root)', ''),
('MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 2, 'menuSeq', 'input', '菜单序号', ''),
('MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 3, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'add_menu_sbw', 'add_menu_sbw_div', 4, 'menuDesc', 'input', '菜单名称', ''),
('MenuMaintain', 'add_menu_sbw', 'add_menu_sub_window_swFooter', 1, 'addMenuButton', 'button', '提交', 'class="inputArea_sub_button"'),
-- 点击表格记录中的“编辑”按钮后的弹窗内容
('MenuMaintain', 'edt_menu_sbw', 'body', 1, 'edt_menu_sub_window', 'subWindow', '编辑菜单', ''),
('MenuMaintain', 'edt_menu_sbw', 'edt_menu_sub_window_swBody', 1, 'edt_menu_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 1, 'web_menu_id', 'input', 'web_menu_id', ''),
('MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 2, 'menu_parent', 'input', '父级菜单(顶级为root)', ''),
('MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 3, 'menu_seq', 'input', '菜单序号', ''),
('MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 4, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'edt_menu_sbw', 'edt_menu_sbw_div', 5, 'menu_desc', 'input', '菜单名称', ''),
('MenuMaintain', 'edt_menu_sbw', 'edt_menu_sub_window_swFooter', 1, 'edtMenuButton', 'button', '提交', 'class="inputArea_sub_button"'),

-- 点击表格记录中的“维护页面元素”按钮后显示内容
('MenuMaintain', 'ele_page', 'inputArea', 201, 'eleTableDiv', 'div', '', 'class="inputArea"'),
('MenuMaintain', 'ele_page', 'eleTableDiv', 1, 'addEle', 'button', '新增页面元素', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_page', 'eleTableDiv', 2, 'eleTable', 'table', '元素列表', 'class="output_table"'),
('MenuMaintain', 'ele_page', 'eleTable', 998, 'edt_ele_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'ele_page', 'eleTable', 999, 'del_ele_button', 'table_record_button', '删除', 'class="label_button"'),
-- 点击“新增页面元素”按钮后的弹窗内容
('MenuMaintain', 'add_ele_sbw', 'body', 1, 'add_ele_sub_window', 'subWindow', '新增页面元素', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sub_window_swBody', 1, 'add_ele_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 1, 'webElementId', 'input', 'web_element_id', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 4, 'elementParent', 'input', '父级元素', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 5, 'elementSeq', 'input', '元素序号', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 6, 'element', 'input', '元素', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 7, 'elementType', 'input', '元素类型', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 8, 'elementDesc', 'input', '菜单名称', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sbw_div', 9, 'elementAttr', 'input', '属性', ''),
('MenuMaintain', 'add_ele_sbw', 'add_ele_sub_window_swFooter', 1, 'addEleButton', 'button', '提交', 'class="inputArea_sub_button"'),
-- 点击表格记录中的“编辑”按钮后的弹窗内容
('MenuMaintain', 'edt_ele_sbw', 'body', 1, 'edt_ele_sub_window', 'subWindow', '编辑页面元素', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sub_window_swBody', 1, 'edt_ele_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 1, 'web_element_id', 'input', 'web_element_id', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 4, 'element_parent', 'input', '父级元素', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 5, 'element_seq', 'input', '元素序号', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 6, 'element', 'input', '元素', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 7, 'element_type', 'input', '元素类型', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 8, 'element_desc', 'input', '菜单名称', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sbw_div', 9, 'element_attr', 'input', '属性', ''),
('MenuMaintain', 'edt_ele_sbw', 'edt_ele_sub_window_swFooter', 1, 'edtEleButton', 'button', '提交', 'class="inputArea_sub_button"'),

-- 点击表格记录中的“维护页面元素”按钮后显示内容
('MenuMaintain', 'evn_page', 'inputArea', 201, 'evnTableDiv', 'div', '', 'class="inputArea"'),
('MenuMaintain', 'evn_page', 'evnTableDiv', 1, 'addEvn', 'button', '新增页面事件', 'class="inputArea_sub_button"'),
('MenuMaintain', 'evn_page', 'evnTableDiv', 2, 'evnTable', 'table', '元素事件列表', 'class="output_table"'),
('MenuMaintain', 'evn_page', 'evnTable', 998, 'edt_evn_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'evn_page', 'evnTable', 999, 'del_evn_button', 'table_record_button', '删除', 'class="label_button"'),
-- 点击“新增页面元素”按钮后的弹窗内容
('MenuMaintain', 'add_evn_sbw', 'body', 1, 'add_evn_sub_window', 'subWindow', '新增页面元素', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sub_window_swBody', 1, 'add_evn_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 1, 'webEventId', 'input', 'web_event_id', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 4, 'element', 'input', '元素', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 5, 'eventType', 'input', '事件类型', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 6, 'requestType', 'input', '请求类型', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 7, 'requestBean', 'input', '请求处理的bean', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 8, 'nextPage', 'input', '跳转显示页', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sbw_div', 9, 'param', 'input', '参数(JSON)', ''),
('MenuMaintain', 'add_evn_sbw', 'add_evn_sub_window_swFooter', 1, 'addEvnButton', 'button', '提交', 'class="inputArea_sub_button"'),
-- 点击表格记录中的“编辑”按钮后的弹窗内容
('MenuMaintain', 'edt_evn_sbw', 'body', 1, 'edt_evn_sub_window', 'subWindow', '编辑页面元素', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sub_window_swBody', 1, 'edt_evn_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 1, 'web_event_id', 'input', 'web_event_id', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 4, 'element', 'input', '元素', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 5, 'event_type', 'input', '事件类型', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 6, 'request_type', 'input', '请求类型', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 7, 'request_bean', 'input', '请求处理的bean', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 8, 'next_page', 'input', '跳转显示页', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sbw_div', 9, 'param', 'input', '参数(JSON)', ''),
('MenuMaintain', 'edt_evn_sbw', 'edt_evn_sub_window_swFooter', 1, 'edtEvnButton', 'button', '提交', 'class="inputArea_sub_button"'),


-- 点击表格记录中的“维护页面元素”按钮后显示内容
('MenuMaintain', 'dta_page', 'inputArea', 201, 'dtaTableDiv', 'div', '', 'class="inputArea"'),
('MenuMaintain', 'dta_page', 'dtaTableDiv', 1, 'addDta', 'button', '新增页面数据', 'class="inputArea_sub_button"'),
('MenuMaintain', 'dta_page', 'dtaTableDiv', 2, 'dtaTable', 'table', '元素数据列表', 'class="output_table"'),
('MenuMaintain', 'dta_page', 'dtaTable', 998, 'edt_dta_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'dta_page', 'dtaTable', 999, 'del_dta_button', 'table_record_button', '删除', 'class="label_button"'),
-- 点击“新增页面元素”按钮后的弹窗内容
('MenuMaintain', 'add_dta_sbw', 'body', 1, 'add_dta_sub_window', 'subWindow', '新增页面数据', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sub_window_swBody', 1, 'add_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 1, 'webDataId', 'input', 'web_data_id', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 4, 'element', 'input', '元素', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 5, 'dataType', 'input', '数据类型', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 6, 'dataAttr', 'input', '数据属性', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sbw_div', 7, 'express', 'input', '表达式', ''),
('MenuMaintain', 'add_dta_sbw', 'add_dta_sub_window_swFooter', 1, 'addDtaButton', 'button', '提交', 'class="inputArea_sub_button"'),
-- 点击表格记录中的“编辑”按钮后的弹窗内容
('MenuMaintain', 'edt_dta_sbw', 'body', 1, 'edt_dta_sub_window', 'subWindow', '编辑页面数据', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sub_window_swBody', 1, 'edt_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 1, 'web_data_id', 'input', 'web_data_id', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 4, 'element', 'input', '元素', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 5, 'data_type', 'input', '数据类型', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 6, 'data_attr', 'input', '数据属性', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sbw_div', 7, 'express', 'input', '表达式', ''),
('MenuMaintain', 'edt_dta_sbw', 'edt_dta_sub_window_swFooter', 1, 'edtDtaButton', 'button', '提交', 'class="inputArea_sub_button"'),

-- 事件执行后
-- 点击表格记录中的“维护页面元素”按钮后显示内容
('MenuMaintain', 'aft_page', 'inputArea', 201, 'aftTableDiv', 'div', '', 'class="inputArea"'),
('MenuMaintain', 'aft_page', 'aftTableDiv', 1, 'addAft', 'button', '新增页面事后处理步骤', 'class="inputArea_sub_button"'),
('MenuMaintain', 'aft_page', 'aftTableDiv', 2, 'aftTable', 'table', '事后处理步骤列表', 'class="output_table"'),
('MenuMaintain', 'aft_page', 'aftTable', 998, 'edt_aft_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'aft_page', 'aftTable', 999, 'del_aft_button', 'table_record_button', '删除', 'class="label_button"'),
-- 点击“新增页面元素”按钮后的弹窗内容
('MenuMaintain', 'add_aft_sbw', 'body', 1, 'add_aft_sub_window', 'subWindow', '新增页面事后处理步骤', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sub_window_swBody', 1, 'add_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 1, 'webCallAfterId', 'input', 'web_call_after_id', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 4, 'processBean', 'input', '处理bean', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 5, 'processStatus', 'input', '处理状态', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 6, 'oprType', 'input', '操作类型', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 7, 'requestType', 'input', '请求类型', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 8, 'requestBean', 'input', '请求Bean', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sbw_div', 9, 'param', 'input', '参数', ''),
('MenuMaintain', 'add_aft_sbw', 'add_aft_sub_window_swFooter', 1, 'addAftButton', 'button', '提交', 'class="inputArea_sub_button"'),
-- 点击表格记录中的“编辑”按钮后的弹窗内容
('MenuMaintain', 'edt_aft_sbw', 'body', 1, 'edt_aft_sub_window', 'subWindow', '编辑页面事后处理步骤', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sub_window_swBody', 1, 'edt_dta_sbw_div', 'div', '', 'class="showWithRowDivGrid"'),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 1, 'web_data_id', 'input', 'web_data_id', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 2, 'menu', 'input', '菜单', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 3, 'page', 'input', '页面', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 4, 'element', 'input', '元素', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 5, 'data_type', 'input', '数据类型', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 6, 'data_attr', 'input', '数据属性', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sbw_div', 7, 'express', 'input', '表达式', ''),
('MenuMaintain', 'edt_aft_sbw', 'edt_aft_sub_window_swFooter', 1, 'edtAftButton', 'button', '提交', 'class="inputArea_sub_button"');

DELETE FROM web_event where menu='MenuMaintain';
INSERT INTO web_event(menu, page, "element", event_type, request_type, request_bean, next_page, trigger_type, trigger_element, trigger_element_type, param)VALUES
('MenuMaintain', 'menuEvent', '1', 'click', 'menuReq', 'MenuMaintain', NULL, NULL, NULL, NULL, NULL),

('MenuMaintain', 'start_page', 'addMenu', 'click', 'swDataReq', 'MenuMaintain', 'add_menu_sbw', NULL, NULL, NULL, NULL),
('MenuMaintain', 'start_page', 'edt_menu_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_menu_sbw', NULL, NULL, NULL, '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'start_page', 'del_menu_button', 'click', 'buttonReq', 'delNewMenu', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'add_menu_sbw', 'addMenuButton', 'click', 'buttonReq', 'addNewMenu', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'edt_menu_sbw', 'edtMenuButton', 'click', 'buttonReq', 'edtNewMenu', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'start_page', 'dsp_ele_button', 'click', 'webDataReq', 'getWebElement', '26', NULL, NULL, NULL, NULL),
('MenuMaintain', 'start_page', 'dsp_eve_button', 'click', 'webDataReq', 'getWebElement', '55', NULL, NULL, NULL, NULL),
('MenuMaintain', 'start_page', 'dsp_dta_button', 'click', 'webDataReq', 'getWebElement', '84', NULL, NULL, NULL, NULL),
('MenuMaintain', 'start_page', 'dsp_aft_button', 'click', 'webDataReq', 'getWebElement', '109', NULL, NULL, NULL, NULL),

('MenuMaintain', 'ele_page', 'addEle', 'click', 'swDataReq', 'xxxx', 'add_ele_sbw', NULL, NULL, NULL, NULL),
('MenuMaintain', 'ele_page', 'edt_ele_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_ele_sbw', NULL, NULL, NULL, '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'ele_page', 'del_ele_button', 'click', 'buttonReq', 'delNewEle', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'add_ele_sbw', 'addEleButton', 'click', 'buttonReq', 'addNewEle', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'edt_ele_sbw', 'edtEleButton', 'click', 'buttonReq', 'edtNewEle', NULL, NULL, NULL, NULL, NULL),

('MenuMaintain', 'evn_page', 'addEvn', 'click', 'swDataReq', 'xxxx', 'add_evn_sbw', NULL, NULL, NULL, NULL),
('MenuMaintain', 'evn_page', 'edt_evn_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_evn_sbw', NULL, NULL, NULL, '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'evn_page', 'del_evn_button', 'click', 'buttonReq', 'delNewEvn', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'add_evn_sbw', 'addEvnButton', 'click', 'buttonReq', 'addNewEvn', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'edt_evn_sbw', 'edtEvnButton', 'click', 'buttonReq', 'edtNewEvn', NULL, NULL, NULL, NULL, NULL),

('MenuMaintain', 'dta_page', 'addDta', 'click', 'swDataReq', 'xxxx', 'add_dta_sbw', NULL, NULL, NULL, NULL),
('MenuMaintain', 'dta_page', 'edt_dta_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_dta_sbw', NULL, NULL, NULL, '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'dta_page', 'del_dta_button', 'click', 'buttonReq', 'delNewDta', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'add_dta_sbw', 'addDtaButton', 'click', 'buttonReq', 'addNewDta', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'edt_dta_sbw', 'edtDtaButton', 'click', 'buttonReq', 'edtNewDta', NULL, NULL, NULL, NULL, NULL),

('MenuMaintain', 'aft_page', 'addAft', 'click', 'swDataReq', 'xxxx', 'add_aft_sbw', NULL, NULL, NULL, NULL),
('MenuMaintain', 'aft_page', 'edt_aft_button', 'click', 'swDataReq', 'MenuMaintain', 'edt_aft_sbw', NULL, NULL, NULL, '{"valueFromSelectedRecord":true}'),
('MenuMaintain', 'aft_page', 'del_aft_button', 'click', 'buttonReq', 'delNewAft', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'add_aft_sbw', 'addAftButton', 'click', 'buttonReq', 'addNewAft', NULL, NULL, NULL, NULL, NULL),
('MenuMaintain', 'edt_aft_sbw', 'edtAftButton', 'click', 'buttonReq', 'edtNewAft', NULL, NULL, NULL, NULL, NULL);

delete from web_call_after where menu='MenuMaintain';
INSERT INTO web_call_after(menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
('MenuMaintain', 'start_page_sub_add_menu', 'addNewMenu', 'false', 'showMessage', NULL, NULL, '{"msg":"新增菜单失败"}'),
('MenuMaintain', 'start_page_sub_add_menu', 'addNewMenu', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增菜单"}'),
('MenuMaintain', 'start_page_sub_add_menu', 'addNewMenu', 'success', 'closeSw', '', '', '{"subWindowId":"add_menu_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'start_page_sub_add_menu', 'addNewMenu', 'success', 'request', 'menuReq', 'MenuMaintain', ''),

('MenuMaintain', 'start_page_sub_edt_menu', 'edtNewMenu', 'false', 'showMessage', NULL, NULL, '{"msg":"更新菜单失败"}'),
('MenuMaintain', 'start_page_sub_edt_menu', 'edtNewMenu', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新菜单"}'),
('MenuMaintain', 'start_page_sub_edt_menu', 'edtNewMenu', 'success', 'closeSw', '', '', '{"subWindowId":"edt_menu_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'start_page_sub_edt_menu', 'edtNewMenu', 'success', 'request', 'menuReq', 'MenuMaintain', ''),

('MenuMaintain', 'start_page', 'delNewMenu', 'false', 'showMessage', NULL, NULL, '{"msg":"删除菜单失败"}'),
('MenuMaintain', 'start_page', 'delNewMenu', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除菜单"}'),
('MenuMaintain', 'start_page', 'delNewMenu', 'success', 'request', 'menuReq', 'MenuMaintain', ''),

-- 元素
('MenuMaintain', 'add_ele_sbw', 'addNewEle', 'false', 'showMessage', NULL, NULL, '{"msg":"新增元素失败"}'),
('MenuMaintain', 'add_ele_sbw', 'addNewEle', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增元素"}'),
('MenuMaintain', 'add_ele_sbw', 'addNewEle', 'success', 'closeSw', '', '', '{"subWindowId":"add_ele_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_ele_sbw', 'addNewEle', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"26"}'),

('MenuMaintain', 'edt_ele_sbw', 'edtNewEle', 'false', 'showMessage', NULL, NULL, '{"msg":"更新元素失败"}'),
('MenuMaintain', 'edt_ele_sbw', 'edtNewEle', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新元素"}'),
('MenuMaintain', 'edt_ele_sbw', 'edtNewEle', 'success', 'closeSw', '', '', '{"subWindowId":"edt_ele_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_ele_sbw', 'edtNewEle', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"26"}'),

('MenuMaintain', 'start_page', 'delNewEle', 'false', 'showMessage', NULL, NULL, '{"msg":"删除元素失败"}'),
('MenuMaintain', 'start_page', 'delNewEle', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除元素"}'),
('MenuMaintain', 'start_page', 'delNewEle', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"26"}'),
-- 事件
('MenuMaintain', 'add_evn_sbw', 'addNewEvn', 'false', 'showMessage', NULL, NULL, '{"msg":"新增事件失败"}'),
('MenuMaintain', 'add_evn_sbw', 'addNewEvn', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增事件"}'),
('MenuMaintain', 'add_evn_sbw', 'addNewEvn', 'success', 'closeSw', '', '', '{"subWindowId":"add_evn_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_evn_sbw', 'addNewEvn', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"55"}'),

('MenuMaintain', 'edt_evn_sbw', 'edtNewEvn', 'false', 'showMessage', NULL, NULL, '{"msg":"更新事件失败"}'),
('MenuMaintain', 'edt_evn_sbw', 'edtNewEvn', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新事件"}'),
('MenuMaintain', 'edt_evn_sbw', 'edtNewEvn', 'success', 'closeSw', '', '', '{"subWindowId":"edt_evn_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_evn_sbw', 'edtNewEvn', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"55"}'),

('MenuMaintain', 'start_page', 'delNewEvn', 'false', 'showMessage', NULL, NULL, '{"msg":"删除事件失败"}'),
('MenuMaintain', 'start_page', 'delNewEvn', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除事件"}'),
('MenuMaintain', 'start_page', 'delNewEvn', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"55"}'),
-- 数据配置
('MenuMaintain', 'add_dta_sbw', 'addNewDta', 'false', 'showMessage', NULL, NULL, '{"msg":"新增数据配置失败"}'),
('MenuMaintain', 'add_dta_sbw', 'addNewDta', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增数据配置"}'),
('MenuMaintain', 'add_dta_sbw', 'addNewDta', 'success', 'closeSw', '', '', '{"subWindowId":"add_dta_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_dta_sbw', 'addNewDta', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"84"}'),

('MenuMaintain', 'edt_dta_sbw', 'edtNewDta', 'false', 'showMessage', NULL, NULL, '{"msg":"更新数据配置失败"}'),
('MenuMaintain', 'edt_dta_sbw', 'edtNewDta', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新数据配置"}'),
('MenuMaintain', 'edt_dta_sbw', 'edtNewDta', 'success', 'closeSw', '', '', '{"subWindowId":"edt_dta_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_dta_sbw', 'edtNewDta', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"84"}'),

('MenuMaintain', 'start_page', 'delNewDta', 'false', 'showMessage', NULL, NULL, '{"msg":"删除数据配置失败"}'),
('MenuMaintain', 'start_page', 'delNewDta', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除数据配置"}'),
('MenuMaintain', 'start_page', 'delNewDta', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"84"}'),
-- 事件后步骤配置
('MenuMaintain', 'add_aft_sbw', 'addNewAft', 'false', 'showMessage', NULL, NULL, '{"msg":"新增事件处理后步骤配置失败"}'),
('MenuMaintain', 'add_aft_sbw', 'addNewAft', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增事件处理后步骤配置"}'),
('MenuMaintain', 'add_aft_sbw', 'addNewAft', 'success', 'closeSw', '', '', '{"subWindowId":"add_aft_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'add_aft_sbw', 'addNewAft', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"109"}'),

('MenuMaintain', 'edt_aft_sbw', 'edtNewAft', 'false', 'showMessage', NULL, NULL, '{"msg":"更新事件处理后步骤配置失败"}'),
('MenuMaintain', 'edt_aft_sbw', 'edtNewAft', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新事件处理后步骤配置"}'),
('MenuMaintain', 'edt_aft_sbw', 'edtNewAft', 'success', 'closeSw', '', '', '{"subWindowId":"edt_aft_sub_window_subWindowBackGround"}'),
('MenuMaintain', 'edt_aft_sbw', 'edtNewAft', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"109"}'),

('MenuMaintain', 'start_page', 'delNewAft', 'false', 'showMessage', NULL, NULL, '{"msg":"删除事件处理后步骤配置失败"}'),
('MenuMaintain', 'start_page', 'delNewAft', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除事件处理后步骤配置"}'),
('MenuMaintain', 'start_page', 'delNewAft', 'success', 'request', 'webDataReq', 'getWebElement', '{"refreshFlag":"Y","refreshPage":"109"}'),

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
('MenuMaintain', 'start_page', 'dsp_aft_button', 'success', 'removeEle', '', '', '{"removeEleId":"dtaTableDiv"}');


DELETE FROM web_data where menu='MenuMaintain';
INSERT INTO web_data(menu, page, "element", data_type, data_attr, express)VALUES
('MenuMaintain', 'start_page', 'menuTable', 'sql', NULL, 'select * from web_menu'),
('MenuMaintain', 'start_page', 'body_record_list', 'sql', NULL, 'select * from web_menu'),
('MenuMaintain', 'ele_page', 'eleTable', 'sql', NULL, 'select * from web_element where menu=#menu# order by web_element_id'),
('MenuMaintain', 'evn_page', 'evnTable', 'sql', NULL, 'select * from web_event where menu=#menu# order by web_event_id'),
('MenuMaintain', 'dta_page', 'dtaTable', 'sql', NULL, 'select * from web_data where menu=#menu# order by web_data_id'),
('MenuMaintain', 'aft_page', 'aftTable', 'sql', NULL, 'select * from web_call_after where menu=#menu# order by web_call_after_id');

DELETE FROM web_element where menu='putoutLoan';
INSERT INTO web_element(menu, page, element_parent, element_seq, "element", element_type, element_desc, element_attr)VALUES
('putoutLoan', 'start_page', 'contentArea', 1, 'inputArea', 'div', '输入区域', 'class="inputArea showWithRowDivFlex"'),
('putoutLoan', 'start_page', 'inputArea', 1, 'transInfoDiv', 'div', '交易信息div', 'class="inputArea background_color_1"'),
('putoutLoan', 'start_page', 'transInfoDiv', 0, 'transInfoDivTitle', 'divTitle', '交易信息', 'class="content_title"'),
('putoutLoan', 'start_page', 'transInfoDiv', 1, 'transSeq', 'input', '交易流水号', ''),
('putoutLoan', 'start_page', 'transInfoDiv', 2, 'transDate', 'input', '交易日期', ''),
('putoutLoan', 'start_page', 'transInfoDiv', 3, 'transChannel', 'input', '交易渠道', ''),
('putoutLoan', 'start_page', 'transInfoDiv', 4, 'businessSource', 'input', '业务来源', ''),
('putoutLoan', 'start_page', 'transInfoDiv', 5, 'businessChannel', 'input', '业务渠道', ''),

('putoutLoan', 'start_page', 'inputArea', 2, 'customerInfoDiv', 'div', '客户信息div', 'class="inputArea background_color_1"'),
('putoutLoan', 'start_page', 'customerInfoDiv', 0, 'customerInfoDivTitle', 'divTitle', '客户信息', 'class="content_title"'),
('putoutLoan', 'start_page', 'customerInfoDiv', 1, 'customerName', 'input', '客户姓名', ''),
('putoutLoan', 'start_page', 'customerInfoDiv', 2, 'certNo', 'input', '身份证号', ''),
('putoutLoan', 'start_page', 'customerInfoDiv', 3, 'customerPhone', 'input', '客户手机号', ''),

('putoutLoan', 'start_page', 'inputArea', 3, 'produceInfoDiv', 'div', '产品信息输入区域', 'class="inputArea background_color_1"'),
('putoutLoan', 'start_page', 'produceInfoDiv', 0, 'produceInfoDivTitle', 'divTitle', '产品信息', 'class="content_title"'),
('putoutLoan', 'start_page', 'produceInfoDiv', 1, 'lineId', 'selectOption', '资金方ID', ''),
('putoutLoan', 'start_page', 'produceInfoDiv', 2, 'productId', 'selectOption', '产品ID', ''),
('putoutLoan', 'start_page', 'produceInfoDiv', 3, 'productType', 'input', '产品类型', ''),
('putoutLoan', 'start_page', 'produceInfoDiv', 4, 'collaborate', 'input', '合作模式', ''),

('putoutLoan', 'start_page', 'inputArea', 4, 'loanInfoDiv', 'div', '贷款信息div', 'class="inputArea background_color_1"'),
('putoutLoan', 'start_page', 'loanInfoDiv', 0, 'loanInfoDivTitle', 'divTitle', '贷款信息', 'class="content_title"'),
('putoutLoan', 'start_page', 'loanInfoDiv', 1, 'applyNo', 'input', '申请编号', ''),
('putoutLoan', 'start_page', 'loanInfoDiv', 2, 'loanNo', 'input', '出账编号', ''),
('putoutLoan', 'start_page', 'loanInfoDiv', 3, 'loanTerm', 'input', '贷款期次', ''),
('putoutLoan', 'start_page', 'loanInfoDiv', 4, 'businessSum', 'input', '放款金额', ''),
('putoutLoan', 'start_page', 'loanInfoDiv', 5, 'putoutDate', 'input', '放款日期', ''),
('putoutLoan', 'start_page', 'loanInfoDiv', 6, 'maturityDate', 'input', '到期日期', ''),
('putoutLoan', 'start_page', 'loanInfoDiv', 7, 'beginDate', 'input', '首次还款日', ''),
('putoutLoan', 'start_page', 'loanInfoDiv', 8, 'returnMethod', 'input', '还款方式', ''),

('putoutLoan', 'start_page', 'inputArea', 5, 'rateInfoDiv', 'div', '利率信息div', 'class="inputArea background_color_1"'),
('putoutLoan', 'start_page', 'rateInfoDiv', 0, 'rateInfoDivTitle', 'divTitle', '利率信息', 'class="content_title"'),
('putoutLoan', 'start_page', 'rateInfoDiv', 1, 'executeRate', 'input', '执行利率', ''),
('putoutLoan', 'start_page', 'rateInfoDiv', 2, 'baseDays', 'input', '利率基准天数', ''),
('putoutLoan', 'start_page', 'rateInfoDiv', 3, 'graceTerm', 'input', '宽限期', ''),
('putoutLoan', 'start_page', 'rateInfoDiv', 4, 'fineRate', 'input', '罚息利率', ''),
('putoutLoan', 'start_page', 'rateInfoDiv', 5, 'compoundRate', 'input', '复利利率', ''),

('putoutLoan', 'start_page', 'inputArea', 6, 'ownerInfoDiv', 'div', '归属信息div', 'class="inputArea background_color_1"'),
('putoutLoan', 'start_page', 'ownerInfoDiv', 0, 'ownerInfoDivTitle', 'divTitle', '归属信息', 'class="content_title"'),
('putoutLoan', 'start_page', 'ownerInfoDiv', 1, 'operateOrgId', 'input', '入账机构', ''),
('putoutLoan', 'start_page', 'ownerInfoDiv', 2, 'accountOwnerCd', 'input', '账务归属', ''),

('putoutLoan', 'start_page', 'inputArea', 7, 'buttonDiv', 'div', '按钮div', 'class="inputArea background_color_1"'),
('putoutLoan', 'start_page', 'buttonDiv', 1, 'submitButton', 'button', '提交', 'class="inputArea_sub_button"');

DELETE FROM web_event where menu='putoutLoan';
INSERT INTO web_event(menu, page, "element", event_type, request_type, request_bean, next_page, trigger_type, trigger_element, trigger_element_type, param)VALUES
('putoutLoan', 'menuEvent', '1', 'click', 'menuReq', 'putoutLoan', '', NULL, NULL, NULL, NULL),
('putoutLoan', 'start_page', 'submitButton', 'click', 'buttonReq', 'loanPutout', NULL, NULL, NULL, NULL, NULL);





















DELETE FROM web_element;
INSERT INTO web_element (menu, page, element_parent, element_seq, "element", element_type, element_desc, element_attr) VALUES
-- 开始页面-显示菜单内容
-- 新增菜单弹窗
-- 编辑菜单弹窗
-- 展示事件弹窗
-- 新增元素事件弹窗
-- 编辑元素事件弹窗
-- 页面元素展示
-- 新增页面元素弹窗
-- 展示事件弹窗
-- 新增元素事件弹窗
-- 编辑元素事件弹窗
-- 展示元素数据弹窗
-- 新增元素事件弹窗
-- 编辑元素事件弹窗
('MenuMaintain', 'start_page', 'contentArea', 1, 'inputArea', 'div', '输入区域', 'class="inputArea"'),
('MenuMaintain', 'start_page', 'inputArea', 1, 'menuArea', 'div', '菜单信息显示区域', 'class="inputArea_overflow_auto"'),
('MenuMaintain', 'start_page', 'menuArea', 1, 'addMenu', 'button', '新增菜单', 'class="inputArea_sub_button"'),
('MenuMaintain', 'start_page', 'menuArea', 2, 'menuTable', 'table', '菜单列表', 'class="output_table"'),
('MenuMaintain', 'start_page', 'menuTable', 999, 'menu_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 999, 'menu_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 999, 'menu_record_event_show_button', 'table_record_button', '菜单事件', 'class="label_button"'),
('MenuMaintain', 'menu_add_sw_page', 'body', 0, 'menu_add_sub_window', 'subWindow', '新增菜单弹窗', NULL),
('MenuMaintain', 'menu_add_sw_page', 'menu_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_add_sw_page', 'menu_add_sub_window_swFooter', 0, 'confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_edit_sw_page', 'body', 0, 'menu_edit_sub_window', 'subWindow', '编辑菜单', NULL),
('MenuMaintain', 'menu_edit_sw_page', 'menu_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_edit_sw_page', 'menu_edit_sub_window_swFooter', 0, 'edit_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_eventList_sw_page', 'body', 0, 'show_menu_event_sub_window', 'subWindow', '展示菜单事件', NULL),
('MenuMaintain', 'menu_eventList_sw_page', 'show_menu_event_sub_window_swBody', 1, 'addMenuEvent', 'button', '新增菜单事件', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_eventList_sw_page', 'show_menu_event_sub_window_swBody', 2, 'menuEventTable', 'table', '事件清单', 'class="output_table"'),
('MenuMaintain', 'menu_eventList_sw_page', 'menuEventTable', 999, 'event_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'menu_eventList_sw_page', 'menuEventTable', 999, 'event_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'menu_event_add_sw_page', 'body', 0, 'menu_event_add_sub_window', 'subWindow', '新增菜单事件弹窗', ''),
('MenuMaintain', 'menu_event_add_sw_page', 'menu_event_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '新增菜单事件输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_event_add_sw_page', 'menu_event_add_sub_window_swFooter', 0, 'add_menu_event_confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_event_edit_sw_page', 'body', 0, 'menu_event_edit_sub_window', 'subWindow', '编辑菜单事件', NULL),
('MenuMaintain', 'menu_event_edit_sw_page', 'menu_event_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_event_edit_sw_page', 'menu_event_edit_sub_window_swFooter', 0, 'edit_menu_event_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_start_page', 'inputArea', 2, 'ele_show_area', 'div', '页面元素展示区域', 'class="inputArea_overflow_auto"'),
('MenuMaintain', 'ele_start_page', 'ele_show_area', 3, 'addPageElement', 'button', '新增页面元素', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_start_page', 'ele_show_area', 4, 'elementTable', 'table', '页面元素列表', 'class="output_table"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_show_event_button', 'table_record_button', '元素事件', 'class="label_button"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_show_data_button', 'table_record_button', '元素数据', 'class="label_button"'),
('MenuMaintain', 'ele_add_sw_page', 'body', 0, 'ele_add_sub_window', 'subWindow', '新增页面元素弹窗', NULL),
('MenuMaintain', 'ele_add_sw_page', 'ele_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'ele_add_sw_page', 'ele_add_sub_window_swFooter', 0, 'confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_edit_sw_page', 'body', 0, 'ele_edit_sub_window', 'subWindow', '编辑页面元素', NULL),
('MenuMaintain', 'ele_edit_sw_page', 'ele_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'ele_edit_sw_page', 'ele_edit_sub_window_swFooter', 0, 'edit_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_eventList_sw_page', 'body', 0, 'show_event_sub_window', 'subWindow', '展示元素事件', NULL),
('MenuMaintain', 'ele_eventList_sw_page', 'show_event_sub_window_swBody', 1, 'addElementEvent', 'button', '新增元素事件', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_eventList_sw_page', 'show_event_sub_window_swBody', 2, 'eventTable', 'table', '事件清单', 'class="output_table"'),
('MenuMaintain', 'ele_eventList_sw_page', 'eventTable', 999, 'event_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'ele_eventList_sw_page', 'eventTable', 999, 'event_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'event_add_sw_page', 'body', 0, 'event_add_sub_window', 'subWindow', '新增元素事件弹窗', ''),
('MenuMaintain', 'event_add_sw_page', 'event_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '新增元素事件输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'event_add_sw_page', 'event_add_sub_window_swFooter', 0, 'add_event_confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'event_edit_sw_page', 'body', 0, 'event_edit_sub_window', 'subWindow', '编辑元素事件', NULL),
('MenuMaintain', 'event_edit_sw_page', 'event_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'event_edit_sw_page', 'event_edit_sub_window_swFooter', 0, 'edit_event_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_dataList_sw_page', 'body', 0, 'show_data_sub_window', 'subWindow', '展示元素数据', NULL),
('MenuMaintain', 'ele_dataList_sw_page', 'show_data_sub_window_swBody', 1, 'addElementData', 'button', '新增元素数据', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_dataList_sw_page', 'show_data_sub_window_swBody', 2, 'eleDataTable', 'table', '元素数据清单', 'class="output_table"'),
('MenuMaintain', 'ele_dataList_sw_page', 'eleDataTable', 999, 'data_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'ele_dataList_sw_page', 'eleDataTable', 999, 'data_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'data_add_sw_page', 'body', 0, 'data_add_sub_window', 'subWindow', '新增元素数据弹窗', ''),
('MenuMaintain', 'data_add_sw_page', 'data_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '新增元素数据输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'data_add_sw_page', 'data_add_sub_window_swFooter', 0, 'add_data_confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'data_edit_sw_page', 'body', 0, 'data_edit_sub_window', 'subWindow', '编辑元素数据', NULL),
('MenuMaintain', 'data_edit_sw_page', 'data_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'data_edit_sw_page', 'data_edit_sub_window_swFooter', 0, 'edit_data_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('QueryTableStructure', 'start_page', 'contentArea', 1, 'inputArea', 'div', '输入区域', 'class="inputArea"'),
('QueryTableStructure', 'start_page', 'inputArea', 0, 'dbName', 'selectOption', '数据库', 'style=width:120px'),
('QueryTableStructure', 'start_page', 'inputArea', 1, 'libName', 'selectOption', '库名', 'style=width:200px'),
('QueryTableStructure', 'start_page', 'inputArea', 2, 'tableName', 'selectOption', '表名', ''),
('QueryTableStructure', 'start_page', 'inputArea', 3, 'queryButton', 'button', '查询', 'class="inputArea_sub_button"'),
('QueryTableStructure', 'query_out_page', 'inputArea', 0, 'query_out_page_div', 'div', '输出区域', 'class="inputArea"'),
('QueryTableStructure', 'query_out_page', 'query_out_page_div', 0, 'table_div', 'div', '输出表格div', 'class="inputArea"'),
('QueryTableStructure', 'query_out_page', 'table_div', 0, 'outTable', 'table', 'table', 'class="output_table"');




DELETE FROM web_event;
INSERT INTO web_event (menu, page, "element", event_type, request_type, request_bean, next_page, param) VALUES
-- 菜单维护菜单单击事件
('MenuMaintain', 'menuEvent', '', 'click', 'menuReq', 'MenuMaintain', '', NULL),
-- 新增菜单按钮单击事件
('MenuMaintain', 'start_page', 'addMenu', 'click', 'swDataReq', 'addMenuButton', 'menu_add_sw_page', ''),
-- 菜单记录编辑按钮单击事件
('MenuMaintain', 'start_page', 'menu_record_edit_button', 'click', 'swDataReq', 'editMenuButton', 'menu_edit_sw_page', ''),
-- 菜单记录显示事件按钮单击事件
('MenuMaintain', 'start_page', 'menu_record_event_show_button', 'click', 'swDataReq', 'showMenuEventButton', 'menu_eventList_sw_page', ''),
-- 菜单记录删除按钮单击事件
('MenuMaintain', 'start_page', 'menu_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_menu","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"start_page","refreshEle":"menuTable"}'),
-- 菜单记录记录行单击事件
('MenuMaintain', 'start_page', 'menuTable', 'record_click', 'webDataReq', 'queryMenuEle', 'ele_start_page', '{"parentEle":"contentArea"}'),
-- 菜单新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'menu_add_sw_page', 'confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_menu","refreshPage":"start_page","refreshEle":"menuTable","closeSW":"menu_add_sub_window"}'),
-- 菜单编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'menu_edit_sw_page', 'edit_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_menu","refreshPage":"start_page","refreshEle":"menuTable","closeSW":"menu_edit_sub_window"}'),

-- 菜单事件弹窗新增按钮单击事件
('MenuMaintain', 'menu_eventList_sw_page', 'addMenuEvent', 'click', 'swDataReq', 'menuEventAddButton', 'menu_event_add_sw_page', ''),
-- 菜单事件弹窗记录编辑按钮单击事件
('MenuMaintain', 'menu_eventList_sw_page', 'menu_event_record_edit_button', 'click', 'swDataReq', 'menuEventEditButton', 'menu_event_edit_sw_page', ''),
-- 菜单事件弹窗记录删除按钮单击事件
('MenuMaintain', 'menu_eventList_sw_page', 'menu_event_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_event","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"menu_eventList_sw_page","refreshEle":"menuEventTable"}'),
-- 菜单事件弹窗新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'menu_event_add_sw_page', 'add_menu_event_confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_event","refreshPage":"menu_event_add_sw_page","refreshEle":"menuEventTable","closeSW":"menu_event_add_sub_window"}'),
-- 菜单事件弹窗编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'menu_event_edit_sw_page', 'edit_menu_event_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_event","refreshPage":"menu_event_edit_sw_page","refreshEle":"menuEventTable","closeSW":"menu_event_edit_sub_window"}'),

-- 页面元素新增按钮单击事件
('MenuMaintain', 'ele_start_page', 'addPageElement', 'click', 'swDataReq', 'eleAddButton', 'ele_add_sw_page', ''),
-- 元素记录编辑按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_record_edit_button', 'click', 'swDataReq', 'eleEditButton', 'ele_edit_sw_page', ''),
-- 元素记录删除按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_element","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"ele_start_page","refreshEle":"elementTable"}'),
-- 元素记录事件展示按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_show_event_button', 'click', 'swDataReq', 'eleEventShowButton', 'ele_eventList_sw_page', NULL),
-- 元素记录数据展示按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_show_data_button', 'click', 'swDataReq', 'eleDataShowButton', 'ele_dataList_sw_page', NULL),
-- 元素记录新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'ele_add_sw_page', 'confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_element","refreshPage":"ele_start_page","refreshEle":"elementTable","closeSW":"ele_add_sub_window"}'),
-- 元素记录编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'ele_edit_sw_page', 'edit_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_element","refreshPage":"ele_start_page","refreshEle":"elementTable","closeSW":"ele_edit_sub_window"}'),

-- 元素事件新增按钮单击事件
('MenuMaintain', 'ele_eventList_sw_page', 'addElementEvent', 'click', 'swDataReq', 'eventAddButton', 'event_add_sw_page', ''),
-- 元素事件记录编辑按钮单击事件
('MenuMaintain', 'ele_eventList_sw_page', 'event_record_edit_button', 'click', 'swDataReq', 'eventEditButton', 'event_edit_sw_page', ''),
-- 元素事件记录删除按钮单击事件
('MenuMaintain', 'ele_eventList_sw_page', 'event_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_event","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"ele_eventList_sw_page","refreshEle":"eventTable"}'),
-- 元素事件新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'event_add_sw_page', 'add_event_confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_event","refreshPage":"event_add_sw_page","refreshEle":"eventTable","closeSW":"event_add_sub_window"}'),
-- 元素事件编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'event_edit_sw_page', 'edit_event_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_event","refreshPage":"event_edit_sw_page","refreshEle":"eventTable","closeSW":"event_edit_sub_window"}'),

-- 元素数据新增按钮单击事件
('MenuMaintain', 'ele_dataList_sw_page', 'addElementData', 'click', 'swDataReq', 'dataAddButton', 'data_add_sw_page', ''),
-- 元素数据记录编辑按钮单击事件
('MenuMaintain', 'ele_dataList_sw_page', 'data_record_edit_button', 'click', 'swDataReq', 'dataEditButton', 'data_edit_sw_page', ''),
-- 元素数据记录删除按钮单击事件
('MenuMaintain', 'ele_dataList_sw_page', 'data_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_data","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"ele_eventList_sw_page","refreshEle":"eleDataTable"}'),
-- 元素数据新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'data_add_sw_page', 'add_data_confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_data","refreshPage":"data_add_sw_page","refreshEle":"eleDataTable","closeSW":"data_add_sub_window"}'),
-- 元素数据编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'data_edit_sw_page', 'edit_data_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_data","refreshPage":"data_edit_sw_page","refreshEle":"eleDataTable","closeSW":"data_edit_sub_window"}');

DELETE FROM web_data;
INSERT INTO web_data (menu, page, "element", data_type, data_attr, express) VALUES
('MenuMaintain', 'start_page', 'menuTable', 'sql', '', 'select * from web_menu order by menu_seq'),
('MenuMaintain', 'menu_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_menu'),
('MenuMaintain', 'menu_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_menu'),

('MenuMaintain', 'menu_eventList_sw_page', 'menuEventTable', 'sql', NULL, 'select * from web_event where menu=#menu# and page=\'menuEvent\' order by web_event_id'),
('MenuMaintain', 'menu_event_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),
('MenuMaintain', 'menu_event_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),

('MenuMaintain', 'ele_start_page', 'elementTable', 'sql', NULL, 'select * from web_element where menu=#menu# order by web_element_id'),
('MenuMaintain', 'ele_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_element'),
('MenuMaintain', 'ele_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_element'),

('MenuMaintain', 'ele_eventList_sw_page', 'eventTable', 'sql', NULL, 'select * from web_event where menu=#menu# and page=#page# and element=#element# order by web_event_id'),
('MenuMaintain', 'event_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),
('MenuMaintain', 'event_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),

('MenuMaintain', 'ele_dataList_sw_page', 'eleDataTable', 'sql', NULL, 'select * from web_data where menu=#menu# and page=#page# and element=#element# order by web_data_id'),
('MenuMaintain', 'data_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_data'),
('MenuMaintain', 'data_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_data');


DELETE FROM config_database_info;
INSERT INTO config_database_info (database_name, database_type, database_driver, database_addr, database_label, login_name, login_password, database_attr) VALUES
('local_sqlite', 'sqlite', 'org.sqlite.JDBC', 'jdbc:sqlite:sqlitedb/hlhome.db', '', '', '', ''),
-- ('local_oracle', 'oracle', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@localhost:1521:hlhome', '', 'hlhome', 'root', 'Root#98e');
('dev',       'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.20.16.15:5102', 'caes', 'deployop', 'iN9Wac@NAn*6', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('caes_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'caes', 'caesopr', 'De79#r6fd3', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('caes_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'caes', 'caesopr', 'Dfs@3K3#r3', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('caes_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'caes', 'caesopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('dfds_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'dfds', 'dfdsopr', 'Tfa5H3#y1', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('dfds_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'dfds', 'dfdsopr', 'Tfa5H3#y2', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('dfds_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'dfds', 'dfdsopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ldps_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'ldps', 'ldpsopr', 'De79#r6sd3', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ldps_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'ldps', 'ldpsopr', 'Dafjfl#15', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ldps_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'ldps', 'ldpsopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('bdcs_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'bdcs', 'bdcsopr', 'bdcsDasdf8#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('bdcs_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'bdcs', 'bdcsopr', 'bdcsDasdf8#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('bdcs_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'bdcs', 'bdcsopr', 'bdcsDasdf8#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cpms_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'cpms', 'cpmsopr', 'De79#r6fd8', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cpms_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'cpms', 'dfdsro', 'iCOWaVU6$bJq', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cpms_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'cpms', 'cpmsopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cccs_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'cccs', 'cccsopr', 'u&SXs8LaPTcD', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cccs_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'cccs', 'cccsopr', 'v4d0wh9usTY^', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cccs_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'cccs', 'cccsopr', 'M88jR@E4FzVT', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ipos_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'ipos', 'iposopr', '*nP*!X6ixmSH', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ipos_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'ipos', 'iposopr', '*nP*!X6ixmSH', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ipos_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'ipos', 'iposopr', '*nP*!X6ixmSH', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai');

