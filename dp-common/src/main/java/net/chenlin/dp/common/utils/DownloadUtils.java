package net.chenlin.dp.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下载工具类
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 *
 * @date 2017年8月25日 下午4:34:27
 */
public class DownloadUtils {

	private static Logger log = LoggerFactory.getLogger(DownloadUtils.class);

	/**
	 * 相对路径
	 * @param fileName
	 * @param filepath
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void download(String fileName, String filepath, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext().getRealPath("/") + filepath;
		try {
			File file = new File(ctxPath);
			if (!file.exists())
				log.warn("download agent is error ! messages --->> " + ctxPath + " is not exists !");
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			bis = new BufferedInputStream(new FileInputStream(ctxPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			log.warn("download agent is error ! messages --->> " + e.fillInStackTrace());
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	
	/**
	 * 绝对路径
	 * @param fileName
	 * @param filepath
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void downloadRealPath(String fileName, String filepath, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		try {
			File file = new File(filepath);
			if (!file.exists())
				log.warn("download agent is error ! messages --->> " + filepath + " is not exists !");
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			bis = new BufferedInputStream(new FileInputStream(filepath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			log.warn("download agent is error ! messages --->> " + e.fillInStackTrace());
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

}
