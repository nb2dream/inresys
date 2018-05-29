package net.chenlin.dp.manage.program.dto;

import net.chenlin.dp.manage.material.dto.ManaMaterialDTO;
import net.chenlin.dp.manage.program.entity.ManaProgramMaterialEntity;

/**
 * 节目素材DTO
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/21 15:48
 */
public class ManaProgramMaterialDTO extends ManaProgramMaterialEntity {

    /**
     * 素材名
     */
    private String materialName;

    /**
     * 分类名
     */
    private String classifyName;

    /**
     * 节目样式
     */
    private Integer type;

    /**
     * 素材的详细信息
     */
    private ManaMaterialDTO manaMaterialDTO;


    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ManaMaterialDTO getManaMaterialDTO() {
        return manaMaterialDTO;
    }

    public void setManaMaterialDTO(ManaMaterialDTO manaMaterialDTO) {
        this.manaMaterialDTO = manaMaterialDTO;
    }
}
