package net.chenlin.dp.common.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * json工具类(fastjson)
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 *
 * @date 2017年8月11日 下午3:35:28
 */
public class JSONUtils {

	/**
	 * Bean对象转JSON
	 * @param object
	 * @param dataFormatString
	 * @return
	 */
	public static String beanToJson(Object object,String dataFormatString){
        if(object != null){
            if(StringUtils.isEmpty(dataFormatString)){
                return JSONObject.toJSONString(object);
            }
            return JSON.toJSONStringWithDateFormat(object,dataFormatString);
        }else{
            return null;
        }
    }
	
	/**
	 * Bean对象转JSON
	 * @param object
	 * @return
	 */
	public static String beanToJson(Object object){
        if(object != null){
            return JSON.toJSONString(object);
        }else{
            return null;
        }
    }
	
	/**
	 * String转JSON字符串
	 * @param key
	 * @param value
	 * @return
	 */
	public static String stringToJsonByFastjson(String key,String value){
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
            return null;
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put(key, value);
        return beanToJson(map,null);
    }
	
	/**
	 * 将json字符串转换成对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object jsonToBean(String json,Object clazz){
        if(StringUtils.isEmpty(json) || clazz == null){
            return null;
        }
        return JSON.parseObject(json, clazz.getClass());
    }
	
	/**
	 * json字符串转map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) {
		if(StringUtils.isEmpty(json)) {
			return null;
		}
		return JSON.parseObject(json, Map.class);
	}

	/**
	 * json字符串保存到文件
	 * @param jsonStr
	 * @param jsonName
	 * @param savePath
	 * @return
	 * @throws IOException
	 */
	public static Boolean jsonStrToFile(String jsonStr, String jsonName, String savePath) throws IOException {
		// 标记文件生成是否成功
		boolean flag = true;
		Writer write = null;
		String fullPath = savePath + jsonName + ".json";
		try {
			File file = new File(fullPath);
			if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
				file.getParentFile().mkdirs();
			}
			if (file.exists()) { // 如果已存在,删除旧文件
				file.delete();
			}
			write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			write.write(jsonStr);

		} catch (Exception e) {
			flag = false;
		} finally {
			write.flush();
			write.close();
		}

		return flag;

	}
	
}
