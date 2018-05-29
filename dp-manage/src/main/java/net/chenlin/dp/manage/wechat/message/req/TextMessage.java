package net.chenlin.dp.manage.wechat.message.req;

/**
 * Created by Jacey on 2017/7/9.
 */
public class TextMessage extends BaseMessage{

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
