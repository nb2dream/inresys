package net.chenlin.dp.manage.program.service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;

import java.util.Map;

/**
 * 节目与节目单关系 的Service 层接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/14 17:20
 */
public interface ManaProgramTaskRelationService {

    /**
     * 分页获取节目与节目表关系列表
     * @param params
     * @return
     */
    Page<ManaProgramTaskRelationDTO> listProgramTaskRelationByPage(Map<String, Object> params);

    R saveProgramTaskRelation(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO);

    R updateProgramTaskRelation(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO);

    /**
     * 统计节目单里有多少节目
     * @param taskId
     * @return
     */
    R countTotalByTaskId(Long taskId);

    /**
     * 修改节目单的节目播发顺序
     * @param params
     * @return
     */
    R moveRelation(Map<String, Object> params);

    /**
     * 保存节目单的节目信息
     * @param params
     * @return
     */
    R saveProgramList(Map<String, Object> params);

    /**
     * 批量删除
     * @param params
     * @return
     */
    R batchRemove(Map<String, Object> params);


}
