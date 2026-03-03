
DELETE FROM web_menu WHERE menu = 'CommonQueryConfig';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0023', 23, 'CommonQueryConfig', 'SystemMaintain', '通用查询配置');

DELETE FROM web_element WHERE menu = 'CommonQueryConfig';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2026030200000001, 'EM000001', 001, 'CommonQueryConfig', 'start_page', 'contentArea', 'inputArea', 'div', '', '', ''),
(2026030200000002, 'EM000002', 002, 'CommonQueryConfig', 'start_page', 'inputArea', 'itfButtonArea', 'div', '', '', ''),
(2026030200000004, 'EM000004', 004, 'CommonQueryConfig', 'start_page', 'itfButtonArea', 'show_itf_add_sbw_btn', 'button', '新增查询配置', 'class="inputArea_sub_button"', ''),
(2026030200000005, 'EM000005', 005, 'CommonQueryConfig', 'start_page', 'inputArea', 'itfQueryArea', 'div', '', 'class="area_div"', ''),
(2026030200000006, 'EM000006', 006, 'CommonQueryConfig', 'start_page', 'itfQueryArea', 'queryAreaDivTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2026030200000007, 'EM000007', 007, 'CommonQueryConfig', 'start_page', 'itfQueryArea', 'inputQueryName', 'input', '查询名称', 'out="Y"', ''),
(2026030200000008, 'EM000008', 008, 'CommonQueryConfig', 'start_page', 'itfQueryArea', 'itf_cond_query_btn', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2026030200000009, 'EM000009', 009, 'CommonQueryConfig', 'start_page', 'inputArea', 'itfOutArea', 'div', '', '', ''),
(2026030200000010, 'EM000010', 010, 'CommonQueryConfig', 'start_page', 'itfOutArea', 'itfOutAreaTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2026030200000011, 'EM000011', 011, 'CommonQueryConfig', 'start_page', 'itfOutArea', 'itfInfoTable', 'table', '新增查询配置表', 'class="output_table"', '{"hideFields":["commonQueryConfigId"]}'),
(2026030200000012, 'EM000012', 012, 'CommonQueryConfig', 'start_page', 'itfInfoTable', 'show_itf_edt_sbw_btn', 'table_record_button', '编辑', 'class="label_button"', NULL),
(2026030200000014, 'EM000014', 014, 'CommonQueryConfig', 'start_page', 'itfInfoTable', 'itf_del_button', 'table_record_button', '删除', 'class="label_button"', NULL),
(2026030200000015, 'EM000015', 015, 'CommonQueryConfig', 'itfAddSbwPage', 'body', 'itfAddSubWindow', 'subWindow', '新增配置定义', '', NULL),
(2026030200000016, 'EM000016', 016, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddSubWindow', 'itfAddFieldListDiv', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2026030200000017, 'EM000017', 017, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'queryType', 'input', '查询类型', 'out="Y"', NULL),
(2026030200000018, 'EM000018', 018, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'queryName', 'input', '查询名称', 'out="Y"', NULL),
(2026030200000019, 'EM000019', 019, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'queryDesc', 'input', '查询描述', 'out="Y"', NULL),
(2026030200000020, 'EM000020', 020, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'queryCond', 'textareaLabel', '查询条件配置', 'out="Y"#style="width:700px;field-sizing:content"', '{"setData":true}'),
(2026030200000021, 'EM000021', 021, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddFieldListDiv', 'querySql', 'textareaLabel', '查询SQL', 'out="Y"#style="width:700px;field-sizing:content"', '{"setData":true}'),
(2026030200000022, 'EM000022', 022, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddSubWindow', 'itfAddSubWindowFooterDiv', 'div', '', 'class="subWidowFooter"', NULL),
(2026030200000024, 'EM000024', 024, 'CommonQueryConfig', 'itfAddSbwPage', 'itfAddSubWindowFooterDiv', 'itf_add_button', 'button', '提交', 'class="inputArea_sub_button"', ''),
(2026030200000025, 'EM000025', 025, 'CommonQueryConfig', 'itfEdtSbwPage', 'body', 'itfEdtSubWindow', 'subWindow', '编辑字典定义', '', NULL),
(2026030200000026, 'EM000026', 026, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtSubWindow', 'itfEdtFieldListDiv', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2026030200000027, 'EM000027', 027, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'commonQueryConfigId', 'input', 'id', 'out="Y"', '{"hide":true}'),
(2026030200000028, 'EM000028', 028, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'queryType', 'input', '查询类型', 'out="Y"', NULL),
(2026030200000029, 'EM000029', 029, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'queryName', 'input', '查询名称', 'out="Y"', NULL),
(2026030200000030, 'EM000030', 030, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'queryDesc', 'input', '查询描述', 'out="Y"', NULL),
(2026030200000031, 'EM000031', 031, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'queryCond', 'textareaLabel', '查询条件配置', 'out="Y"#style="width:700px;field-sizing:content"', '{"setData":true}'),
(2026030200000032, 'EM000032', 032, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtFieldListDiv', 'querySql', 'textareaLabel', '查询SQL', 'out="Y"#style="width:700px;field-sizing:content"', '{"setData":true}'),
(2026030200000033, 'EM000033', 033, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtSubWindow', 'itfEdtSubWindowFooterDiv', 'div', '', 'class="subWidowFooter"', NULL),
(2026030200000034, 'EM000034', 034, 'CommonQueryConfig', 'itfEdtSbwPage', 'itfEdtSubWindowFooterDiv', 'itf_edt_button', 'button', '提交', 'class="inputArea_sub_button"', NULL);

DELETE FROM web_event WHERE menu = 'CommonQueryConfig';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2026030200000001, 'CommonQueryConfig', 'menuEvent', '1', 'click', 'menuReq', 'CommonQueryConfig', '', ''),
(2026030200000002, 'CommonQueryConfig', 'start_page', 'itf_cond_query_btn', 'click', 'buttonReq', 'commonQueryConfigQry', 'CommonQueryConfig#start_page#itfOutArea', '{"dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030200000003, 'CommonQueryConfig', 'start_page', 'show_itf_add_sbw_btn', 'click', 'swDataReq', 'CommonQueryConfig', 'itfAddSbwPage', ''),
(2026030200000004, 'CommonQueryConfig', 'start_page', 'itf_del_button', 'click', 'buttonReq', 'commonQueryConfigDel', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？"}'),
(2026030200000005, 'CommonQueryConfig', 'itfAddSbwPage', 'itf_add_button', 'click', 'buttonReq', 'commonQueryConfigAdd', '', ''),
(2026030200000006, 'CommonQueryConfig', 'itfEdtSbwPage', 'itf_edt_button', 'click', 'buttonReq', 'commonQueryConfigUpd', NULL, ''),
(2026030200000007, 'CommonQueryConfig', 'start_page', 'show_itf_edt_sbw_btn', 'click', 'swDataReq', 'CommonQueryConfig', 'itfEdtSbwPage', '{"valueFromSelectedRecord":true}');

DELETE FROM web_data WHERE menu = 'CommonQueryConfig';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2026030200000001, 'CommonQueryConfig', 'start_page', 'itfInfoTable', 'tableHead', '', '{"headMap":{"commonQueryConfigId":"记录id","queryType":"查询类型","queryName":"查询名称","queryDesc":"查询描述","queryCond":"查询条件配置","querySql":"查询SQL"}}');

DELETE FROM web_call_after WHERE menu = 'CommonQueryConfig';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2026030200000001, 'CommonQueryConfig', 'start_page', 'itf_del_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除配置失败"}'),
(2026030200000002, 'CommonQueryConfig', 'start_page', 'itf_del_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除配置"}'),
(2026030200000003, 'CommonQueryConfig', 'start_page', 'itf_del_button', 'success', 'request', 'buttonReq', 'commonQueryConfigQry', '{"nextPage":"CommonQueryConfig#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030200000004, 'CommonQueryConfig', 'itfAddSbwPage', 'itf_add_button', 'false', 'showMessage', '', '', '{"msg":"新增配置失败!"}'),
(2026030200000005, 'CommonQueryConfig', 'itfAddSbwPage', 'itf_add_button', 'success', 'showMessage', '', '', '{"msg":"新增配置成功!"}'),
(2026030200000006, 'CommonQueryConfig', 'itfAddSbwPage', 'itf_add_button', 'success', 'closeSw', '', '', '{"subWindowId":"itfAddSubWindow"}'),
(2026030200000007, 'CommonQueryConfig', 'itfAddSbwPage', 'itf_add_button', 'success', 'request', 'buttonReq', 'commonQueryConfigQry', '{"nextPage":"CommonQueryConfig#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}'),
(2026030200000008, 'CommonQueryConfig', 'itfEdtSbwPage', 'itf_edt_button', 'false', 'showMessage', NULL, NULL, '{"msg":"更新配置失败"}'),
(2026030200000009, 'CommonQueryConfig', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新配置"}'),
(2026030200000010, 'CommonQueryConfig', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'closeSw', '', '', '{"subWindowId":"itfEdtSubWindow"}'),
(2026030200000011, 'CommonQueryConfig', 'itfEdtSbwPage', 'itf_edt_button', 'success', 'request', 'buttonReq', 'commonQueryConfigQry', '{"nextPage":"CommonQueryConfig#start_page#itfInfoTable","dataToEle":"itfInfoTable","dataToElePos":"data","dataToEleType":"list"}');



