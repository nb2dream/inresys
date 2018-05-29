package net.chenlin.dp.manage.terminal.entity;

import net.chenlin.dp.common.entity.BaseEntity;

/**
 * 设备管理实体类
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/23 10:37
 */
public class ManaDevicesEntity extends BaseEntity {

    private String deviceId;        // 设备本地唯一id

    private String DeviceToken;     // 友盟生成的设备唯一 token   【已经不用】

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }
}
