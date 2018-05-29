$(function () {
    getGrid();
});

function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url: '/manage/programTask/list_relation?_' + $.now(),
        height: $(window).height() - 108,
        queryParams: function (params) {       // 自定义请求参数
            params.name = vm.keyword;           // 搜索参数
            params.id = vm.id;
            return params;
        },
        columns: [{
            checkbox: true
        }, {
            field: "playerNum",
            title: "播放序号",
            width: "50px"
        }, {
            field: "programName",
            title: "节目名",
            width: "150px"
        }
            // ,{
            // field : "ratio",
            // title : "分辨率",
            // width : "100px",
            // formatter : function(value, row, index) {
            //     return value + "*" + row.height;
            // }
            // }
            , {
                field: "beginDate",
                title: "播放日期",
                width: "220px",
                formatter: function (value, row, index) {
                    if (value == null) {
                        var date = new Date();
                        var year = date.getFullYear();
                        var month = date.getMonth() + 1;
                        var nextMonth = month + 1;
                        if (month >= 1 && month <= 9) {
                            month = '0' + month;
                        }
                        if(nextMonth >= 1 && nextMonth <= 9) {
                            nextMonth = '0' + nextMonth;
                        }
                        var day = date.getDate();
                        if (day >= 1 && day <= 9) {
                            day = '0' + day;
                        }
                        var startDate = year + '-' + month + '-' + day;
                        var endDate = year + '-' + nextMonth + '-' + day;
                        return startDate + " ~ " + endDate;
                    }
                    return value + " ~ " + row.endDate;
                }
            }, {
                field: "beginTime",
                title: "播放时间",
                width: "150px",
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "00:00:00" + " ~ " + "00:00:00";
                    }
                    return value + " ~ " + row.endTime;
                }
            }
            // ,{
            //     field : "week",
            //     title : "星期",
            //     width : "100px"
            // }
            , {
                field: "description",
                title: "描述"
            }, {
                field: "description",
                title: "操作",
                width: "140px",
                formatter: function (value, row, index) {
                    return '<button class="btn btn-default btn-xs" onclick="vm.moveR(' + row.id + ',' + (row.taskId) + ',' + (row.playerNum - 1) + ')"><i class="fa fa-arrow-circle-up"></i>&nbsp;上移</button>&nbsp;&nbsp;' +
                        '<button class="btn btn-default btn-xs" onclick="vm.moveR(' + row.id + ',' + (row.taskId) + ',' + (row.playerNum + 1) + ')" ><i class="fa fa-arrow-circle-down"></i>&nbsp;下移</button>';
                }
            }]
    });
}

var vm = new Vue({
    el: '#list',
    data: {
        id: 0,
        width: 1920,
        height: 1080,
        params: {}
    },
    methods: {
        load: function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
        },
        acceptClick: function () {
            var ck = $('#dataGrid').bootstrapTable('getData');
            var isOk = true;
            if (ck == undefined || ck == "" || ck == 'null' || ck == 'undefined') {
                common.showMessage("-.-")
            } else {
                $.each(ck, function (index, item) {
                    /*if (item.beginDate == null || item.beginDate == "" || item.beginTime == null || item.beginTime == "") {
                        dialogMsg('请设置节目播放日期时间', common.msgType.ERROR);
                        isOk = false;
                        return;
                    }*/
                })
            }
            return isOk;
        },
        remove: function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            var ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function (index, item) {
                    ids[index] = item.id;
                });
                vm.params.taskId = vm.id;
                vm.params.ids = ids;
                $.RemoveForm({
                    url: "/manage/programTask/remove_relation?_" + $.now(),
                    param: {},
                    param: vm.params,
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        moveR: function (id, taskId, playerNum) {
            this.params.id = id;
            this.params.taskId = taskId;
            this.params.playerNum = playerNum;
            $.ajax({
                url: "/manage/programTask/moveRelation",
                data: JSON.stringify(vm.params),
                type: "post",
                dataType: "json",
                contentType: 'application/json',
                success: function (data) {
                    vm.load();
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    dialogMsg(errorThrown, 'error');
                },
                beforeSend: function () {
                    dialogLoading(true);
                },
                complete: function () {
                    dialogLoading(false);
                }
            });
        },
        addProgram: function () {
            dialogOpen({
                title: "新增节目",
                id: "add",
                url: "/manage/program/list_select_program.html",
                width: "800px",
                height: "500px",
                success: function (iframeId) {
                    top.frames[iframeId].vm.id = vm.id;  // 把节目单id传过去
                    top.frames[iframeId].vm.width = vm.width;  // 把节目单id传过去
                    top.frames[iframeId].vm.height = vm.height;  // 把节目单id传过去
                    top.frames[iframeId].vm.load();
                },
                yes: function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        timing: function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            if (checkedRow(ck)) {
                dialogOpen({
                    title: "定时任务",
                    id: "add",
                    url: "/manage/program/program_timing.html",
                    width: "670px",
                    height: "420px",
                    success: function (iframeId) {
                        top.frames[iframeId].vm.params.id = ck[0].id;
                        top.frames[iframeId].vm.params.begin_date = ck[0].beginDate;
                        top.frames[iframeId].vm.params.begin_time = ck[0].beginTime;
                        top.frames[iframeId].vm.params.end_date = ck[0].endDate;
                        top.frames[iframeId].vm.params.end_time = ck[0].endTime;
                        top.frames[iframeId].vm.strJoin();
                    },
                    yes: function (iframeId) {
                        var url = "/manage/programTask/update_relation";
                        top.frames[iframeId].vm.acceptClick(url);
                    }
                });
            }
        },
        preview: function () {
            var ck = $("#dataGrid").bootstrapTable("getSelections");
            if (checkedRow(ck)) {
                common.showMessage(ck[0]);
                window.open("/manage/program/toPreview/" + ck[0].programId);
            }
        }

    }
});