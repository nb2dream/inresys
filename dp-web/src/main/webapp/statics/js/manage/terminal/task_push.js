vm = new Vue({
    el : "#push",
    data: {
        programTaskList: [],
        taskVersion: "V@_2",
        params : {
            terminalIds: [],
            programTaskId:0,
            netType: 0    // 网络终端网络形式， 0 局域网  1 互联网
        }
    },
    methods : {
        acceptClick: function () {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: "/manage/terminal/tasks_push?_" + $.now(),
                param: vm.params,
                success: function (data) {
                    $.currentIframe().vm.load(); // 刷新列表
                }
            });
        },
        getProgramTaskList : function () {
            $.SetForm({
                url: "/manage/programTask/list_isPass_task",
                param: {},
                success: function(data) {
                    vm.programTaskList = data;
                }
            })
        }
    },
    created: function () {
        this.getProgramTaskList();
    }
});