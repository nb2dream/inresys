package net.chenlin.dp.manage.material.entity;

import net.chenlin.dp.common.entity.BaseEntity;

import java.util.Date;

public class ManaMaterialEntity extends BaseEntity{

    /**
     * 素材文字内容
     */
    private String content;

    /**
     * 素材名
     */
    private String materialName;

    /**
     * 素材保存路径
     */
    private String path;

    /**
     * 素材后缀
     */
    private String suffix;

    /**
     * 创建用户
     */
    private Long createUserId;

    /**
     * 素材分类id
     */
    private Long classifyId;

    /**
     * 素材类型 //1:图片，2:视频，3:音频，4:文字,5:网址
     */
    private Integer type;

    /**
     * 文件大小 单位 m
     */
    private String fileSize;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}