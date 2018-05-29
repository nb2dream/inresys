package net.chenlin.dp.manage.terminal.manager;

import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/23 11:24
 */
public interface ManaDevicesManager {

    /**
     * 保存设备信息
     * @param manaDevicesDTO
     * @return
     */
    int saveDevice(ManaDevicesDTO manaDevicesDTO);

    /**
     * 根据 deviceId 更新设备信息
     * @param manaDevicesDTO
     * @return
     */
    int updateDeviceByDeviceId(ManaDevicesDTO manaDevicesDTO);

    /**
     * 根据 deviceId 获取设备信息
     * @param deviceId
     * @return
     */
    ManaDevicesDTO getDeviceByDeviceId(String deviceId);

    /**
     * 根据 id 更新 设备信息
     * @param manaDevicesDTO
     * @return
     */
    int updateDeviceById(ManaDevicesDTO manaDevicesDTO);

}
