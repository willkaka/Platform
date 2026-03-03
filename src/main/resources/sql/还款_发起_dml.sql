
DELETE FROM web_menu WHERE menu ='repay01';
INSERT INTO web_menu(web_menu_id, menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
(10014, 'MN0014', 14, 'repay01', 'lcs', '还款-发起');

DELETE FROM web_element WHERE menu ='repay01';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2025071700006601, 'EM006601', 01, 'repay01', 'start_page', 'contentArea', 'inputArea', 'div', 'div', '', ''),
(2025071700006602, 'EM006602', 02, 'repay01', 'start_page', 'inputArea', 'buttonArea', 'div', 'div', '', ''),
(2025071700006603, 'EM006603', 03, 'repay01', 'start_page', 'buttonArea', 'addNewTrxSbwBut', 'button', '新增按期还款', 'class="inputArea_sub_button"', ''),
(2025071700006604, 'EM006604', 04, 'repay01', 'start_page', 'buttonArea', 'addNewTrxAmtSbwBut', 'button', '新增按金额还款', 'class="inputArea_sub_button"', ''),
(2025071700006605, 'EM006605', 05, 'repay01', 'start_page', 'inputArea', 'queryArea', 'div', 'div', '', ''),
(2025071700006606, 'EM006606', 06, 'repay01', 'start_page', 'queryArea', 'queryConditionArea', 'div', 'div', '', ''),
(2025071700006607, 'EM006607', 07, 'repay01', 'start_page', 'queryConditionArea', 'queryConditionAreaTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2025071700006608, 'EM006608', 08, 'repay01', 'start_page', 'queryConditionArea', 'queryLoanNo', 'input', '出账编号', '', ''),
(2025071700006609, 'EM006609', 09, 'repay01', 'start_page', 'queryConditionArea', 'queryCustomerName', 'input', '客户姓名', '', ''),
(2025071700006610, 'EM006610', 10, 'repay01', 'start_page', 'queryConditionArea', 'queryCertNo', 'input', '身份证号', '', ''),
(2025071700006611, 'EM006611', 11, 'repay01', 'start_page', 'queryConditionArea', 'queryFlowButton', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2025071700006612, 'EM006612', 12, 'repay01', 'start_page', 'inputArea', 'queryOutTableDiv', 'div', '', '', ''),
(2025071700006613, 'EM006613', 13, 'repay01', 'start_page', 'queryOutTableDiv', 'queryOutTableDivTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2025071700006614, 'EM006614', 14, 'repay01', 'start_page', 'queryOutTableDiv', 'repayFlowListTable', 'table', '', 'class="output_table"', '{"hideFields":["phaseNo"]}'),
(2025071700006615, 'EM006615', 15, 'repay01', 'start_page', 'repayFlowListTable', 'submit_next_button', 'table_record_button', '提交', 'class="label_button"', NULL),
(2025071700006616, 'EM006616', 16, 'repay01', 'start_page', 'repayFlowListTable', 'delete_rcd_button', 'table_record_button', '删除', 'class="label_button"', NULL),
(2025071700006617, 'EM006617', 17, 'repay01', 'queryLoan_sbw', 'body', 'queryLoan_sub_window', 'subWindow', '查询贷款欠还期次', '', NULL),
(2025071700006618, 'EM006618', 18, 'repay01', 'queryLoan_sbw', 'queryLoan_sub_window', 'sbwInputArea', 'div', '', '', ''),
(2025071700006619, 'EM006619', 19, 'repay01', 'queryLoan_sbw', 'sbwInputArea', 'queryDiv', 'div', '', '', ''),
(2025071700006620, 'EM006620', 20, 'repay01', 'queryLoan_sbw', 'queryDiv', 'queryLoanNo', 'input', '出账编号', '', ''),
(2025071700006621, 'EM006621', 21, 'repay01', 'queryLoan_sbw', 'queryDiv', 'queryLoanOweTermButton', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2025071700006622, 'EM006622', 22, 'repay01', 'queryLoan_sbw', 'sbwInputArea', 'outAreaDiv', 'div', '', '', ''),
(2025071700006623, 'EM006623', 23, 'repay01', 'queryLoan_sbw', 'outAreaDiv', 'outTableDiv', 'div', '', '', ''),
(2025071700006624, 'EM006624', 24, 'repay01', 'queryLoan_sbw', 'outTableDiv', 'outTableDivTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2025071700006625, 'EM006625', 25, 'repay01', 'queryLoan_sbw', 'outTableDiv', 'loanOwnPlanTable', 'table', '', 'class="output_table"', ''),
(2025071700006626, 'EM006626', 26, 'repay01', 'queryLoan_sbw', 'loanOwnPlanTable', 'repay_sbw_button', 'table_record_button', '还款', 'class="label_button"', NULL),
(2025071700006627, 'EM006627', 27, 'repay01', 'repay_sbw', 'body', 'repay_sub_window', 'subWindow', '还款-按期还款', '', NULL),
(2025071700006628, 'EM006628', 28, 'repay01', 'repay_sbw', 'repay_sub_window', 'repay_sbw_div', 'div', '', 'class="showWithRowDivGrid"', NULL),
(2025071700006629, 'EM006629', 29, 'repay01', 'repay_sbw', 'repay_sbw_div', 'infoDiv', 'div', '', '', NULL),
(2025071700006630, 'EM006630', 30, 'repay01', 'repay_sbw', 'infoDiv', 'infoDivTitle', 'divTitle', '期次信息', 'class="content_title"', ''),
(2025071700006631, 'EM006631', 31, 'repay01', 'repay_sbw', 'infoDiv', 'loanNo', 'input', '出账编号', 'out="Y"', NULL),
(2025071700006632, 'EM006632', 32, 'repay01', 'repay_sbw', 'infoDiv', 'paymentNo', 'input', '期次', 'out="Y"', NULL),
(2025071700006633, 'EM006633', 33, 'repay01', 'repay_sbw', 'infoDiv', 'paymentDate', 'input', '应还日期', '', NULL),
(2025071700006634, 'EM006634', 34, 'repay01', 'repay_sbw', 'repay_sbw_div', 'repay_input_div', 'div', '', '', NULL),
(2025071700006635, 'EM006635', 35, 'repay01', 'repay_sbw', 'repay_input_div', 'inputInfoDivTitle', 'divTitle', '请输入还款信息', 'class="content_title"', ''),
(2025071700006636, 'EM006636', 36, 'repay01', 'repay_sbw', 'repay_input_div', 'accDate', 'input', '入账日期', 'out="Y"#type="date"#value="${curDate}"', ''),
(2025071700006637, 'EM006637', 37, 'repay01', 'repay_sbw', 'repay_input_div', 'transChannel', 'selectOption', '还款渠道', 'out="Y"', NULL),
(2025071700006638, 'EM006638', 38, 'repay01', 'repay_sbw', 'repay_input_div', 'repay_input_amt_div', 'div', '', '', NULL),
(2025071700006639, 'EM006639', 39, 'repay01', 'repay_sbw', 'repay_input_amt_div', 'repayInputAmtDivTitle', 'divTitle', '请输入金额信息', 'class="content_title"', ''),
(2025071700006640, 'EM006640', 40, 'repay01', 'repay_sbw', 'repay_input_amt_div', 'repay_input_amt_owe_div', 'div', '', 'style="display:inline-grid"', NULL),
(2025071700006641, 'EM006641', 41, 'repay01', 'repay_sbw', 'repay_input_amt_owe_div', 'owePrin', 'input', '欠还本金', '', NULL),
(2025071700006642, 'EM006642', 42, 'repay01', 'repay_sbw', 'repay_input_amt_owe_div', 'oweIntr', 'input', '欠还利息', '', NULL),
(2025071700006643, 'EM006643', 43, 'repay01', 'repay_sbw', 'repay_input_amt_owe_div', 'fee004OweAmount', 'input', '欠还担保费', '', NULL),
(2025071700006644, 'EM006644', 44, 'repay01', 'repay_sbw', 'repay_input_amt_div', 'repay_input_amt_act_div', 'div', '', 'style="display:inline-grid"', NULL),
(2025071700006645, 'EM006645', 45, 'repay01', 'repay_sbw', 'repay_input_amt_act_div', 'curActPrin', 'input', '还本金', 'out="Y"#value="0.00"', ''),
(2025071700006646, 'EM006646', 46, 'repay01', 'repay_sbw', 'repay_input_amt_act_div', 'curActIntr', 'input', '还利息', 'out="Y"#value="0.00"', ''),
(2025071700006647, 'EM006647', 47, 'repay01', 'repay_sbw', 'repay_input_amt_act_div', 'curFee004ActAmount', 'input', '还担保费', 'out="Y"#value="0.00"', ''),
(2025071700006648, 'EM006648', 48, 'repay01', 'repay_sbw', 'repay_sub_window', 'repay_sbw_footer_div', 'div', '', 'class="subWidowFooter"', NULL),
(2025071700006649, 'EM006649', 49, 'repay01', 'repay_sbw', 'repay_sbw_footer_div', 'repayTrxSubmitButton', 'button', '提交', 'class="inputArea_sub_button"', NULL),

-- queryLoan_total_sbw
(2025071700006650, 'EM006650', 50, 'repay01', 'queryLoan_total_page', 'body', 'queryLoan_total_sbw', 'subWindow', '查询贷款欠还金额', '', NULL),
(2025071700006651, 'EM006651', 51, 'repay01', 'queryLoan_total_page', 'queryLoan_total_sbw', 'qryAmtSbwInputArea', 'div', '', '', ''),
(2025071700006652, 'EM006652', 52, 'repay01', 'queryLoan_total_page', 'qryAmtSbwInputArea', 'queryDiv', 'div', '', '', ''),
(2025071700006653, 'EM006653', 53, 'repay01', 'queryLoan_total_page', 'queryDiv', 'queryDivTitle', 'divTitle', '请输入查询条件', 'class="content_title"', ''),
(2025071700006654, 'EM006654', 54, 'repay01', 'queryLoan_total_page', 'queryDiv', 'queryLoanNo', 'input', '出账编号', '', ''),
(2025071700006655, 'EM006655', 55, 'repay01', 'queryLoan_total_page', 'queryDiv', 'queryLoanOweAmtButton', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2025071700006656, 'EM006656', 56, 'repay01', 'queryLoan_total_page', 'qryAmtSbwInputArea', 'queryRtnInfoDiv', 'div', '', '', NULL),
(2025071700006657, 'EM006657', 57, 'repay01', 'queryLoan_total_page', 'queryRtnInfoDiv', 'queryRtnInfoDivTitle', 'divTitle', '欠还金额查询结果', 'class="content_title"', ''),
(2025071700006658, 'EM006658', 58, 'repay01', 'queryLoan_total_page', 'queryRtnInfoDiv', 'queryRtnInfoSubDiv', 'div', '', '', NULL),
(2025071700006659, 'EM006659', 59, 'repay01', 'queryLoan_total_page', 'queryRtnInfoSubDiv', 'loanNo', 'input', '出账编号', 'out="Y"', NULL),
(2025071700006660, 'EM006660', 60, 'repay01', 'queryLoan_total_page', 'queryRtnInfoSubDiv', 'curBusDate', 'input', '截止日期', '', NULL),
(2025071700006661, 'EM006661', 61, 'repay01', 'queryLoan_total_page', 'queryRtnInfoSubDiv', 'oweTotal', 'input', '欠还总金额', 'out="Y"', NULL),
(2025071700006662, 'EM006662', 62, 'repay01', 'queryLoan_total_page', 'qryAmtSbwInputArea', 'inputActAmtDiv', 'div', '', '', NULL),
(2025071700006663, 'EM006663', 63, 'repay01', 'queryLoan_total_page', 'inputActAmtDiv', 'inputActAmtDivTitle', 'divTitle', '输入还款信息', 'class="content_title"', ''),
(2025071700006664, 'EM006664', 64, 'repay01', 'queryLoan_total_page', 'inputActAmtDiv', 'accDate', 'input', '入账日期', 'out="Y"#type="date"#value="${curDate}"', ''),
(2025071700006665, 'EM006665', 65, 'repay01', 'queryLoan_total_page', 'inputActAmtDiv', 'transChannel', 'selectOption', '还款渠道', 'out="Y"', NULL),
(2025071700006666, 'EM006666', 66, 'repay01', 'queryLoan_total_page', 'inputActAmtDiv', 'actTotalAmt', 'input', '客户还款总金额', 'out="Y"', NULL),
(2025071700006667, 'EM006667', 67, 'repay01', 'queryLoan_total_page', 'queryLoan_total_sbw', 'total_sbw_footer_div', 'div', '', 'class="subWidowFooter"', NULL),
(2025071700006668, 'EM006668', 68, 'repay01', 'queryLoan_total_page', 'total_sbw_footer_div', 'repayTrxTotalAmtSubmitButton', 'button', '提交', 'class="inputArea_sub_button"', NULL);



DELETE FROM web_event WHERE menu ='repay01';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2025071700006601, 'repay01', 'menuEvent', '1', 'click', 'menuReq', 'repay01', '', ''),
(2025071700006602, 'repay01', 'start_page', 'addNewTrxSbwBut', 'click', 'swDataReq', 'repay01', 'queryLoan_sbw', ''),
(2025071700006603, 'repay01', 'start_page', 'addNewTrxAmtSbwBut', 'click', 'swDataReq', 'repay01', 'queryLoan_total_page', ''),
(2025071700006604, 'repay01', 'start_page', 'queryFlowButton', 'click', 'buttonReq', 'queryTransFlow', 'repay01#start_page#queryOutTableDiv', '{"host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}'),
(2025071700006605, 'repay01', 'start_page', 'submit_next_button', 'click', 'buttonReq', 'flowNext', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认提交？","host":"lcs"}'),
(2025071700006606, 'repay01', 'start_page', 'delete_rcd_button', 'click', 'buttonReq', 'flowRemove', NULL, '{"showConfirmSW":true,"confirmCnt":"是否确认删除？","host":"lcs"}'),
(2025071700006607, 'repay01', 'queryLoan_sbw', 'queryLoanOweTermButton', 'click', 'buttonReq', 'queryLoanPlanOwe', 'repay01#queryLoan_sbw#outTableDiv', '{"host":"lcs","dataToEle":"loanOwnPlanTable","dataToElePos":"data","dataToEleType":"list"}'),
(2025071700006608, 'repay01', 'queryLoan_total_page', 'queryLoanOweAmtButton', 'click', 'buttonReq', 'queryLoanOweTotal', 'repay01#queryLoan_total_page#queryRtnInfoSubDiv', '{"host":"lcs"}'),
(2025071700006609, 'repay01', 'queryLoan_sbw', 'repay_sbw_button', 'click', 'swDataReq', 'repay01', 'repay_sbw', '{"valueFromSelectedRecord":true}'),
(2025071700006610, 'repay01', 'repay_sbw', 'repayTrxSubmitButton', 'click', 'buttonReq', 'repayTrxDetailAmtSave', NULL, '{"host":"lcs"}'),
(2025071700006611, 'repay01', 'queryLoan_total_page', 'repayTrxTotalAmtSubmitButton', 'click', 'buttonReq', 'repayTrxTotalAmtSave', NULL, '{"host":"lcs"}');

DELETE FROM web_data WHERE menu = 'repay01';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2025071700006601, 'repay01', 'start_page', 'repayFlowListTable', 'tableHead', '', '{"headMap":{"trxSeq":"流水号","phaseNo":"流程节点","phaseName":"流程阶段","customerName":"客户姓名","certNo":"身份证号","loanNo":"出账编号","paymentNo":"期次","accDate":"记账日期","transChannel":"交易渠道","actPrin":"还本金","actIntr":"还利息","fee004ActAmount":"还担保费金额","totalActAmt":"总还款金额"}}'),
(2025071700006602, 'repay01', 'queryLoan_sbw', 'loanOwnPlanTable', 'tableHead', '', '{"headMap":{"loanNo":"出账编号","paymentNo":"期次","paymentDate":"应还日期","payPrin":"应还本金","actPrin":"已还本金","owePrin":"欠还本金","payIntr":"应还利息","actIntr":"已还利息","oweIntr":"欠还利息","fee004PayAmount":"担保费应还金额","fee004ActAmount":"担保费已还金额","fee004OweAmount":"担保费欠还金额","totalOweAmt":"总欠还金额"}}'),
(2025071700006603, 'repay01', 'repay_sbw', 'transChannel', 'callInterface', 'lcs#getDictMap', '{"dataPosition":"data","dataType":"map","inputParam":{"dictCode":"transChannel"}}'),
(2025071700006604, 'repay01', 'queryLoan_total_page', 'transChannel', 'callInterface', 'lcs#getDictMap', '{"dataPosition":"data","dataType":"map","inputParam":{"dictCode":"transChannel"}}');


DELETE FROM web_call_after WHERE menu = 'repay01';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2025071700006601, 'repay01', 'repay_sbw', 'repayTrxSubmitButton', 'false', 'showMessage', NULL, NULL, '{"msg":"保存还款数据失败"}'),
(2025071700006602, 'repay01', 'repay_sbw', 'repayTrxSubmitButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功保存还款数据,请查询刷新结果"}'),
(2025071700006603, 'repay01', 'repay_sbw', 'repayTrxSubmitButton', 'success', 'closeSw', '', '', '{"subWindowId":"repay_sub_window"}'),
(2025071700006604, 'repay01', 'repay_sbw', 'repayTrxSubmitButton', 'success', 'closeSw', '', '', '{"subWindowId":"queryLoan_sub_window"}'),
(2025071700006605, 'repay01', 'repay_sbw', 'repayTrxSubmitButton', 'success', 'request', 'buttonReq', 'queryTransFlow', '{"nextPage":"repay01#start_page#queryOutTableDiv","host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}'),

(2025071700006606, 'repay01', 'queryLoan_total_page', 'repayTrxTotalAmtSubmitButton', 'false', 'showMessage', NULL, NULL, '{"msg":"保存还款数据失败"}'),
(2025071700006607, 'repay01', 'queryLoan_total_page', 'repayTrxTotalAmtSubmitButton', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功保存还款数据,请查询刷新结果"}'),
(2025071700006608, 'repay01', 'queryLoan_total_page', 'repayTrxTotalAmtSubmitButton', 'success', 'closeSw', '', '', '{"subWindowId":"queryLoan_total_sbw"}'),
(2025071700006609, 'repay01', 'queryLoan_total_page', 'repayTrxTotalAmtSubmitButton', 'success', 'request', 'buttonReq', 'queryTransFlow', '{"nextPage":"repay01#start_page#queryOutTableDiv","host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}'),

(2025071700006610, 'repay01', 'start_page', 'submit_next_button', 'false', 'showMessage', NULL, NULL, '{"msg":"提交失败"}'),
(2025071700006611, 'repay01', 'start_page', 'submit_next_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功提交到复核流程"}'),
(2025071700006612, 'repay01', 'start_page', 'submit_next_button', 'success', 'request', 'buttonReq', 'queryTransFlow', '{"nextPage":"repay01#start_page#queryOutTableDiv","host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}'),

(2025071700006613, 'repay01', 'start_page', 'delete_rcd_button', 'false', 'showMessage', NULL, NULL, '{"msg":"删除失败"}'),
(2025071700006614, 'repay01', 'start_page', 'delete_rcd_button', 'success', 'showMessage', NULL, NULL, '{"msg":"已成功删除"}'),
(2025071700006615, 'repay01', 'start_page', 'delete_rcd_button', 'success', 'request', 'buttonReq', 'queryTransFlow', '{"nextPage":"repay01#start_page#queryOutTableDiv","host":"lcs","dataToEle":"repayFlowListTable","dataToElePos":"data.records","dataToEleType":"list"}');

DELETE FROM service_interface WHERE host_name='lcs' and interface_name in ('queryLoanPlanOwe','queryLoanOweTotal','repayTrxDetailAmtSave','repayTrxTotalAmtSave','queryTransFlow','flowNext','flowRemove');
INSERT INTO service_interface(host_name, interface_name, interface_path, request_method, interface_desc, charset)VALUES
('lcs', 'queryLoanPlanOwe', '/queryLoanPlanOwe', 'GET', '查询贷款欠还还款计划', 'UTF-8'),
('lcs', 'queryLoanOweTotal', '/queryLoanOweTotal', 'GET', '查询贷款欠还总金额', 'UTF-8'),
('lcs', 'repayTrxDetailAmtSave', '/repayTrxSave', 'POST', '还款交易数据保存', 'UTF-8'),
('lcs', 'repayTrxTotalAmtSave', '/repayTrxSave', 'POST', '还款交易数据保存', 'UTF-8'),
('lcs', 'queryTransFlow', '/queryTransFlow', 'POST', '查询流程数据', 'UTF-8'),
('lcs', 'flowNext', '/flow/next', 'GET', '查询流程数据', 'UTF-8'),
('lcs', 'flowRemove', '/flow/remove', 'GET', '查询流程数据', 'UTF-8');



DELETE FROM service_interface_data WHERE interface_name in ('queryLoanPlanOwe','queryLoanOweTotal','repayTrxDetailAmtSave','repayTrxTotalAmtSave','queryTransFlow','flowNext','flowRemove');
INSERT INTO service_interface_data(interface_name, data_type, field_seq, field_name, field_source, field_type, field_desc)VALUES
('queryLoanPlanOwe', 'req', 1, 'loanNo', 'inputCurValue.queryLoanNo', 'String', '贷款编号'),
('queryLoanOweTotal', 'req', 1, 'loanNo', 'inputCurValue.queryLoanNo', 'String', '贷款编号'),
('repayTrxDetailAmtSave', 'req', 1, 'transType', '"R001"', 'String', '贷款编号'),
('repayTrxDetailAmtSave', 'req', 2, 'transScene', '"WEB_REPAY_BY_PAYMENT_NO"', 'String', '贷款编号'),
('repayTrxDetailAmtSave', 'req', 3, 'loanNo', 'inputCurValue.loanNo', 'String', '贷款编号'),
('repayTrxDetailAmtSave', 'req', 4, 'paymentNo', 'inputCurValue.paymentNo', 'String', '期次'),
('repayTrxDetailAmtSave', 'req', 5, 'accDate', 'inputCurValue.accDate', 'String', '记账日期'),
('repayTrxDetailAmtSave', 'req', 6, 'transChannel', 'inputCurValue.transChannel', 'String', '交易渠道'),
('repayTrxDetailAmtSave', 'req', 7, 'actPrin', 'inputCurValue.curActPrin', 'String', '还本金金额'),
('repayTrxDetailAmtSave', 'req', 8, 'actIntr', 'inputCurValue.curActIntr', 'String', '还利息金额'),
('repayTrxDetailAmtSave', 'req', 9, 'actFee004', 'inputCurValue.curFee004ActAmount', 'String', '还担保费金额'),
('repayTrxTotalAmtSave', 'req', 1, 'transType', '"R001"', 'String', '贷款编号'),
('repayTrxTotalAmtSave', 'req', 2, 'transScene', '"WEB_REPAY_BY_TOTAL_AMT"', 'String', '贷款编号'),
('repayTrxTotalAmtSave', 'req', 3, 'loanNo', 'inputCurValue.loanNo', 'String', '贷款编号'),
('repayTrxTotalAmtSave', 'req', 4, 'accDate', 'inputCurValue.accDate', 'String', '记账日期'),
('repayTrxTotalAmtSave', 'req', 5, 'transChannel', 'inputCurValue.transChannel', 'String', '交易渠道'),
('repayTrxTotalAmtSave', 'req', 6, 'actTotalAmt', 'inputCurValue.actTotalAmt', 'String', '客户还款总金额'),
('queryTransFlow', 'req', 1, 'loanNo', 'inputCurValue.queryLoanNo', 'String', '出账编号'),
('queryTransFlow', 'req', 2, 'customerName', 'inputCurValue.queryCustomerName', 'String', '客户姓名'),
('queryTransFlow', 'req', 3, 'certNo', 'inputCurValue.queryCertNo', 'String', '身份证号'),
('queryTransFlow', 'req', 4, 'flowNo', '"RepayTrans"', 'String', '流程名称'),
('queryTransFlow', 'req', 5, 'phaseNoList', '["0001","0002"]', 'String', '流程节点'),
('queryTransFlow', 'req', 6, 'pageNo', '1', 'Integer', '页码'),
('queryTransFlow', 'req', 7, 'pageSize', '10', 'Integer', '每页记录数'),
('flowNext', 'req', 1, 'flowSeq', 'req.eventInfo.paramMap.trxSeq', 'String', '流程流水号'),
('flowNext', 'req', 2, 'phaseNo', 'req.eventInfo.paramMap.phaseNo', 'String', '当前节点编号'),
('flowRemove', 'req', 1, 'flowSeq', 'req.eventInfo.paramMap.trxSeq', 'String', '流程流水号'),
('flowRemove', 'req', 2, 'phaseNo', 'req.eventInfo.paramMap.phaseNo', 'String', '当前节点编号');