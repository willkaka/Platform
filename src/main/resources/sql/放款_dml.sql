
DELETE FROM web_menu WHERE menu = 'putoutLoan';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0011', 11, 'putoutLoan', 'lcs', '放款');

DELETE FROM web_element WHERE menu = 'putoutLoan';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2025070400000001, 'EM000001', 001, 'putoutLoan', 'start_page', 'contentArea', 'inputArea', 'div', '输入区域', 'class="inputArea showWithRowDivFlex"', NULL),
(2025070400000002, 'EM000002', 002, 'putoutLoan', 'start_page', 'inputArea', 'transInfoDiv', 'div', '交易信息div', 'class="inputArea background_color_1"', NULL),
(2025070400000003, 'EM000003', 003, 'putoutLoan', 'start_page', 'transInfoDiv', 'transInfoDivTitle', 'divTitle', '交易信息', 'class="content_title"', NULL),
(2025070400000004, 'EM000004', 004, 'putoutLoan', 'start_page', 'transInfoDiv', 'transSeq', 'input', '交易流水号', 'out="Y"', NULL),
(2025070400000005, 'EM000005', 005, 'putoutLoan', 'start_page', 'transInfoDiv', 'transDate', 'input', '交易日期', 'out="Y"#type="date"#value="${curDate}"', ''),
(2025070400000006, 'EM000006', 006, 'putoutLoan', 'start_page', 'transInfoDiv', 'transChannel', 'input', '交易渠道', 'out="Y"', NULL),
(2025070400000007, 'EM000007', 007, 'putoutLoan', 'start_page', 'transInfoDiv', 'businessSource', 'input', '业务来源', 'out="Y"', NULL),
(2025070400000008, 'EM000008', 008, 'putoutLoan', 'start_page', 'transInfoDiv', 'businessChannel', 'input', '业务渠道', 'out="Y"', NULL),
(2025070400000009, 'EM000009', 009, 'putoutLoan', 'start_page', 'inputArea', 'customerInfoDiv', 'div', '客户信息div', 'class="inputArea background_color_1"', NULL),
(2025070400000010, 'EM000010', 010, 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerInfoDivTitle', 'divTitle', '客户信息', 'class="content_title"', NULL),
(2025070400000011, 'EM000011', 011, 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerName', 'input', '客户姓名', 'out="Y"', NULL),
(2025070400000012, 'EM000012', 012, 'putoutLoan', 'start_page', 'customerInfoDiv', 'certNo', 'input', '身份证号', 'out="Y"', NULL),
(2025070400000013, 'EM000013', 013, 'putoutLoan', 'start_page', 'customerInfoDiv', 'customerPhone', 'input', '客户手机号', 'out="Y"', NULL),
(2025070400000014, 'EM000014', 014, 'putoutLoan', 'start_page', 'inputArea', 'produceInfoDiv', 'div', '产品信息输入区域', 'class="inputArea background_color_1"', NULL),
(2025070400000015, 'EM000015', 015, 'putoutLoan', 'start_page', 'produceInfoDiv', 'produceInfoDivTitle', 'divTitle', '产品信息', 'class="content_title"', NULL),
(2025070400000016, 'EM000016', 016, 'putoutLoan', 'start_page', 'produceInfoDiv', 'lineId', 'selectOption', '资金方ID', 'out="Y"', NULL),
(2025070400000017, 'EM000017', 017, 'putoutLoan', 'start_page', 'produceInfoDiv', 'productId', 'selectOption', '产品ID', 'out="Y"', NULL),
(2025070400000018, 'EM000018', 018, 'putoutLoan', 'start_page', 'produceInfoDiv', 'productType', 'input', '产品类型', 'out="Y"', NULL),
(2025070400000019, 'EM000019', 019, 'putoutLoan', 'start_page', 'produceInfoDiv', 'collaborate', 'selectOption', '合作模式', 'out="Y"', NULL),
(2025070400000020, 'EM000020', 020, 'putoutLoan', 'start_page', 'inputArea', 'loanInfoDiv', 'div', '贷款信息div', 'class="inputArea background_color_1"', NULL),
(2025070400000021, 'EM000021', 021, 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanInfoDivTitle', 'divTitle', '贷款信息', 'class="content_title"', NULL),
(2025070400000022, 'EM000022', 022, 'putoutLoan', 'start_page', 'loanInfoDiv', 'applyNo', 'input', '申请编号', 'out="Y"', NULL),
(2025070400000023, 'EM000023', 023, 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanNo', 'input', '出账编号', 'out="Y"', NULL),
(2025070400000024, 'EM000024', 024, 'putoutLoan', 'start_page', 'loanInfoDiv', 'loanTerm', 'input', '贷款期次', 'out="Y"', NULL),
(2025070400000025, 'EM000025', 025, 'putoutLoan', 'start_page', 'loanInfoDiv', 'businessSum', 'input', '放款金额', 'out="Y"', NULL),
(2025070400000026, 'EM000026', 026, 'putoutLoan', 'start_page', 'loanInfoDiv', 'putoutDate', 'input', '放款日期', 'out="Y"#type="date"#value="${curDate}"', ''),
(2025070400000027, 'EM000027', 027, 'putoutLoan', 'start_page', 'loanInfoDiv', 'maturityDate', 'input', '到期日期', 'out="Y"#type="date"#value="${curDate}"', ''),
(2025070400000028, 'EM000028', 028, 'putoutLoan', 'start_page', 'loanInfoDiv', 'beginDate', 'input', '首次还款日', 'out="Y"#type="date"#value="${curDate}"', ''),
(2025070400000029, 'EM000029', 029, 'putoutLoan', 'start_page', 'loanInfoDiv', 'returnMethod', 'selectOption', '还款方式', 'out="Y"', NULL),
(2025070400000030, 'EM000030', 030, 'putoutLoan', 'start_page', 'inputArea', 'rateInfoDiv', 'div', '利率信息div', 'class="inputArea background_color_1"', NULL),
(2025070400000031, 'EM000031', 031, 'putoutLoan', 'start_page', 'rateInfoDiv', 'rateInfoDivTitle', 'divTitle', '利率信息', 'class="content_title"', NULL),
(2025070400000032, 'EM000032', 032, 'putoutLoan', 'start_page', 'rateInfoDiv', 'executeRate', 'input', '执行利率', 'out="Y"', NULL),
(2025070400000033, 'EM000033', 033, 'putoutLoan', 'start_page', 'rateInfoDiv', 'baseDays', 'input', '利率基准天数', 'out="Y"', NULL),
(2025070400000034, 'EM000034', 034, 'putoutLoan', 'start_page', 'rateInfoDiv', 'graceTerm', 'input', '宽限期', 'out="Y"', NULL),
(2025070400000035, 'EM000035', 035, 'putoutLoan', 'start_page', 'rateInfoDiv', 'fineRate', 'input', '罚息利率', 'out="Y"', NULL),
(2025070400000036, 'EM000036', 036, 'putoutLoan', 'start_page', 'rateInfoDiv', 'compoundRate', 'input', '复利利率', 'out="Y"', NULL),
(2025070400000037, 'EM000037', 037, 'putoutLoan', 'start_page', 'inputArea', 'ownerInfoDiv', 'div', '归属信息div', 'class="inputArea background_color_1"', NULL),
(2025070400000038, 'EM000038', 038, 'putoutLoan', 'start_page', 'ownerInfoDiv', 'ownerInfoDivTitle', 'divTitle', '归属信息', 'out="Y"#class="content_title"', NULL),
(2025070400000039, 'EM000039', 039, 'putoutLoan', 'start_page', 'ownerInfoDiv', 'operateOrgId', 'selectOption', '入账机构', 'out="Y"', NULL),
(2025070400000040, 'EM000040', 040, 'putoutLoan', 'start_page', 'ownerInfoDiv', 'accountOwnerCd', 'selectOption', '账务归属', 'out="Y"', NULL),
(2025070400000041, 'EM000041', 041, 'putoutLoan', 'start_page', 'inputArea', 'feeInfoDiv', 'div', '费用信息div', 'out="Y"#class="inputArea background_color_1"', NULL),
(2025070400000042, 'EM000042', 042, 'putoutLoan', 'start_page', 'feeInfoDiv', 'feeInfoDivTitle', 'divTitle', '费用信息', 'class="content_title"', NULL),
(2025070400000043, 'EM000043', 043, 'putoutLoan', 'start_page', 'feeInfoDiv', 'feeInfoDetailDiv_list_1', 'div', '费用明细信息div', 'out="Y"#list="feeInfoList"', NULL),
(2025070400000044, 'EM000044', 044, 'putoutLoan', 'start_page', 'feeInfoDetailDiv_list_1', 'feeType', 'selectOption', '费用名称', 'out="Y"', NULL),
(2025070400000045, 'EM000045', 045, 'putoutLoan', 'start_page', 'feeInfoDetailDiv_list_1', 'feeOwner', 'selectOption', '费用收取方', 'out="Y"', NULL),
(2025070400000046, 'EM000046', 046, 'putoutLoan', 'start_page', 'feeInfoDetailDiv_list_1', 'feeCalcMethod', 'selectOption', '费用计算方式', 'out="Y"', NULL),
(2025070400000047, 'EM000047', 047, 'putoutLoan', 'start_page', 'feeInfoDetailDiv_list_1', 'feeRepayMethod', 'selectOption', '费用还款方式', 'out="Y"', NULL),
(2025070400000048, 'EM000048', 048, 'putoutLoan', 'start_page', 'feeInfoDetailDiv_list_1', 'feeRate', 'input', '费率', 'out="Y"#style="width:100px"', NULL),
(2025070400000049, 'EM000049', 049, 'putoutLoan', 'start_page', 'feeInfoDetailDiv_list_1', 'addNewFeeLineBut', 'button', '+', 'defType="addRecordLineButton"#class="inputArea_sub_button"', NULL),
(2025070400000050, 'EM000050', 050, 'putoutLoan', 'start_page', 'inputArea', 'cardInfoDiv', 'div', '绑卡信息div', 'out="Y"#class="inputArea background_color_1"', NULL),
(2025070400000051, 'EM000051', 051, 'putoutLoan', 'start_page', 'cardInfoDiv', 'cardInfoDivTitle', 'divTitle', '绑卡信息', 'class="content_title"', NULL),
(2025070400000052, 'EM000052', 052, 'putoutLoan', 'start_page', 'cardInfoDiv', 'cardInfoDetailDiv_list_1', 'div', '绑卡明细信息div', 'out="Y"#list="cardInfoList"', NULL),
(2025070400000053, 'EM000053', 053, 'putoutLoan', 'start_page', 'cardInfoDetailDiv_list_1', 'bankCardType', 'selectOption', '银行卡类型', 'out="Y"#style="width:100px"', NULL),
(2025070400000054, 'EM000054', 054, 'putoutLoan', 'start_page', 'cardInfoDetailDiv_list_1', 'bankCode', 'selectOption', '银行', 'out="Y"#style="width:250px"', NULL),
(2025070400000055, 'EM000055', 055, 'putoutLoan', 'start_page', 'cardInfoDetailDiv_list_1', 'bankCardNo', 'input', '卡号', 'out="Y"', NULL),
(2025070400000056, 'EM000056', 056, 'putoutLoan', 'start_page', 'cardInfoDetailDiv_list_1', 'bankCardName', 'input', '卡用户名', 'out="Y"#style="width:100px"', NULL),
(2025070400000057, 'EM000057', 057, 'putoutLoan', 'start_page', 'cardInfoDetailDiv_list_1', 'bankCardPhone', 'input', '卡留银行手机号', 'out="Y"#style="width:130px"', NULL),
(2025070400000058, 'EM000058', 058, 'putoutLoan', 'start_page', 'cardInfoDetailDiv_list_1', 'bankCardCertNo', 'input', '卡留银行身份证', 'out="Y"#style="width:190px"', NULL),
(2025070400000059, 'EM000059', 059, 'putoutLoan', 'start_page', 'cardInfoDetailDiv_list_1', 'addNewCardLineBut', 'button', '+', 'defType="addRecordLineButton"#class="inputArea_sub_button"', NULL),
(2025070400000060, 'EM000060', 060, 'putoutLoan', 'start_page', 'inputArea', 'buttonDiv', 'div', '按钮div', 'class="inputArea background_color_1 center"', NULL),
(2025070400000061, 'EM000061', 061, 'putoutLoan', 'start_page', 'buttonDiv', 'submitButton', 'button', '提交', 'class="inputArea_sub_button"', NULL);

DELETE FROM web_event WHERE menu = 'putoutLoan';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2025070400000001, 'putoutLoan', 'menuEvent', '1', 'click', 'menuReq', 'putoutLoan', '', NULL),
(2025070400000002, 'putoutLoan', 'start_page', 'addNewCardLineBut', 'click', 'addNewRecord', 'xxxx', '', '{"buttonType":"addNewRecordLine","targetDiv":"cardInfoDetailDiv_list_"}'),
(2025070400000003, 'putoutLoan', 'start_page', 'addNewFeeLineBut', 'click', 'addNewRecord', 'xxxx', '', '{"buttonType":"addNewRecordLine","targetDiv":"feeInfoDetailDiv_list_"}'),
(2025070400000004, 'putoutLoan', 'start_page', 'submitButton', 'click', 'buttonReq', 'loanPutout', '', '{"host":"lcs"}');

DELETE FROM web_data WHERE menu = 'putoutLoan';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2025070400000001, 'putoutLoan', 'start_page', 'accountOwnerCd', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"AccountOwner"}}'),
(2025070400000002, 'putoutLoan', 'start_page', 'bankCardType', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"DeductAccountCd"}}'),
(2025070400000003, 'putoutLoan', 'start_page', 'bankCode', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"BankCode"}}'),
(2025070400000004, 'putoutLoan', 'start_page', 'collaborate', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"CollaborateMode"}}'),
(2025070400000005, 'putoutLoan', 'start_page', 'feeCalcMethod', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"FeeMethod"}}'),
(2025070400000006, 'putoutLoan', 'start_page', 'feeOwner', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"LoanFeeType"}}'),
(2025070400000007, 'putoutLoan', 'start_page', 'feeRepayMethod', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"LoanFeeType"}}'),
(2025070400000008, 'putoutLoan', 'start_page', 'feeType', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"LoanFeeType"}}'),
(2025070400000009, 'putoutLoan', 'start_page', 'lineId', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"LineNo"}}'),
(2025070400000010, 'putoutLoan', 'start_page', 'operateOrgId', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"BranchName"}}'),
(2025070400000011, 'putoutLoan', 'start_page', 'productId', 'callInterface', 'pfcs#getProductList', '{"dataPosition":"data.list","dataType":"list","keyField":"productId","valueField":"dto.partnerName+\"_\"+dto.firstProduct","inputParam":{}}'),
(2025070400000012, 'putoutLoan', 'start_page', 'productType', 'callInterface', 'caes#getCodeList', '{"dataPosition":"data","dataType":"list","keyField":"itemNo","valueField":"dto.itemName","inputParam":{"codeNo":"ProductType"}}'),
(2025070400000013, 'putoutLoan', 'start_page', 'returnMethod', 'callInterface', 'lcs#getDictMap', '{"dataPosition":"data","dataType":"map","inputParam":{"dictCode":"paymentMethod"}}');

