vm = new Vue({
    el : "#add",
    data: {
        ratios: '1920*1080', // 默认分辨率
        isEdit: false,
        program : {
            id : 0,
            width: 1920,
            height: 1080,
            ratio: '1920*1080'
        }
    },
    methods : {
        acceptClick: function (url) {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: url + "?_" + $.now(),
                param: vm.program,
                success: function (data) {
                    $.currentIframe().vm.load(); // 刷新列表
                }
            });
        },
        strSplit : function () {
            if (isNullOrEmpty(this.ratios)) {
                return;
            }
            // this.program.width = this.ratios.split("*")[0];
            // this.program.height = this.ratios.split("*")[1];
            var width = this.ratios.split("*")[0];
            var height = this.ratios.split("*")[1];
            if (width == "3840" || width == "1920") {
                this.program.width = 1920
            } else if (width == "2160" || width == "1080") {
                this.program.width = 1080
            }
            if (height == "2160" || height == "1080") {
                this.program.height = 1080
            } else if (height == "3840" || height == "1920") {
                this.program.height = 1920
            }
            this.program.ratio = this.ratios;
            common.showMessage(JSON.stringify(this.program))
        },
        strJoin : function () {
            this.ratios = this.program.width + "*" + this.program.height;
        },
        setForm : function () {
            $.SetForm({
                url: "/manage/program/info",
                param: vm.program.id,
                success: function(data) {
                    vm.isEdit = true;
                    vm.program = data;
                    vm.strJoin()
                }
            })
        }
    }
});