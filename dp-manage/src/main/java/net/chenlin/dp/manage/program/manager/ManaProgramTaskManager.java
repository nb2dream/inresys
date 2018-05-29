package net.chenlin.dp.manage.program.manager;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.entity.ManaProgramTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * 节目单Mapper层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/13 18:05
 */
public interface ManaProgramTaskManager {

    List<ManaProgramTaskDTO> listProgramByPage(Page<ManaProgramTaskDTO> page, Query search);

    ManaProgramTaskDTO getProgramTaskById(Long id);

    int saveProgramTask(ManaProgramTaskDTO programTask);

    int batchRemove(Long[] ids);

    int updateProgramTask(ManaProgramTaskDTO programTask);

    /**
     * 获取已经通过审核的节目单表
     * @param createUserId
     * @return
     */
    List<ManaProgramTaskDTO> listIsPassTask(Long createUserId);


    ManaProgramTaskDTO getProgramTaskByParams(Map<String, Object> params);
}
