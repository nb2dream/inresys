package net.chenlin.dp.manage.material.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片查看，视频查看页面跳转
 */
@RequestMapping("/view")
@Controller
public class DisplayController {

    @Value("${material.savePath}")
    private String basePath;

    /**
     * 用base64查看图片
     * @param filePath
     * @param model
     * @return
     */
    @RequestMapping("/image")
    public String image(String filePath, Model model) {
        try {
            InputStream inputStream = null;
            byte[] data = null;
            try {
                inputStream = new FileInputStream(basePath + "/" + filePath);
                data = new byte[inputStream.available()];
                inputStream.read(data);
                inputStream.close();
            } catch (IOException e) {
                //TODO 图片加载失败处理
            }
            // 加密
            BASE64Encoder encoder = new BASE64Encoder();
            model.addAttribute("base64img", encoder.encode(data));
        } catch (Exception e) {
            //TODO 异常处理未完成
            e.printStackTrace();
        }

        return "/manage/material/display/image.html";
    }

    @RequestMapping("/video")
    public String video(String filePath, Model model) {
        String suffix = filePath.substring(filePath.lastIndexOf(".")+1);

        model.addAttribute("filePath", filePath);
        model.addAttribute("suffix", suffix);
        return "/manage/material/display/video.html";
    }

}
