DELETE FROM web_element;
INSERT INTO web_element (menu, page, element_parent, element_seq, "element", element_type, element_desc, element_attr) VALUES
-- 开始页面-显示菜单内容
-- 新增菜单弹窗
-- 编辑菜单弹窗
-- 展示事件弹窗
-- 新增元素事件弹窗
-- 编辑元素事件弹窗
-- 页面元素展示
-- 新增页面元素弹窗
-- 展示事件弹窗
-- 新增元素事件弹窗
-- 编辑元素事件弹窗
-- 展示元素数据弹窗
-- 新增元素事件弹窗
-- 编辑元素事件弹窗
('MenuMaintain', 'start_page', 'contentArea', 1, 'inputArea', 'div', '输入区域', 'class="inputArea"'),
('MenuMaintain', 'start_page', 'inputArea', 1, 'menuArea', 'div', '菜单信息显示区域', 'class="inputArea_overflow_auto"'),
('MenuMaintain', 'start_page', 'menuArea', 1, 'addMenu', 'button', '新增菜单', 'class="inputArea_sub_button"'),
('MenuMaintain', 'start_page', 'menuArea', 2, 'menuTable', 'table', '菜单列表', 'class="output_table"'),
('MenuMaintain', 'start_page', 'menuTable', 999, 'menu_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 999, 'menu_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'start_page', 'menuTable', 999, 'menu_record_event_show_button', 'table_record_button', '菜单事件', 'class="label_button"'),
('MenuMaintain', 'menu_add_sw_page', 'body', 0, 'menu_add_sub_window', 'subWindow', '新增菜单弹窗', NULL),
('MenuMaintain', 'menu_add_sw_page', 'menu_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_add_sw_page', 'menu_add_sub_window_swFooter', 0, 'confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_edit_sw_page', 'body', 0, 'menu_edit_sub_window', 'subWindow', '编辑菜单', NULL),
('MenuMaintain', 'menu_edit_sw_page', 'menu_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_edit_sw_page', 'menu_edit_sub_window_swFooter', 0, 'edit_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_eventList_sw_page', 'body', 0, 'show_menu_event_sub_window', 'subWindow', '展示菜单事件', NULL),
('MenuMaintain', 'menu_eventList_sw_page', 'show_menu_event_sub_window_swBody', 1, 'addMenuEvent', 'button', '新增菜单事件', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_eventList_sw_page', 'show_menu_event_sub_window_swBody', 2, 'menuEventTable', 'table', '事件清单', 'class="output_table"'),
('MenuMaintain', 'menu_eventList_sw_page', 'menuEventTable', 999, 'event_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'menu_eventList_sw_page', 'menuEventTable', 999, 'event_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'menu_event_add_sw_page', 'body', 0, 'menu_event_add_sub_window', 'subWindow', '新增菜单事件弹窗', ''),
('MenuMaintain', 'menu_event_add_sw_page', 'menu_event_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '新增菜单事件输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_event_add_sw_page', 'menu_event_add_sub_window_swFooter', 0, 'add_menu_event_confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'menu_event_edit_sw_page', 'body', 0, 'menu_event_edit_sub_window', 'subWindow', '编辑菜单事件', NULL),
('MenuMaintain', 'menu_event_edit_sw_page', 'menu_event_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'menu_event_edit_sw_page', 'menu_event_edit_sub_window_swFooter', 0, 'edit_menu_event_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_start_page', 'inputArea', 2, 'ele_show_area', 'div', '页面元素展示区域', 'class="inputArea_overflow_auto"'),
('MenuMaintain', 'ele_start_page', 'ele_show_area', 3, 'addPageElement', 'button', '新增页面元素', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_start_page', 'ele_show_area', 4, 'elementTable', 'table', '页面元素列表', 'class="output_table"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_show_event_button', 'table_record_button', '元素事件', 'class="label_button"'),
('MenuMaintain', 'ele_start_page', 'elementTable', 999, 'ele_show_data_button', 'table_record_button', '元素数据', 'class="label_button"'),
('MenuMaintain', 'ele_add_sw_page', 'body', 0, 'ele_add_sub_window', 'subWindow', '新增页面元素弹窗', NULL),
('MenuMaintain', 'ele_add_sw_page', 'ele_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'ele_add_sw_page', 'ele_add_sub_window_swFooter', 0, 'confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_edit_sw_page', 'body', 0, 'ele_edit_sub_window', 'subWindow', '编辑页面元素', NULL),
('MenuMaintain', 'ele_edit_sw_page', 'ele_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'ele_edit_sw_page', 'ele_edit_sub_window_swFooter', 0, 'edit_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_eventList_sw_page', 'body', 0, 'show_event_sub_window', 'subWindow', '展示元素事件', NULL),
('MenuMaintain', 'ele_eventList_sw_page', 'show_event_sub_window_swBody', 1, 'addElementEvent', 'button', '新增元素事件', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_eventList_sw_page', 'show_event_sub_window_swBody', 2, 'eventTable', 'table', '事件清单', 'class="output_table"'),
('MenuMaintain', 'ele_eventList_sw_page', 'eventTable', 999, 'event_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'ele_eventList_sw_page', 'eventTable', 999, 'event_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'event_add_sw_page', 'body', 0, 'event_add_sub_window', 'subWindow', '新增元素事件弹窗', ''),
('MenuMaintain', 'event_add_sw_page', 'event_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '新增元素事件输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'event_add_sw_page', 'event_add_sub_window_swFooter', 0, 'add_event_confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'event_edit_sw_page', 'body', 0, 'event_edit_sub_window', 'subWindow', '编辑元素事件', NULL),
('MenuMaintain', 'event_edit_sw_page', 'event_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'event_edit_sw_page', 'event_edit_sub_window_swFooter', 0, 'edit_event_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_dataList_sw_page', 'body', 0, 'show_data_sub_window', 'subWindow', '展示元素数据', NULL),
('MenuMaintain', 'ele_dataList_sw_page', 'show_data_sub_window_swBody', 1, 'addElementData', 'button', '新增元素数据', 'class="inputArea_sub_button"'),
('MenuMaintain', 'ele_dataList_sw_page', 'show_data_sub_window_swBody', 2, 'eleDataTable', 'table', '元素数据清单', 'class="output_table"'),
('MenuMaintain', 'ele_dataList_sw_page', 'eleDataTable', 999, 'data_record_edit_button', 'table_record_button', '编辑', 'class="label_button"'),
('MenuMaintain', 'ele_dataList_sw_page', 'eleDataTable', 999, 'data_record_del_button', 'table_record_button', '删除', 'class="label_button"'),
('MenuMaintain', 'data_add_sw_page', 'body', 0, 'data_add_sub_window', 'subWindow', '新增元素数据弹窗', ''),
('MenuMaintain', 'data_add_sw_page', 'data_add_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '新增元素数据输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'data_add_sw_page', 'data_add_sub_window_swFooter', 0, 'add_data_confirm', 'button', '确认', 'class="inputArea_sub_button"'),
('MenuMaintain', 'data_edit_sw_page', 'body', 0, 'data_edit_sub_window', 'subWindow', '编辑元素数据', NULL),
('MenuMaintain', 'data_edit_sw_page', 'data_edit_sub_window_swBody', 0, 'swBodyInputList', 'inputList', '输入框组', 'class="inputArea_div_grp_inline"'),
('MenuMaintain', 'data_edit_sw_page', 'data_edit_sub_window_swFooter', 0, 'edit_data_confirm', 'button', '更新', 'class="inputArea_sub_button"'),
('QueryTableStructure', 'start_page', 'contentArea', 1, 'inputArea', 'div', '输入区域', 'class="inputArea"'),
('QueryTableStructure', 'start_page', 'inputArea', 0, 'dbName', 'selectOption', '数据库', 'style=width:120px'),
('QueryTableStructure', 'start_page', 'inputArea', 1, 'libName', 'selectOption', '库名', 'style=width:200px'),
('QueryTableStructure', 'start_page', 'inputArea', 2, 'tableName', 'selectOption', '表名', ''),
('QueryTableStructure', 'start_page', 'inputArea', 3, 'queryButton', 'button', '查询', 'class="inputArea_sub_button"'),
('QueryTableStructure', 'query_out_page', 'inputArea', 0, 'query_out_page_div', 'div', '输出区域', 'class="inputArea"'),
('QueryTableStructure', 'query_out_page', 'query_out_page_div', 0, 'table_div', 'div', '输出表格div', 'class="inputArea"'),
('QueryTableStructure', 'query_out_page', 'table_div', 0, 'outTable', 'table', 'table', 'class="output_table"');




DELETE FROM web_event;
INSERT INTO web_event (menu, page, "element", event_type, request_type, request_bean, next_page, param) VALUES
-- 菜单维护菜单单击事件
('MenuMaintain', 'menuEvent', '', 'click', 'menuReq', 'MenuMaintain', '', NULL),
-- 新增菜单按钮单击事件
('MenuMaintain', 'start_page', 'addMenu', 'click', 'swDataReq', 'addMenuButton', 'menu_add_sw_page', ''),
-- 菜单记录编辑按钮单击事件
('MenuMaintain', 'start_page', 'menu_record_edit_button', 'click', 'swDataReq', 'editMenuButton', 'menu_edit_sw_page', ''),
-- 菜单记录显示事件按钮单击事件
('MenuMaintain', 'start_page', 'menu_record_event_show_button', 'click', 'swDataReq', 'showMenuEventButton', 'menu_eventList_sw_page', ''),
-- 菜单记录删除按钮单击事件
('MenuMaintain', 'start_page', 'menu_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_menu","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"start_page","refreshEle":"menuTable"}'),
-- 菜单记录记录行单击事件
('MenuMaintain', 'start_page', 'menuTable', 'record_click', 'webDataReq', 'queryMenuEle', 'ele_start_page', '{"parentEle":"contentArea"}'),
-- 菜单新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'menu_add_sw_page', 'confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_menu","refreshPage":"start_page","refreshEle":"menuTable","closeSW":"menu_add_sub_window"}'),
-- 菜单编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'menu_edit_sw_page', 'edit_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_menu","refreshPage":"start_page","refreshEle":"menuTable","closeSW":"menu_edit_sub_window"}'),

-- 菜单事件弹窗新增按钮单击事件
('MenuMaintain', 'menu_eventList_sw_page', 'addMenuEvent', 'click', 'swDataReq', 'menuEventAddButton', 'menu_event_add_sw_page', ''),
-- 菜单事件弹窗记录编辑按钮单击事件
('MenuMaintain', 'menu_eventList_sw_page', 'menu_event_record_edit_button', 'click', 'swDataReq', 'menuEventEditButton', 'menu_event_edit_sw_page', ''),
-- 菜单事件弹窗记录删除按钮单击事件
('MenuMaintain', 'menu_eventList_sw_page', 'menu_event_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_event","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"menu_eventList_sw_page","refreshEle":"menuEventTable"}'),
-- 菜单事件弹窗新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'menu_event_add_sw_page', 'add_menu_event_confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_event","refreshPage":"menu_event_add_sw_page","refreshEle":"menuEventTable","closeSW":"menu_event_add_sub_window"}'),
-- 菜单事件弹窗编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'menu_event_edit_sw_page', 'edit_menu_event_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_event","refreshPage":"menu_event_edit_sw_page","refreshEle":"menuEventTable","closeSW":"menu_event_edit_sub_window"}'),

-- 页面元素新增按钮单击事件
('MenuMaintain', 'ele_start_page', 'addPageElement', 'click', 'swDataReq', 'eleAddButton', 'ele_add_sw_page', ''),
-- 元素记录编辑按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_record_edit_button', 'click', 'swDataReq', 'eleEditButton', 'ele_edit_sw_page', ''),
-- 元素记录删除按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_element","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"ele_start_page","refreshEle":"elementTable"}'),
-- 元素记录事件展示按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_show_event_button', 'click', 'swDataReq', 'eleEventShowButton', 'ele_eventList_sw_page', NULL),
-- 元素记录数据展示按钮单击事件
('MenuMaintain', 'ele_start_page', 'ele_show_data_button', 'click', 'swDataReq', 'eleDataShowButton', 'ele_dataList_sw_page', NULL),
-- 元素记录新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'ele_add_sw_page', 'confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_element","refreshPage":"ele_start_page","refreshEle":"elementTable","closeSW":"ele_add_sub_window"}'),
-- 元素记录编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'ele_edit_sw_page', 'edit_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_element","refreshPage":"ele_start_page","refreshEle":"elementTable","closeSW":"ele_edit_sub_window"}'),

-- 元素事件新增按钮单击事件
('MenuMaintain', 'ele_eventList_sw_page', 'addElementEvent', 'click', 'swDataReq', 'eventAddButton', 'event_add_sw_page', ''),
-- 元素事件记录编辑按钮单击事件
('MenuMaintain', 'ele_eventList_sw_page', 'event_record_edit_button', 'click', 'swDataReq', 'eventEditButton', 'event_edit_sw_page', ''),
-- 元素事件记录删除按钮单击事件
('MenuMaintain', 'ele_eventList_sw_page', 'event_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_event","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"ele_eventList_sw_page","refreshEle":"eventTable"}'),
-- 元素事件新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'event_add_sw_page', 'add_event_confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_event","refreshPage":"event_add_sw_page","refreshEle":"eventTable","closeSW":"event_add_sub_window"}'),
-- 元素事件编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'event_edit_sw_page', 'edit_event_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_event","refreshPage":"event_edit_sw_page","refreshEle":"eventTable","closeSW":"event_edit_sub_window"}'),

