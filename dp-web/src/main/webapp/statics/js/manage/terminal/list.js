$(function () {
    initialPage();
    getGrid();
});

// 初始化面板
function initialPage() {
    $("#treePanel").css("height", $(window).height()-232); // 392
    // 调整窗口大小时触发 resize()
    $(window).resize(function () {
        $("#treePanel").css("height", $(window).height()-232);
        $("#dataGrid").bootstrapTable("resetView", {
            height: $(window).height()-225
            // 386
        });
    });
}

function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url:'/manage/terminal/list?_' + $.now(),
        height: $(window).height()-225,
        queryParams : function (params) {  // 自定义请求参数
            params.name = vm.keyword;           // 搜索参数
            params.classifyId = vm.parentId;    // 分组id
            params.rootNode = vm.rootNode;      // 根节点
            return params;
        },
        columns: [{
            checkbox: true
        },{
            field : "id",
            title : "编号",
            width : "50px",
            align : "center"
        },{
            field : "classifyName",
            title : "所属分组",
            width : "80px",
            align : "center"
        },{
            field : "terminalName",
            title : "终端名",
            width : "150px",
            align : "center"
        },{
            field : "terminalId",
            title : "终端标识码",
            width : "150px",
            align : "center"
        },{
            field : "networkStatus",
            title : "网络状态",
            width : "80px",
            align : "center",
            formatter : function(value, row, index) {
                if(value=='0'){
                    return '<span class="label label-danger">离线</span>';
                }else if(value=='1'){
                    return '<span class="label label-success">正常</span>';
                }
            }
        },{
            field : "syncStatus",
            title : "同步状态",
            width : "80px",
            formatter : function(value, row, index) {
                if(value=='0'){
                    return '<span class="label label-info">同步中</span>';
                }else if(value=='1'){
                    return '<span class="label label-success">已同步</span>';
                } else if (value == '-1') {
                    return '<span class="label label-default">未同步</span>'
                } else if (value == '2') {
                    return '<span class="label label-danger">同步失败</span>'
                }
            }
        },{
            field : "createDate",
            title : "创建时间",
            width : "200px",
            align : "center"
        },{
            field : "remark",
            title : "备注",
            align : "center"
        }]
    });
}

// ztree setting 树节点设置
var setting = {
    async : {
        enable: true,                                   // 开启异步加载模式
        type: "get",	                                // 请求方式get
        url: "/manage/terminalClassify/select",         // url功能：根据父级code查询子节点，子区域列表
        autoParam: ["classifyId=parentId"],              // 需要提交的参数列表
        dataFilter: function (treeId, parentNode, responseData) {     // 异步请求数据的预处理
            return responseData.rows;
        }
    },
    data : {
        simpleData : {
            enable : true,  		// 是否采用简单数据模式
            idKey : "classifyId",		// 节点数据中保存唯一标识的属性名称
            pIdKey : "parentId",  // 节点数据中保存其父节点唯一标识的属性名称
            rootPId : "0"			// 用于修正根节点父节点数据
        },
        key : {
            url : "nourl" // zTree 节点数据保存节点链接的目标 URL 的属性名称。
            // 特殊用途：当后台数据只能生成 url 属性，又不想实现点击节点跳转的功能时，
            // 可以直接修改此属性为其他不存在的属性名称
        }
    },
    callback : {
        // 用于捕获节点被点击的事件回调函数
        onClick : function(event, treeId, treeNode) {
            vm.parentId = treeNode.classifyId;
            vm.parentName = treeNode.name;
            vm.load();  // 刷新表格
        }
    }

};

var ztree;

