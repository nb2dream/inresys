package net.chenlin.dp.manage.api.utils;


import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {
    public static Map<String, String> getWeatherInfo(String cityCode) throws Exception {
        String apiUrl = "http://www.weather.com.cn/data/cityinfo/" + cityCode + ".html";
        URL url = new URL(apiUrl);
        URLConnection open = url.openConnection();
        InputStream input = open.getInputStream();
        //这里转换为String
        String result = IOUtils.toString(input, "utf-8");
        //输出
        JSONObject jsonData = JSONObject.fromObject(result);
        JSONObject info = jsonData.getJSONObject("weatherinfo");
        Map<String, String> map = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        map.put("week", getWeek(cal.get(Calendar.DAY_OF_WEEK)));
        map.put("city", (String) info.get("city"));
        map.put("weather", (String) info.get("weather"));
        map.put("min_temp", (String) info.get("temp1"));
        map.put("max_temp", (String) info.get("temp2"));
        return map;
    }

    private static String getWeek(int iw) {
        String weekStr = "";
        switch (iw) {
            case 1:
                weekStr = "星期天";
                break;
            case 2:
                weekStr = "星期一";
                break;
            case 3:
                weekStr = "星期二";
                break;
            case 4:
                weekStr = "星期三";
                break;
            case 5:
                weekStr = "星期四";
                break;
            case 6:
                weekStr = "星期五";
                break;
            case 7:
                weekStr = "星期六";
                break;
            default:
                break;
        }
        return weekStr;
    }
}
