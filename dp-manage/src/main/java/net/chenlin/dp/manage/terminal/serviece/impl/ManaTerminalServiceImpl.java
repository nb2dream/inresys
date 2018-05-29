package net.chenlin.dp.manage.terminal.serviece.impl;

import net.chenlin.dp.common.constant.PushStatusCodeConstant;
import net.chenlin.dp.common.constant.StatusConstant;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.exception.RRException;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskManager;
import net.chenlin.dp.manage.program.service.ManaProgramTaskService;
import net.chenlin.dp.manage.push.BasePushService;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;
import net.chenlin.dp.manage.terminal.manager.ManaTerminalManager;
import net.chenlin.dp.manage.terminal.serviece.ManaDevicesService;
import net.chenlin.dp.manage.terminal.serviece.ManaTerminalService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 终端 Service 接口 实现类
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/19 14:03
 */
@Service("manaTerminalService")
public class ManaTerminalServiceImpl implements ManaTerminalService {

    private static Logger log = LoggerFactory.getLogger(ManaTerminalServiceImpl.class);

    @Autowired
    private ManaTerminalManager manaTerminalManager;

    @Autowired
    private ManaProgramTaskManager manaProgramTaskManager;

    @Autowired
    private ManaDevicesService manaDevicesService;

    @Autowired
    private ManaProgramTaskService manaProgramTaskService;

    @Resource(name = "webSocketPushImpl")
    private BasePushService webSocketPush;


    @Override
    public Page<ManaTerminalEntity> listTerminalByPageAndClassifyId(Map<String, Object> params) {
        // 查询条件
        Query form = new Query(params);
        // 分页
        Page<ManaTerminalEntity> page = new Page<>(form);
        manaTerminalManager.listTerminalByPageAndClassifyId(page, form);
        return page;
    }

    @Override
    public R saveTerminal(ManaTerminalEntity terminal) {
        if (CommonUtils.isNullOrEmpty(terminal.getTerminalId())) {
            return R.error("参数错误！");
        } else {
            ManaDevicesDTO manaDevicesDTO = manaDevicesService.getDeviceByDeviceId(terminal.getTerminalId());
            ManaTerminalEntity terminalEntity = manaTerminalManager.getTerminalByTerminalId(terminal.getTerminalId());
            if (CommonUtils.isNullOrEmpty(manaDevicesDTO)) throw new RRException("终端设备未注册，请把设备联网初始化注册!");
            if (!CommonUtils.isNullOrEmpty(terminalEntity)) throw new RRException("该终端已被其他用户使用，请添加其他设备");
            // 判断如果设备已经连接webSockt 则设置在线
            if (webSocketPush.checkOnline(terminal.getTerminalId())) {
                terminal.setNetworkStatus(StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_ONLINE);
            }
        }

        int count = manaTerminalManager.SaveTerminal(terminal);
        return CommonUtils.msg(count);
    }

    @Override
    public R updateTerminal(ManaTerminalEntity terminal) {
        int count = manaTerminalManager.update(terminal);
        return CommonUtils.msg(count);
    }

    @Override
    public R batchRemove(Long[] id) {
        int count = manaTerminalManager.batchRemove(id);
        return CommonUtils.msg(count);
    }

    @Override
    public R getTerminalById(Long id) {
        ManaTerminalEntity manaTerminalEntity = manaTerminalManager.getById(id);
        return CommonUtils.msg(manaTerminalEntity);
    }

    @Override
    public R countTerminalTotal(int networkStatus, Long createUserId) {
        Query form = new Query();
        form.put("networkStatus", networkStatus);
        form.put("createUserId", createUserId);
        Integer count = manaTerminalManager.countTotal(form);
        return CommonUtils.msg(count);
    }

