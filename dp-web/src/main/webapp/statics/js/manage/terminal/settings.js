vm = new Vue({
    el: "#settings",
    data: {
        showDate: "",
        volume: 0,
        params: {
            timeOn: " ",
            timeOff: " ",
            setTimeOffAndOn: true,
            ids: []
        }
    },
    methods: {
        use: function () {
            vm.params.setTimeOffAndOn = true;
            if (this.params.timeOn == "" || this.params.timeOff == "") {
                dialogMsg("请设置好关开机时间", common.msgType.ERROR);
                return;
            }
            if (this.params.timeOn == this.params.timeOff) {
                dialogMsg("定时关开机时间不能相同", common.msgType.ERROR);
                return;
            }
            $.ConfirmForm({
                msg: "您确定要保存当前数据吗？",
                url: "/manage/terminal/timingONOFF",
                param: vm.params
            })
        },
        menuHide: function () {
            // 底部导航隐藏
            $.SaveForm({
                url: "/manage/terminal/menu_hide",
                param: this.params,
                close: false,
                success: function (data) {
                }
            })
        },
        menuShow: function () {
            // 底部导航显示
            $.SaveForm({
                url: "/manage/terminal/menu_show",
                param: this.params,
                close: false,
                success: function (data) {
                }
            })
        },
        backlightOn: function () {
            $.ConfirmForm({
                msg: "您确定要启用背光吗？",
                url: "/manage/terminal/backlightOnOFF",
                param: {
                    ids: vm.params.ids,
                    isOffAndOn: true
                }
            })
        },
        backlightOff: function () {
            $.ConfirmForm({
                msg: "您确定要关闭背光吗？",
                url: "/manage/terminal/backlightOnOFF",
                param: {
                    ids: vm.params.ids,
                    isOffAndOn: false
                }
            })
        },
        reset: function () {
            layer.confirm('<input id="aaa" type="text" class="form-control" placeholder="请输入操作密码">', {
                btn: ['确认', '取消'] //按钮
            }, function () {
                var operationPassword = $("#aaa").val();
                if (!operationPassword) {
                    var index = dialogAlert("请输入操作密码", 'error');
                    return;
                }
                $.ConfirmAjax({
                    msg: "确定重置系统？",
                    url: "/manage/terminal/reset",
                    contentType: "application/json",
                    param: {
                        ids: vm.params.ids,
                        password: operationPassword
                    },
                    success: function (data) {
                        layer.closeAll();
                    }
                });
            }, function () {
            });
        },
        cancel: function () {
            vm.params.setTimeOffAndOn = false;
            $.ConfirmForm({
                msg: "您确定要取消当前终端定时关开机吗？",
                url: "/manage/terminal/timingONOFF",
                param: vm.params,
                close: true
            })
        },
        setVolume: function () {
            common.showMessage(vm.volume);
            // 底部导航显示
            $.SaveForm({
                url: "/manage/terminal/set_volume",
                param: {
                    ids: vm.params.ids,
                    volume: vm.volume
                },
                close: false,
                success: function (data) {
                }
            })
        }
    },
    created: function () {
        datetimeRender({
            elem: "#timeOn",
            type: "datetime",
            range: "~",
            done: function (value, date, endDate) {
                common.showMessage(value);
                vm.showDate = value;
                vm.params.timeOn = value.split("~")[1];
                vm.params.timeOff = value.split("~")[0];
                common.showMessage(vm.params.timeOn);
            }
        });
        // datetimeRender({
        //     elem: "#timeOff",
        //     type: "time",
        //     range: false,
        //     done: function (value, date, endDate) {
        //         vm.params.timeOff = value;
        //     }
        // });
        // datetimeRender({
        //     elem: "#dateOn",
        //     range: true,
        //     done: function (value, date, endDate) {
        //         vm.params.dateOn = value;
        //     }
        // });
        // datetimeRender({
        //     elem: "#dateOff",
        //     range: false,
        //     done: function (value, date, endDate) {
        //         vm.params.dateOff = value;
        //     }
        // });
    }
});