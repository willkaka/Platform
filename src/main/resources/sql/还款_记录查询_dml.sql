
DELETE FROM web_menu WHERE menu ='repay03';
INSERT INTO web_menu(web_menu_id, menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
(10016, 'MN0016', 16, 'repay03', 'lcs', '还款-记录查询');

DELETE FROM web_element WHERE menu ='repay03';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2025071700006801, 'EM006801', 01, 'repay03', 'start_page', 'contentArea', 'inputArea', 'div', 'div', '', ''),
(2025071700006802, 'EM006802', 02, 'repay03', 'start_page', 'inputArea', 'queryArea', 'div', 'div', '', ''),
(2025071700006803, 'EM006803', 03, 'repay03', 'start_page', 'queryArea', 'queryConditionArea', 'div', 'div', '', ''),
(2025071700006804, 'EM006804', 04, 'repay03', 'start_page', 'queryConditionArea', 'queryConditionAreaTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2025071700006805, 'EM006805', 05, 'repay03', 'start_page', 'queryConditionArea', 'queryLoanNo', 'input', '出账编号', '', ''),
(2025071700006806, 'EM006806', 06, 'repay03', 'start_page', 'queryConditionArea', 'queryCustomerName', 'input', '客户姓名', '', ''),
(2025071700006807, 'EM006807', 07, 'repay03', 'start_page', 'queryConditionArea', 'queryCertNo', 'input', '身份证号', '', ''),
(2025071700006808, 'EM006808', 08, 'repay03', 'start_page', 'queryConditionArea', 'queryFlowButton', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2025071700006809, 'EM006809', 09, 'repay03', 'start_page', 'inputArea', 'queryOutTableDiv', 'div', '', '', ''),
(2025071700006810, 'EM006810', 10, 'repay03', 'start_page', 'queryOutTableDiv', 'queryOutTableDivTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2025071700006811, 'EM006811', 11, 'repay03', 'start_page', 'queryOutTableDiv', 'repayFlowListTable', 'table', '', 'class="output_table"', '{"hideFields":["phaseNo"]}');


DELETE FROM web_event WHERE menu ='repay03';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2025071700006801, 'repay03', 'menuEvent', '1', 'click', 'menuReq', 'repay03', '', ''),
(2025071700006802, 'repay03', 'start_page', 'queryFlowButton', 'click', 'buttonReq', 'queryTransFlow03', 'repay03#start_page#queryOutTableDiv', '{"host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}');


DELETE FROM web_data WHERE menu = 'repay03';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2025071700006801, 'repay03', 'start_page', 'repayFlowListTable', 'tableHead', '', '{"headMap":{"trxSeq":"流水号","phaseNo":"流程节点","phaseName":"流程阶段","customerName":"客户姓名","certNo":"身份证号","loanNo":"出账编号","paymentNo":"期次","accDate":"记账日期","transChannel":"交易渠道","actPrin":"还本金","actIntr":"还利息","fee004ActAmount":"还担保费金额","totalActAmt":"总还款金额"}}');


DELETE FROM service_interface WHERE host_name='lcs' and interface_name in ('queryTransFlow03');
INSERT INTO service_interface(host_name, interface_name, interface_path, request_method, interface_desc, charset)VALUES
('lcs', 'queryTransFlow03', '/queryTransFlow', 'POST', '查询流程数据', 'UTF-8');

DELETE FROM service_interface_data WHERE interface_name in ('queryTransFlow03');
INSERT INTO service_interface_data(interface_name, data_type, field_seq, field_name, field_source, field_type, field_desc)VALUES
('queryTransFlow03', 'req', 1, 'loanNo', 'inputCurValue.queryLoanNo', 'String', '出账编号'),
('queryTransFlow03', 'req', 2, 'customerName', 'inputCurValue.queryCustomerName', 'String', '客户姓名'),
('queryTransFlow03', 'req', 3, 'certNo', 'inputCurValue.queryCertNo', 'String', '身份证号'),
('queryTransFlow03', 'req', 4, 'flowNo', '"RepayTrans"', 'String', '流程名称'),
('queryTransFlow03', 'req', 5, 'pageNo', '1', 'Integer', '页码'),
('queryTransFlow03', 'req', 6, 'pageSize', '10', 'Integer', '每页记录数');