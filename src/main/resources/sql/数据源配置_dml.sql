
DELETE FROM web_menu WHERE menu = 'DbSourceConfig';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0024', 24, 'DbSourceConfig', 'SystemMaintain', '数据源配置');

DELETE FROM web_element WHERE menu = 'DbSourceConfig';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2026030300000001, 'EM000001', 001, 'DbSourceConfig', 'start_page', 'contentArea', 'inputArea', 'div', '', '', ''),
(2026030300000002, 'EM000002', 002, 'DbSourceConfig', 'start_page', 'inputArea', 'itfButtonArea', 'div', '', '', ''),
(2026030300000003, 'EM000003', 003, 'DbSourceConfig', 'start_page', 'itfButtonArea', 'show_itf_add_sbw_btn', 'button', '新增数据源配置', 'class="inputArea_sub_button"', ''),
(2026030300000004, 'EM000004', 004, 'DbSourceConfig', 'start_page', 'inputArea', 'itfQueryArea', 'div', '', 'class="area_div"', ''),
(2026030300000005, 'EM000005', 005, 'DbSourceConfig', 'start_page', 'itfQueryArea', 'queryAreaDivTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2026030300000006, 'EM000006', 006, 'DbSourceConfig', 'start_page', 'itfQueryArea', 'inputDatabaseName', 'input', '数据库名称', 'out="Y"', ''),
(2026030300000007, 'EM000007', 007, 'DbSourceConfig', 'start_page', 'itfQueryArea', 'inputDatabaseType', 'input', '数据库类型', 'out="Y"', ''),
(2026030300000008, 'EM000008', 008, 'DbSourceConfig', 'start_page', 'itfQueryArea', 'itf_cond_query_btn', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2026030300000009, 'EM000009', 009, 'DbSourceConfig', 'start_page', 'inputArea', 'itfOutArea', 'div', '', '', ''),
(2026030300000010, 'EM000010', 010, 'DbSourceConfig', 'start_page', 'itfOutArea', 'itfOutAreaTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2026030300000011, 'EM000011', 011, 'DbSourceConfig', 'start_page', 'itfOutArea', 'itfInfoTable', 'table', '新增查询配置表', 'class="output_table"', '{"hideFields":["DbSourceConfigId"]}'),
(2026030300000012, 'EM000012', 012, 'DbSourceConfig', 'start_page', 'itfInfoTable', 'show_itf_edt_sbw_btn', 'table_record_button', '编辑', 'class="label_button"', NULL),
(2026030300000013, 'EM000013', 013, 'DbSourceConfig', 'start_page', 'itfInfoTable', 'itf_del_button', 'table_record_button', '删除', 'class="label_button"', NULL),
(2026030300000014, 'EM000014', 014, 'DbSourceConfig', 'itfAddSbwPage', 'body', 'itfAddSubWindow', 'subWindow', '新增配置定义', '', NULL),
(2026030300000015, 'EM000015', 015, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddSubWindow', 'itfAddFieldListDiv', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2026030300000016, 'EM000016', 016, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseName', 'input', '数据库名称', 'out="Y"', NULL),
(2026030300000017, 'EM000017', 017, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseType', 'input', '数据库类型', 'out="Y"', NULL),
(2026030300000018, 'EM000018', 018, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseDriver', 'input', '数据库驱动', 'out="Y"', NULL),
(2026030300000019, 'EM000019', 019, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseAddr', 'input', '数据库地址', 'out="Y"', NULL),
(2026030300000020, 'EM000020', 020, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseAttr', 'input', '数据库连接属性', 'out="Y"', NULL),
(2026030300000021, 'EM000021', 021, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseLabel', 'input', '数据库连接标签', 'out="Y"', NULL),
(2026030300000022, 'EM000022', 022, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'loginName', 'input', '登录用户名', 'out="Y"', NULL),
(2026030300000023, 'EM000023', 023, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'loginPassword', 'input', '登录密码', 'out="Y"', NULL),
(2026030300000024, 'EM000024', 024, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddSubWindow', 'itfAddSubWindowFooterDiv', 'div', '', 'class="subWidowFooter"', NULL),
(2026030300000025, 'EM000025', 025, 'DbSourceConfig', 'itfAddSbwPage', 'itfAddSubWindowFooterDiv', 'itf_add_button', 'button', '提交', 'class="inputArea_sub_button"', ''),
(2026030300000026, 'EM000026', 026, 'DbSourceConfig', 'itfEdtSbwPage', 'body', 'itfEdtSubWindow', 'subWindow', '编辑数据源配置', '', NULL),
(2026030300000027, 'EM000027', 027, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtSubWindow', 'itfEdtFieldListDiv', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2026030300000028, 'EM000028', 028, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseName', 'input', '数据库名称', 'out="Y"', NULL),
(2026030300000029, 'EM000029', 029, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseType', 'input', '数据库类型', 'out="Y"', NULL),
(2026030300000030, 'EM000030', 030, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseDriver', 'input', '数据库驱动', 'out="Y"', NULL),
(2026030300000031, 'EM000031', 031, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseAddr', 'input', '数据库地址', 'out="Y"', NULL),
(2026030300000032, 'EM000032', 032, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseAttr', 'input', '数据库连接属性', 'out="Y"', NULL),
(2026030300000033, 'EM000033', 033, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseLabel', 'input', '数据库连接标签', 'out="Y"', NULL),
(2026030300000034, 'EM000034', 034, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'loginName', 'input', '登录用户名', 'out="Y"', NULL),
(2026030300000035, 'EM000035', 035, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'loginPassword', 'input', '登录密码', 'out="Y"', NULL),
(2026030300000036, 'EM000036', 036, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtSubWindow', 'itfEdtSubWindowFooterDiv', 'div', '', 'class="subWidowFooter"', NULL),
(2026030300000037, 'EM000037', 037, 'DbSourceConfig', 'itfEdtSbwPage', 'itfEdtSubWindowFooterDiv', 'itf_edt_button', 'button', '提交', 'class="inputArea_sub_button"', NULL);

DELETE FROM web_event WHERE menu = 'DbSourceConfig';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2026030300000001, 'DbSourceConfig', 'menuEvent', '1', 'click', 'menuReq', 'DbSourceConfig', '', ''),
(2026030300000002, 'DbSourceConfig', 'start_page', 'itf_cond_query_btn', 'click', 'buttonReq', 'configDatabaseInfoQry', 'DbSourceConfig#start_page#itfOutArea', '{"dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030300000003, 'DbSourceConfig', 'start_page', 'show_itf_add_sbw_btn', 'click', 'swDataReq', 'DbSourceConfig', 'itfAddSbwPage', ''),
(2026030300000004, 'DbSourceConfig', 'start_page', 'itf_del_button', 'click', 'buttonReq', 'configDatabaseInfoDel', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),
(2026030300000005, 'DbSourceConfig', 'itfAddSbwPage', 'itf_add_button', 'click', 'buttonReq', 'configDatabaseInfoAdd', '', ''),
(2026030300000006, 'DbSourceConfig', 'itfEdtSbwPage', 'itf_edt_button', 'click', 'buttonReq', 'configDatabaseInfoUpd', NULL, ''),
(2026030300000007, 'DbSourceConfig', 'start_page', 'show_itf_edt_sbw_btn', 'click', 'swDataReq', 'DbSourceConfig', 'itfEdtSbwPage', '{"valueFromSelectedRecord":true}');

DELETE FROM web_data WHERE menu = 'DbSourceConfig';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2026030300000001, 'DbSourceConfig', 'start_page', 'itfInfoTable', 'tableHead', '', '{"headMap":{"databaseName":"数据库名称","databaseType":"数据库类型","databaseDriver":"数据库驱动","databaseAddr":"数据库地址","databaseAttr":"数据库连接属性","databaseLabel":"数据库连接标签","loginName":"登录用户名","loginPassword":"登录密码"}}');

DELETE FROM web_call_after WHERE menu = 'DbSourceConfig';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2026030300000001, 'DbSourceConfig', 'start_page', 'itf_del_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除配置失败"}'),
(2026030300000002, 'DbSourceConfig', 'start_page', 'itf_del_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除配置"}'),
(2026030300000003, 'DbSourceConfig', 'start_page', 'itf_del_button', 'success', 'request', 'buttonReq', 'configDatabaseInfoQry', '{"nextPage":"DbSourceConfig#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030300000004, 'DbSourceConfig', 'itfAddSbwPage', 'itf_add_button', 'false', 'showMessage', '', '', '{"msg":"新增配置失败!"}'),
(2026030300000005, 'DbSourceConfig', 'itfAddSbwPage', 'itf_add_button', 'success', 'showMessage', '', '', '{"msg":"新增配置成功!"}'),
(2026030300000006, 'DbSourceConfig', 'itfAddSbwPage', 'itf_add_button', 'success', 'closeSw', '', '', '{"subWindowId":"itfAddSubWindow"}'),
(2026030300000007, 'DbSourceConfig', 'itfAddSbwPage', 'itf_add_button', 'success', 'request', 'buttonReq', 'configDatabaseInfoQry', '{"nextPage":"DbSourceConfig#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030300000008, 'DbSourceConfig', 'itfEdtSbwPage', 'itf_edt_button', 'false', 'showMessage', NULL, NULL, '{"msg":"更新配置失败"}'),
(2026030300000009, 'DbSourceConfig', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新配置"}'),
(2026030300000010, 'DbSourceConfig', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'closeSw', '', '', '{"subWindowId":"itfEdtSubWindow"}'),
(2026030300000011, 'DbSourceConfig', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'request', 'buttonReq', 'configDatabaseInfoQry', '{"nextPage":"DbSourceConfig#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}');



