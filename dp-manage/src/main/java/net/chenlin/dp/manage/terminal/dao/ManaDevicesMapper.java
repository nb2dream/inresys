package net.chenlin.dp.manage.terminal.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;
import org.springframework.stereotype.Component;

/**
 * 设备管理DAO层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/23 10:42
 */
@Component
public interface ManaDevicesMapper extends BaseMapper<ManaDevicesDTO> {

    /**
     * 根据deviceId 查找ManaDevices 对象
     * @param deviceId
     * @return
     */
    ManaDevicesDTO getDeviceByDeviceId(String deviceId);

    /**
     * 根据DeviceId 更新
     * @param manaDevicesDTO
     * @return
     */
    int updateByDeviceId(ManaDevicesDTO manaDevicesDTO);

}
