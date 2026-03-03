-- ==================== 菜单配置 ====================
DELETE FROM web_menu WHERE menu = 'CommonQuery';
INSERT INTO web_menu(menu_no, sort_no, menu, menu_parent, menu_desc)VALUES
('MN0022', 22, 'CommonQuery', 'SystemMaintain', '通用查询');

-- ==================== 页面元素配置 ====================
DELETE FROM web_element WHERE menu = 'CommonQuery';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
-- 主页面元素
(2026022800001001, 'EM002001', 1001, 'CommonQuery', 'start_page', 'contentArea', 'queryArea', 'div', '查询区域', 'class="showWithRowDivGrid"', NULL),
(2026022800001002, 'EM002002', 1002, 'CommonQuery', 'start_page', 'queryArea', 'queryAreaDivTitle', 'divTitle', '查询类型选择', 'class="content_title"', ''),
(2026022800001003, 'EM002003', 1003, 'CommonQuery', 'start_page', 'queryArea', 'queryArea2', 'div', '查询类型输入区域', 'class="inputArea"', NULL),
(2026022800001004, 'EM002004', 1004, 'CommonQuery', 'start_page', 'queryArea2', 'queryType', 'selectOption', '查询类型', 'out="Y"', NULL),
(2026022800001005, 'EM002005', 1005, 'CommonQuery', 'start_page', 'queryArea2', 'dbName', 'selectOption', '数据库名称', 'out="Y"', NULL),
(2026022800001006, 'EM002006', 1006, 'CommonQuery', 'start_page', 'queryArea', 'personCondArea', 'div', '自定义查询条件输入区域', 'class="inputArea"', NULL),
(2026022800001007, 'EM002007', 1007, 'CommonQuery', 'start_page', 'personCondArea', 'personCondAreaDivTitle', 'divTitle', '自定义查询条件', 'class="content_title"', ''),
(2026022800001008, 'EM002008', 1008, 'CommonQuery', 'start_page', 'personCondArea', 'personCondDiv', 'div', '自定义查询条件输入区域', 'class="inputArea"', NULL),
(2026022800001009, 'EM002009', 1009, 'CommonQuery', 'start_page', 'queryArea', 'queryButton', 'button', '查询', 'class="inputArea_sub_button"#style="width:100px;"', NULL),
(2026022800001010, 'EM002010', 1010, 'CommonQuery', 'start_page', 'contentArea', 'resultDivTitle', 'divTitle', '查询结果', 'class="content_title"', ''),
(2026022800001011, 'EM002011', 1011, 'CommonQuery', 'start_page', 'contentArea', 'resultTable', 'table', '查询结果', 'class="output_table"', '{"hideFields":["query_config_id"]}');

-- ==================== 事件配置 ====================
DELETE FROM web_event WHERE menu = 'CommonQuery';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2026022800001001, 'CommonQuery', 'menuEvent', '1', 'click', 'menuReq', 'CommonQuery', NULL, NULL),
(2026022800001002, 'CommonQuery', 'start_page', 'queryType', 'change', 'buttonReq', 'commonQueryCond', 'CommonQuery#start_page#personCondDiv', NULL),
(2026022800001003, 'CommonQuery', 'start_page', 'queryButton', 'click', 'buttonReq', 'commonQuery', 'CommonQuery#start_page#resultTable', '{"dataToEle":"resultTable","dataToElePos":"data","dataToEleType":"list"}');

delete from web_data where menu = 'CommonQuery';
INSERT INTO web_data
(web_data_id, menu, page, "element", data_type, data_attr, express)VALUES
(2026022800001001, 'CommonQuery', 'start_page', 'queryType', '', 'sql', 'select query_type ''key'',query_name value from common_query_config order by common_query_config_id'),
(2026022800001002, 'CommonQuery', 'start_page', 'dbName', '', 'sql', 'select database_name ''key'',database_name value from config_database_info order by database_name');

-- ==================== 事后处理配置 ====================
DELETE FROM web_call_after WHERE menu = 'CommonQuery';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2026022800001001, 'CommonQuery', 'start_page', 'queryButton', 'false', 'showMessage', NULL, NULL, '{"msg":"查询失败"}');


DELETE FROM common_query_config WHERE 1=1;
INSERT INTO common_query_config(common_query_config_id,query_type,query_name,query_desc,query_cond,query_sql)VALUES
(1, 'transEntryQry', '分录配置查询', '分录配置查询', '[{"field":"modelId","fieldName":"核算办法","type":"selectOption","dataSql":"select model_id ''key'',name value from caes.acct_model_organization where record_ind=''A''"},{"field":"transId","fieldName":"交易类型","type":"selectOption","dataSql":"select item_no ''key'',item_name value from caes.code_library where code_no=''TransId'' and record_ind=''A''"}]',
'select model_id 核算办法,trans_id 交易类型,group_cd 分组, sort_id 序号,direction 方向,subject_no 科目,expression 计算表达式,digest 科目名称 from caes.trans_entry WHERE record_ind=''A'' and model_id=${modelId} and trans_id=${transId}'),

(2, 'dictQry', '字典查询', '字典查询', '[{"field":"codeNo","fieldName":"字典码","type":"selectOption","dataSql":"select code_no ''key'',code_no value from caes.code_library where record_ind=''A'' and code_no<>''CODENO'' group by code_no"}]',
'select code_no 字典码,sort_no,item_no,item_name,item_description,item_script from caes.code_library WHERE record_ind=''A'' and code_no=${codeNo}'),

(3, 'curDateBatchExeLogQry', '当日批次执行记录查询', '当日批次执行记录查询', '[{"field":"targetName","fieldName":"批次名称","type":"selectOption","dataSql":"select target_name ''key'',target_describe value from batchtask_status bs where record_ind =''A'' and input_date =(select business_date from business_date_config) group by target_name ,target_describe"}]',
'select target_name,input_date,task_name,task_describe,begin_time,end_time,status,date_flag from caes.batchtask_status bs where target_name =${targetName} and input_date=(select business_date from business_date_config)');

