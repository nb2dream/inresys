package net.chenlin.dp.manage.terminal.manager;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;

import java.util.List;
import java.util.Map;

/**
 * 终端Manager层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/19 18:01
 */

public interface ManaTerminalManager {

    List<ManaTerminalEntity> listTerminalByPageAndClassifyId(Page<ManaTerminalEntity> page, Query search);

    int SaveTerminal(ManaTerminalEntity terminal);

    int batchRemove(Long[] id);

    ManaTerminalEntity getById(Long id);

    int update(ManaTerminalEntity manaTerminalEntity);

    int countTotal(Query query);

    /**
     * 根据特定条件查找终端信息
     * @param manaTerminalEntity
     * @return
     */
    ManaTerminalEntity getTerminal(ManaTerminalEntity manaTerminalEntity);

    /**
     * 根据terminalId 获取 终端信息
     * @param terminalId
     * @return
     */
    ManaTerminalEntity getTerminalByTerminalId(String terminalId);

    /**
     * 终端id（MAC地址） 列表
     * @return
     */
    List<String> listTerminalId();

    /**
     * 根据条件获取终端列表
     * @param query
     * @return
     */
    List<ManaTerminalEntity> list(Query query);

    String getCityCodeByName(String cityName);

    void updateDeviceStatus();
}
