package net.chenlin.dp.common.utils;

import freemarker.template.utility.DateUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * 文件处理工具类
 */
public class FileUtils {

    static Logger log = Logger.getLogger(FileUtils.class);
    /**
     * 保存文件
     * @param path  保存的物理路径
     * @param suffix 文件后缀
     * @param file
     * @return
     */
    public static final String saveFile(String path, String suffix, MultipartFile file) throws Exception{

        String dateStr = DateUtils.format(new Date()).replace("-", "");
        File fileFolder = new File(path+"/"+dateStr);
        if (!fileFolder.exists()){
            fileFolder.mkdirs();
        }
        String newName = dateStr + "/" + UUID.randomUUID().toString();
        String fileName = newName + "." + suffix;

        file.transferTo(new File(path + "/" + fileName));

        return fileName;
    }

    /**
     * 文件删除
     * @param path
     * @param fileName
     */
    public static void deleteFile(String path,String fileName) {

        File file = new File(path+"/"+fileName);

        if(file.exists() && file.isFile()){
            file.delete();
        }

    }

    /**
     * 复制单个文件
     *
     * @param srcFileName
     *            待复制的文件名
     * @param destFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName,
                                   boolean overlay) {
        File srcFile = new File(srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            log.info("复制文件，原文件不存在："+srcFile);
            return false;
        } else if (!srcFile.isFile()) {
            log.info("复制文件，原文件不是正确文件，可能是目录："+srcFile);
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            log.error(e);
            return false;
        } catch (IOException e) {
            log.error(e);
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    public static boolean copyFileAndDir(String srcFileName, String destFileName,
                                         boolean overlay) {

        File destFile = new File(destFileName);
        File parentFile = new File(destFile.getParent());
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        return copyFile(srcFileName,destFileName,overlay);
    }

    /**
     *  删除文件夹和其下所有内容
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    public static File multipartToFile(MultipartFile multfile) throws Exception {
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
        //这个myfile是MultipartFile的
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        //手动创建临时文件
        if(file.length() < 1024){
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                    file.getName());
            multfile.transferTo(tmpFile);
            return tmpFile;
        }
        return file;
    }

}
