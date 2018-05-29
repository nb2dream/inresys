$(function () {
    getGrid();
});

function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url:'/manage/programTask/list?_' + $.now(),
        height: $(window).height()-108,
        queryParams : function (params) {  // 自定义请求参数
            params.name = vm.keyword;           // 搜索参数
            params.status = vm.status;
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
            width : "150px"
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
            field : "status",
            title : "状态",
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
        }]
    });
}

var vm = new Vue({
    el : '#list',
    data : {
        status : -1,
        keyword: null,  // 搜索用关键字
        isShow : false,
        params: {
            status: '1',
            ids:[]
        }
    },
    methods : {
        load : function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
        },
        toRelation : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                dialogOpen({
                    title : "节目单-节目管理",
                    url :"/manage/program/list_check_program.html",
                    width : "1200px",
                    height : "600px",
                    success : function(iframeId) {
                        top.frames[iframeId].vm.id = ck[0].id;  // 把节目单id传过去
                        top.frames[iframeId].vm.load();
                    },
                    yes : function (iframeId) {
                        top.layer.closeAll();
                        dialogMsg("操作成功", "success");
                    }
                })
            }
        },
        checkSelect : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedArray(ck)) {
                this.isShow = true;
            }
        },
        submit : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            $.each(ck, function (index, item) {
                vm.params.ids[index] = item.id;
            });
            $.SaveForm({
                url: "/manage/programTask/check?_" + $.now(),
                param: vm.params,
                close: false,
                success: function (data) {
                    $('#myModal').modal('hide');
                    vm.load();
                }
            });
        }
    }
});