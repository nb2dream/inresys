$(function () {
    getGrid();
});


function getGrid() {
    $("#dataGrid").bootstrapTableEx({
        url: "/manage/program/listProgramMaterial?_" + $.now(),
        height: $(window).height()-125,
        queryParams : function (params) {  // 自定义请求参数
            params.itemId = vm.params.itemId;
            params.programId = vm.params.programId;
            params.type = vm.type;
            return params;
        },
        columns: [{
            checkbox: true
        },{
            field : "sortNum",
            title : "播放顺序",
            width : "50px"
        },{
            field : "materialName",
            title : "素材名",
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
            field : "createUserName",
            title : "操作",
            width : "150px",
            formatter : function(value, row, index) {
                return '<button class="btn btn-default btn-xs" onclick="vm.moveR('+ row.id +','+ (row.sortNum-1) +')"><i class="fa fa-arrow-circle-up"></i>&nbsp;上移</button>&nbsp;&nbsp;' +
                    '<button class="btn btn-default btn-xs" onclick="vm.moveR('+ row.id +','+ (row.sortNum+1) +')" ><i class="fa fa-arrow-circle-down"></i>&nbsp;下移</button>';
            }
        }]
    });
}



var vm  = new Vue({
    el : '#list',
    data : {
        params: {
            type: 0,
            itemId: null,
            programId: null,
            ids:[]
        }
    },
    methods : {
        load : function () {
            $("#dataGrid").bootstrapTable("refresh"); // 刷新表格数据
        },
        save : function () {
            dialogOpen({
                title : "素材选择",
                id: "select",
                url : "/manage/program/list_select_material.html",
                width : "1250px",
                height : "600px",
                success : function(iframeId) {
                    top.frames[iframeId].vm.type = vm.params.type;
                    top.frames[iframeId].vm.itemId = vm.params.itemId;
                    top.frames[iframeId].vm.programId = vm.params.programId;
                    top.frames[iframeId].vm.load();
                },
                yes : function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            })
        },
        remove : function () {
            var ck = $('#dataGrid').bootstrapTable('getSelections');
            var ids = [];
            if (checkedArray(ck)) {
                $.each(ck, function (index, item) {
                    vm.params.ids[index] = item.id;
                });
                $.RemoveForm({
                    url: "/manage/program/removeProgramMaterial?_" + $.now(),
                    param: {},
                    param: vm.params,
                    success: function (data) {
                        vm.load();
                    }
                });
            }
        },
        moveR : function (id, sortNum) {
            this.params.id = id;
            this.params.sortNum = sortNum;
            $.ajax({
                url : "/manage/program/moveProgramMaterial",
                data : JSON.stringify(vm.params),
                type : "post",
                dataType : "json",
                contentType : 'application/json',
                success : function(data) {
                    vm.load();
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    dialogMsg(errorThrown, 'error');
                },
                beforeSend : function() {
                    dialogLoading(true);
                },
                complete : function() {
                    dialogLoading(false);
                }
            });
        },
    }
});