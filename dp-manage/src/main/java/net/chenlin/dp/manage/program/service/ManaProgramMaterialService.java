package net.chenlin.dp.manage.program.service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;

import java.util.List;
import java.util.Map;

/**
 * 节目素材Service层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/21 16:40
 */
public interface ManaProgramMaterialService {

    /**
     * 分页查询节目
     * @param params
     * @return
     */
    Page<ManaProgramMaterialDTO> listProgramMaterialByPage(Map<String, Object> params);

    /**
     * 保存节目的素材列表
     * @param params
     * @return
     */
    R saveMaterialList(Map<String, Object> params);

    /**
     * 批量删除
     * @param params
     * @return
     */
    R batchRemove(Map<String, Object> params);

    /**
     * 移动节目素材播放顺序
     * @param params
     * @return
     */
    R moveMaterial(Map<String, Object> params);

    /**
     * 根据节目id 获取 Itemid 列表
     * @param programId
     * @return
     */
    List<String> listItemIdsByProgramId(Long programId);

}
