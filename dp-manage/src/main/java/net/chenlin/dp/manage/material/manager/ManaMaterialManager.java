package net.chenlin.dp.manage.material.manager;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.material.dto.ManaMaterialDTO;

import java.util.List;

/**
 * 素材的manager层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 15:30
 */
public interface ManaMaterialManager {

    /**
     * 分页查询节目素材
     * @param page
     * @param search
     * @return
     */
    List<ManaMaterialDTO> listForPage(Page<ManaMaterialDTO> page, Query search);

    /**
     * 分页未被选中素材列表
     * @param page
     * @param search
     * @return
     */
    List<ManaMaterialDTO> listSelectMaterialByPage(Page<ManaMaterialDTO> page, Query search);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchRemove(Long[] ids);

    /**
     * 保存素材
     * @param manaMaterialDTO
     * @return
     */
    int saveMaterial(ManaMaterialDTO manaMaterialDTO);

    /**
     * 更新素材
     * @param manaMaterialDTO
     * @return
     */
    int updateMaterial(ManaMaterialDTO manaMaterialDTO);

    /**
     * 根据素材id获取素材对象
     * @param id
     * @return
     */
    ManaMaterialDTO getMaterialById(Long id);

}
