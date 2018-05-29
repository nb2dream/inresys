package net.chenlin.dp.manage.program.manager.impl;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dao.ManaProgramMapper;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;
import net.chenlin.dp.manage.program.entity.ManaProgramEntity;
import net.chenlin.dp.manage.program.manager.ManaProgramManager;
import net.chenlin.dp.manage.program.manager.ManaProgramMaterialManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/10 15:17
 */
@Component("manaProgramManager")
public class ManaProgramManagerImpl implements ManaProgramManager {

    @Autowired
    private ManaProgramMapper manaProgramMapper;

    private ManaProgramMaterialManager manaProgramMaterialManager;

    @Override
    public List<ManaProgramDTO> listProgramByPage(Page<ManaProgramDTO> page, Query search) {
        return manaProgramMapper.listForPage(page, search);
    }

    @Override
    public ManaProgramDTO getProgramById(Long id) {
        return manaProgramMapper.getObjectById(id);
    }

    @Override
    public int saveProgram(ManaProgramDTO program) {
        return manaProgramMapper.save(program);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return manaProgramMapper.batchRemove(ids);
    }

    @Override
    public int updateProgram(ManaProgramDTO program) {
        return manaProgramMapper.update(program);
    }

    @Override
    public List<ManaProgramDTO> listSelectProgramByPage(Page<ManaProgramDTO> page, Query query) {
        return manaProgramMapper.listSelectProgramByPage(page, query);
    }

    @Override
    public Boolean hasChildren(Long id) {
        return null;
    }
}
