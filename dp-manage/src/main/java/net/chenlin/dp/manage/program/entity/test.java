package net.chenlin.dp.manage.program.entity;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/15 14:07
 */
public class test {

    // 根节点json对象
    protected final JSONObject rootJson = new JSONObject();

    // 根节点
    protected static final HashSet<String> ROOT_KEYS = new HashSet<String>(Arrays.asList(new String[]{"programList"}));

    // 节目项目
    protected static final HashSet<String> PROGRAM_ITEM_KEYS = new HashSet<String>(Arrays.asList(new String[]{"playNum", "matItem", "timing", "layout"}));

    // 节目的定时任务
    protected static final HashSet<String> TIMING_KEYS = new HashSet<String>(Arrays.asList(new String[]{"beginDate", "beginTime", "endDate", "endTime"}));

    // 节目布局
    protected static final HashSet<String> LAYOUT_KEYS = new HashSet<String>(Arrays.asList(new String[]{"width", "height", "items"}));

    // 设置根节点
    public void setPredefinedKeyValue(String key, Object value) throws JSONException {
        if (ROOT_KEYS.contains(key)) {
            rootJson.put(key, value);
        } else if (PROGRAM_ITEM_KEYS.contains(key)) {

        }
    }

    public static void main(String[] args) throws JSONException {
        List<Map<String, Object>> programList = new ArrayList<>();
        Map<String, Object> programItemMap = new HashMap<>();

        Map<String, Object> timingMap = new HashMap<>();
        Map<String, Object> layoutMap = new HashMap<>();

        List<Map<String, Object>> matItemList = new ArrayList<>();
        Map<String, Object> matItemMap = new HashMap<>();
/**================================<<<matItem>>>==================================================*/
        matItemMap.put("sortNum", "1");
        matItemMap.put("itemId", "image_1_1502961361996");
        matItemMap.put("type", "1");
        matItemMap.put("suffix", "jpg");
        matItemMap.put("path", "20170817/e98affef-8bcd-43bf-80b4-8905f58f00c5.jpg");

        matItemList.add(matItemMap);
        matItemList.add(matItemMap);

/**=================================<<<timing>>>===================================================*/
        timingMap.put("beginDate", "2017-11-12");
        timingMap.put("beginTime", "02:00:00");
        timingMap.put("endDate", "2017-11-12");
        timingMap.put("endTime", "02:00:00");

/**=================================<<<layout>>>===================================================*/
        layoutMap.put("height", "1080");
        layoutMap.put("width", "1920");
        layoutMap.put("items", "aaaaa");

/**=================================<<programItem当个节目项>>============================*/
        programItemMap.put("playNum", "1");
        programItemMap.put("matItem", matItemList);
        programItemMap.put("timing", timingMap);
        programItemMap.put("layout", layoutMap);

        programList.add(programItemMap);

        JSONObject rootJson = new JSONObject();
        rootJson.put("programList", programList);
        System.out.println(rootJson.toString());
    }

}
