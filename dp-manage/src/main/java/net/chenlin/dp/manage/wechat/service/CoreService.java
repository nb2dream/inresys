package net.chenlin.dp.manage.wechat.service;


import net.chenlin.dp.manage.wechat.message.req.TextMessage;
import net.chenlin.dp.manage.wechat.utils.MessageUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 核心服务类
 */
@Service
public class CoreService {

    /**
     * 处理微信发来的请求
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {

        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";

        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");  // 该用户名
            String msgType = map.get("MsgType");        // 发送过来的消息类型
            String content = map.get("Content");        // 消息主题

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setFromUserName(toUserName);       // 消息来自哪个用户
            textMessage.setToUserName(fromUserName);       // 要发送的用户
            textMessage.setCreateTime(new Date().getTime());
//            textMessage.setContent("你发送的消息是：" + content);
            textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                // str.contains("..")       判断某个字符串是否包含指定内容
                // str.startsWith("...")    判断某个字符串以指定内容开头
                // str.endsWith("...")     判断某个字符串以指定内容结尾
                if (content.endsWith("天气")) {
                    respContent = "今天天气不错哦";
                } else {
                    respContent = content;
                }
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = map.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！" + map.get("FromUserName") + "\n";
                    System.out.printf("关注 openid:" + map.get("FromUserName") + "\n");
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                    System.out.printf("取消 openid:" + map.get("FromUserName") + "\n");
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }

            // 设置文本消息的内容
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.textMessageToXML(textMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

}
