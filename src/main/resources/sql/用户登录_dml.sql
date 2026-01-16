

DELETE FROM web_element WHERE menu = 'root';
INSERT INTO web_element(web_element_id, element_no, sort_no, menu, page, element_parent, "element", element_type, element_desc, element_attr, param)VALUES
(2025010100000001, 'EM000001', 1, 'root', 'start_page', 'contentArea', 'login_sbw', 'subWindow', '登录', '', ''),
(2025010100000002, 'EM000002', 2, 'root', 'start_page', 'login_sbw', 'login_sbw_content_div', 'div', '', 'class="showWithRowDivGrid"', ''),
(2025010100000003, 'EM000003', 3, 'root', 'start_page', 'login_sbw_content_div', 'userId', 'input', '用户', 'out="Y"', NULL),
(2025010100000004, 'EM000004', 4, 'root', 'start_page', 'login_sbw_content_div', 'password', 'input', '密码', 'out="Y";type="password"', NULL),
(2025010100000005, 'EM000005', 5, 'root', 'start_page', 'login_sbw', 'login_sbw_footer_div', 'div', '', 'class="subWidowFooter"', ''),
(2025010100000006, 'EM000006', 6, 'root', 'start_page', 'login_sbw_footer_div', 'login', 'button', '登录', 'class="inputArea_sub_button"', NULL);


DELETE FROM web_event WHERE menu = 'root';
INSERT INTO web_event(web_event_id, menu, page, "element", event_type, request_type, request_bean, next_page, param)VALUES
(2025010100000001, 'root', 'start_page', 'login', 'click', 'buttonReq', 'userLogin', NULL, NULL);

DELETE FROM web_call_after WHERE menu = 'root';
INSERT INTO web_call_after(web_call_after_id, menu, page, process_bean, process_status, opr_type, request_type, request_bean, param)VALUES
(2025010100000001, 'root', 'start_page', 'login', 'false', 'showMessage', NULL, NULL, '{"msg":"登录失败"}'),
(2025010100000002, 'root', 'start_page', 'login', 'success', 'showMessage', NULL, NULL, '{"msg":"登录成功"}'),
(2025010100000003, 'root', 'start_page', 'login', 'success', 'loginSucSetUserInfo', NULL, NULL, '{}'),
(2025010100000004, 'root', 'start_page', 'login', 'success', 'closeSw', NULL, NULL, '{"subWindowId":"login_sbw"}'),
(2025010100000005, 'root', 'start_page', 'login', 'success', 'request', '', 'initPageInfo', '{}');