    @Override
    public R taskPush(Map<String, Object> params) {
        Query query = new Query(params);
        Long programTaskId = query.getAsLong("programTaskId");
        Long userId = query.getAsLong("userId");
        List<Long> terminalIds = query.getAsLongList("terminalIds");
        if (CommonUtils.isNullOrEmpty(programTaskId) || CommonUtils.isNullOrEmpty(userId) || CommonUtils.isNullOrEmpty(terminalIds)) {
            throw new RRException("参数异常！");
        }

        ManaProgramTaskDTO programTask = manaProgramTaskManager.getProgramTaskByParams(params);
        /*if (!CommonUtils.isNullOrEmpty(programTask)) {
            if (programTask.getCreateUserId() != userId) {
                throw new RRException("你没有权限下发该节目");
            }
        } else {
           throw new RRException("没找到该节目单");
        }*/

        if (CommonUtils.isNullOrEmpty(programTask)) {
            throw new RRException("没找到该节目单");
        }


        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        // 获取设备的 DeviceToken
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());
        // 拼接内容
        JSONObject customJson = new JSONObject();
        try {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("url", "/api/get_task_config_json.do");
            dataMap.put("taskId", programTaskId);

            customJson.put("code", PushStatusCodeConstant.PUSH_PROGRAM_TASK_CODE);
            customJson.put("data", dataMap);
        } catch (Exception e) {
            log.error("【消息推送】构建消息json错误....");
        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }

        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);

        // 3 修改终端状态
        for (String terminalId : terminalIdList) {
            syncStatus(terminalId, StatusConstant.TerminalSyncStatusConstant.SYNC_STATUS_WAIT_SYNC);
        }

        return R.ok("推送成功");
    }

    @Override
    public R shutdown(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceId
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream().map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = null;
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_SHUTDOWN_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {

        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);

        return R.ok("推送成功");
    }

    @Override
    public R restart(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        // 获取设备的 Deviceid
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        // java8 lombda 表达式
        List<String> terminalIdList = devicesDTOList.stream().map(e -> e.getDeviceId()).collect(Collectors.toList());

        //================< 开始推送动作 >=================
        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = null;
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_RESTART_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R start(Map<String, Object> params) {
        return R.error();
    }

    @Override
    public void syncStatus(String terminalId, int sysncStatus) {
        if (terminalId != null) {
            ManaTerminalEntity terminalEntity = new ManaTerminalEntity();
            terminalEntity.setTerminalId(terminalId);
            terminalEntity.setSyncStatus(sysncStatus);
            terminalEntity.setNetworkStatus(StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_ONLINE);
            manaTerminalManager.update(terminalEntity);
        }
    }

    @Override
    public void networkOnline(String terminalId) {
        ManaTerminalEntity terminalEntity = manaTerminalManager.getTerminalByTerminalId(terminalId);
        if (terminalEntity != null) {
            if (terminalEntity.getNetworkStatus() == StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_OFFLINE) {
                terminalEntity.setNetworkStatus(StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_ONLINE);
                manaTerminalManager.update(terminalEntity);
            }
        }
    }

    @Override
    public R timeSync(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = null;
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_TIME_SYNC_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {

        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R menuHide(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = null;
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_MENU_HIDE_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {

        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R menuShow(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = null;
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_MENU_SHOW_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {

        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R reset(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = null;
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_RESET_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {

        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R timingONOFF(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        String timeOn = query.getAsString("timeOn");     // 开机时间
        String timeOff = query.getAsString("timeOff");   // 关机时间
        String setTimeOffAndOn = query.getAsString("setTimeOffAndOn");
        boolean b = Boolean.valueOf(setTimeOffAndOn).booleanValue();
//        if (StringUtils.isEmpty(timeOn) || StringUtils.isEmpty(timeOff) ) {
//            return R.error("参数错误");
//        }
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = new HashMap();
            dateMap.put("timeOn", timeOn);    // 开机时间
            dateMap.put("timeOff", timeOff);  // 关机时间
            dateMap.put("setTimeOffAndOn", b);  // 关机时间
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_TIMING_ONOFF_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R backlightOnOFF(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        Boolean isOffAndOn = query.getAsBoolean("isOffAndOn");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = new HashMap();
            dateMap.put("isOffAndOn", isOffAndOn);   // 是否开启背光
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_BACKLIGHT_ONOFF_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R setVolume(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        Integer volume = query.getAsInt("volume");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = new HashMap();
            dateMap.put("volume", volume);   // 设置音量数
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_VOLUME_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }

    @Override
    public R screenShot(Map<String, Object> params) {
        Query query = new Query(params);
        List<Long> terminalIds = query.getAsLongList("ids");
        Long userId = query.getAsLong("userId");
        Integer volume = query.getAsInt("volume");
        //================< 开始推送动作 >=================
        // 获取设备的 DeviceToken
        List<ManaDevicesDTO> devicesDTOList = check(terminalIds, userId);
        List<String> terminalIdList = devicesDTOList.stream()
                .map(e -> e.getDeviceId()).collect(Collectors.toList());

        JSONObject customJson = new JSONObject();
        try {
            Map dateMap = new HashMap();
            dateMap.put("volume", volume);   // 设置音量数
            // 推送内容状态码
            customJson.put("code", PushStatusCodeConstant.PUSH_VOLUME_CODE);
            // 推送内容状态码
            customJson.put("data", dateMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 1. 判断这个终端是否在线
        for (String terminalId : terminalIdList) {
            if (!webSocketPush.checkOnline(terminalId)) {
                throw new RRException(String.format("终端设备: %s 处于离线状态，无法推送", terminalId));
            }
        }
        // 2. 推送消息
        webSocketPush.sendMessageGroup(customJson.toString(), terminalIdList);
        return R.ok("推送成功");
    }


    private List<ManaDevicesDTO> check(List<Long> terminalIds, Long userId) {
        List<String> terminalIdList = new ArrayList<>();
        for (Long id : terminalIds) {
            ManaTerminalEntity entity = manaTerminalManager.getById(id);
            if (!CommonUtils.isNullOrEmpty(entity)) {
                if (entity.getCreateUserId() != userId) {
                    throw new RRException("你没有权限操作终端：" + id);
                }
                terminalIdList.add(entity.getTerminalId());
            } else {
                throw new RRException("没找到终端: " + id);
            }

        }
        // 获取设备的 DeviceToken
//        List<String> terminalTokenList = new ArrayList<>();
        List<ManaDevicesDTO> devicesDTOList = new ArrayList<>();
        for (String terminalId : terminalIdList) {
            ManaDevicesDTO manaDevicesDTO = manaDevicesService.getDeviceByDeviceId(terminalId);
            if (CommonUtils.isNullOrEmpty(manaDevicesDTO)) {
                throw new RRException("推送失败, 终端设备:" + terminalId + "  未注册，请把设备联网初始化注册!");
            }
//            terminalTokenList.add(manaDevicesDTO.getDeviceToken());
            devicesDTOList.add(manaDevicesDTO);
        }
        return devicesDTOList;
    }

    public String getCityCodeByName(String cityName) {
        return manaTerminalManager.getCityCodeByName(cityName);
    }

}
