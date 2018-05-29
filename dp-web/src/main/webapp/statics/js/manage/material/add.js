
var vm = new Vue({
    el: '#add',
    data: {
        material:{
            type:1, //1:图片，2:视频，3:音频，4:文字,5:网址
            classifyName: null
        },
        accept: {
            title: 'uploaderFile',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
        }
    },
    methods: {
        acceptClick: function () {
            if (!$('#form').Validform()) {
                return false;
            }
            var formData = new FormData($( "#uploadForm" )[0]);
            dialogLoading(true);
            window.setTimeout(function(){
                $.ajax({
                    url: '/manage/material/save?_' + $.now() ,
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success : function(data) {
                        if (data.code == '500') {
                            dialogAlert(data.msg, 'error');
                        } else if (data.code == '0') {
                            dialogMsg(data.msg, 'success');
                            $.currentIframe().vm.load(); // 刷新列表
                            dialogClose();
                        }
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown) {
                        dialogLoading(false);
                        dialogMsg(errorThrown, 'error');
                    },
                    beforeSend : function() {
                        dialogLoading(true);
                    },
                    complete : function() {
                        dialogLoading(false);
                    }
                });
            }, 500);
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
        setUpLoadType: function () {
            if (this.material.type == 1) {
                this.accept.title = 'Images',
                this.accept.extensions = 'gif,jpg,jpeg,bmp,png',
                this.accept.mimeTypes = 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
            } else if (this.material.type == 2) {
                this.accept.title = 'Video',
                this.accept.extensions = 'mp4,webm,ogg',
                this.accept.mimeTypes = 'video/mp4,video/webm,video/ogg'
            } else if (this.material.type == 3) {
                this.accept.title = 'Audio',
                this.accept.extensions = 'mp3,ogg,wma',
                this.accept.mimeTypes = 'audio/mp3,audio/ogg,audio/wma'
            } else if (this.material.type == 4) {
            } else if (this.material.type == 5) {

            }
            // uploadInit();
        },
        inputText: function () {
            
        }

    }
});

// function uploadInit() {
//     uploader = WebUploader.create({
//
//         // 不压缩image
//         resize: false,
//
//         // swf文件路径
//         swf: '/statics/plugins/webuploader/Uploader.swf',
//
//         // 文件接收服务端。
//         server: '/manage/material/save',
//
//         // 选择文件的按钮。可选。
//         // 内部根据当前运行是创建，可能是input元素，也可能是flash.
//         pick: {
//             id:"#picker",
//             innerHTML:"请选择文件",
//             multiple: true
//         },
//
//         formData: {
//             id : 1
//         }
//
//         // accept: vm.accept
//     });
//     return uploader;
// };

// $(function(){
//     var $ = jQuery,
//         $list = $('#thelist'),
//         $btn = $('#ctlBtn'),
//         state = 'pending',
//         uploader;
//
//     uploader = uploadInit();
//
//     // 当有文件添加进来的时候
//     uploader.on( 'fileQueued', function( file ) {
//         var arr = new Array();
//         var value = $("input[name='list']").val()  // 获取文本框的值
//         if (value) {
//             arr.push(value);
//         }
//         arr.push(file.name);
//         var str = arr.join("，");             // 拼接内容
//         $("input[name='list']").val(str);   // 赋值回文本框
//         $("#thelist").show();               // 文本框内容显示
//     });
//
//     // 开始上传
//     uploader.on( 'startUpload', function( file ) {
//         dialogLoading(true);
//     });
//     // 上传成功
//     uploader.on( 'uploadSuccess', function( file ) {
//         dialogLoading(false);
//         dialogMsg("上传成功!", common.SUCCESS);
//     });
//     // 上传错误
//     uploader.on( 'uploadError', function( file ) {
//         dialogLoading(false);
//         dialogMsg("发生错误!", common.ERROR);
//     });
//     // 上传完成
//     uploader.on( 'uploadComplete', function( file ) {
//         // dialogLoading(false);
//     });
//
//     // // 全部动作
//     // uploader.on( 'all', function( type ) {
//     //     if ( type === 'startUpload' ) {
//     //         state = 'uploading';
//     //     } else if ( type === 'stopUpload' ) {
//     //         state = 'paused';
//     //     } else if ( type === 'uploadFinished' ) {
//     //         state = 'done';
//     //     }
//     //
//     //     if ( state === 'uploading' ) {
//     //         $btn.text('暂停上传');
//     //     } else {
//     //         $btn.text('开始上传');
//     //     }
//     // });
//
//     $btn.on( 'click', function() {
//         uploader.upload();
//     });
// });