package net.chenlin.dp.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * MD5加密工具
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 *
 * @date 2017年8月8日 下午5:17:34
 */
public class MD5Utils {

	private static final String SALT = "1qazxsw2";
	
	private static final String ALGORITH_NAME = "md5";
	
	private static final int HASH_ITERATIONS = 2;

	/**
	 * 使用md5生成加密后的密码
	 * @param user
	 * @return
	 */
	public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}
	
	/**
	 * 使用md5生成加密后的密码
	 * @param user
	 * @return
	 */
	public static String encrypt(String username, String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	/**
	 * 获取文件的MD5值
	 * @param file
	 * @return
	 */
	public static String getFileMD5Code(File file) throws Exception {
		FileInputStream in = null;
		String result = "";
		byte buffer[] = new byte[8192];
		int len;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				md5.update(buffer, 0, len);
			}
			byte[] bytes = md5.digest();

			for (byte b : bytes) {
				String temp = Integer.toHexString(b & 0xff);
				if (temp.length() == 1) {
					temp = "0" + temp;
				}
				result += temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null!=in){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
