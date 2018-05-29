package net.chenlin.dp.manage.terminal.manager.impl;

import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.terminal.dao.ManaClassifyMapper;
import net.chenlin.dp.manage.terminal.dao.ManaTerminalMapper;
import net.chenlin.dp.manage.terminal.entity.ManaClassifyEntity;
import net.chenlin.dp.manage.terminal.manager.ManaClassifyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 终端分类manager 实现
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/20 14:48
 */
@Component("ManaClassifyManager")
public class ManaClassifyManagerImpl implements ManaClassifyManager {

    @Autowired
    private ManaClassifyMapper manaClassifyMapper;

    @Autowired
    private ManaTerminalMapper manaTerminalMapper;

    @Override
    public List<ManaClassifyEntity> listClassify(Query query) {
        return manaClassifyMapper.listClassify(query);
    }

    @Override
    public List<ManaClassifyEntity> listClassifyByParentId(Query query) {
        return manaClassifyMapper.listClassifyByParentId(query);
    }

    @Override
    public int saveClassify(ManaClassifyEntity manaClassifyEntity) {
        return manaClassifyMapper.save(manaClassifyEntity);
    }

    @Override
    public List<ManaClassifyEntity> listClassifyNotParentId() {
        return manaClassifyMapper.listClassifyNotParentId();
    }

    @Override
    public int updateClassify(ManaClassifyEntity manaClassifyEntity) {
        return manaClassifyMapper.update(manaClassifyEntity);
    }

    @Override
    public ManaClassifyEntity getClassifyById(Long id) {
        return manaClassifyMapper.getObjectById(id);
    }

    @Override
    public Boolean hasChildren(Long id) {
        //TODO 未添加分组下是否有素材验证
        Integer chidren = manaClassifyMapper.countClassifyChildren(id);
        Integer terminalNumber = manaTerminalMapper.countTerminalByClassifyId(id);
        if (!CommonUtils.isIntThanZero(chidren) && !CommonUtils.isIntThanZero(terminalNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public int remove(Long id) {
        return manaClassifyMapper.remove(id);
    }

}
