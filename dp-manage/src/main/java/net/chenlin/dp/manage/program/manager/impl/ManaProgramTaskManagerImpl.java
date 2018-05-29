package net.chenlin.dp.manage.program.manager.impl;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dao.ManaProgramTaskMapper;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/13 18:08
 */
@Component("manaProgramTaskManager")
public class ManaProgramTaskManagerImpl implements ManaProgramTaskManager {

    @Autowired
    private ManaProgramTaskMapper manaProgramTaskMapper;

    @Override
    public List<ManaProgramTaskDTO> listProgramByPage(Page<ManaProgramTaskDTO> page, Query search) {
        return manaProgramTaskMapper.listForPage(page, search);
    }

    @Override
    public ManaProgramTaskDTO getProgramTaskById(Long id) {
        return manaProgramTaskMapper.getObjectById(id);
    }

    @Override
    public int saveProgramTask(ManaProgramTaskDTO programTask) {
        return manaProgramTaskMapper.save(programTask);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return manaProgramTaskMapper.batchRemove(ids);
    }

    @Override
    public int updateProgramTask(ManaProgramTaskDTO programTask) {
        return manaProgramTaskMapper.update(programTask);
    }

    @Override
    public List<ManaProgramTaskDTO> listIsPassTask(Long createUserId) {
        return manaProgramTaskMapper.listIsPassTask(createUserId);
    }

    @Override
    public ManaProgramTaskDTO getProgramTaskByParams(Map<String, Object> params) {
        return manaProgramTaskMapper.getProgramTaskByParams(params);
    }
}
