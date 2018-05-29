$(function () {
    getGrid();
});

function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url:'/manage/programTask/list?_' + $.now(),
        height: $(window).height()-108,
        queryParams : function (params) {  // 自定义请求参数
            params.name = vm.keyword;           // 搜索参数
            return params;
        },
        columns: [{
            checkbox: true
        },{
            field : "id",
            title : "编号",
            width : "50px"
        },{
            field : "taskName",
            title : "节目单名",
            width : "220px"
        },{
            field : "playModel",
            title : "播放方式",
            width : "80px",
            formatter : function(value, row, index) {
                if (value == 1) {
                    return '<span class="label label-success">顺序播放</span>';
                } else {
                    return '<span class="label label-info">随机播发</span>';
                }
            }
        },{
            field : "createUserName",
            title : "创建用户",
            width : "150px"
        },{
            field : "width",
            title : "分辨率",
            width : "120px",
            formatter : function(value, row, index) {
                return value + "*" + row.height;
            }
        },{
            field : "status",
            title : "审核状态",
            width : "80px",
            formatter : function(value, row, index) {
                if (value == 1) {
                    return '<span class="label label-success">已审核</span>';
                } else if (value == 0){
                    return '<span class="label label-danger">审核未通过</span>';
                } else if (value == -1){
                    return '<span class="label label-warning">待审核</span>';
                } else if (value == -2){
                    return '<span class="label label-default">未审核</span>';
                }
            }
        },{
            field : "gmtCreate",
            title : "创建时间",
            width : "150px"
        },{
            field : "description",
            title : "描述"
        },{
            field : "id",
            title : "操作",
            width : "80px",
            formatter : function(value, row, index) {
                return '<a target="_Blank" class="btn btn-default btn-xs" href="/manage/programTask/download_task?taskId='+ value +'"><i class="fa fa-download"></i>&nbsp;下载</button>&nbsp;&nbsp;'
            }
        }]
    });
}

var vm = new Vue({
    el : '#list',
    data : {
        saveUrl : "/manage/programTask/save",
        updateUrl : "/manage/programTask/update",
        keyword: null,  // 搜索用关键字
        params : {
            status : '-1',
            ids: []
        }
    },
    methods : {
        load : function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
        },
        save : function () {
            dialogOpen({
                title : "新增节目",
                url :"/manage/program/add_task.html",
                width : "500px",
                height : "240px",
                success : function() {
                },
                yes : function (iframeId) {
                    top.frames[iframeId].vm.acceptClick(vm.saveUrl);
                }
            })
        },
        edit : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                dialogOpen({
                    title : "编辑节目",
                    url :"/manage/program/add_task.html",
                    width : "500px",
                    height : "200px",
                    success : function(iframeId) {
                        top.frames[iframeId].vm.programTask.id = ck[0].id;
                        top.frames[iframeId].vm.setForm();
                    },
                    yes : function (iframeId) {
                        top.frames[iframeId].vm.acceptClick(vm.updateUrl);
                    }
                })
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
                    url: "/manage/programTask/remove?_" + $.now(),
                    param: ids,
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        toRelation : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                dialogOpen({
                    title : "节目单-节目管理",
                    url :"/manage/program/list_relation.html",
                    width : "1200px",
                    height : "600px",
                    btn: ['确定'],
                    success : function(iframeId) {
                        top.frames[iframeId].vm.id = ck[0].id;  // 把节目单id传过去
                        top.frames[iframeId].vm.width = ck[0].width;  // 把节目单id传过去
                        top.frames[iframeId].vm.height = ck[0].height;  // 把节目单id传过去
                        top.frames[iframeId].vm.load();
                    },
                    yes : function (iframeId) {
                        var check = top.frames[iframeId].vm.acceptClick();
                        if (check) {
                            top.layer.closeAll();
                            dialogMsg("操作成功", common.msgType.SUCCESS);
                            vm.load();
                        }
                    }
                })
            }
        },
        applyCheck : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            var isOk = true;
            if (checkedArray(ck)) {
                $.each(ck, function (index, item) {
                    if (item.status == -1 || item.status == 1) {
                        isOk = false;
                        dialogAlert("请选择未审核对象或审核未通过对象!!", common.msgType.ERROR);
                        return;
                    }
                    vm.params.ids[index] = item.id;
                });
                if (!isOk) {return;}
                $.SaveForm({
                    url: "/manage/programTask/check?_" + $.now(),
                    param: vm.params,
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        publishTask : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {

                $.SaveForm({
                    url: "/manage/programTask/publish_task?_" + $.now(),
                    param: ck[0].id,
                    success: function (data) {
                        vm.load(); // 刷新列表
                    }
                });
            }
        }

    }
});