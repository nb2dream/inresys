package net.chenlin.dp.manage.program.entity;

import net.chenlin.dp.common.entity.BaseEntity;

import java.util.Date;

public class ManaProgramEntity extends BaseEntity {

    /**
     * 节目名
     */
    private String name;

    /**
     * 创建的用户id
     */
    private Long createUserId;

    /**
     * 节目宽
     */
    private String width;

    /**
     * 节目高
     */
    private String height;

    /**
     * 节目描述
     */
    private String description;

    /**
     * 节目信息json串
     */
    private String items;

    /**
     * 节目审核状态  0 未审核  1 审核通过
     */
    private Integer status;

    /**
     * 节目分辨率：只做展示用
     */
    private String ratio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width == null ? null : width.trim();
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items == null ? null : items.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}