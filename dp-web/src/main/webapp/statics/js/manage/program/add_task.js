vm = new Vue({
    el : "#add",
    data: {
        ratios : '1920*1080', // 默认分辨率
        isEdit : false,
        programTask : {
            id : 0,
            playModel : 1,
            width: 1920,
            height: 1080,
        }
    },
    methods : {
        acceptClick: function (url) {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: url + "?_" + $.now(),
                param: vm.programTask,
                success: function (data) {
                    $.currentIframe().vm.load(); // 刷新列表
                }
            });
        },
        setForm : function () {
            vm.isEdit = true;
            $.SetForm({
                url: "/manage/programTask/info",
                param: vm.programTask.id,
                success: function(data) {
                    vm.programTask = data;
                }
            })
        },
        strSplit : function () {
            if (isNullOrEmpty(this.ratios)) {
                return;
            }
            this.programTask.width = this.ratios.split("*")[0];
            this.programTask.height = this.ratios.split("*")[1];

            // var width = this.ratios.split("*")[0];
            // var height = this.ratios.split("*")[1];
            // if (width == "3840" || width == "1920") {
            //     this.programTask.width = 1920
            // } else if (width == "2160" || width == "1080") {
            //     this.programTask.width = 1080
            // }
            // if (height == "2160" || height == "1080") {
            //     this.programTask.height = 1080
            // } else if (height == "3840" || height == "1920") {
            //     this.programTask.height = 1920
            // }
            // this.programTask.ratios = this.ratios;
            // common.showMessage(".......")
        },
        strJoin : function () {
            this.ratios = this.programTask.width + "*" + this.programTask.height;
        }
    }
});