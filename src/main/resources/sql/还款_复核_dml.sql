
DELETE FROM web_menu WHERE menu ='repay02';
INSERT INTO web_menu(web_menu_id, menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
(10015, 'MN0015', 15, 'repay02', 'lcs', '还款-复核');

DELETE FROM web_element WHERE menu ='repay02';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2025071700006701, 'EM006701', 01, 'repay02', 'start_page', 'contentArea', 'inputArea', 'div', 'div', '', ''),
(2025071700006702, 'EM006702', 02, 'repay02', 'start_page', 'inputArea', 'queryArea', 'div', 'div', '', ''),
(2025071700006703, 'EM006703', 03, 'repay02', 'start_page', 'queryArea', 'queryConditionArea', 'div', 'div', '', ''),
(2025071700006704, 'EM006704', 04, 'repay02', 'start_page', 'queryConditionArea', 'queryConditionAreaTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2025071700006705, 'EM006705', 05, 'repay02', 'start_page', 'queryConditionArea', 'queryLoanNo', 'input', '出账编号', '', ''),
(2025071700006706, 'EM006706', 06, 'repay02', 'start_page', 'queryConditionArea', 'queryCustomerName', 'input', '客户姓名', '', ''),
(2025071700006707, 'EM006707', 07, 'repay02', 'start_page', 'queryConditionArea', 'queryCertNo', 'input', '身份证号', '', ''),
(2025071700006708, 'EM006708', 08, 'repay02', 'start_page', 'queryConditionArea', 'queryFlowButton', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2025071700006709, 'EM006709', 09, 'repay02', 'start_page', 'inputArea', 'queryOutTableDiv', 'div', '', '', ''),
(2025071700006710, 'EM006710', 10, 'repay02', 'start_page', 'queryOutTableDiv', 'queryOutTableDivTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2025071700006711, 'EM006711', 11, 'repay02', 'start_page', 'queryOutTableDiv', 'repayFlowListTable', 'table', '', 'class="output_table"', '{"hideFields":["phaseNo"]}'),
(2025071700006712, 'EM006712', 12, 'repay02', 'start_page', 'repayFlowListTable', 'submit_acc_button', 'table_record_button', '记账', 'class="label_button"', NULL),
(2025071700006713, 'EM006713', 13, 'repay02', 'start_page', 'repayFlowListTable', 'back_rcd_button', 'table_record_button', '退回', 'class="label_button"', NULL);


DELETE FROM web_event WHERE menu ='repay02';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2025071700006701, 'repay02', 'menuEvent', '1', 'click', 'menuReq', 'repay02', '', ''),
(2025071700006702, 'repay02', 'start_page', 'queryFlowButton', 'click', 'buttonReq', 'queryTransFlow02', 'repay02#start_page#queryOutTableDiv', '{"host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025071700006703, 'repay02', 'start_page', 'submit_acc_button', 'click', 'buttonReq', 'repayTrxAcc', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认记账？","host":"lcs"}'),
(2025071700006704, 'repay02', 'start_page', 'back_rcd_button', 'click', 'buttonReq', 'flowBack', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认退回？","host":"lcs"}'),
(2025071700006705, 'repay02', 'start_page', 'repayFlowListTable', 'click', 'buttonReq', 'queryTransFlow02', 'repay02#start_page#queryOutTableDiv', '{"host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}');


DELETE FROM web_data WHERE menu = 'repay02';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2025071700006701, 'repay02', 'start_page', 'repayFlowListTable', 'tableHead', '', '{"headMap":{"trxSeq":"流水号","phaseNo":"流程节点","phaseName":"流程阶段","customerName":"客户姓名","certNo":"身份证号","loanNo":"出账编号","paymentNo":"期次","accDate":"记账日期","transChannel":"交易渠道","actPrin":"还本金","actIntr":"还利息","fee004ActAmount":"还担保费金额","totalActAmt":"总还款金额"}}');

DELETE FROM web_call_after WHERE menu = 'repay02';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2025071700006701, 'repay02', 'start_page', 'submit_acc_button', 'false', 'showMessage', NULL, NULL, '{"msg":"记账失败"}'),
(2025071700006702, 'repay02', 'start_page', 'submit_acc_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功记账，可到完成页面查看明细"}'),
(2025071700006703, 'repay02', 'start_page', 'submit_acc_button', 'success', 'request', 'buttonReq', 'queryTransFlow02', '{"nextPage":"repay02#start_page#queryOutTableDiv","host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025071700006704, 'repay02', 'start_page', 'back_rcd_button', 'false', 'showMessage', NULL, NULL, '{"msg":"退回失败"}'),
(2025071700006705, 'repay02', 'start_page', 'back_rcd_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已退回"}'),
(2025071700006706, 'repay02', 'start_page', 'back_rcd_button', 'success', 'request', 'buttonReq', 'queryTransFlow02', '{"nextPage":"repay02#start_page#queryOutTableDiv","host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}');


DELETE FROM service_interface WHERE host_name='lcs' and interface_name in ('queryTransFlow02','transAcc','repayTrxAcc','flowBack');
INSERT INTO service_interface(host_name, interface_name, interface_path, request_method, interface_desc, charset)VALUES
('lcs', 'queryTransFlow02', '/queryTransFlow', 'POST', '查询流程数据', 'UTF-8'),
('lcs', 'repayTrxAcc', '/repayTrxAcc', 'POST', '按期还款交易记账', 'UTF-8'),
('lcs', 'flowBack', '/flow/back', 'GET', '退回流程', 'UTF-8');



DELETE FROM service_interface_data WHERE interface_name in ('queryTransFlow02','transAcc','repayTrxAcc','flowBack');
INSERT INTO service_interface_data(interface_name, data_type, field_seq, field_name, field_source, field_type, field_desc)VALUES
('queryTransFlow02', 'req', 1, 'loanNo', 'inputCurValue.queryLoanNo', 'String', '出账编号'),
('queryTransFlow02', 'req', 2, 'customerName', 'inputCurValue.queryCustomerName', 'String', '客户姓名'),
('queryTransFlow02', 'req', 3, 'certNo', 'inputCurValue.queryCertNo', 'String', '身份证号'),
('queryTransFlow02', 'req', 4, 'flowNo', '"RepayTrans"', 'String', '流程名称'),
('queryTransFlow02', 'req', 5, 'phaseNoList', '["0020"]', 'String', '流程节点'),
('queryTransFlow02', 'req', 6, 'pageNo', '1', 'Integer', '页码'),
('queryTransFlow02', 'req', 7, 'pageSize', '10', 'Integer', '每页记录数'),
('repayTrxAcc', 'req', 1, 'transSeq', 'req.eventInfo.paramMap.trxSeq', 'String', '流程流水号'),
('repayTrxAcc', 'req', 2, 'phaseNo', 'req.eventInfo.paramMap.phaseNo', 'String', '当前节点编号'),
('flowBack', 'req', 1, 'flowSeq', 'req.eventInfo.paramMap.trxSeq', 'String', '流程流水号'),
('flowBack', 'req', 2, 'phaseNo', 'req.eventInfo.paramMap.phaseNo', 'String', '当前节点编号');