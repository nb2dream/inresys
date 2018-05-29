package net.chenlin.dp.manage.material.service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.material.dto.ManaMaterialDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 素材的Service 层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 15:37
 */
public interface ManaMaterialService {

    /**
     * 分页获取节目素材
     * @param params
     * @return
     */
    Page<ManaMaterialDTO> listMaterialByPage(Map<String, Object> params);

    /**
     * 分页未被选中素材列表
     * @param params
     * @return
     */
    Page<ManaMaterialDTO> listSelectMaterialByPage(Map<String, Object> params);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    R batchRemove(Long[] ids);

    /**
     * 更新素材
     * @param manaMaterialDTO
     * @return
     */
    R updateMaterial(ManaMaterialDTO manaMaterialDTO);

    /**
     * 保存素材
     * @param manaMaterialDTO
     * @return
     */
    R saveMaterial(ManaMaterialDTO manaMaterialDTO);

    /**
     * 根据id获取素材信息
     * @param id
     * @return
     */
    R getMaterialById(Long id);

}
