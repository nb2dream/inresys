package net.chenlin.dp.manage.program.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;
import org.springframework.stereotype.Component;

/**
 * 节目与节目单关系 dao层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/14 16:32
 */
@Component
public interface ManaProgramTaskRelationMapper extends BaseMapper<ManaProgramTaskRelationDTO>{

    /**
     * 根据节目单id 获取节目总数
     * @param taksId
     * @return
     */
    int countTotalByTaskId(Long taksId);

    /**
     * 根据条件获取节目关系对象
     * @param query
     * @return
     */
    ManaProgramTaskRelationDTO getRelationSingle(Query query);


}
