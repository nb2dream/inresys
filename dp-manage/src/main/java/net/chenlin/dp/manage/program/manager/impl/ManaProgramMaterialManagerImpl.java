package net.chenlin.dp.manage.program.manager.impl;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dao.ManaProgramMaterialMapper;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramManager;
import net.chenlin.dp.manage.program.manager.ManaProgramMaterialManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/21 16:38
 */
@Component("manaProgramMaterialManager")
public class ManaProgramMaterialManagerImpl implements ManaProgramMaterialManager {

    @Autowired
    private ManaProgramMaterialMapper manaProgramMaterialMapper;

    @Override
    public List<ManaProgramMaterialDTO> listProgramMaterialByPage(Page<ManaProgramMaterialDTO> page, Query search) {
        return manaProgramMaterialMapper.listForPage(page, search);
    }

    @Override
    public int saveProgramMaterial(ManaProgramMaterialDTO manaProgramMaterialDTO) {
        return manaProgramMaterialMapper.save(manaProgramMaterialDTO);
    }

    @Override
    public int updateProgramMaterial(ManaProgramMaterialDTO manaProgramMaterialDTO) {
        return manaProgramMaterialMapper.update(manaProgramMaterialDTO);
    }

    @Override
    public int countMaterialInItemId(Query query) {
        return manaProgramMaterialMapper.countMaterialInItemId(query);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return manaProgramMaterialMapper.batchRemove(ids);
    }

    @Override
    public List<ManaProgramMaterialDTO> listProgramMaterial(Query search) {
        return manaProgramMaterialMapper.list(search);
    }

    @Override
    public ManaProgramMaterialDTO getProgramMaterialSingle(Query query) {
        return manaProgramMaterialMapper.getProgramMaterialSingle(query);
    }

    @Override
    public ManaProgramMaterialDTO getProgramMaterialById(Long id) {
        return manaProgramMaterialMapper.getObjectById(id);
    }

    @Override
    public List<String> listItemIdsByProgramId(Long programId) {
        return manaProgramMaterialMapper.listItemIdsByProgramId(programId);
    }

    @Override
    public List<ManaProgramMaterialDTO> listProgramMaterialByMaterialId(Long materialId) {
        Query query = new Query();
        query.put("materialId", materialId);
        return manaProgramMaterialMapper.list(query);
    }

    @Override
    public List<ManaProgramMaterialDTO> listProgramMaterialByProgramId(Long programId) {
        Query query = new Query();
        query.put("programId", programId);
        return manaProgramMaterialMapper.list(query);
    }

    @Override
    public int removeByProgramId(Long programId) {
        return manaProgramMaterialMapper.removeByProgramId(programId);
    }


}
