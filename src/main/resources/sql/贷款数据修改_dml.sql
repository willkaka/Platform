
DELETE FROM web_menu WHERE menu = 'LoanDataChg';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0025', 25, 'LoanDataChg', 'SystemMaintain', '贷款数据修改');

DELETE FROM web_element WHERE menu = 'LoanDataChg';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
--(2026030301000001, 'EM000001', 001, 'LoanDataChg', 'start_page', 'contentArea', 'inputArea', 'div', '', '', ''),
(2026030401000001, 'EM000001', 001, 'LoanDataChg', 'start_page', 'contentArea', 'itfQueryArea', 'div', '', 'class="area_div"', ''),
(2026030401000002, 'EM000002', 002, 'LoanDataChg', 'start_page', 'itfQueryArea', 'queryAreaDivTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2026030401000003, 'EM000003', 003, 'LoanDataChg', 'start_page', 'itfQueryArea', 'queryLoanNo', 'input', '出账编号', 'out="Y"', ''),
(2026030401000004, 'EM000004', 004, 'LoanDataChg', 'start_page', 'contentArea', 'accQryButArea', 'div', '查询按钮区域', 'class="showWithRowDivGrid"', NULL),
(2026030401000005, 'EM000005', 005, 'LoanDataChg', 'start_page', 'accQryButArea', 'accQryButAreaTitle', 'divTitle', '账户信息查询', 'class="content_title"', ''),
(2026030401000006, 'EM000006', 006, 'LoanDataChg', 'start_page', 'accQryButArea', 'lbQryBtn', 'button', '余额表', 'class="inputArea_sub_button"#style="width:100px;"', NULL),
(2026030401000007, 'EM000007', 007, 'LoanDataChg', 'start_page', 'accQryButArea', 'lrQryBtn', 'button', '余额附属表', 'class="inputArea_sub_button"#style="width:100px;"', NULL),
(2026030401000008, 'EM000008', 008, 'LoanDataChg', 'start_page', 'accQryButArea', 'dppQryBtn', 'button', '本息计划', 'class="inputArea_sub_button"#style="width:100px;"', NULL),
(2026030401000009, 'EM000009', 009, 'LoanDataChg', 'start_page', 'accQryButArea', 'dfpQryBtn', 'button', '费用计划', 'class="inputArea_sub_button"#style="width:100px;"', NULL),
(2026030401000010, 'EM000010', 010, 'LoanDataChg', 'start_page', 'accQryButArea', 'ofiQryBtn', 'button', '分润计划', 'class="inputArea_sub_button"#style="width:100px;"', NULL),
(2026030401000011, 'EM000011', 011, 'LoanDataChg', 'start_page', 'queryButArea', 'trxQryButAreaTitle', 'divTitle', '交易信息查询', 'class="content_title"', ''),
(2026030401000012, 'EM000012', 012, 'LoanDataChg', 'start_page', 'accQryButArea', 'ltiQryBtn', 'button', '总单', 'class="inputArea_sub_button"#style="width:100px;"', NULL),
(2026030401000013, 'EM000013', 013, 'LoanDataChg', 'start_page', 'accQryButArea', 'bbQryBtn', 'button', '单据', 'class="inputArea_sub_button"#style="width:100px;"', NULL);

DELETE FROM web_event WHERE menu = 'LoanDataChg';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2026030401000001, 'LoanDataChg', 'menuEvent', '1', 'click', 'menuReq', 'LoanDataChg', '', ''),
(2026030401000007, 'LoanDataChg', 'start_page', 'lbQryBtn', 'click', 'swDataReq', 'LoanDataChg', 'lbInfoSbwPage', '{"valueFromSelectedRecord":true}'),
(2026030401000007, 'LoanDataChg', 'start_page', 'lrQryBtn', 'click', 'swDataReq', 'LoanDataChg', 'lrInfoSbwPage', '{"valueFromSelectedRecord":true}'),
(2026030401000007, 'LoanDataChg', 'start_page', 'dppQryBtn', 'click', 'swDataReq', 'LoanDataChg', 'dppInfoSbwPage', '{"valueFromSelectedRecord":true}'),
(2026030401000007, 'LoanDataChg', 'start_page', 'dfpQryBtn', 'click', 'swDataReq', 'LoanDataChg', 'dfpInfoSbwPage', '{"valueFromSelectedRecord":true}'),
(2026030401000007, 'LoanDataChg', 'start_page', 'ofiQryBtn', 'click', 'swDataReq', 'LoanDataChg', 'ofiInfoSbwPage', '{"valueFromSelectedRecord":true}'),
(2026030401000007, 'LoanDataChg', 'start_page', 'ltiQryBtn', 'click', 'swDataReq', 'LoanDataChg', 'ltiInfoSbwPage', '{"valueFromSelectedRecord":true}'),
(2026030401000007, 'LoanDataChg', 'start_page', 'bbQryBtn', 'click', 'swDataReq', 'LoanDataChg', 'bbInfoSbwPage', '{"valueFromSelectedRecord":true}');

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



