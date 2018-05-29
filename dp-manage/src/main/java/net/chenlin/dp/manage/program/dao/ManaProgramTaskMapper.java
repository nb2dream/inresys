package net.chenlin.dp.manage.program.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 节目单DAO接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/13 17:40
 */
@Component
public interface ManaProgramTaskMapper extends BaseMapper<ManaProgramTaskDTO> {

    /**
     * 获取已经通过审核的节目单表
     * @param createUserId
     * @return
     */
    List<ManaProgramTaskDTO> listIsPassTask(Long createUserId);

    ManaProgramTaskDTO getProgramTaskByParams(Map<String, Object> params);

}
