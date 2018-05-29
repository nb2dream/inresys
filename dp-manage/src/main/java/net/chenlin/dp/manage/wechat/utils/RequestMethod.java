package net.chenlin.dp.manage.wechat.utils;

import com.sun.org.apache.xpath.internal.SourceTree;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2018/3/21 11:44
 */
public class RequestMethod {

    //post请求
    public static String sendPost(String param, String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            // out = new PrintWriter(conn.getOutputStream());
            out = new PrintWriter(new OutputStreamWriter(
                    conn.getOutputStream(), "utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //根据url下载文件，参数（文件网址，存文件的本地地址）
    public static Boolean downloadFile(String urlString, String filePath){
        // 构造URL
        URL url;
        try {
            url = new URL(urlString);
            // 打开连接
            URLConnection con;
            try {
                con = url.openConnection();
                // 输入流
                InputStream is = con.getInputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                OutputStream os = new FileOutputStream(filePath);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                os.close();
                is.close();
                return true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) {
        //获取数据的地址（微信提供）
        String access_token = "7_wALnPQ-RaJFN7yWkpdnJcUBDY121AHc0imSKE8Gzw1gcGnDs7L2GM27ww4bDEAiqnBwTOx0xoWbQJLH6jbXDD3W1damIpmvvpXnWTrRoD_8SfyhgNKBal9yC-aAGSFhAEALXK";
        String account = "xxxxx";
        String url = String.format("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s", access_token);
        String jsonStr = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\"：{\"scene\": {\"scene_id\": "+account+"}}}";
        String response = RequestMethod.sendPost(jsonStr, url);
        System.out.printf(response.toString());
    }

}
