
DELETE FROM web_menu WHERE menu = 'LoanDataChg';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0025', 25, 'LoanDataChg', 'SystemMaintain', '贷款数据修改');

DELETE FROM web_element WHERE menu = 'LoanDataChg';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2026030301000001, 'EM000001', 001, 'LoanDataChg', 'start_page', 'contentArea', 'inputArea', 'div', '', '', ''),
(2026030301000002, 'EM000002', 002, 'LoanDataChg', 'start_page', 'inputArea', 'itfButtonArea', 'div', '', '', ''),
(2026030301000003, 'EM000003', 003, 'LoanDataChg', 'start_page', 'itfButtonArea', 'show_itf_add_sbw_btn', 'button', '新增数据源配置', 'class="inputArea_sub_button"', ''),
(2026030301000004, 'EM000004', 004, 'LoanDataChg', 'start_page', 'inputArea', 'itfQueryArea', 'div', '', 'class="area_div"', ''),
(2026030301000005, 'EM000005', 005, 'LoanDataChg', 'start_page', 'itfQueryArea', 'queryAreaDivTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2026030301000006, 'EM000006', 006, 'LoanDataChg', 'start_page', 'itfQueryArea', 'queryLoanNo', 'input', '出账编号', 'out="Y"', ''),
(2026030301000008, 'EM000008', 008, 'LoanDataChg', 'start_page', 'itfQueryArea', 'itf_cond_query_btn', 'button', '查询', 'class="inputArea_sub_button"', ''),

(2026030301000008, 'EM002001', 001, 'LoanDataChg', 'start_page', 'contentArea', 'queryArea', 'div', '查询区域', 'class="showWithRowDivGrid"', NULL),
(2026030301000008, 'EM002002', 002, 'LoanDataChg', 'start_page', 'queryArea', 'queryAreaDivTitle', 'divTitle', '查询参数', 'class="content_title"', ''),
(2026030301000008, 'EM002003', 003, 'LoanDataChg', 'start_page', 'queryArea', 'queryArea2', 'div', '查询输入区域', 'class="inputArea"', NULL),
(2026030301000008, 'EM002004', 004, 'LoanDataChg', 'start_page', 'queryArea2', 'queryLoanNo', 'input', '出账编号', 'out="Y"', NULL),
(2026030301000008, 'EM002005', 005, 'LoanDataChg', 'start_page', 'queryArea2', 'dbName', 'selectOption', '数据库名称', 'out="Y"', NULL),
(2026030301000008, 'EM002006', 006, 'LoanDataChg', 'start_page', 'queryArea', 'personCondArea', 'div', '查询按钮区域', 'class="inputArea"', NULL),
(2026030301000008, 'EM002007', 007, 'LoanDataChg', 'start_page', 'personCondArea', 'personCondAreaDivTitle', 'divTitle', '查询按钮', 'class="content_title"', ''),
(2026030301000008, 'EM002008', 008, 'LoanDataChg', 'start_page', 'personCondArea', 'personCondDiv', 'div', '自定义查询条件输入区域', 'class="inputArea"', NULL),
(2026030301000008, 'EM002009', 009, 'LoanDataChg', 'start_page', 'queryArea', 'queryButton', 'button', '查询', 'class="inputArea_sub_button"#style="width:100px;"', NULL),

(2026030301000009, 'EM000009', 009, 'LoanDataChg', 'start_page', 'inputArea', 'itfOutArea', 'div', '', '', ''),
(2026030301000010, 'EM000010', 010, 'LoanDataChg', 'start_page', 'itfOutArea', 'itfOutAreaTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2026030301000011, 'EM000011', 011, 'LoanDataChg', 'start_page', 'itfOutArea', 'itfInfoTable', 'table', '新增查询配置表', 'class="output_table"', '{"hideFields":["LoanDataChgId"]}'),
(2026030301000012, 'EM000012', 012, 'LoanDataChg', 'start_page', 'itfInfoTable', 'show_itf_edt_sbw_btn', 'table_record_button', '编辑', 'class="label_button"', NULL),
(2026030301000013, 'EM000013', 013, 'LoanDataChg', 'start_page', 'itfInfoTable', 'itf_del_button', 'table_record_button', '删除', 'class="label_button"', NULL),
(2026030301000014, 'EM000014', 014, 'LoanDataChg', 'itfAddSbwPage', 'body', 'itfAddSubWindow', 'subWindow', '新增配置定义', '', NULL),
(2026030301000015, 'EM000015', 015, 'LoanDataChg', 'itfAddSbwPage', 'itfAddSubWindow', 'itfAddFieldListDiv', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2026030301000016, 'EM000016', 016, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseName', 'input', '数据库名称', 'out="Y"', NULL),
(2026030301000017, 'EM000017', 017, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseType', 'input', '数据库类型', 'out="Y"', NULL),
(2026030301000018, 'EM000018', 018, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseDriver', 'input', '数据库驱动', 'out="Y"', NULL),
(2026030301000019, 'EM000019', 019, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseAddr', 'input', '数据库地址', 'out="Y"', NULL),
(2026030301000020, 'EM000020', 020, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseAttr', 'input', '数据库连接属性', 'out="Y"', NULL),
(2026030301000021, 'EM000021', 021, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'databaseLabel', 'input', '数据库连接标签', 'out="Y"', NULL),
(2026030301000022, 'EM000022', 022, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'loginName', 'input', '登录用户名', 'out="Y"', NULL),
(2026030301000023, 'EM000023', 023, 'LoanDataChg', 'itfAddSbwPage', 'itfAddFieldListDiv', 'loginPassword', 'input', '登录密码', 'out="Y"', NULL),
(2026030301000024, 'EM000024', 024, 'LoanDataChg', 'itfAddSbwPage', 'itfAddSubWindow', 'itfAddSubWindowFooterDiv', 'div', '', 'class="subWidowFooter"', NULL),
(2026030301000025, 'EM000025', 025, 'LoanDataChg', 'itfAddSbwPage', 'itfAddSubWindowFooterDiv', 'itf_add_button', 'button', '提交', 'class="inputArea_sub_button"', ''),
(2026030301000026, 'EM000026', 026, 'LoanDataChg', 'itfEdtSbwPage', 'body', 'itfEdtSubWindow', 'subWindow', '编辑数据源配置', '', NULL),
(2026030301000027, 'EM000027', 027, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtSubWindow', 'itfEdtFieldListDiv', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2026030301000028, 'EM000028', 028, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseName', 'input', '数据库名称', 'out="Y"', NULL),
(2026030301000029, 'EM000029', 029, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseType', 'input', '数据库类型', 'out="Y"', NULL),
(2026030301000030, 'EM000030', 030, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseDriver', 'input', '数据库驱动', 'out="Y"', NULL),
(2026030301000031, 'EM000031', 031, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseAddr', 'input', '数据库地址', 'out="Y"', NULL),
(2026030301000032, 'EM000032', 032, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseAttr', 'input', '数据库连接属性', 'out="Y"', NULL),
(2026030301000033, 'EM000033', 033, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'databaseLabel', 'input', '数据库连接标签', 'out="Y"', NULL),
(2026030301000034, 'EM000034', 034, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'loginName', 'input', '登录用户名', 'out="Y"', NULL),
(2026030301000035, 'EM000035', 035, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'loginPassword', 'input', '登录密码', 'out="Y"', NULL),
(2026030301000036, 'EM000036', 036, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtSubWindow', 'itfEdtSubWindowFooterDiv', 'div', '', 'class="subWidowFooter"', NULL),
(2026030301000037, 'EM000037', 037, 'LoanDataChg', 'itfEdtSbwPage', 'itfEdtSubWindowFooterDiv', 'itf_edt_button', 'button', '提交', 'class="inputArea_sub_button"', NULL);

DELETE FROM web_event WHERE menu = 'LoanDataChg';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2026030301000001, 'LoanDataChg', 'menuEvent', '1', 'click', 'menuReq', 'LoanDataChg', '', ''),
(2026030301000002, 'LoanDataChg', 'start_page', 'itf_cond_query_btn', 'click', 'buttonReq', 'configDatabaseInfoQry', 'LoanDataChg#start_page#itfOutArea', '{"dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030301000003, 'LoanDataChg', 'start_page', 'show_itf_add_sbw_btn', 'click', 'swDataReq', 'LoanDataChg', 'itfAddSbwPage', ''),
(2026030301000004, 'LoanDataChg', 'start_page', 'itf_del_button', 'click', 'buttonReq', 'configDatabaseInfoDel', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),
(2026030301000005, 'LoanDataChg', 'itfAddSbwPage', 'itf_add_button', 'click', 'buttonReq', 'configDatabaseInfoAdd', '', ''),
(2026030301000006, 'LoanDataChg', 'itfEdtSbwPage', 'itf_edt_button', 'click', 'buttonReq', 'configDatabaseInfoUpd', NULL, ''),
(2026030301000007, 'LoanDataChg', 'start_page', 'show_itf_edt_sbw_btn', 'click', 'swDataReq', 'LoanDataChg', 'itfEdtSbwPage', '{"valueFromSelectedRecord":true}');

DELETE FROM web_data WHERE menu = 'LoanDataChg';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2026030301000001, 'LoanDataChg', 'start_page', 'itfInfoTable', 'tableHead', '', '{"headMap":{"databaseName":"数据库名称","databaseType":"数据库类型","databaseDriver":"数据库驱动","databaseAddr":"数据库地址","databaseAttr":"数据库连接属性","databaseLabel":"数据库连接标签","loginName":"登录用户名","loginPassword":"登录密码"}}');

DELETE FROM web_call_after WHERE menu = 'LoanDataChg';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2026030301000001, 'LoanDataChg', 'start_page', 'itf_del_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除配置失败"}'),
(2026030301000002, 'LoanDataChg', 'start_page', 'itf_del_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除配置"}'),
(2026030301000003, 'LoanDataChg', 'start_page', 'itf_del_button', 'success', 'request', 'buttonReq', 'configDatabaseInfoQry', '{"nextPage":"LoanDataChg#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030301000004, 'LoanDataChg', 'itfAddSbwPage', 'itf_add_button', 'false', 'showMessage', '', '', '{"msg":"新增配置失败!"}'),
(2026030301000005, 'LoanDataChg', 'itfAddSbwPage', 'itf_add_button', 'success', 'showMessage', '', '', '{"msg":"新增配置成功!"}'),
(2026030301000006, 'LoanDataChg', 'itfAddSbwPage', 'itf_add_button', 'success', 'closeSw', '', '', '{"subWindowId":"itfAddSubWindow"}'),
(2026030301000007, 'LoanDataChg', 'itfAddSbwPage', 'itf_add_button', 'success', 'request', 'buttonReq', 'configDatabaseInfoQry', '{"nextPage":"LoanDataChg#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030301000008, 'LoanDataChg', 'itfEdtSbwPage', 'itf_edt_button', 'false', 'showMessage', NULL, NULL, '{"msg":"更新配置失败"}'),
(2026030301000009, 'LoanDataChg', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新配置"}'),
(2026030301000010, 'LoanDataChg', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'closeSw', '', '', '{"subWindowId":"itfEdtSubWindow"}'),
(2026030301000011, 'LoanDataChg', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'request', 'buttonReq', 'configDatabaseInfoQry', '{"nextPage":"LoanDataChg#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}');



