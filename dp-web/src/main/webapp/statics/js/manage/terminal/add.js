vm = new Vue({
    el : "#add",
    data: {
        terminal : {
            classifyName: null,
            networkStatus: 0,
            syncStatus: -1,
            rootNode: 0,
        }
    },
    methods : {
        acceptClick: function () {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: "/manage/terminal/save?_" + $.now(),
                param: vm.terminal,
                success: function (data) {
                    $.currentIframe().vm.load(); // 刷新列表
                }
            });
        },
        classifyTree : function () {
            dialogOpen({
                id: 'classify',
                title: '选择分组',
                url: '/manage/terminal/tree.html',
                scroll : true,
                width: "300px",
                height: "450px",
                yes : function (iframeId) {
                    // dialogMsg("完成", msgType.SUCCESS);
                    // var index = top.layer.getFrameIndex(window.name);
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        }
    }
});