
DELETE FROM web_menu WHERE menu = 'loanInfo';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0012', 2, 'loanInfo', 'lcs', '贷款台账');

DELETE FROM web_element WHERE menu = 'loanInfo';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2025063000006201, 'EM006101', 1001, 'loanInfo', 'start_page', 'contentArea', 'inputArea', 'div', 'div', '', ''),
(2025063000006202, 'EM006102', 1002, 'loanInfo', 'start_page', 'inputArea', 'queryArea', 'div', 'div', 'class="area_div"', ''),
(2025063000006203, 'EM006103', 1003, 'loanInfo', 'start_page', 'queryArea', 'queryAreaDivTitle', 'divTitle', '贷款查询条件', 'class="content_title"', ''),
(2025063000006204, 'EM006104', 1004, 'loanInfo', 'start_page', 'queryArea', 'queryInputLoanNo', 'input', '出账编号', 'out="Y"', ''),
(2025063000006205, 'EM006105', 1005, 'loanInfo', 'start_page', 'queryArea', 'queryInputCustomerName', 'input', '客户姓名', 'out="Y"', ''),
(2025063000006206, 'EM006106', 1006, 'loanInfo', 'start_page', 'queryArea', 'queryInputCertNo', 'input', '身份证号', 'out="Y"', ''),
(2025063000006207, 'EM006107', 1007, 'loanInfo', 'start_page', 'queryArea', 'queryLoanInfoBut', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2025063000006208, 'EM006108', 1008, 'loanInfo', 'start_page', 'inputArea', 'loanRcdOutArea', 'div', '', '', ''),
(2025063000006209, 'EM006109', 1009, 'loanInfo', 'start_page', 'loanRcdOutArea', 'loanTableDiv', 'div', 'div', '', ''),
(2025063000006210, 'EM006110', 1010, 'loanInfo', 'loanTablePage', 'loanTableDiv', 'loanTableDiv1', 'div', 'div', '', ''),
(2025063000006211, 'EM006111', 1011, 'loanInfo', 'loanTablePage', 'loanTableDiv1', 'loanTableDiv2', 'div', 'div', 'class="area_div"', ''),
(2025063000006212, 'EM006112', 1012, 'loanInfo', 'loanTablePage', 'loanTableDiv2', 'loanTableDiv2Title', 'divTitle', '贷款查询结果', 'class="content_title"', ''),
(2025063000006213, 'EM006113', 1013, 'loanInfo', 'loanTablePage', 'loanTableDiv2', 'loanTable', 'table', '贷款记录表', 'class="output_table"', ''),
(2025063000006214, 'EM006114', 1014, 'loanInfo', 'loanTablePage', 'loanTableDiv1', 'loanDetailOutArea', 'div', '', '', ''),
(2025063000006215, 'EM006115', 1015, 'loanInfo', 'loanDetailOutPage', 'loanDetailOutArea', 'loanDetailPDiv', 'div', 'div', '', ''),
(2025063000006216, 'EM006116', 1016, 'loanInfo', 'loanDetailOutPage', 'loanDetailPDiv', 'loanDetailDiv', 'div', 'div', 'class="area_div"', ''),
(2025063000006217, 'EM006117', 1017, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'loanDetailDivTitle', 'divTitle', '贷款详情', 'class="content_title"', ''),
(2025063000006218, 'EM006118', 1018, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'ld_loanNo', 'input', '出账编号', '', '{"dataField":"loanNo"}'),
(2025063000006219, 'EM006119', 1019, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'ld_loanStatus', 'input', '贷款状态', '', '{"dataField":"loanStatus"}'),
(2025063000006220, 'EM006120', 1020, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'ld_putoutDate', 'input', '放款日期', '', '{"dataField":"putoutDate"}'),
(2025063000006221, 'EM006121', 1021, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'ld_loanAmount', 'input', '放款金额', '', '{"dataField":"loanAmount"}'),
(2025063000006222, 'EM006122', 1022, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'ld_loanTerm', 'input', '贷款期数', '', '{"dataField":"loanTerm"}'),
(2025063000006223, 'EM006123', 1023, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'ld_firstPayDate', 'input', '首次还款日', '', '{"dataField":"firstPayDate"}'),
(2025063000006224, 'EM006124', 1024, 'loanInfo', 'loanDetailOutPage', 'loanDetailDiv', 'ld_paymentMethodName', 'input', '还款方式', '', '{"dataField":"paymentMethodName"}'),
(2025063000006225, 'EM006125', 1025, 'loanInfo', 'loanDetailOutPage', 'loanDetailPDiv', 'bankPlanPDiv', 'div', '还款计划div', '', ''),
(2025063000006226, 'EM006126', 1026, 'loanInfo', 'bankPlanPage', 'bankPlanPDiv', 'bankPlanDiv', 'div', 'div', 'class="area_div"', ''),
(2025063000006227, 'EM006127', 1027, 'loanInfo', 'bankPlanPage', 'bankPlanDiv', 'bankPlanDivTitle', 'divTitle', '还款计划', 'class="content_title"', ''),
(2025063000006228, 'EM006128', 1028, 'loanInfo', 'bankPlanPage', 'bankPlanDiv', 'bankPlanTable', 'table', '还款计划', 'class="output_table"', '');

DELETE FROM web_event WHERE menu = 'loanInfo';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2025063000006201, 'loanInfo', 'menuEvent', '1', 'click', 'menuReq', 'loanInfo', '', ''),
(2025063000006203, 'loanInfo', 'start_page', 'queryLoanInfoBut', 'click', 'buttonReq', 'queryLoanByCust', 'loanInfo#loanTablePage#loanTableDiv1', '{"host":"lcs","dataToEle":"loanTable","dataToElePos":"data","dataToEleType":"list"}'),
(2025063000006202, 'loanInfo', 'loanTablePage', 'loanTable', 'record_click', 'buttonReq', 'queryLoanInfoDetail', 'loanInfo#loanDetailOutPage#loanDetailPDiv', '{"host":"lcs"}');

DELETE FROM web_data WHERE menu = 'loanInfo';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2025063000006201, 'loanInfo', 'bankPlanPage', 'bankPlanTable', 'tableHead', '', '{"headMap":{"paymentNo":"期次","paymentDate":"应还日期","accDate":"记账日期","payPrin":"应还本金","actPrin":"已还本金","payIntr":"应还利息","actIntr":"已还利息","fee004PayAmount":"应还担保费","fee004ActAmount":"已还担保费","payoff":"结清标志"}}'),
(2025063000006202, 'loanInfo', 'loanTablePage', 'loanTable', 'tableHead', '', '{"headMap":{"customerName":"客户姓名","certNo":"身份证号","loanNo":"出账编号","putoutDate":"放款日期","loanStatus":"贷款状态","loanAmount":"放款金额","paymentMethodName":"还款方式"}}');

DELETE FROM web_call_after WHERE menu = 'loanInfo';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2025063000006201, 'loanInfo', 'loanTablePage', 'loanTable', 'success', 'request', 'buttonReq', 'queryLoanPlan', '{"nextPage":"loanInfo#bankPlanPage#bankPlanDiv","host":"lcs","dataToEle":"bankPlanTable","dataToElePos":"data","dataToEleType":"list"}');