-- 元素数据新增按钮单击事件
('MenuMaintain', 'ele_dataList_sw_page', 'addElementData', 'click', 'swDataReq', 'dataAddButton', 'data_add_sw_page', ''),
-- 元素数据记录编辑按钮单击事件
('MenuMaintain', 'ele_dataList_sw_page', 'data_record_edit_button', 'click', 'swDataReq', 'dataEditButton', 'data_edit_sw_page', ''),
-- 元素数据记录删除按钮单击事件
('MenuMaintain', 'ele_dataList_sw_page', 'data_record_del_button', 'click', 'buttonReq', 'delRecord', '', '{"tableName":"web_data","showConfirmSW":true,"confirmCnt":"确认删除?","refreshPage":"ele_eventList_sw_page","refreshEle":"eleDataTable"}'),
-- 元素数据新增弹窗-新增确认按钮单击事件
('MenuMaintain', 'data_add_sw_page', 'add_data_confirm', 'click', 'buttonReq', 'addRecord', '', '{"tableName":"web_data","refreshPage":"data_add_sw_page","refreshEle":"eleDataTable","closeSW":"data_add_sub_window"}'),
-- 元素数据编辑弹窗-编辑确认按钮单击事件
('MenuMaintain', 'data_edit_sw_page', 'edit_data_confirm', 'click', 'buttonReq', 'updRecord', '', '{"tableName":"web_data","refreshPage":"data_edit_sw_page","refreshEle":"eleDataTable","closeSW":"data_edit_sub_window"}');

