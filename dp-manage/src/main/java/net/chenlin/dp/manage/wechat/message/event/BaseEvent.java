package net.chenlin.dp.manage.wechat.message.event;

/**
 * 事件基类
 *
 * 推送XML数据包示例：
     <xml>
         <ToUserName><![CDATA[toUser]]></ToUserName>
         <FromUserName><![CDATA[FromUser]]></FromUserName>
         <CreateTime>123456789</CreateTime>
         <MsgType><![CDATA[event]]></MsgType>
         <Event><![CDATA[subscribe]]></Event>
     </xml>

     参数说明：
     参数  描述
        ToUserName  开发者微信号
        FromUserName  发送方帐号（一个OpenID）
        CreateTime  消息创建时间 （整型）
        MsgType  消息类型，event
        Event  事件类型，subscribe(订阅)、unsubscribe(取消订阅)
 */
public class BaseEvent {

    // 开发者微信号
    private String ToUserName;
    // 发送方帐号（一个OpenID）
    private String FromUserName;
    // 消息创建时间 （整型）
    private long CreateTime;
    // 消息类型
    private String MsgType;
    // 事件类型
    private String Event;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

}
