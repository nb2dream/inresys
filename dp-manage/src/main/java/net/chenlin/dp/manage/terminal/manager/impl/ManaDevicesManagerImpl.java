package net.chenlin.dp.manage.terminal.manager.impl;

import net.chenlin.dp.manage.terminal.dao.ManaDevicesMapper;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;
import net.chenlin.dp.manage.terminal.manager.ManaDevicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/23 11:32
 */
@Component("ManaDevicesManager")
public class ManaDevicesManagerImpl implements ManaDevicesManager{

    @Autowired
    private ManaDevicesMapper manaDevicesMapper;

    @Override
    public int saveDevice(ManaDevicesDTO manaDevicesDTO) {
        return manaDevicesMapper.save(manaDevicesDTO);
    }

    @Override
    public int updateDeviceByDeviceId(ManaDevicesDTO manaDevicesDTO) {
        return manaDevicesMapper.updateByDeviceId(manaDevicesDTO);
    }

    @Override
    public ManaDevicesDTO getDeviceByDeviceId(String deviceId) {
        return manaDevicesMapper.getDeviceByDeviceId(deviceId);
    }

    @Override
    public int updateDeviceById(ManaDevicesDTO manaDevicesDTO) {
        return manaDevicesMapper.update(manaDevicesDTO);
    }
}
