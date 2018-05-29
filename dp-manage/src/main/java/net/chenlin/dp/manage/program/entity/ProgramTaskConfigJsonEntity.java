package net.chenlin.dp.manage.program.entity;

import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * 节目单配置文件Json实体
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/15 9:37
 */
public class ProgramTaskConfigJsonEntity {

    // 根节点json对象
    protected static final JSONObject rootJson = new JSONObject();

    // 根节点
    protected static final HashSet<String> ROOT_KEYS = new HashSet<String>(Arrays.asList(new String[]{"programList"}));

    // 节目项目
    protected static final HashSet<String> PROGRAM_ITEM_KEYS = new HashSet<String>(Arrays.asList(new String[]{"playNum", "matItem", "timing", "layout"}));

    // 节目的定时任务
    protected static final HashSet<String> TIMING_KEYS = new HashSet<String>(Arrays.asList(new String[]{"beginDate", "beginTime", "endDate", "endTime"}));

    // 节目布局
    protected static final HashSet<String> LAYOUT_KEYS = new HashSet<String>(Arrays.asList(new String[]{"width", "height", "items"}));

    private Map<String, Object> programItemMap = new HashMap<>();
    private Map<String, Object> timingMap = new HashMap<>();
    private Map<String, Object> layoutMap = new HashMap<>();
    private List<Map<String, Object>> matItemList = new ArrayList<>();
    private Map<String, Object> matItemMap = new HashMap<>();
    // 设置根节点
    public static void setPredefinedKeyValue(String key, Object value) throws JSONException {
        if (ROOT_KEYS.contains(key)) {
            rootJson.append(key, value);
        }
    }


    // 设置节目list
    public void setProgramList(Map<String, Object> programItemMap) throws JSONException {
        setPredefinedKeyValue("programList", programItemMap);
    }

    // 节目项目
    public void programItemMapKeyVelue(String key, Object value) {
        programItemMap.put(key, value);
    }

    // 节目素材 List
    public void setMatItemList(Map<String, Object> matItem) {
        matItemList.add(matItemMap);
    }
    // 节目素材 List
    public void setMatItemList() {
        matItemList.add(matItemMap);
    }

    // 节目素材每个item项
    public void seTmatItemMapKeyVelue(String key, Object value) {
        matItemMap.put(key, value);
    }

    // Timing定时项
    public void setTimingMapKeyVelue(String key, Object value) {
        timingMap.put(key, value);
    }

    //Layout节目样式
    public void setLayoutMapKeyVelue(String key, Object value) {
        layoutMap.put(key, value);
    }

    public void getJSon() {

    }


    public static void main(String[] args) throws JSONException {
        ProgramTaskConfigJsonEntity e = new ProgramTaskConfigJsonEntity();
        e.seTmatItemMapKeyVelue("sortNum", "1");
        e.seTmatItemMapKeyVelue("itemId", "image_1_1502961361996");
        e.seTmatItemMapKeyVelue("type", "1");
        e.seTmatItemMapKeyVelue("suffix", "jpg");
        e.seTmatItemMapKeyVelue("path", "20170817/e98affef-8bcd-43bf-80b4-8905f58f00c5.jpg");
        e.setMatItemList();
        e.setMatItemList();
        e.setTimingMapKeyVelue("beginDate", "2017-11-12");
        e.setTimingMapKeyVelue("beginDate", "2017-11-12");
        e.setTimingMapKeyVelue("beginDate", "2017-11-12");
        e.setTimingMapKeyVelue("beginDate", "2017-11-12");

    }
}
