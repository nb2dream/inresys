package net.chenlin.dp.manage.program.dto;

import net.chenlin.dp.manage.program.entity.ManaProgramTaskRelationEntity;

/**
 * 节目与节目单关系  的DTO
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/14 15:49
 */
public class ManaProgramTaskRelationDTO extends ManaProgramTaskRelationEntity {

    private String programName;

    private String width;

    private String height;

    private String description;

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
