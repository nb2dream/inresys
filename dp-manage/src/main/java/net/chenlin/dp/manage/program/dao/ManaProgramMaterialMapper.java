package net.chenlin.dp.manage.program.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 节目素材dao层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/21 15:55
 */
@Component
public interface ManaProgramMaterialMapper extends BaseMapper<ManaProgramMaterialDTO>{

    /**
     * 统计ItemId下有多少素材
     * @param query
     * @return
     */
    int countMaterialInItemId(Query query);

    /**
     * 根据条件 获取 单个对象
     * @param query
     * @return
     */
    ManaProgramMaterialDTO getProgramMaterialSingle(Query query);

    /**
     * 根据节目id 获取所有 ItemId
     * @param programId
     * @return
     */
    List<String> listItemIdsByProgramId(Long programId);

    /**
     * 根据节目id 移除素材关系
     * @param programId
     * @return
     */
    int removeByProgramId(Long programId);

}
