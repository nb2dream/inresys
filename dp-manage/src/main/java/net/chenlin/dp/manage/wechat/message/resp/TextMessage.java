package net.chenlin.dp.manage.wechat.message.resp;

/**
 * Created by Jacey on 2017/7/10.
 */
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
