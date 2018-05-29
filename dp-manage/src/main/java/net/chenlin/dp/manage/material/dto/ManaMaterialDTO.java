package net.chenlin.dp.manage.material.dto;

import net.chenlin.dp.manage.material.entity.ManaMaterialEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 素材dto类
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 10:57
 */
public class ManaMaterialDTO extends ManaMaterialEntity {

    /** 上传的文件 */
    private MultipartFile[] files;

    /** 分类名 */
    private String classifyName;

    /** 创建的用户名 */
    private String createUserName;

    /** 字体大小 */
    private Integer fontSize;

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
