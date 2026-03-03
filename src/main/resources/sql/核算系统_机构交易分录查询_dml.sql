

DELETE FROM web_menu WHERE menu = 'TransEntryQry';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0017', 17, 'TransEntryQry', 'lcs', '交易分录查询');

DELETE FROM web_element WHERE menu = 'TransEntryQry';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2026010000001001, 'EM261001', 1001, 'TransEntryQry', 'start_page', 'contentArea', 'inputArea', 'div', 'div', '', ''),
(2026010000001002, 'EM261002', 1002, 'TransEntryQry', 'start_page', 'inputArea', 'queryArea', 'div', 'div', 'class="area_div"', ''),
(2026010000001003, 'EM261003', 1003, 'TransEntryQry', 'start_page', 'queryArea', 'queryAreaDivTitle', 'divTitle', '查询条件', 'class="content_title"', ''),
(2026010000001004, 'EM261004', 1004, 'TransEntryQry', 'start_page', 'queryArea', 'dbName', 'selectOption', '环境', 'out="Y"', ''),
(2026010000001005, 'EM261005', 1005, 'TransEntryQry', 'start_page', 'queryArea', 'queryInputModelId', 'selectOption', '核算办法', 'out="Y"', ''),
(2026010000001006, 'EM261006', 1006, 'TransEntryQry', 'start_page', 'queryArea', 'queryInputBranchNo', 'selectOption', '分公司', 'out="Y"', ''),
(2026010000001007, 'EM261007', 1007, 'TransEntryQry', 'start_page', 'queryArea', 'queryInputAccountOwner', 'selectOption', '归属', 'out="Y"', ''),
(2026010000001008, 'EM261008', 1008, 'TransEntryQry', 'start_page', 'queryArea', 'queryInputTransChannel', 'selectOption', '交易渠道', 'out="Y"', ''),
(2026010000001009, 'EM261009', 1009, 'TransEntryQry', 'start_page', 'queryArea', 'queryInputTaxFree', 'selectOption', '免税', 'out="Y"', ''),
(2026010000001010, 'EM261010', 1010, 'TransEntryQry', 'start_page', 'queryArea', 'queryInputTransType', 'selectOption', '交易类型', 'out="Y"', ''),
(2026010000001011, 'EM261011', 1011, 'TransEntryQry', 'start_page', 'queryArea', 'queryTransEntryBut', 'button', '查询', 'class="inputArea_sub_button"', ''),
(2026010000001012, 'EM261012', 1012, 'TransEntryQry', 'start_page', 'inputArea', 'teOutArea', 'div', '', '', ''),
(2026010000001013, 'EM261013', 1013, 'TransEntryQry', 'start_page', 'teOutArea', 'teOutAreaTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2026010000001014, 'EM261014', 1014, 'TransEntryQry', 'start_page', 'teOutArea', 'transEntryTable', 'table', '分录数据表', 'class="output_table"', '{"hideFields":["serviceInterfaceId"]}');

DELETE FROM web_event WHERE menu = 'TransEntryQry';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2026010000001001, 'TransEntryQry', 'menuEvent', '1', 'click', 'menuReq', 'TransEntryQry', '', ''),
(2026010000001002, 'TransEntryQry', 'start_page', 'dbName', 'change', 'webDataReq', 'xxx', 'add_dict_sbw', '{"refreshFlag":"Y","refreshPage":"TransEntryQry#start_page#queryInputModelId","onlyUpd":"Y"}'),
(2026010000001003, 'TransEntryQry', 'start_page', 'dbName', 'change', 'webDataReq', 'xxx', 'add_dict_sbw', '{"refreshFlag":"Y","refreshPage":"TransEntryQry#start_page#queryInputBranchNo","onlyUpd":"Y"}'),
(2026010000001004, 'TransEntryQry', 'start_page', 'dbName', 'change', 'webDataReq', 'xxx', 'add_dict_sbw', '{"refreshFlag":"Y","refreshPage":"TransEntryQry#start_page#queryInputAccountOwner","onlyUpd":"Y"}'),
(2026010000001005, 'TransEntryQry', 'start_page', 'dbName', 'change', 'webDataReq', 'xxx', 'add_dict_sbw', '{"refreshFlag":"Y","refreshPage":"TransEntryQry#start_page#queryInputTransChannel","onlyUpd":"Y"}'),
(2026010000001006, 'TransEntryQry', 'start_page', 'queryInputModelId', 'change', 'webDataReq', 'xxx', 'add_dict_sbw', '{"refreshFlag":"Y","refreshPage":"TransEntryQry#start_page#queryInputTransType","onlyUpd":"Y"}'),
(2026010000001007, 'TransEntryQry', 'start_page', 'queryTransEntryBut', 'click', 'buttonReq', 'querySubject', 'TransEntryQry#start_page#transEntryTable', '{"dataToEle":"transEntryTable","dataToElePos":"data","dataToEleType":"list"}');

DELETE FROM web_data WHERE menu = 'TransEntryQry';
INSERT INTO web_data(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2026010000001001, 'TransEntryQry', 'start_page', 'dbName', '', 'sql', 'select database_name ''key'',database_name value from config_database_info order by database_name'),
(2026010000001002, 'TransEntryQry', 'start_page', 'queryInputModelId', '', 'remoteSql', '{"sql":"select model_id as ''key'' ,name as value from caes.acct_model_organization amo where record_ind=''A'' order by created_date desc"}'),
(2026010000001003, 'TransEntryQry', 'start_page', 'queryInputBranchNo', '', 'remoteSql', '{"sql":"select item_no as ''key'',item_name as value from caes.code_library cl where code_no=''BranchName'' and record_ind=''A'' order by item_no"}'),
(2026010000001004, 'TransEntryQry', 'start_page', 'queryInputAccountOwner', '', 'remoteSql', '{"sql":"select item_no as ''key'',item_name as value from caes.code_library cl where code_no=''AccountOwner'' and record_ind=''A'' order by item_no"}'),
(2026010000001005, 'TransEntryQry', 'start_page', 'queryInputTransChannel', '', 'remoteSql', '{"sql":"select item_no as ''key'',item_name as value from caes.code_library cl where code_no=''TransChannel'' and record_ind=''A'' order by item_no"}'),
(2026010000001006, 'TransEntryQry', 'start_page', 'queryInputTaxFree', '', 'optionList', '[{"value":"Y","text":"是"},{"value":"N","text":"否"}]'),
(2026010000001007, 'TransEntryQry', 'start_page', 'queryInputTransType', '', 'remoteSql', '{"sql":"select te.trans_id as ''key'',(select cl.item_name from caes.code_library cl where cl.code_no=''TransId'' and cl.item_no=te.trans_id and cl.record_ind=''A'') as value from caes.trans_entry te where model_id=#queryInputModelId# and record_ind=''A'' group by trans_id order by trans_id"}'),
(2026010000001008, 'TransEntryQry', 'start_page', 'transEntryTable', 'tableHead', '','{"headMap":{"transId":"交易ID","sortId":"排序号","direction":"发生方向","subjectNo":"科目","accountNum":"EAS科目编号","asstactFirstNum":"核算项目编码1","groupCd":"分组","digest":"摘要","expression":"表达式"}}');