DELETE FROM web_call_after WHERE menu = 'putoutLoan';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2025070400000001, 'putoutLoan', 'start_page', 'submitButton', 'success', 'showMessage', NULL, NULL, '{"msg":"新增贷款已录入成功！"}');

DELETE FROM service_interface WHERE interface_name = 'loanPutout';
INSERT INTO service_interface(service_interface_id, host_name, interface_name, interface_path, request_method, interface_desc, charset)VALUES
(2, 'lcs', 'loanPutout', '/putout', 'POST', '放款', 'UTF-8');

DELETE FROM service_interface_data WHERE interface_name = 'loanPutout';
INSERT INTO service_interface_data(service_interface_data_id, interface_name, data_type, field_seq, field_name, field_source, field_type, field_desc)VALUES
(201, 'loanPutout', 'req', 1, 'transSeq', 'inputCurValue.transSeq', 'String', '交易流水号'),
(202, 'loanPutout', 'req', 2, 'transDate', 'inputCurValue.transDate', 'String', '交易日期'),
(203, 'loanPutout', 'req', 3, 'transChannel', 'inputCurValue.transChannel', 'String', '交易渠道'),
(204, 'loanPutout', 'req', 4, 'loanSource', 'inputCurValue.businessSource', 'String', '业务来源'),
(205, 'loanPutout', 'req', 5, 'businessChannel', 'inputCurValue.businessChannel', 'String', '业务渠道'),
(206, 'loanPutout', 'req', 6, 'customerName', 'inputCurValue.customerName', 'String', '客户姓名'),
(207, 'loanPutout', 'req', 7, 'cerNum', 'inputCurValue.certNo', 'String', '身份证号'),
(208, 'loanPutout', 'req', 8, 'customerPhone', 'inputCurValue.customerPhone', 'String', '客户手机号'),
(209, 'loanPutout', 'req', 9, 'partnerId', 'inputCurValue.lineId', 'String', '资金方ID'),
(210, 'loanPutout', 'req', 10, 'productNo', 'inputCurValue.productId', 'String', '产品ID'),
(211, 'loanPutout', 'req', 11, 'productType', 'inputCurValue.productType', 'String', '产品类型'),
(212, 'loanPutout', 'req', 12, 'collaborate', 'inputCurValue.collaborate', 'String', '合作模式'),
(213, 'loanPutout', 'req', 13, 'applyNo', 'inputCurValue.applyNo', 'String', '申请编号'),
(214, 'loanPutout', 'req', 14, 'loanNo', 'inputCurValue.loanNo', 'String', '贷款编号'),
(215, 'loanPutout', 'req', 15, 'loanTerm', 'inputCurValue.loanTerm', 'String', '贷款期次'),
(216, 'loanPutout', 'req', 16, 'loanAmount', 'inputCurValue.businessSum', 'String', '放款金额'),
(217, 'loanPutout', 'req', 17, 'lendingDate', 'inputCurValue.putoutDate', 'String', '放款日期'),
(218, 'loanPutout', 'req', 18, 'maturityDate', 'inputCurValue.maturityDate', 'String', '到期日期'),
(219, 'loanPutout', 'req', 19, 'firstPayDate', 'inputCurValue.beginDate', 'String', '首次还款日'),
(220, 'loanPutout', 'req', 20, 'paymentMethod', 'inputCurValue.returnMethod', 'String', '还款方式'),
(221, 'loanPutout', 'req', 21, 'loanRate', 'inputCurValue.executeRate', 'String', '执行利率'),
(222, 'loanPutout', 'req', 22, 'baseDays', 'inputCurValue.baseDays', 'String', '利率基准天数'),
(223, 'loanPutout', 'req', 23, 'graceTerm', 'inputCurValue.graceTerm', 'String', '宽限期'),
(224, 'loanPutout', 'req', 24, 'fineRate', 'inputCurValue.fineRate', 'String', '罚息利率'),
(225, 'loanPutout', 'req', 25, 'compoundRate', 'inputCurValue.compoundRate', 'String', '复利利率'),
(226, 'loanPutout', 'req', 26, 'branchNo', 'inputCurValue.operateOrgId', 'String', '入账机构'),
(227, 'loanPutout', 'req', 27, 'accountOwnerCd', 'inputCurValue.accountOwnerCd', 'String', '账务归属'),
(228, 'loanPutout', 'req', 28, 'feeInfoList', 'inputCurValue.feeInfoList', 'List', '费用信息list'),
(229, 'loanPutout', 'req', 29, 'cardInfoList', 'inputCurValue.cardInfoList', 'List', '绑卡信息list');