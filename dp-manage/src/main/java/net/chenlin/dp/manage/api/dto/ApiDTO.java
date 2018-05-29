package net.chenlin.dp.manage.api.dto;

import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;

/**
 * API参数绑定对象
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/22 16:40
 */
public class ApiDTO {

    private String timestamp;   //时间戳
    private String signature;   //加密生成的签名（时间戳与token数组排序，然后sha1加密生成）
    private String token;       //token

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
