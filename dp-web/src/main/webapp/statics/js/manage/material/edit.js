vm = new Vue({
    el : "#edit",
    data: {
        id: 0,
        material : {
            materialName: null,
            type: 1,
            classifyName: null,
            classifyId: null
        }
    },
    methods : {
        acceptClick: function () {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: "/manage/material/update?_" + $.now(),
                param: vm.material,
                success: function (data) {
                    $.currentIframe().vm.load(); // 刷新列表
                }
            });
        },
        setForm : function () {
            $.SetForm({
                url: "/manage/material/info?_" + $.now(),
                param: vm.id,
                success: function(data) {
                    vm.material = data;
                    if (data && data.type == 4) {
                        common.showMessage(JSON.parse(vm.material.content));
                        vm.material.fontSize = JSON.parse(vm.material.content).fontSize;
                        vm.material.content = JSON.parse(vm.material.content).content;
                    }
                }
            })
        },
        classifyTree : function () {
            dialogOpen({
                id: 'classify',
                title: '选择分组',
                url: "/manage/material/tree.html",
                scroll : true,
                width: "300px",
                height: "450px",
                success : function(iframeId) {

                },
                yes : function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
    }
});