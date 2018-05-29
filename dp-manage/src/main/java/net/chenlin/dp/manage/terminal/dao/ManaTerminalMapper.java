package net.chenlin.dp.manage.terminal.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 终端管理
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/18 17:39
 */
@Component
public interface ManaTerminalMapper extends BaseMapper<ManaTerminalEntity> {

    List<ManaTerminalEntity> listTerminalByPageAndClassifyId(Page<ManaTerminalEntity> page, Query search);

    Integer countTerminalByClassifyId(Long id);

    /**
     * 获取全部终端id, 也就是mac地址
     * @return
     */
    List<String> listTerminalId();

    String getCityCodeByName(String cityName);

    void updateDeviceStatus();
}
