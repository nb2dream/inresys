package net.chenlin.dp.manage.program.manager;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;

import java.util.List;

/**
 * 节目素材Manager层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/21 16:35
 */
public interface ManaProgramMaterialManager {

    /**
     * 分页获取节目素材信息
     * @param page
     * @param search
     * @return
     */
    List<ManaProgramMaterialDTO> listProgramMaterialByPage(Page<ManaProgramMaterialDTO> page, Query search);

    /**
     * 保存节目素材素材信息
     * @param manaProgramMaterialDTO
     * @return
     */
    int saveProgramMaterial(ManaProgramMaterialDTO manaProgramMaterialDTO);


    /**
     * 更新节目素材信息
     * @param manaProgramMaterialDTO
     * @return
     */
    int updateProgramMaterial(ManaProgramMaterialDTO manaProgramMaterialDTO);

    /**
     * 统计某个 itemId 下有多少素材
     * @param query
     * @return
     */
    int countMaterialInItemId(Query query);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int batchRemove(Long[] ids);

    /**
     * 获取节目已选素材列表
     * @param search
     * @return
     */
    List<ManaProgramMaterialDTO> listProgramMaterial(Query search);

    /**
     * 根据查询条件获取单个对象
     * @param query
     * @return
     */
    ManaProgramMaterialDTO getProgramMaterialSingle(Query query);

    /**
     * 根据id 获取对象
     * @param id
     * @return
     */
    ManaProgramMaterialDTO getProgramMaterialById(Long id);

    /**
     * 根据节目id 获取ItemId 列表
     * @param programId
     * @return
     */
    List<String> listItemIdsByProgramId(Long programId);

    /**
     * 根据素材id 获取有多少个节目用到了该素材
     * @param materialId
     * @return
     */
    List<ManaProgramMaterialDTO> listProgramMaterialByMaterialId(Long materialId);

    /**
     * 根据节目id 获取节目该节目的全部素材
     * @param programId
     * @return
     */
    List<ManaProgramMaterialDTO> listProgramMaterialByProgramId(Long programId);

    /**
     * 根据节目id 移除节目全部素材
     * @param programId
     * @return
     */
    int removeByProgramId(Long programId);

}
