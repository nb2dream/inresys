$(function () {
    initialPage();
    getGrid();
});

// 初始化面板
function initialPage() {
    $("#treePanel").css("height", $(window).height()-132); // 392
    // 调整窗口大小时触发 resize()
    $(window).resize(function () {
        $("#treePanel").css("height", $(window).height()-132);
        $("#dataGrid").bootstrapTable("resetView", {
            height: $(window).height()-125
            // 386
        });
    });
}

function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url:'/manage/material/list?_' + $.now(),
        height: $(window).height()-125,
        queryParams : function (params) {  // 自定义请求参数
            params.name = vm.keyword;           // 搜索参数
            params.classifyId = vm.parentId;    // 分组id
            params.type = vm.type;
            // params.rootNode = vm.rootNode;      // 根节点
            return params;
        },
        columns: [{
            checkbox: true
        },{
            field : "id",
            title : "编号",
            width : "50px"
        },{
            field : "materialName",
            title : "素材名",
            width : "300px"
        },{
            field : "classifyName",
            title : "所属分类",
            width : "150px"
        },{
            field : "type",
            title : "素材类型",
            width : "30px",
            formatter : function(value, row, index) {
                //1:图片，2:视频，3:音频，4:文字,5:网址
                if (value == 1) {
                    return '<span class="label label-primary">图片</span>';
                } else if (value == 2) {
                    return '<span class="label label-success">视频</span>';
                } else if (value == 3) {
                    return '<span class="label label-info">音频</span>';
                } else if (value == 4) {
                    return '<span class="label label-default">文字</span>';
                } else if (value == 5) {
                    return '<span class="label label-danger">网址</span>';
                }
            }
        },{
            field : "fileSize",
            title : "素材大小",
            width : "50px",
            formatter : function(value, row, index) {
                if (value) {
                    return value + "M"
                } else {
                    return "";
                }
            }
        },{
            field : "createUserName",
            title : "创建用户",
            width : "80px"
        },{
            field : "gmtCreate",
            title : "录入时间",
        }]
    });
}

// ztree setting 树节点设置
var setting = {
    async : {
        enable: true,                                   // 开启异步加载模式
        type: "get",	                                // 请求方式get
        url: "/manage/materialClassify/select",         // url功能：根据父级code查询子节点，子区域列表
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
        rootNode: '0',          // 用于搜索全部信息的根节点
        parentId: null,
        parentName: null,
        type: 0,
        keyword : null          // 搜索是用的关键字
    },
    methods : {
        load : function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
        },
        getTree : function (parentId) {
            $.get("/manage/materialClassify/select", {parentId: parentId}, function (r) {
                if (r.code == '0') {
                    ztree = $.fn.zTree.init($("#classifyTree"), setting, r.rows);
                } else {
                    dialogMsg(r.msg, 'error');
                }
            })
        },
        save : function () {
            dialogOpen({
                title : "新增素材",
                url : "/manage/material/add.html",
                width : '500px',
                height : '350px',
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
                    title : "编辑素材",
                    url : "/manage/material/edit.html",
                    width : '500px',
                    height : '350px',
                    success : function(iframeId) {
                        top.frames[iframeId].vm.id = ck[0].id;
                        top.frames[iframeId].vm.material.classifyName = ck[0].classifyName;
                        top.frames[iframeId].vm.material.classifyId = ck[0].classifyId;
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
                    url: "/manage/material/remove?_" + $.now(),
                    param: ids,
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
                    top.frames[iframeId].vm.getTreeUrl = "/manage/material/tree_classify.html";
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick("/manage/materialClassify/save");
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
                        top.frames[iframeId].vm.getTreeUrl = "/manage/material/tree_classify.html";
                        top.frames[iframeId].vm.classify.classify_id = vm.parentId;
                        top.frames[iframeId].vm.setForm();
                    },
                    yes : function(iframeId) {
                        top.frames[iframeId].vm.acceptClick("/manage/materialClassify/edit");
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
        view : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                var type = ck[0].type;
                if (type == 1) {
                    window.open("/view/image?filePath=" + ck[0].path);
                } else if (type == 2 || type == 3) {
                    layer.open({
                        type: 2,
                        title: false,
                        area: ['630px', '360px'],
                        shade: 0.8,
                        closeBtn: 0,
                        shadeClose: true,
                        content: "/webfile/" +  ck[0].path
                    });
                } else if (type == 5) {
                    window.open(ck[0].content);
                } else {
                    dialogAlert(JSON.parse(ck[0].content).content, "info");
                }
            }
        }
    },
    created : function () {
        this.getTree(this.rootNode);
    }
});