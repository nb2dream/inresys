package net.chenlin.dp.manage.material.manager.impl;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.material.dao.ManaMaterialMapper;
import net.chenlin.dp.manage.material.dto.ManaMaterialDTO;
import net.chenlin.dp.manage.material.manager.ManaMaterialManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 15:32
 */
@Component("manaMaterialManager")
public class ManaMaterialManagerImpl implements ManaMaterialManager {

    @Autowired
    private ManaMaterialMapper manaMaterialMapper;

    @Override
    public List<ManaMaterialDTO> listForPage(Page<ManaMaterialDTO> page, Query search) {
        return manaMaterialMapper.listForPage(page, search);
    }

    @Override
    public List<ManaMaterialDTO> listSelectMaterialByPage(Page<ManaMaterialDTO> page, Query search) {
        return manaMaterialMapper.listSelectMaterialByPage(page, search);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return manaMaterialMapper.batchRemove(ids);
    }

    @Override
    public int saveMaterial(ManaMaterialDTO manaMaterialDTO) {
        return manaMaterialMapper.save(manaMaterialDTO);
    }

    @Override
    public int updateMaterial(ManaMaterialDTO manaMaterialDTO) {
        return manaMaterialMapper.update(manaMaterialDTO);
    }

    @Override
    public ManaMaterialDTO getMaterialById(Long id) {
        return manaMaterialMapper.getObjectById(id);
    }
}
