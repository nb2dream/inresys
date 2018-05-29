/*

  2018-1-12 15:00:10

*/
-- quartz 的数据内容导入，请注意表与表的关联关系

INSERT INTO `sys_macro` VALUES (1, 0, '系统参数', 'sys', 1, 0, 0, NULL, '2017-8-15 14:51:27', '2017-10-17 14:19:35');
INSERT INTO `sys_macro` VALUES (2, 1, '用户状态', 'userStatus', 1, 0, 0, NULL, '2017-8-15 14:51:30', NULL);
INSERT INTO `sys_macro` VALUES (3, 2, '正常', '1', 0, 1, 0, '用户可登录', '2017-8-15 14:52:48', '2017-8-15 20:23:29');
INSERT INTO `sys_macro` VALUES (4, 2, '禁用', '0', 1, 1, 0, '禁止用户登录', '2017-8-15 14:52:51', '2017-8-15 20:44:42');
INSERT INTO `sys_macro` VALUES (5, 0, '客户关系', ' clientManage', 1, 0, 1, NULL, '2017-8-15 19:58:24', NULL);
INSERT INTO `sys_macro` VALUES (6, 5, '产品信息', 'clientProduct', 1, 0, 0, NULL, '2017-8-15 19:59:06', NULL);
INSERT INTO `sys_macro` VALUES (7, 0, '节目单状态', 'taskStatus', 1, 0, 3, NULL, '2017-10-17 14:17:03', NULL);
INSERT INTO `sys_macro` VALUES (8, 7, '未审核', '-2', 0, 1, 0, NULL, '2017-10-17 14:18:01', '2017-10-17 16:47:37');
INSERT INTO `sys_macro` VALUES (9, 7, '待审核', '-1', 0, 1, 1, NULL, '2017-10-17 14:22:22', '2017-10-17 16:47:25');
INSERT INTO `sys_macro` VALUES (10, 7, '已审核', '1', 1, 1, 2, NULL, '2017-10-17 14:23:07', '2017-10-17 14:23:59');
INSERT INTO `sys_macro` VALUES (11, 7, '审核未通过', '0', 0, 1, 3, NULL, '2017-10-17 14:25:18', '2017-10-17 14:26:55');
INSERT INTO `sys_macro` VALUES (12, 0, '终端节目同步状态', 'terminal_sync_status', 1, 0, 4, NULL, '2017-11-24 13:50:24', '2017-11-24 13:50:40');
INSERT INTO `sys_macro` VALUES (13, 12, '未同步', '-1', 1, 1, 0, NULL, '2017-11-24 13:52:08', NULL);
INSERT INTO `sys_macro` VALUES (14, 12, '同步中', '0', 1, 1, 1, NULL, '2017-11-24 13:52:44', NULL);
INSERT INTO `sys_macro` VALUES (15, 12, '已同步', '1', 1, 1, 3, NULL, '2017-11-24 13:53:01', '2017-11-24 13:53:18');

