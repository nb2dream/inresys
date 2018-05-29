package net.chenlin.dp.manage.terminal.manager.impl;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.terminal.dao.ManaTerminalMapper;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;
import net.chenlin.dp.manage.terminal.manager.ManaTerminalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 终端Manager层实现
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/19 18:03
 */
@Component("manaTerminalManager")
public class ManaTerminalManagerImpl implements ManaTerminalManager {

    @Autowired
    private ManaTerminalMapper manaTerminalMapper;

    @Override
    public List<ManaTerminalEntity> listTerminalByPageAndClassifyId(Page<ManaTerminalEntity> page, Query search) {
        return manaTerminalMapper.listForPage(page, search);
    }

    @Override
    public int SaveTerminal(ManaTerminalEntity terminal) {
        int count = manaTerminalMapper.save(terminal);
        return count;
    }

    @Override
    public int batchRemove(Long[] id) {
        return manaTerminalMapper.batchRemove(id);
    }

    @Override
    public ManaTerminalEntity getById(Long id) {
        return manaTerminalMapper.getObjectById(id);
    }

    @Override
    public int update(ManaTerminalEntity manaTerminalEntity) {
        return manaTerminalMapper.update(manaTerminalEntity);
    }

    @Override
    public int countTotal(Query query) {
        return manaTerminalMapper.countTotal(query);
    }

    @Override
    public ManaTerminalEntity getTerminal(ManaTerminalEntity manaTerminalEntity) {
        return manaTerminalMapper.getObject(manaTerminalEntity);
    }

    @Override
    public ManaTerminalEntity getTerminalByTerminalId(String terminalId) {
        ManaTerminalEntity manaTerminalEntity = new ManaTerminalEntity();
        manaTerminalEntity.setTerminalId(terminalId);
        return manaTerminalMapper.getObject(manaTerminalEntity);
    }

    @Override
    public List<String> listTerminalId() {
        return manaTerminalMapper.listTerminalId();
    }

    @Override
    public List<ManaTerminalEntity> list(Query query) {
        return manaTerminalMapper.list(query);
    }

    public String getCityCodeByName(String cityName) {
        return manaTerminalMapper.getCityCodeByName(cityName);
    }

    @Override
    public void updateDeviceStatus() {
        manaTerminalMapper.updateDeviceStatus();
    }

}
