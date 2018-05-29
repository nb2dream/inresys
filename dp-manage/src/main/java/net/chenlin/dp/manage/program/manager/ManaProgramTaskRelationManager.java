package net.chenlin.dp.manage.program.manager;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;

import java.util.List;

/**
 * 节目与节目单 关系manger层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/14 17:13
 */
public interface ManaProgramTaskRelationManager {

    List<ManaProgramTaskRelationDTO> listProgramTaskRelationByPage(Page<ManaProgramTaskRelationDTO> page, Query search);

    int save(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO);

    int update(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO);

    int countTotalByTaskId(Long taskId);

    ManaProgramTaskRelationDTO getRelationSingle(Query query);

    ManaProgramTaskRelationDTO getRelationById(Long id);

    int batchRemove(Long[] ids);

    /**
     * 获取节目单与节目关系列表
     * @param query
     * @return
     */
    List<ManaProgramTaskRelationDTO> listProgramTaskRelation(Query query);

    /**
     * 根据节目id查找
     * @param programId
     * @return
     */
    List<ManaProgramTaskRelationDTO> listProgramTaskRelationByProgramId(Long programId);

    /**
     * 根据节目单id 获取
     * @param taskId
     * @return
     */
    List<ManaProgramTaskRelationDTO> listProgramTaskRelationByTaskId(Long taskId);

}