INSERT INTO `sys_menu` VALUES (1, 0, '基础管理', NULL, '', 0, 'fa fa-coffee', 3, '2017-8-9 22:49:47', '2017-10-18 15:38:40');
INSERT INTO `sys_menu` VALUES (2, 3, '系统菜单', 'base/menu/list.html', NULL, 1, 'fa fa-th-list', 2, '2017-8-9 22:55:15', NULL);
INSERT INTO `sys_menu` VALUES (3, 0, '系统管理', NULL, NULL, 0, 'fa fa-desktop', 4, '2017-8-9 23:06:55', '2017-10-10 09:42:53');
INSERT INTO `sys_menu` VALUES (4, 1, '通用字典', 'base/macro/list.html', NULL, 1, 'fa fa-book', 2, '2017-8-9 23:06:58', '2017-10-9 17:48:19');
INSERT INTO `sys_menu` VALUES (6, 3, '用户管理', 'base/user/list.html', NULL, 1, 'fa fa-user', 0, '2017-8-10 14:12:11', NULL);
INSERT INTO `sys_menu` VALUES (7, 3, '角色管理', 'base/role/list.html', NULL, 1, 'fa fa-paw', 1, '2017-8-10 14:13:19', NULL);
INSERT INTO `sys_menu` VALUES (11, 6, '刷新', NULL, 'sys:user:list', 2, NULL, 0, '2017-8-14 10:51:05', NULL);
INSERT INTO `sys_menu` VALUES (12, 6, '新增', NULL, 'sys:user:save', 2, NULL, 0, '2017-8-14 10:51:35', NULL);
INSERT INTO `sys_menu` VALUES (13, 6, '编辑', NULL, 'sys:user:edit', 2, NULL, 0, '2017-8-14 10:52:06', NULL);
INSERT INTO `sys_menu` VALUES (14, 6, '删除', NULL, 'sys:user:remove', 2, NULL, 0, '2017-8-14 10:52:24', NULL);
INSERT INTO `sys_menu` VALUES (15, 7, '刷新', NULL, 'sys:role:list', 2, NULL, 0, '2017-8-14 10:56:37', NULL);
INSERT INTO `sys_menu` VALUES (16, 7, '新增', NULL, 'sys:role:save', 2, NULL, 0, '2017-8-14 10:57:02', NULL);
INSERT INTO `sys_menu` VALUES (17, 7, '编辑', NULL, 'sys:user:edit', 2, NULL, 0, '2017-8-14 10:57:31', NULL);
INSERT INTO `sys_menu` VALUES (18, 7, '删除', NULL, 'sys:role:remove', 2, NULL, 0, '2017-8-14 10:57:50', NULL);
INSERT INTO `sys_menu` VALUES (19, 7, '分配权限', NULL, 'sys:role:authorize', 2, NULL, 0, '2017-8-14 10:58:55', NULL);
INSERT INTO `sys_menu` VALUES (20, 2, '刷新', NULL, 'sys:menu:list', 2, NULL, 0, '2017-8-14 10:59:32', NULL);
INSERT INTO `sys_menu` VALUES (21, 2, '新增', NULL, 'sys:menu:save', 2, NULL, 0, '2017-8-14 10:59:56', NULL);
INSERT INTO `sys_menu` VALUES (22, 2, '编辑', NULL, 'sys:menu:edit', 2, NULL, 0, '2017-8-14 11:00:26', NULL);
INSERT INTO `sys_menu` VALUES (23, 2, '删除', NULL, 'sys:menu:remove', 2, NULL, 0, '2017-8-14 11:00:58', NULL);
INSERT INTO `sys_menu` VALUES (24, 6, '启用', NULL, 'sys:user:enable', 2, NULL, 0, '2017-8-14 17:27:18', NULL);
INSERT INTO `sys_menu` VALUES (25, 6, '停用', NULL, 'sys:user:disable', 2, NULL, 0, '2017-8-14 17:27:43', NULL);
INSERT INTO `sys_menu` VALUES (26, 6, '重置密码', NULL, 'sys:user:resetPassword', 2, NULL, 0, '2017-8-14 17:28:34', NULL);
INSERT INTO `sys_menu` VALUES (27, 3, '系统日志', 'base/log/list.html', NULL, 1, 'fa fa-warning', 2, '2017-8-14 22:11:53', '2017-10-9 16:50:12');
INSERT INTO `sys_menu` VALUES (28, 27, '刷新', NULL, 'sys:log:list', 2, NULL, 0, '2017-8-14 22:30:22', NULL);
INSERT INTO `sys_menu` VALUES (29, 27, '删除', NULL, 'sys:log:remove', 2, NULL, 0, '2017-8-14 22:30:43', NULL);
INSERT INTO `sys_menu` VALUES (30, 27, '清空', NULL, 'sys:log:clear', 2, NULL, 0, '2017-8-14 22:31:02', NULL);
INSERT INTO `sys_menu` VALUES (32, 4, '刷新', NULL, 'sys:macro:list', 2, NULL, 0, '2017-8-15 16:55:33', NULL);
INSERT INTO `sys_menu` VALUES (33, 4, '新增', NULL, 'sys:macro:save', 2, NULL, 0, '2017-8-15 16:55:52', NULL);
INSERT INTO `sys_menu` VALUES (34, 4, '编辑', NULL, 'sys:macro:edit', 2, NULL, 0, '2017-8-15 16:56:09', NULL);
INSERT INTO `sys_menu` VALUES (35, 4, '删除', NULL, 'sys:macro:remove', 2, NULL, 0, '2017-8-15 16:56:29', NULL);
INSERT INTO `sys_menu` VALUES (42, 1, '定时任务', 'base/quartz/list.html', NULL, 1, 'fa fa-bell', 4, '2017-8-19 23:00:08', NULL);
INSERT INTO `sys_menu` VALUES (43, 42, '刷新', NULL, 'quartz:job:list', 2, NULL, 0, '2017-8-19 23:00:54', NULL);
INSERT INTO `sys_menu` VALUES (44, 42, '新增', NULL, 'quartz:job:save', 2, NULL, 0, '2017-8-19 23:01:29', NULL);
INSERT INTO `sys_menu` VALUES (45, 42, '编辑', NULL, 'quartz:job:edit', 2, NULL, 0, '2017-8-19 23:01:58', NULL);
INSERT INTO `sys_menu` VALUES (46, 42, '删除', NULL, 'quartz:job:remove', 2, NULL, 0, '2017-8-19 23:02:30', NULL);
INSERT INTO `sys_menu` VALUES (47, 42, '启用', NULL, 'quartz:job:enable', 2, NULL, 0, '2017-8-19 23:08:59', NULL);
INSERT INTO `sys_menu` VALUES (48, 42, '停用', NULL, 'quartz:job:disable', 2, NULL, 0, '2017-8-19 23:09:31', NULL);
INSERT INTO `sys_menu` VALUES (49, 42, '立即执行', NULL, 'quartz:job:run', 2, NULL, 0, '2017-8-19 23:10:09', NULL);
INSERT INTO `sys_menu` VALUES (50, 42, '日志列表', NULL, 'quartz:job:log', 2, NULL, 0, '2017-8-19 23:10:40', NULL);
INSERT INTO `sys_menu` VALUES (51, 42, '刷新', NULL, 'quartz:log:list', 2, NULL, 0, '2017-8-21 13:25:33', NULL);
INSERT INTO `sys_menu` VALUES (52, 42, '删除', NULL, 'quartz:log:remove', 2, NULL, 0, '2017-8-21 13:25:52', NULL);
INSERT INTO `sys_menu` VALUES (53, 42, '清空', NULL, 'quartz:log:clear', 2, NULL, 0, '2017-8-21 13:26:11', NULL);
INSERT INTO `sys_menu` VALUES (81, 92, '终端管理', '/manage/terminal/list_.html', '', 1, 'fa fa-window-restore', 1, '2017-9-18 15:47:16', '2017-11-10 14:32:11');
INSERT INTO `sys_menu` VALUES (83, 81, '新增', NULL, 'mana:terminal:save', 2, NULL, 0, '2017-9-21 08:54:13', NULL);
INSERT INTO `sys_menu` VALUES (84, 81, '删除', NULL, 'mana:terminal:remove', 2, NULL, 0, '2017-9-21 08:55:08', NULL);
INSERT INTO `sys_menu` VALUES (85, 81, '编辑', NULL, 'mana:terminal:edit', 2, NULL, 0, '2017-9-21 08:55:43', '2017-9-21 16:00:54');
INSERT INTO `sys_menu` VALUES (86, 81, '禁用', NULL, 'mana:terminal:disable', 2, NULL, 0, '2017-9-21 15:46:46', NULL);
INSERT INTO `sys_menu` VALUES (87, 81, '启用', NULL, 'mana:terminal:enable', 2, NULL, 0, '2017-9-21 15:47:36', NULL);
INSERT INTO `sys_menu` VALUES (88, 81, '重启', NULL, 'mana:terminal:restart', 2, NULL, 0, '2017-9-21 15:48:52', '2018-3-1 09:10:34');
INSERT INTO `sys_menu` VALUES (89, 0, '节目管理', '', NULL, 0, 'fa fa-eercast', 1, '2017-10-9 17:45:20', '2017-10-10 09:42:27');
INSERT INTO `sys_menu` VALUES (90, 89, '节目制作', '/manage/program/list_.html', NULL, 1, 'fa fa-grav', 0, '2017-10-10 09:26:19', '2017-11-10 14:24:14');
INSERT INTO `sys_menu` VALUES (91, 89, '节目单', '/manage/program/list_task.html', NULL, 1, 'fa fa-sitemap', 1, '2017-10-10 09:26:57', '2017-11-10 13:58:10');
INSERT INTO `sys_menu` VALUES (92, 0, '  终端管理', NULL, NULL, 0, 'fa fa-credit-card-alt', 2, '2017-10-10 09:41:39', '2017-10-10 09:43:56');
INSERT INTO `sys_menu` VALUES (93, 89, '节目审核', '/manage/program/list_check.html', NULL, 1, 'fa fa-pencil-square-o', 3, '2017-10-10 09:47:40', '2017-11-10 13:58:42');
INSERT INTO `sys_menu` VALUES (94, 0, ' 素材管理', NULL, NULL, 0, 'fa fa-free-code-camp', 0, '2017-10-18 10:36:18', '2017-10-18 15:38:49');
INSERT INTO `sys_menu` VALUES (95, 94, '节目素材', '/manage/material/list_.html', NULL, 1, 'fa fa-caret-square-o-right', 0, '2017-10-18 10:37:33', '2017-11-10 14:02:47');
INSERT INTO `sys_menu` VALUES (97, 95, '新增', NULL, 'mana:material:save', 2, NULL, 0, '2018-2-28 17:33:43', NULL);
INSERT INTO `sys_menu` VALUES (98, 95, '编辑', NULL, 'mana:material:edit', 2, NULL, 0, '2018-2-28 17:34:18', NULL);
INSERT INTO `sys_menu` VALUES (99, 95, '删除', NULL, 'mana:material:remove', 2, NULL, 0, '2018-2-28 17:35:16', NULL);
INSERT INTO `sys_menu` VALUES (100, 95, '查看', NULL, 'mana:material:view', 2, NULL, 0, '2018-2-28 17:37:58', NULL);
INSERT INTO `sys_menu` VALUES (101, 90, '新增', NULL, 'mana:program:save', 2, NULL, 0, '2018-2-28 17:42:47', NULL);
INSERT INTO `sys_menu` VALUES (102, 90, '编辑', NULL, 'mana:program:edit', 2, NULL, 0, '2018-2-28 17:43:53', NULL);
INSERT INTO `sys_menu` VALUES (103, 90, '删除', NULL, 'mana:program:remove', 2, NULL, 0, '2018-2-28 17:44:58', NULL);
INSERT INTO `sys_menu` VALUES (104, 90, '布局', NULL, 'mana:program:layout', 2, NULL, 0, '2018-2-28 17:46:43', NULL);
INSERT INTO `sys_menu` VALUES (105, 90, '素材', NULL, 'mana:program:material', 2, NULL, 0, '2018-2-28 17:47:30', NULL);
INSERT INTO `sys_menu` VALUES (106, 90, '预览', NULL, 'mana:program:preview', 2, NULL, 0, '2018-2-28 17:48:46', NULL);
INSERT INTO `sys_menu` VALUES (107, 91, '新增', NULL, 'mana:programtask:save', 2, NULL, 0, '2018-2-28 17:54:59', NULL);
INSERT INTO `sys_menu` VALUES (108, 91, '编辑', NULL, 'mana:programtask:edit', 2, NULL, 0, '2018-2-28 17:55:44', NULL);
INSERT INTO `sys_menu` VALUES (109, 91, '删除', NULL, 'mana:programtask:remove', 2, NULL, 0, '2018-2-28 17:57:02', NULL);
INSERT INTO `sys_menu` VALUES (110, 91, '申请审核', NULL, 'mana:programtask:check', 2, NULL, 0, '2018-2-28 18:00:35', NULL);
INSERT INTO `sys_menu` VALUES (111, 91, '节目管理', NULL, NULL, 1, NULL, 0, '2018-2-28 18:05:53', NULL);
INSERT INTO `sys_menu` VALUES (112, 111, '新增', NULL, 'mana:programmanage:save', 2, NULL, 0, '2018-3-1 09:05:45', NULL);
INSERT INTO `sys_menu` VALUES (113, 111, '删除', NULL, 'mana:programmanage:remove', 2, NULL, 0, '2018-3-1 09:06:06', NULL);
INSERT INTO `sys_menu` VALUES (114, 111, '预览', NULL, 'mana:programmanage:preview', 2, NULL, 0, '2018-3-1 09:07:17', NULL);
INSERT INTO `sys_menu` VALUES (115, 111, '定时', NULL, 'mana:programmanage:timing', 2, NULL, 0, '2018-3-1 09:09:40', NULL);
INSERT INTO `sys_menu` VALUES (116, 93, '审核节目', NULL, 'mana:programcheck:check', 2, NULL, 0, '2018-3-1 09:18:57', '2018-3-1 09:22:42');
INSERT INTO `sys_menu` VALUES (117, 93, '节目内容', NULL, 'mana:programcheck:program', 2, NULL, 0, '2018-3-1 09:21:30', '2018-3-1 10:56:17');
INSERT INTO `sys_menu` VALUES (119, 81, '任务下发', NULL, 'mana:terminal:taskspush', 2, NULL, 0, '2018-3-1 09:44:58', NULL);
INSERT INTO `sys_menu` VALUES (120, 81, '截图', NULL, 'mana:terminal:printscreen', 2, NULL, 0, '2018-3-1 09:47:27', NULL);
INSERT INTO `sys_menu` VALUES (121, 81, '重置终端', NULL, 'mana:terminal:reset', 2, NULL, 0, '2018-3-1 09:48:34', NULL);
INSERT INTO `sys_menu` VALUES (122, 95, '刷新', NULL, 'mana:material:list', 2, NULL, 0, '2018-3-1 10:46:51', NULL);
INSERT INTO `sys_menu` VALUES (123, 90, '刷新', NULL, 'mana:program:list', 2, NULL, 0, '2018-3-1 10:47:20', NULL);
INSERT INTO `sys_menu` VALUES (124, 91, '刷新', NULL, 'mana:programtask:list', 2, NULL, 0, '2018-3-1 10:47:58', NULL);
INSERT INTO `sys_menu` VALUES (125, 93, '刷新', NULL, 'mana:programcheck:list', 2, NULL, 0, '2018-3-1 10:48:59', '2018-3-1 10:56:41');
INSERT INTO `sys_menu` VALUES (126, 81, '刷新', NULL, 'mana:terminal:list', 2, NULL, 0, '2018-3-1 10:50:13', NULL);
INSERT INTO `sys_menu` VALUES (127, 81, '设置', NULL, 'mana:terminal:settings', 2, NULL, 0, '2018-03-07 10:15:57', NULL);


INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', '【系统内置】', 2, '2017-8-12 00:43:52', '2017-8-12 19:14:59');


INSERT INTO `sys_role_menu` VALUES (2238, 1, 94);
INSERT INTO `sys_role_menu` VALUES (2239, 1, 95);
INSERT INTO `sys_role_menu` VALUES (2240, 1, 97);
INSERT INTO `sys_role_menu` VALUES (2241, 1, 98);
INSERT INTO `sys_role_menu` VALUES (2242, 1, 99);
INSERT INTO `sys_role_menu` VALUES (2243, 1, 100);
INSERT INTO `sys_role_menu` VALUES (2244, 1, 122);
INSERT INTO `sys_role_menu` VALUES (2245, 1, 89);
INSERT INTO `sys_role_menu` VALUES (2246, 1, 90);
INSERT INTO `sys_role_menu` VALUES (2247, 1, 101);
INSERT INTO `sys_role_menu` VALUES (2248, 1, 102);
INSERT INTO `sys_role_menu` VALUES (2249, 1, 103);
INSERT INTO `sys_role_menu` VALUES (2250, 1, 104);
INSERT INTO `sys_role_menu` VALUES (2251, 1, 105);
INSERT INTO `sys_role_menu` VALUES (2252, 1, 106);
INSERT INTO `sys_role_menu` VALUES (2253, 1, 123);
INSERT INTO `sys_role_menu` VALUES (2254, 1, 91);
INSERT INTO `sys_role_menu` VALUES (2255, 1, 107);
INSERT INTO `sys_role_menu` VALUES (2256, 1, 108);
INSERT INTO `sys_role_menu` VALUES (2257, 1, 109);
INSERT INTO `sys_role_menu` VALUES (2258, 1, 110);
INSERT INTO `sys_role_menu` VALUES (2259, 1, 111);
INSERT INTO `sys_role_menu` VALUES (2260, 1, 112);
INSERT INTO `sys_role_menu` VALUES (2261, 1, 113);
INSERT INTO `sys_role_menu` VALUES (2262, 1, 114);
INSERT INTO `sys_role_menu` VALUES (2263, 1, 115);
INSERT INTO `sys_role_menu` VALUES (2264, 1, 124);
INSERT INTO `sys_role_menu` VALUES (2265, 1, 93);
INSERT INTO `sys_role_menu` VALUES (2266, 1, 116);
INSERT INTO `sys_role_menu` VALUES (2267, 1, 117);
INSERT INTO `sys_role_menu` VALUES (2268, 1, 125);
INSERT INTO `sys_role_menu` VALUES (2269, 1, 92);
INSERT INTO `sys_role_menu` VALUES (2270, 1, 81);
INSERT INTO `sys_role_menu` VALUES (2271, 1, 83);
INSERT INTO `sys_role_menu` VALUES (2272, 1, 84);
INSERT INTO `sys_role_menu` VALUES (2273, 1, 85);
INSERT INTO `sys_role_menu` VALUES (2274, 1, 86);
INSERT INTO `sys_role_menu` VALUES (2275, 1, 87);
INSERT INTO `sys_role_menu` VALUES (2276, 1, 88);
INSERT INTO `sys_role_menu` VALUES (2277, 1, 119);
INSERT INTO `sys_role_menu` VALUES (2278, 1, 120);
INSERT INTO `sys_role_menu` VALUES (2279, 1, 121);
INSERT INTO `sys_role_menu` VALUES (2280, 1, 126);
INSERT INTO `sys_role_menu` VALUES (2281, 1, 3);
INSERT INTO `sys_role_menu` VALUES (2282, 1, 6);
INSERT INTO `sys_role_menu` VALUES (2283, 1, 11);
INSERT INTO `sys_role_menu` VALUES (2284, 1, 12);
INSERT INTO `sys_role_menu` VALUES (2285, 1, 13);
INSERT INTO `sys_role_menu` VALUES (2286, 1, 14);
INSERT INTO `sys_role_menu` VALUES (2287, 1, 24);
INSERT INTO `sys_role_menu` VALUES (2288, 1, 25);
INSERT INTO `sys_role_menu` VALUES (2289, 1, 26);
INSERT INTO `sys_role_menu` VALUES (2290, 1, 7);
INSERT INTO `sys_role_menu` VALUES (2291, 1, 15);
INSERT INTO `sys_role_menu` VALUES (2292, 1, 16);
INSERT INTO `sys_role_menu` VALUES (2293, 1, 17);
INSERT INTO `sys_role_menu` VALUES (2294, 1, 18);
INSERT INTO `sys_role_menu` VALUES (2295, 1, 19);
INSERT INTO `sys_role_menu` VALUES (2296, 1, 2);
INSERT INTO `sys_role_menu` VALUES (2297, 1, 20);
INSERT INTO `sys_role_menu` VALUES (2298, 1, 21);
INSERT INTO `sys_role_menu` VALUES (2299, 1, 22);
INSERT INTO `sys_role_menu` VALUES (2300, 1, 23);
INSERT INTO `sys_role_menu` VALUES (2301, 1, 27);
INSERT INTO `sys_role_menu` VALUES (2302, 1, 28);
INSERT INTO `sys_role_menu` VALUES (2303, 1, 29);
INSERT INTO `sys_role_menu` VALUES (2304, 1, 30);
INSERT INTO `sys_role_menu` VALUES (2348, 1, 127);

INSERT INTO `sys_user` VALUES (1, 'admin', '33808479d49ca8a3cdc93d4f976d1e3d', 'admin@example.com', '123456', 1, 1, '2017-8-15 21:40:39', '2017-9-18 15:32:04');

INSERT INTO `sys_user_role` VALUES (50, 1, 1);






