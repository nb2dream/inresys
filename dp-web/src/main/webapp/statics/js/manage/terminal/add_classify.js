vm = new Vue({
    el: "#add",
    data: {
        getTreeUrl:'',
        classify : {
            parentId: 0,
            parentName: "主分组"
        }
    },
    methods : {
        setForm: function () {
            $.SetForm({
                url: "/manage/terminalClassify/info?_" + $.now(),
                param: vm.classify.classify_id,
                success: function (data) {
                    vm.classify = data;
                }
            });
        },
        acceptClick: function (url) {
            if (!$('#form').Validform()) {
                return false;
            }
            $.SaveForm({
                url: url + "?_" + $.now(),
                param: vm.classify,
                success: function (data) {
                    $.currentIframe().vm.getTree(0);
                }
            });
        },
        classifyTree : function () {
            dialogOpen({
                id: 'classify',
                title: '选择分组',
                url: vm.getTreeUrl,
                scroll : true,
                width: "300px",
                height: "450px",
                success : function(iframeId) {

                },
                yes : function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        }
    }
})