

DELETE FROM web_menu WHERE menu = 'DataDict';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0013', 3, 'DataDict', 'SystemMaintain', '数据字典');

DELETE FROM web_element WHERE menu = 'DataDict';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2025070100006301, 'EM006301', 01, 'DataDict', 'start_page', 'contentArea', 'inputArea', 'div', '', 'class="inputArea background_color_1"', ''),
(2025070100006302, 'EM006302', 02, 'DataDict', 'start_page', 'inputArea', 'dictButtonArea', 'div', '', '', ''),
(2025070100006303, 'EM006303', 03, 'DataDict', 'start_page', 'dictButtonArea', 'addNewDict', 'button', '新增字典定义', 'class="inputArea_sub_button"', ''),
(2025070100006304, 'EM006304', 04, 'DataDict', 'start_page', 'inputArea', 'queryArea', 'div', '', '', ''),
(2025070100006305, 'EM006305', 05, 'DataDict', 'start_page', 'queryArea', 'queryAreaDivTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2025070100006306, 'EM006306', 06, 'DataDict', 'start_page', 'queryArea', 'queryConditionDictCode', 'input', '字典码', 'out="Y"', ''),
(2025070100006307, 'EM006307', 07, 'DataDict', 'start_page', 'queryArea', 'queryBut', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2025070100006308, 'EM006308', 08, 'DataDict', 'start_page', 'inputArea', 'outArea', 'div', '', 'class="inputArea background_color_1"', ''),
(2025070100006309, 'EM006309', 09, 'DataDict', 'start_page', 'outArea', 'outAreaDiv', 'div', '', '', ''),
(2025070100006310, 'EM006310', 10, 'DataDict', 'out_page', 'outAreaDiv', 'outAreaDiv1', 'div', '', '', ''),
(2025070100006311, 'EM006311', 11, 'DataDict', 'out_page', 'outAreaDiv1', 'outAreaDivTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2025070100006312, 'EM006312', 12, 'DataDict', 'out_page', 'outAreaDiv1', 'dictTable', 'table', '字典定义表', 'class="output_table"', '{"hideFields":["sysDictId"]}'),
(2025070100006313, 'EM006313', 13, 'DataDict', 'out_page', 'dictTable', 'dsp_enum_button', 'table_record_button', '枚举详情', 'class="label_button"', NULL),
(2025070100006314, 'EM006314', 14, 'DataDict', 'out_page', 'dictTable', 'edt_dict_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
(2025070100006315, 'EM006315', 15, 'DataDict', 'out_page', 'dictTable', 'del_dict_button', 'table_record_button', '删除', 'class="label_button"', NULL),
(2025070100006316, 'EM006316', 16, 'DataDict', 'out_page', 'outAreaDiv1', 'dictDetailOutArea', 'div', '', '', ''),
(2025070100006317, 'EM006317', 17, 'DataDict', 'dictDetailOutPage', 'body', 'dict_detail_sub_window', 'subWindow', '字典明细', '', NULL),
(2025070100006318, 'EM006318', 18, 'DataDict', 'dictDetailOutPage', 'dict_detail_sub_window_swBody', 'dictDetailOutArea1', 'div', '', '', ''),
(2025070100006319, 'EM006319', 19, 'DataDict', 'dictDetailOutPage', 'dictDetailOutArea1', 'enumButtonArea', 'div', '', '', ''),
(2025070100006320, 'EM006330', 20, 'DataDict', 'dictDetailOutPage', 'enumButtonArea', 'addNewEnum', 'button', '新增枚举', 'class="inputArea_sub_button"', ''),
(2025070100006321, 'EM006321', 21, 'DataDict', 'dictDetailOutPage', 'dictDetailOutArea1', 'dictDetailDiv', 'div', 'div', 'class="inputArea background_color_1"', ''),
(2025070100006322, 'EM006322', 22, 'DataDict', 'dictDetailOutPage', 'dictDetailDiv', 'dictDetailDivTitle', 'divTitle', '字典详情', 'class="content_title"', ''),
(2025070100006323, 'EM006323', 23, 'DataDict', 'dictDetailOutPage', 'dictDetailDiv', 'ld_dictCode', 'input', '字典码', '', '{"dataField":"dictCode"}'),
(2025070100006324, 'EM006324', 24, 'DataDict', 'dictDetailOutPage', 'dictDetailDiv', 'ld_dictDesc', 'input', '字典描述', '', '{"dataField":"description"}'),
(2025070100006325, 'EM006325', 25, 'DataDict', 'dictDetailOutPage', 'dictDetailDiv', 'dictEnumPDiv', 'div', '字典枚举div', '', ''),
(2025070100006326, 'EM006326', 26, 'DataDict', 'dictEnumPage', 'dictEnumPDiv', 'dictEnumDiv', 'div', 'div', '', ''),
(2025070100006327, 'EM006327', 27, 'DataDict', 'dictEnumPage', 'dictEnumDiv', 'dictEnumDivTitle', 'divTitle', '字典枚举定义', 'class="content_title"', ''),
(2025070100006328, 'EM006328', 28, 'DataDict', 'dictEnumPage', 'dictEnumDiv', 'dictEnumTable', 'table', '字典枚举定义表', 'class="output_table"', '{"hideFields":["sysDictId"]}'),
(2025070100006329, 'EM006329', 29, 'DataDict', 'dictEnumPage', 'dictEnumTable', 'edt_enum_button', 'table_record_button', '编辑', 'class="label_button"', NULL),
(2025070100006330, 'EM006330', 30, 'DataDict', 'dictEnumPage', 'dictEnumTable', 'del_enum_button', 'table_record_button', '删除', 'class="label_button"', NULL),
(2025070100006331, 'EM006331', 31, 'DataDict', 'add_dict_sbw', 'body', 'add_dict_sub_window', 'subWindow', '新增字典定义', '', NULL),
(2025070100006332, 'EM006332', 32, 'DataDict', 'add_dict_sbw', 'add_dict_sub_window_swBody', 'add_dict_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2025070100006333, 'EM006333', 33, 'DataDict', 'add_dict_sbw', 'add_dict_sbw_div', 'dictCode', 'input', '字典码', 'out="Y"', NULL),
(2025070100006334, 'EM006334', 34, 'DataDict', 'add_dict_sbw', 'add_dict_sbw_div', 'description', 'input', '字典描述', 'out="Y"', NULL),
(2025070100006335, 'EM006335', 35, 'DataDict', 'add_dict_sbw', 'add_dict_sub_window_swFooter', 'addDictButton', 'button', '提交', 'class="inputArea_sub_button"', ''),
(2025070100006336, 'EM006336', 36, 'DataDict', 'edt_dict_sbw', 'body', 'edt_dict_sub_window', 'subWindow', '编辑字典定义', '', NULL),
(2025070100006337, 'EM006337', 37, 'DataDict', 'edt_dict_sbw', 'edt_dict_sub_window_swBody', 'edt_dict_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2025070100006338, 'EM006338', 38, 'DataDict', 'edt_dict_sbw', 'edt_dict_sbw_div', 'sys_dict_id', 'input', 'id', 'out="Y"', '{"hide":true}'),
(2025070100006339, 'EM006339', 39, 'DataDict', 'edt_dict_sbw', 'edt_dict_sbw_div', 'dictCode', 'input', '字典码', 'out="Y"', NULL),
(2025070100006340, 'EM006340', 40, 'DataDict', 'edt_dict_sbw', 'edt_dict_sbw_div', 'description', 'input', '字典描述', 'out="Y"', NULL),
(2025070100006341, 'EM006341', 41, 'DataDict', 'edt_dict_sbw', 'edt_dict_sub_window_swFooter', 'edtDictButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
(2025070100006342, 'EM006342', 42, 'DataDict', 'add_enum_sbw', 'body', 'add_enum_sub_window', 'subWindow', '新增枚举', '', NULL),
(2025070100006343, 'EM006343', 43, 'DataDict', 'add_enum_sbw', 'add_enum_sub_window_swBody', 'add_enum_sbw_div', 'div', '', 'class="showWithRowDivGrid"', ''),
(2025070100006344, 'EM006344', 44, 'DataDict', 'add_enum_sbw', 'add_enum_sbw_div', 'dictCode', 'input', '字典码', 'out="Y"', '{"valueFrmWeb":"ld_dictCode","hide":true}'),
(2025070100006345, 'EM006345', 45, 'DataDict', 'add_enum_sbw', 'add_enum_sbw_div', 'enumCode', 'input', '枚举码', 'out="Y"', NULL),
(2025070100006346, 'EM006346', 46, 'DataDict', 'add_enum_sbw', 'add_enum_sbw_div', 'enumValue', 'input', '枚举值', 'out="Y"', NULL),
(2025070100006347, 'EM006347', 47, 'DataDict', 'add_enum_sbw', 'add_enum_sbw_div', 'description', 'input', '描述', 'out="Y"', NULL),
(2025070100006348, 'EM006348', 48, 'DataDict', 'add_enum_sbw', 'add_enum_sub_window_swFooter', 'addEnumButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),
(2025070100006349, 'EM006349', 49, 'DataDict', 'edt_enum_sbw', 'body', 'edt_enum_sub_window', 'subWindow', '编辑字典定义', '', NULL),
(2025070100006350, 'EM006340', 50, 'DataDict', 'edt_enum_sbw', 'edt_enum_sub_window_swBody', 'edt_enum_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2025070100006351, 'EM006351', 51, 'DataDict', 'edt_enum_sbw', 'edt_enum_sbw_div', 'sysDictId', 'input', '字典记录id', 'out="Y"', '{"hide":true}'),
(2025070100006352, 'EM006352', 52, 'DataDict', 'edt_enum_sbw', 'edt_enum_sbw_div', 'enumCode', 'input', '枚举码', 'out="Y"', NULL),
(2025070100006353, 'EM006353', 53, 'DataDict', 'edt_enum_sbw', 'edt_enum_sbw_div', 'enumValue', 'input', '枚举值', 'out="Y"', NULL),
(2025070100006354, 'EM006354', 54, 'DataDict', 'edt_enum_sbw', 'edt_enum_sbw_div', 'description', 'input', '描述', 'out="Y"', NULL),
(2025070100006355, 'EM006355', 55, 'DataDict', 'edt_enum_sbw', 'edt_enum_sub_window_swFooter', 'edtEnumButton', 'button', '提交', 'class="inputArea_sub_button"', NULL);

DELETE FROM web_event WHERE menu = 'DataDict';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2025070100006301, 'DataDict', 'menuEvent', '1', 'click', 'menuReq', 'DataDict', '', ''),
(2025070100006302, 'DataDict', 'start_page', 'addNewDict', 'click', 'swDataReq', 'DataDict', 'add_dict_sbw', ''),
(2025070100006303, 'DataDict', 'add_dict_sbw', 'addDictButton', 'click', 'buttonReq', 'addDictDef', '', '{"host":"lcs"}'),
(2025070100006304, 'DataDict', 'start_page', 'queryBut', 'click', 'buttonReq', 'pageDictDef', 'DataDict#out_page#outAreaDiv1', '{"host":"lcs","dataToEle":"dictTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025070100006305, 'DataDict', 'out_page', 'dsp_enum_button', 'click', 'swDataReq', 'DataDict', 'dictDetailOutPage', '{"host":"lcs"}'),
(2025070100006306, 'DataDict', 'out_page', 'dsp_enum_button', 'click', 'buttonReq', 'getDictDef', 'DataDict#dictDetailOutPage#dictDetailOutArea1', '{"host":"lcs"}'),
(2025070100006307, 'DataDict', 'out_page', 'edt_dict_button', 'click', 'swDataReq', 'DataDict', 'edt_dict_sbw', '{"valueFromSelectedRecord":true}'),
(2025070100006308, 'DataDict', 'edt_dict_sbw', 'edtDictButton', 'click', 'buttonReq', 'updDictDef', NULL, '{"host":"lcs"}'),
(2025070100006309, 'DataDict', 'out_page', 'del_dict_button', 'click', 'buttonReq', 'delDictDef', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？","host":"lcs"}'),
(2025070100006310, 'DataDict', 'dictDetailOutPage', 'addNewEnum', 'click', 'swDataReq', 'DataDict', 'add_enum_sbw', ''),
(2025070100006311, 'DataDict', 'add_enum_sbw', 'addEnumButton', 'click', 'buttonReq', 'addDictEnum', NULL, '{"host":"lcs"}'),
(2025070100006312, 'DataDict', 'dictEnumPage', 'edt_enum_button', 'click', 'swDataReq', 'DataDict', 'edt_enum_sbw', '{"valueFromSelectedRecord":true}'),
(2025070100006313, 'DataDict', 'edt_enum_sbw', 'edtEnumButton', 'click', 'buttonReq', 'updDictEnum', NULL, '{"host":"lcs"}'),
(2025070100006314, 'DataDict', 'dictEnumPage', 'del_enum_button', 'click', 'buttonReq', 'delDictEnum', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？","host":"lcs"}');

DELETE FROM web_data WHERE menu = 'DataDict';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2025063000006301, 'DataDict', 'out_page', 'dictTable', 'tableHead', '', '{"headMap":{"sysDictId":"记录id","dictCode":"字典码","description":"字典描述","createdBy":"创建人","createdDate":"创建时间","updatedBy":"更新人","updatedDate":"更新时间"}}'),
(2025063000006302, 'DataDict', 'dictEnumPage', 'dictEnumTable', 'tableHead', '', '{"headMap":{"sysDictId":"记录id","dictCode":"字典码","enumCode":"枚举码","enumValue":"枚举值","description":"描述","createdBy":"创建人","createdDate":"创建时间","updatedBy":"更新人","updatedDate":"更新时间"}}');

DELETE FROM web_call_after WHERE menu = 'DataDict';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2025070100006301, 'DataDict', 'add_dict_sbw', 'addDictButton', 'false', 'showMessage', '', '', '{"msg":"新增字典定义失败!"}'),
(2025070100006302, 'DataDict', 'add_dict_sbw', 'addDictButton', 'success', 'showMessage', '', '', '{"msg":"新增字典定义成功!"}'),
(2025070100006303, 'DataDict', 'add_dict_sbw', 'addDictButton', 'success', 'closeSw', '', '', '{"subWindowId":"add_dict_sub_window_subWindowBackGround"}'),
(2025070100006304, 'DataDict', 'add_dict_sbw', 'addDictButton', 'success', 'request', 'buttonReq', 'pageDictDef', '{"nextPage":"DataDict#out_page#outAreaDiv1","host":"lcs","dataToEle":"dictTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025070100006305, 'DataDict', 'edt_dict_sbw', 'edtDictButton', 'false', 'showMessage', NULL, NULL, '{"msg":"更新字典定义失败"}'),
(2025070100006306, 'DataDict', 'edt_dict_sbw', 'edtDictButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新字典定义"}'),
(2025070100006307, 'DataDict', 'edt_dict_sbw', 'edtDictButton', 'success', 'closeSw', '', '', '{"subWindowId":"edt_dict_sub_window_subWindowBackGround"}'),
(2025070100006308, 'DataDict', 'edt_dict_sbw', 'edtDictButton', 'success', 'request', 'buttonReq', 'pageDictDef', '{"nextPage":"DataDict#out_page#outAreaDiv1","host":"lcs","dataToEle":"dictTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025070100006309, 'DataDict', 'out_page', 'del_dict_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除字典定义失败"}'),
(2025070100006310, 'DataDict', 'out_page', 'del_dict_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除字典定义"}'),
(2025070100006311, 'DataDict', 'out_page', 'del_dict_button', 'success', 'request', 'buttonReq', 'pageDictDef', '{"nextPage":"DataDict#out_page#outAreaDiv1","host":"lcs","dataToEle":"dictTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025070100006312, 'DataDict', 'out_page', 'dsp_enum_button', 'success', 'request', 'buttonReq', 'pageDictEnum', '{"nextPage":"DataDict#dictEnumPage#dictEnumDiv","host":"lcs","dataToEle":"dictEnumTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025070100006313, 'DataDict', 'add_enum_sbw', 'addEnumButton', 'false', 'showMessage', NULL, NULL, '{"msg":"新增枚举失败"}'),
(2025070100006314, 'DataDict', 'add_enum_sbw', 'addEnumButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功新增枚举"}'),
(2025070100006315, 'DataDict', 'add_enum_sbw', 'addEnumButton', 'success', 'closeSw', '', '', '{"subWindowId":"add_enum_sub_window_subWindowBackGround"}'),
(2025070100006316, 'DataDict', 'add_enum_sbw', 'addEnumButton', 'success', 'request', 'buttonReq', 'pageDictEnum', '{"nextPage":"DataDict#dictEnumPage#dictEnumDiv","host":"lcs","dataToEle":"dictEnumTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025070100006317, 'DataDict', 'edt_enum_sbw', 'edtEnumButton', 'false', 'showMessage', NULL, NULL, '{"msg":"更新枚举失败"}'),
(2025070100006318, 'DataDict', 'edt_enum_sbw', 'edtEnumButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功更新枚举"}'),
(2025070100006319, 'DataDict', 'edt_enum_sbw', 'edtEnumButton', 'success', 'closeSw', '', '', '{"subWindowId":"edt_enum_sub_window_subWindowBackGround"}'),
(2025070100006320, 'DataDict', 'edt_enum_sbw', 'edtEnumButton', 'success', 'request', 'buttonReq', 'pageDictEnum', '{"nextPage":"DataDict#dictEnumPage#dictEnumDiv","host":"lcs","dataToEle":"dictEnumTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025070100006321, 'DataDict', 'dictEnumPage', 'del_enum_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除枚举失败"}'),
(2025070100006322, 'DataDict', 'dictEnumPage', 'del_enum_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除枚举"}'),
(2025070100006323, 'DataDict', 'dictEnumPage', 'del_enum_button', 'success', 'request', 'buttonReq', 'pageDictEnum', '{"nextPage":"DataDict#dictEnumPage#dictEnumDiv","host":"lcs","dataToEle":"dictEnumTable","dataToElePos":"data.records","dataToEleType":"list"}');

DELETE FROM service_interface WHERE host_name='lcs' and interface_name in ('pageDictDef','addDictDef','updDictDef','delDictDef','getDictDef', 'pageDictEnum','addDictEnum','updDictEnum','delDictEnum');
INSERT INTO service_interface(host_name, interface_name, interface_path, request_method, interface_desc, charset)VALUES
('lcs', 'pageDictDef', '/pageDictDef', 'GET', '获取字典定义-分页', 'UTF-8'),
('lcs', 'addDictDef', '/addDictDef', 'GET', '新增字典定义', 'UTF-8'),
('lcs', 'updDictDef', '/updDictDef', 'GET', '更新字典定义', 'UTF-8'),
('lcs', 'delDictDef', '/delDictDef', 'GET', '删除字典', 'UTF-8'),
('lcs', 'getDictDef', '/getDictDef', 'GET', '获取字典定义-单个', 'UTF-8'),
('lcs', 'pageDictEnum', '/pageDictEnum', 'GET', '获取字典枚举-分页', 'UTF-8'),
('lcs', 'addDictEnum', '/addDictEnum', 'GET', '新增字典枚举', 'UTF-8'),
('lcs', 'updDictEnum', '/updDictEnum', 'GET', '更新字典枚举', 'UTF-8'),
('lcs', 'delDictEnum', '/delDictEnum', 'GET', '删除字典枚举', 'UTF-8');

DELETE FROM service_interface_data WHERE interface_name in ('pageDictDef','addDictDef','updDictDef','delDictDef','getDictDef', 'pageDictEnum','addDictEnum','updDictEnum','delDictEnum');
INSERT INTO service_interface_data(interface_name, data_type, field_seq, field_name, field_source, field_type, field_desc)VALUES
('pageDictDef', 'req', 1, 'dictCode', 'req.webValueDto.webInputValueMap.queryConditionDictCode.value', 'String', '字典码值'),
('addDictDef', 'req', 1, 'dictCode', 'req.webValueDto.webInputValueMap.dictCode.value', 'String', '字典码值'),
('addDictDef', 'req', 2, 'dictDesc', 'req.webValueDto.webInputValueMap.description.value', 'String', '字典描述'),
('updDictDef', 'req', 1, 'dictCode', 'req.webValueDto.webInputValueMap.dictCode.value', 'String', '字典码值'),
('updDictDef', 'req', 2, 'dictDesc', 'req.webValueDto.webInputValueMap.description.value', 'String', '字典描述'),
('delDictDef', 'req', 1, 'dictCode', 'req.eventInfo.paramMap.dictCode', 'String', '字典码值'),
('getDictDef', 'req', 1, 'dictCode', 'req.eventInfo.paramMap.dictCode', 'String', '字典码值'),
('pageDictEnum', 'req', 1, 'dictCode', 'req.curValueJson.ld_dictCode==null?req.eventInfo.paramMap.dictCode:req.curValueJson.ld_dictCode', 'String', '字典码值'),
('addDictEnum', 'req', 1, 'dictCode', 'inputCurValue.dictCode', 'String', '字典码值'),
('addDictEnum', 'req', 2, 'enumCode', 'inputCurValue.enumCode', 'String', '枚举码'),
('addDictEnum', 'req', 3, 'enumValue', 'inputCurValue.enumValue', 'String', '枚举值'),
('addDictEnum', 'req', 4, 'description', 'inputCurValue.description', 'String', '描述'),
('updDictEnum', 'req', 1, 'sysDictId', 'inputCurValue.sysDictId', 'String', '字典记录id'),
('updDictEnum', 'req', 2, 'enumCode', 'inputCurValue.enumCode', 'String', '枚举码'),
('updDictEnum', 'req', 3, 'enumValue', 'inputCurValue.enumValue', 'String', '枚举值'),
('updDictEnum', 'req', 4, 'description', 'inputCurValue.description', 'String', '描述'),
('delDictEnum', 'req', 1, 'dictCode', 'req.eventInfo.paramMap.dictCode', 'String', '字典码值'),
('delDictEnum', 'req', 2, 'enumCode', 'req.eventInfo.paramMap.enumCode', 'String', '枚举码');

