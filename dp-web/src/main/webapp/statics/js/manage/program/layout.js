vm = new Vue({
    el : ".jq22-container",
    data : {
        program : {
            id : 0
        }
    },
    methods : {
        save : function (items) {
            this.program.items = items;
            $.SaveForm({
                url: "/manage/program/update",
                param : vm.program,
                close : false,
                success: function (data) {
                }
            })
        }
    },
    created: function () {
        // 获取节目id
        var currentUrl = window.location.href;
        currentUrl = currentUrl.split("/")
        this.program.id = currentUrl[currentUrl.length-1]
    }
});

