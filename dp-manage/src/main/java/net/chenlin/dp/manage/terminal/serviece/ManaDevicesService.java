package net.chenlin.dp.manage.terminal.serviece;

import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;

/**
 * 设备信息Service层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/23 11:35
 */
public interface ManaDevicesService {

    /**
     * 保存设备信息
     * @param manaDevicesDTO
     * @return
     */
    R saveDevice(ManaDevicesDTO manaDevicesDTO);

    /**
     * 根据 deviceId 更新设备信息
     * @param manaDevicesDTO
     * @return
     */
    R updateDeviceByDeviceId(ManaDevicesDTO manaDevicesDTO);

    /**
     * 根据 deviceId 获取设备信息
     * @param deviceId
     * @return
     */
    ManaDevicesDTO getDeviceByDeviceId(String deviceId);

    /**
     *
     * @param manaDevicesDTO
     * @return
     */
    R updateDeviceById(ManaDevicesDTO manaDevicesDTO);



}
