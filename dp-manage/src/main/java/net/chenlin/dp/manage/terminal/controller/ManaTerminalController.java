package net.chenlin.dp.manage.terminal.controller;

import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.controller.AbstractController;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.entity.SysUserEntity;
import net.chenlin.dp.manage.push.BasePushService;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;
import net.chenlin.dp.manage.terminal.serviece.ManaTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.User;
import java.util.HashMap;
import java.util.Map;

/**
 * 终端管理
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/19 16:30
 */
@RestController
@RequestMapping("/manage/terminal")
public class ManaTerminalController extends AbstractController {

    @Autowired
    private ManaTerminalService manaTerminalService;

    @Value("${base.operation.password}")
    private String operationPassword;

    @RequestMapping("/list")
    public Page<ManaTerminalEntity> list(@RequestBody Map<String, Object> params, HttpServletResponse response) {
        // 超级管理员可以查看全部终端
        /*if (getUserId() != SystemConstant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }*/
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        params.put("createUserId", getUserId());
        return manaTerminalService.listTerminalByPageAndClassifyId(params);
    }

    @SysLog("新增终端")
    @RequestMapping("/save")
    public R save(@RequestBody ManaTerminalEntity manaTerminalEntity) {
        manaTerminalEntity.setCreateUserId(getUserId());
        return manaTerminalService.saveTerminal(manaTerminalEntity);
    }

    @SysLog("删除终端")
    @RequestMapping("/remove")
    public R batchRemove(@RequestBody Long[] id) {
        return manaTerminalService.batchRemove(id);
    }

    @RequestMapping("/info")
    public R info(@RequestBody Long id) {
        return manaTerminalService.getTerminalById(id);
    }

    @SysLog("更新终端")
    @RequestMapping("/update")
    public R update(@RequestBody ManaTerminalEntity manaTerminalEntity) { //XF_debug
        manaTerminalEntity.setCreateUserId(getUserId());
        return manaTerminalService.updateTerminal(manaTerminalEntity);
    }

    /**
     *
     * @param networkStatus -1: 查找全部终端数  0：查找离线终端数 1：查找在线终端数
     * @return
     */
    @RequestMapping("/countTotal")
    public R countTerminalTotal(@RequestParam int networkStatus) {
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            return manaTerminalService.countTerminalTotal(networkStatus, getUserId());
        }
        return manaTerminalService.countTerminalTotal(networkStatus, null);
    }

    /**
     * 节目任务下发
     * @param params
     * @return
     */
    @RequestMapping("/tasks_push")
    public R taskPush(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.taskPush(params);
    }

    /**
     * 终端关机
     * @param params
     * @return
     */
    @RequestMapping("/shutdown")
    public R shutdown(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.shutdown(params);
    }

    /**
     * 终端重启
     * @param
     * @return
     */
    @RequestMapping("/restart")
    public R restart(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.restart(params);
    }

    /**
     * 终端启动
     * @param params
     * @return
     */
    @RequestMapping("/start")
    public R start(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.start(params);
    }

    /**
     * 时间同步任务
     * @param params
     * @return
     */
    @RequestMapping("/time_sync")
    public R timeSync(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.timeSync(params);
    }

    /**
     * 终端虚拟菜单隐藏
     * @param params
     * @return
     */
    @RequestMapping("/menu_hide")
    public R menuHide(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.menuHide(params);
    }

    /**
     * 终端虚拟菜单显示
     * @param params
     * @return
     */
    @RequestMapping("/menu_show")
    public R menuShow(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.menuShow(params);
    }

    /**
     * 重置系统
     * @param params
     * @return
     */
    @RequestMapping("/reset")
    public R reset(@RequestBody Map<String, Object> params) {
        if (!operationPassword.equals(params.get("password"))) {
            return R.error("操作密码错误");
        }
        params.put("userId", getUserId());
        return manaTerminalService.reset(params);
    }


    /**
     * 推送定时开关机
     * @param params
     * @return
     */
    @RequestMapping("/timingONOFF")
    public R timingONOFF(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.timingONOFF(params);
    }

    /**
     * 背光开关
     * @param params
     * @return
     */
    @RequestMapping("/backlightOnOFF")
    public R backlightOnOFF(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.backlightOnOFF(params);
    }

    /**
     * 设置音量
     * @param params
     * @return
     */
    @RequestMapping("/set_volume")
    public R setVolume(@RequestBody Map<String, Object> params) {
        params.put("userId", getUserId());
        return manaTerminalService.setVolume(params);
    }

    /**
     * 转发协议
     */


}