var vm  = new Vue({
    el : '#list',
    data : {
        terminalTotal: 0,
        onlineTerminal: 0,
        offlineTerminal: 0,
        rootNode: '0',          // 用于搜索全部信息的根节点
        parentId: null,
        parentName: null,
        keyword : null          // 搜索是用的关键字
    },
    methods : {
        init : function () {
            this.countTerminalTotal(-1);  // 获取总终端数
            this.countTerminalTotal(0);  // 获取离线终端数
        },
        countTerminalTotal : function (networkStatusCode) {
            var this_ = this;
            $.ajax({
                type:"GET",
                url:"/manage/terminal/countTotal",
                async:false,
                data:{networkStatus: networkStatusCode},
                success: function(r){
                    if (r.code == '0') {
                        if (networkStatusCode == -1) {
                            this_.terminalTotal = r.rows;
                        } else if (networkStatusCode == 0){
                            this_.offlineTerminal = r.rows;
                            this_.onlineTerminal = this_.terminalTotal - this_.offlineTerminal;
                        } else if (networkStatusCode == 1) {
                            this_.onlineTerminal = r.rows;
                            this_.offlineTerminal = this_.terminalTotal - this_.onlineTerminal;
                        }
                    } else {
                        dialogMsg(r.msg, 'error');
                    }
                }
            });
        },
        load : function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
            this.init();
        },
        getTree : function (parentId) {
            $.get("/manage/terminalClassify/select", {parentId: parentId}, function (r) {
                if (r.code == '0') {
                    ztree = $.fn.zTree.init($("#classifyTree"), setting, r.rows);
                } else {
                    dialogMsg(r.msg, 'error');
                }
            })
        },
        save : function () {
            dialogOpen({
                title : "新增终端",
                url : "/manage/terminal/add.html",
                width : '500px',
                height : '330px',
                success : function(iframeId) {
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        edit :function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                dialogOpen({
                    title : "编辑终端",
                    url : "/manage/terminal/edit.html?_" + $.now(),
                    width : '500px',
                    height : '330px',
                    success : function(iframeId) {
                        top.frames[iframeId].vm.terminal.id = ck[0].id;
                        top.frames[iframeId].vm.setForm();
                    },
                    yes : function(iframeId) {
                        top.frames[iframeId].vm.acceptClick();
                    }
                });
            }
        },
        remove : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            var ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function (index, item) {
                    ids[index] = item.id;
                });
                $.RemoveForm({
                    url: "/manage/terminal/remove?_" + $.now(),
                    param: ids,
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        enable : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            var ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function (index, item) {
                    ids[index] = item.id;
                });
                $.RemoveForm({
                    msg: "你确定要启用该终端？",
                    url: "/manage/terminal/start?_" + $.now(),
                    param: {"ids": ids},
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        disable :function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            var ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function (index, item) {
                    ids[index] = item.id;
                });
                $.RemoveForm({
                    msg: "你确定要禁用该终端？",
                    url: "/manage/terminal/shutdown?_" + $.now(),
                    param: {"ids": ids},
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        restart : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            var ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function (index, item) {
                    ids[index] = item.id;
                });
                $.RemoveForm({
                    msg: "你确定要重启该终端？",
                    url: "/manage/terminal/restart?_" + $.now(),
                    param: {"ids": ids},
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        getAll : function () {
            this.parentId = null;
            this.load();
        },
        saveClassify : function () {
            dialogOpen({
                title : "新增分组",
                url : "/manage/terminal/add_classify.html",
                width : '500px',
                height : '330px',
                success : function(iframeId) {
                    top.frames[iframeId].vm.getTreeUrl = "/manage/terminal/tree_classify.html";
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick("/manage/terminalClassify/save");
                }
            });
        },
        editClassify: function () {
            if (checkedRow(vm.parentId)) {
                dialogOpen({
                    title : "编辑分组",
                    url : "/manage/terminal/add_classify.html",
                    width : '500px',
                    height : '330px',
                    success : function(iframeId) {
                        top.frames[iframeId].vm.classify.classify_id = vm.parentId;
                        top.frames[iframeId].vm.getTreeUrl = "/manage/terminal/tree_classify.html";
                        top.frames[iframeId].vm.setForm();
                    },
                    yes : function(iframeId) {
                        top.frames[iframeId].vm.acceptClick("/manage/terminalClassify/edit");
                    }
                });
            }
        },
        removeClassify : function () {
            if (checkedRow(vm.parentId)) {
                $.RemoveForm({
                    url: "/manage/terminalClassify/remove?_" + $.now(),
                    param: vm.parentId,
                    success: function (data) {
                        vm.getTree(0);
                    }
                });
            }
        },
        taskPush: function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedArray(ck)) {
                terminalIds = [];
                $.each(ck, function (index, item) {
                    terminalIds[index] = item.id;
                });
                dialogOpen({
                    title : "下发任务",
                    id : 'pushLayer',
                    url : "/manage/terminal/task_push.html",
                    width : '500px',
                    height : '200px',
                    success : function(iframeId) {
                        top.frames[iframeId].vm.params.terminalIds = terminalIds;
                    },
                    yes : function(iframeId) {
                        top.frames[iframeId].vm.acceptClick();
                    }
                });
            }
        },
        settings: function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedArray(ck)) {
                terminalIds = [];
                $.each(ck, function (index, item) {
                    terminalIds[index] = item.id;
                });
                dialogOpen({
                    title : "终端设置",
                    id : 'pushLayer',
                    url : "/manage/terminal/settings.html",
                    width : '600px',
                    height : '420px',
                    btn:[],
                    success : function(iframeId) {
                        top.frames[iframeId].vm.params.ids = terminalIds;
                    }
                });
            }
        }
    },
    created : function () {
        this.getTree(this.rootNode);
        this.init();
    }
});