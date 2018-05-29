vm = new Vue({
    el : "#add",
    data: {
        dateRange: "",
        timeRange: "",
        params : {
            id: 0,     // 节目关系id
            begin_date: "",
            begin_time: "",
            end_date: "",
            end_time: ""
        }
    },
    methods : {
        acceptClick: function (url) {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: url + "?_" + $.now(),
                param: vm.params,
                success: function (data) {
                    top.frames["layerForm"].vm.load(); // 刷新列表
                }
            });
        },
        setForm : function () {
            $.SetForm({
                url: "/manage/programTask/info",
                param: vm.programTask.id,
                success: function(data) {
                    vm.programTask = data;
                }
            })
        },
        strJoin: function () {
            if (vm.params.begin_date != "" && vm.params.begin_date != null) {
                vm.dateRange = vm.params.begin_date + " ~ " + vm.params.end_date;
            }
            if (vm.params.begin_time != "" && vm.params.begin_time != null) {
                vm.timeRange = vm.params.begin_time + " ~ " + vm.params.end_time;
            }
        }
    },
    created: function () {
        datetimeRender({
            elem: "#date",
            min: 0,
            range: "~",
            done: function (value, date, endDate) {
                var strArry = value.split("~");
                if (strArry.length > 1 && strArry[0].trim() != null) {
                    vm.params.begin_date = strArry[0].trim()
                    vm.params.end_date = strArry[1].trim();
                } else {
                    vm.params.begin_date = "";
                    vm.params.end_date = "";
                }
            }
        });
        datetimeRender({
            elem: "#time",
            type: "time",
            range: "~",
            done: function (value, date, endDate) {
                var strArry = value.split("~");
                if (strArry.length > 1) {
                    vm.params.begin_time = strArry[0].trim();
                    vm.params.end_time = strArry[1].trim();
                } else {
                    vm.params.begin_time = "";
                    vm.params.end_time = "";
                }
            }
        });
    }
});