DELETE FROM web_data;
INSERT INTO web_data (menu, page, "element", data_type, data_attr, express) VALUES
('MenuMaintain', 'start_page', 'menuTable', 'sql', '', 'select * from web_menu order by menu_seq'),
('MenuMaintain', 'menu_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_menu'),
('MenuMaintain', 'menu_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_menu'),

('MenuMaintain', 'menu_eventList_sw_page', 'menuEventTable', 'sql', NULL, 'select * from web_event where menu=#menu# and page=\'menuEvent\' order by web_event_id'),
('MenuMaintain', 'menu_event_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),
('MenuMaintain', 'menu_event_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),

('MenuMaintain', 'ele_start_page', 'elementTable', 'sql', NULL, 'select * from web_element where menu=#menu# order by web_element_id'),
('MenuMaintain', 'ele_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_element'),
('MenuMaintain', 'ele_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_element'),

('MenuMaintain', 'ele_eventList_sw_page', 'eventTable', 'sql', NULL, 'select * from web_event where menu=#menu# and page=#page# and element=#element# order by web_event_id'),
('MenuMaintain', 'event_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),
('MenuMaintain', 'event_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_event'),

('MenuMaintain', 'ele_dataList_sw_page', 'eleDataTable', 'sql', NULL, 'select * from web_data where menu=#menu# and page=#page# and element=#element# order by web_data_id'),
('MenuMaintain', 'data_add_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_data'),
('MenuMaintain', 'data_edit_sw_page', 'swBodyInputList', 'sql', NULL, 'select * from web_data');


DELETE FROM config_database_info;
INSERT INTO config_database_info (database_name, database_type, database_driver, database_addr, database_label, login_name, login_password, database_attr) VALUES
('local_sqlite', 'sqlite', 'org.sqlite.JDBC', 'jdbc:sqlite:sqlitedb/hlhome.db', '', '', '', ''),
-- ('local_oracle', 'oracle', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@localhost:1521:hlhome', '', 'hlhome', 'root', 'Root#98e');
('dev',       'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.20.16.15:5102', 'caes', 'deployop', 'iN9Wac@NAn*6', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('caes_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'caes', 'caesopr', 'De79#r6fd3', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('caes_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'caes', 'caesopr', 'Dfs@3K3#r3', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('caes_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'caes', 'caesopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('dfds_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'dfds', 'dfdsopr', 'Tfa5H3#y1', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('dfds_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'dfds', 'dfdsopr', 'Tfa5H3#y2', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('dfds_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'dfds', 'dfdsopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ldps_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'ldps', 'ldpsopr', 'De79#r6sd3', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ldps_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'ldps', 'ldpsopr', 'Dafjfl#15', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ldps_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'ldps', 'ldpsopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('bdcs_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'bdcs', 'bdcsopr', 'bdcsDasdf8#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('bdcs_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'bdcs', 'bdcsopr', 'bdcsDasdf8#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('bdcs_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'bdcs', 'bdcsopr', 'bdcsDasdf8#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cpms_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'cpms', 'cpmsopr', 'De79#r6fd8', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cpms_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'cpms', 'dfdsro', 'iCOWaVU6$bJq', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cpms_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'cpms', 'cpmsopr', 'Tc659442e113f#', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cccs_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'cccs', 'cccsopr', 'u&SXs8LaPTcD', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cccs_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'cccs', 'cccsopr', 'v4d0wh9usTY^', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('cccs_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'cccs', 'cccsopr', 'M88jR@E4FzVT', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ipos_sit1', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.15:4576', 'ipos', 'iposopr', '*nP*!X6ixmSH', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ipos_sit3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.31:4588', 'ipos', 'iposopr', '*nP*!X6ixmSH', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai'),
('ipos_sit6', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://10.21.16.52:3307', 'ipos', 'iposopr', '*nP*!X6ixmSH', 'useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Shanghai');

