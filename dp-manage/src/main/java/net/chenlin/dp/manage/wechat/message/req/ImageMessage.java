package net.chenlin.dp.manage.wechat.message.req;

/**
 * 请求消息之图片消息
 */
public class ImageMessage extends BaseMessage {


//    PicUrl	图片链接
//    MediaId	图片消息媒体id，可以调用多媒体文件下载接口拉取数据。

    // 图片链接
    private String PicUrl;
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

}
