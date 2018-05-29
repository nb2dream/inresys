package net.chenlin.dp.manage.terminal.serviece.impl;

import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;
import net.chenlin.dp.manage.terminal.manager.ManaDevicesManager;
import net.chenlin.dp.manage.terminal.serviece.ManaDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/23 11:37
 */
@Service("ManaDevicesService")
public class ManaDevicesServiceImpl implements ManaDevicesService {

    @Autowired
    private ManaDevicesManager manaDevicesManager;

    @Override
    public R saveDevice(ManaDevicesDTO manaDevicesDTO) {
        int count = manaDevicesManager.saveDevice(manaDevicesDTO);
        return CommonUtils.msg(count);
    }

    @Override
    public R updateDeviceByDeviceId(ManaDevicesDTO manaDevicesDTO) {
        int count = manaDevicesManager.updateDeviceByDeviceId(manaDevicesDTO);
        return CommonUtils.msg(count);
    }

    @Override
    public ManaDevicesDTO getDeviceByDeviceId(String deviceId) {
        return manaDevicesManager.getDeviceByDeviceId(deviceId);
    }

    @Override
    public R updateDeviceById(ManaDevicesDTO manaDevicesDTO) {
        int count = manaDevicesManager.updateDeviceById(manaDevicesDTO);
        return CommonUtils.msg(count);
    }


}
