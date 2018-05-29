package net.chenlin.dp.manage.api;

import junit.framework.TestCase;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.utils.AddressUtils;
import net.chenlin.dp.common.utils.NetworkUtil;
import net.chenlin.dp.manage.terminal.dao.ManaTerminalMapper;
import net.chenlin.dp.manage.terminal.manager.ManaTerminalManager;
import net.chenlin.dp.manage.terminal.manager.impl.ManaTerminalManagerImpl;
import net.chenlin.dp.manage.terminal.serviece.ManaTerminalService;
import net.chenlin.dp.manage.terminal.serviece.impl.ManaTerminalServiceImpl;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 17 on 2017/11/21.
 */
public class apiControllerTest extends TestCase {

    /**
     * 时间戳timestamp对应的签名signature生成
     * @throws Exception
     */
    public void testDownloadTask() throws Exception {
        Long timea = System.currentTimeMillis();
        System.out.println("timestamp>>>>>>>" + timea);
        String time_s = timea.toString();
        String token = "test";
        String[] arr = new String[]{time_s, token};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        String signature = getSha1(content.toString());
        System.out.println("signature >>>>>>>>>" + signature);
    }

    public static String getSha1(String str){
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void testWeatherInfo() throws Exception {
        String IP = null;
        AddressUtils addr = new AddressUtils();
        String address = addr.getAddresses(IP, "utf-8");


    }


}