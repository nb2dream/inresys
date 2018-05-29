$(function () {
    getGrid();
});

function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url:'/manage/program/list?_' + $.now(),
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
            field : "name",
            title : "节目名",
            width : "220px"
        },{
            field : "createUserName",
            title : "创建用户",
            width : "80px"
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
        saveUrl : "/manage/program/save",
        updateUrl : "/manage/program/update",
        keyword: null  // 搜索用关键字
    },
    methods : {
        load : function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
        },
        save : function () {
            dialogOpen({
                title : "新增节目",
                url :"/manage/program/add.html",
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
                    url :"/manage/program/add.html",
                    width : "500px",
                    height : "200px",
                    success : function(iframeId) {
                        top.frames[iframeId].vm.program.id = ck[0].id;
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
                    url: "/manage/program/remove?_" + $.now(),
                    param: ids,
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        layout : function () {
            var ck = $("#dataGrid").bootstrapTable("getSelections");
            if (checkedRow(ck)) {
                window.open("/manage/program/layout/" + ck[0].id);
                // dialogOpen({
                //     title : "节目布局",
                //     url : "/manage/program/layout/" + ck[0].id,
                //     width : '1200px',
                //     height : '800px',
                //     scrollbar: false,
                //     success : function (iframeId) {
                //         top.frames[iframeId].vm.program.id = ck[0].id;
                //     },
                //     yes : function () {
                //         top.layer.closeAll();
                //         dialogClose();
                //     }
                // })
            }
        },
        material : function () {
            var ck = $("#dataGrid").bootstrapTable("getSelections");
            if (checkedRow(ck)) {
                window.open("/manage/program/material/" + ck[0].id);
            }
        },
        preview : function () {
            var ck = $("#dataGrid").bootstrapTable("getSelections");
            if (checkedRow(ck)) {
                window.open("/manage/program/toPreview/" + ck[0].id);
            }
        }
    }
});