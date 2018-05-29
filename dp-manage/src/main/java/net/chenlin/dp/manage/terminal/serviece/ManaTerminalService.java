package net.chenlin.dp.manage.terminal.serviece;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;

import java.util.Map;

/**
 * 终端Service 接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/19 13:45
 */
public interface ManaTerminalService {

    /**
     * 根据分页信息和终端分组code获取终端列表
     * @param params
     * @return
     */
    Page<ManaTerminalEntity> listTerminalByPageAndClassifyId(Map<String, Object> params);

    /**
     * 保存终端
     * @param terminal
     * @return
     */
    R saveTerminal(ManaTerminalEntity terminal);

    /**
     * 更新终端
     * @param terminal
     * @return
     */
    R updateTerminal(ManaTerminalEntity terminal);

    /**
     * 批量删除终端
     * @param id
     * @return
     */
    R batchRemove(Long[] id);

    /**
     * 根据id 获取终端信息
     * @param id
     * @return
     */
    R getTerminalById(Long id);

    /**
     * 计算终端总数或 计算在线终端 或 离线终端数
     * @param networkStatus  -1: 查找全部终端数  0：查找离线终端数 1：查找在线终端数
     * @return
     */
    R countTerminalTotal(int networkStatus, Long createUserId);

    /**
     * 终端节目推送
     * @param params
     * @return
     */
    R taskPush(Map<String, Object> params);

    /**
     * 关闭终端
     * @param params
     * @return
     */
    R shutdown(Map<String, Object> params);

    /**
     * 重启终端
     * @param params
     * @return
     */
    R restart(Map<String, Object> params);

    /**
     * 终端启动
     * @param params
     * @return
     */
    R start(Map<String, Object> params);

    /**
     * 同步状态接口
     * @param terminalId  终端id（终端的mac地址）
     */
    void syncStatus(String terminalId, int sysncStatus);

    /**
     * 设备网络状态接口
     * @param terminalId
     */
    void networkOnline(String terminalId);

    /**
     * 推送时间同步任务接口
     * @param params
     */
    R timeSync(Map<String, Object> params);

    /**
     * 虚拟菜单隐藏
     * @param params
     * @return
     */
    R menuHide(Map<String, Object> params);

    /**
     * 虚拟菜单显示
     * @param params
     * @return
     */
    R menuShow(Map<String, Object> params);

    /**
     * 重置
     * @param params
     * @return
     */
    R reset(Map<String, Object> params);

    /**
     * 推送定时开关机
     * @param params
     * @return
     */
    R timingONOFF(Map<String, Object> params);

    /**
     * 背光开关
     * @param params
     * @return
     */
    R backlightOnOFF(Map<String, Object> params);

    /**
     * 设置音量
     * @param params
     * @return
     */
    R setVolume(Map<String, Object> params);

    /**
     * 截屏
     */
    R screenShot(Map<String, Object> params);

    /**
     * 获取城市代码
     */

    String getCityCodeByName(String cityName);
}
