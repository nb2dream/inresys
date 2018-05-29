package net.chenlin.dp.manage.wechat.controller;

import net.chenlin.dp.manage.wechat.entity.WechatPostMsgEntity;
import net.chenlin.dp.manage.wechat.service.CoreService;
import net.chenlin.dp.manage.wechat.utils.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 微信处理controller
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2018/3/21 9:15
 */
@RestController
@RequestMapping("/wx.do")
public class WechatController {

    private static Logger log = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private CoreService coreService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respXml = CoreService.processRequest(request);

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }


    @RequestMapping(method = RequestMethod.GET)
    public void doPost(WechatPostMsgEntity entity, HttpServletResponse response) throws IOException {
        log.debug("开始授权验证");
        String signature = entity.getSignature();
        String timestamp = entity.getTimestamp();
        String nonce = entity.getNonce();
        String echostr = entity.getEchostr(); // 随机字符串

        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr); // 校验通过，原样返回echostr参数内容
        }
    }
}
