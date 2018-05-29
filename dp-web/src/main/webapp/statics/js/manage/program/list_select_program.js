$(function () {
    getGrid();
});

function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url:'/manage/program/listSelectProgram?_' + $.now(),
        height: $(window).height()-50,
        queryParams : function (params) {       // 自定义请求参数
            params.name = vm.keyword;           // 搜索参数
            params.taskId = vm.id;
            // params.width = vm.width;
            // params.height = vm.height;
            params.ratio = vm.width + "*" + vm.height;
            return params;
        },
        columns: [{
            checkbox: true
        },{
            field : "id",
            title : "编号",
            width : "50px"
        },{
            field : "name",
            title : "节目名",
            width : "200px"
        },{
            field : "ratio",
            title : "分辨率",
            width : "100px",
            // formatter : function(value, row, index) {
            //     return value + "*" + row.height;
            // }
        },{
            field : "gmtCreate",
            title : "创建时间",
            width : "200px"
        },{
            field : "description",
            title : "描述"
        }]
    });
}

var vm = new Vue({
    el : '#list',
    data : {
        id : 0,
        keyword: null,
        width: 0,
        height: 0,
        params : {
            ids: []
        }
    },
    methods : {
        load : function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
        },
        acceptClick : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (ck == undefined || ck == "" || ck == 'null' || ck == 'undefined') {
                // 没选中任何东西保存，不做任何操作
                dialogClose();
                dialogMsg("操作成功", "success")
            } else {
                vm.params.taskId = vm.id;
                $.each(ck, function (index, item) {
                    console.info(item.id);
                    vm.params.ids[index] = item.id;
                });
                $.SaveForm({
                    url: "/manage/programTask/save_program_list?_" + $.now(),
                    param: vm.params,
                    success: function (data) {
                        top.frames["layerForm"].vm.load();
                    }
                });
            }
        }
    }
});