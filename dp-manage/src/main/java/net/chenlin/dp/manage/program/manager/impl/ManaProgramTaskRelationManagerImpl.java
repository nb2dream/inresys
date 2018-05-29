package net.chenlin.dp.manage.program.manager.impl;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dao.ManaProgramTaskRelationMapper;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskRelationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/14 17:16
 */
@Component("manaProgramTaskRelationManager")
public class ManaProgramTaskRelationManagerImpl implements ManaProgramTaskRelationManager{

    @Autowired
    private ManaProgramTaskRelationMapper manaProgramTaskRelationMapper;

    @Override
    public List<ManaProgramTaskRelationDTO> listProgramTaskRelationByPage(Page<ManaProgramTaskRelationDTO> page, Query search) {
        return manaProgramTaskRelationMapper.listForPage(page, search);
    }

    @Override
    public int save(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO) {
        return manaProgramTaskRelationMapper.save(manaProgramTaskRelationDTO);
    }

    @Override
    public int update(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO) {
        return manaProgramTaskRelationMapper.update(manaProgramTaskRelationDTO);
    }

    @Override
    public int countTotalByTaskId(Long taskId) {
        return manaProgramTaskRelationMapper.countTotalByTaskId(taskId);
    }

    @Override
    public ManaProgramTaskRelationDTO getRelationSingle(Query query) {
        return manaProgramTaskRelationMapper.getRelationSingle(query);
    }

    @Override
    public ManaProgramTaskRelationDTO getRelationById(Long id) {
        return manaProgramTaskRelationMapper.getObjectById(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return manaProgramTaskRelationMapper.batchRemove(ids);
    }

    @Override
    public List<ManaProgramTaskRelationDTO> listProgramTaskRelation(Query query) {
        return manaProgramTaskRelationMapper.list(query);
    }

    @Override
    public List<ManaProgramTaskRelationDTO> listProgramTaskRelationByProgramId(Long programId) {
        Query query = new Query();
        query.put("programId", programId);
        return manaProgramTaskRelationMapper.list(query);
    }

    @Override
    public List<ManaProgramTaskRelationDTO> listProgramTaskRelationByTaskId(Long taskId) {
        Query query = new Query();
        query.put("taskId", taskId);
        return manaProgramTaskRelationMapper.list(query);
    }
}
