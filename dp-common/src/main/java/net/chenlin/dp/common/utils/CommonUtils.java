package net.chenlin.dp.common.utils;

import net.chenlin.dp.common.constant.FileTypeConstant;
import net.chenlin.dp.common.constant.FileTypeEnum;
import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.entity.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * 通用工具类
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 *
 * @date 2017年8月12日 下午1:34:44
 */
public class CommonUtils {

	/**
	 * 对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 判断整数是否大于零
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isIntThanZero(int number) {
		if (number > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断List 是否为空或无数值
	 * @param list
	 * @return
	 */
	public static boolean isListNullOrZeroEmpty(List list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 新增，修改提示
	 * @param count
	 * @return
	 */
	public static R msg(int count) {
		if(isIntThanZero(count)){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}
		return R.error(MsgConstant.MSG_OPERATION_FAILED);
	}
	
	/**
	 * 查询详情提示
	 * @param data
	 * @return
	 */
	public static R msg(Object data) {
		if(isNullOrEmpty(data)){
			return R.error(MsgConstant.MSG_INIT_FORM);
		}
		return R.ok().put(SystemConstant.DATA_ROWS, data);
	}
	
	/**
	 * 返回数据
	 * @param data
	 * @return
	 */
	public static R msgNotCheckNull(Object data) {
		return R.ok().put(SystemConstant.DATA_ROWS, data);
	}
	
	/**
	 * 删除提示
	 * @param total
	 * @param count
	 * @return
	 */
	public static R msg(Object[] total, int count) {
		if(total.length == count){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}else{
			if(isIntThanZero(count)){
				return R.error(MsgConstant.removeFailed(total.length, count));
			}else{
				return R.error(MsgConstant.MSG_OPERATION_FAILED);
			}
		}
	}

	/**
	 * 判断是否是图片
	 * @param type
	 * @return
	 */
	public static Boolean isImage(String type) {
		return FileTypeEnum.ImageEnum.check(type);
	}

	/**
	 * 判断是否是视频
	 * @param type
	 * @return
	 */
	public static Boolean isVideo(String type) {
		return FileTypeEnum.VideoEnum.check(type);
	}

	/**'
	 * 判断是否是音频
	 * @param type
	 * @return
	 */
	public static Boolean isAudio(String type) {
		return FileTypeEnum.AudioEnum.check(type);
	}

	/**
	 * sha1 加密
	 * @param str
	 * @return
	 */
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

	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static Long timestamp() {
		return System.currentTimeMillis();
	}

	/**
	 * 对数组进行排序，并转成字符串返回
	 * @param arr
	 * @return
	 */
	public static String sortArrayToString(String[] arr) {
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		return content.toString();
	}

	/**
	 * 计算两个时间戳的差值
	 * @param timestampMax
	 * @param timestampSmall
	 * @return
	 */
	public static Long getBetweenMillisecondss(Long timestampMax, Long timestampSmall) {
		return timestampMax-timestampSmall;
	}
}
