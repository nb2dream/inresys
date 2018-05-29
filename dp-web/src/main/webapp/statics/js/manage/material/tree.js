var setting = {
    async : {
        enable: true,                                   // 开启异步加载模式
        type: "get",	                                // 请求方式get
        url: "/manage/materialClassify/select",          // url功能：根据父级code查询子节点，子区域列表
        autoParam: ["classifyId=parentId"],                       // 需要提交的参数列表
        dataFilter: function (treeId, parentNode, responseData) {     // 异步请求数据的预处理
            return responseData.rows;
        }
    },
    data : {
        simpleData : {
            enable : true,  		// 是否采用简单数据模式
            idKey : "classifyId",		// 节点数据中保存唯一标识的属性名称
            pIdKey : "parentId",  // 节点数据中保存其父节点唯一标识的属性名称
            rootPId : "0"			// 用于修正根节点父节点数据
        },
        key : {
            url : "nourl" // zTree 节点数据保存节点链接的目标 URL 的属性名称。
            // 特殊用途：当后台数据只能生成 url 属性，又不想实现点击节点跳转的功能时，
            // 可以直接修改此属性为其他不存在的属性名称
        }
    }
};

var ztree;

var vm = new Vue({
    el : "#app",
    data : {
        parentId: 0
    },
    methods : {
        getClassifyTree : function (parentId) {
            $.get("/manage/materialClassify/select", {parentId: parentId}, function (r) {
                if (r.code == '0') {
                    ztree = $.fn.zTree.init($("#classifyTree"), setting, r.rows);
                } else {
                    dialogMsg(r.msg, 'error');
                }
            })
        },
        acceptClick: function () {
            var node = ztree.getSelectedNodes();
            top.layerForm.vm.material.classifyId = node[0].classifyId;
            top.layerForm.vm.material.classifyName = node[0].name;
            dialogClose(); // 关闭当前iframe
        }
    },
    created : function() {
        this.getClassifyTree(this.parentId);
    }
});