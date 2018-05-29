package net.chenlin.dp.manage.program.entity;

import net.chenlin.dp.common.entity.BaseEntity;

import java.util.Date;

public class ManaProgramTaskEntity extends BaseEntity {

    /**
     * 节目单名
     */
    private String taskName;

    /**
     * 节目单描述
     */
    private String description;

    /**
     * 节目单播放形式  0 : 顺序播放  1 : 随机播发
     */
    private Integer playModel;

    /**
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 节目单宽
     */
    private String width;

    /**
     * 节目单高
     */
    private String height;

    /**
     * 节目审核状态  0 未审核  1 审核通过
     */
    private Integer status;

    /**
     * 节目版本
     */
    private Integer version;


    private String publishPath;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPlayModel() {
        return playModel;
    }

    public void setPlayModel(Integer playModel) {
        this.playModel = playModel;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPublishPath() {
        return publishPath;
    }

    public void setPublishPath(String publishPath) {
        this.publishPath = publishPath == null ? null : publishPath.trim();
    }
}