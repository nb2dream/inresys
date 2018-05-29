package net.chenlin.dp.manage.program.entity;

import net.chenlin.dp.common.entity.BaseEntity;

/**
 *  节目素材 实体
 */
public class ManaProgramMaterialEntity extends BaseEntity{

    /**
     * 节目素材位置唯一标识
     */
    private String itemId;

    /**
     * 播放顺序
     */
    private Integer sortNum;

    /**
     * 素材id
     */
    private Long materialId;

    /**
     * 节目id
     */
    private Long programId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }
}