package net.chenlin.dp.manage.push.thread;

import net.chenlin.dp.common.constant.StatusConstant;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.utils.ApplicationContextHelper;
import net.chenlin.dp.common.utils.DateUtils;
import net.chenlin.dp.manage.push.impl.WebSocketPushImpl;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;
import net.chenlin.dp.manage.terminal.manager.ManaTerminalManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 在线终端检测
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2018/3/1 17:54
 */
public class OnlineTerminalDetectionThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(OnlineTerminalDetectionThread.class);

    private OnlineTerminalDetectionThread() {
    }

    private static OnlineTerminalDetectionThread instance = new OnlineTerminalDetectionThread();

    public static OnlineTerminalDetectionThread getInstance() {
        return instance;
    }

    @Override
    public void run() {
        while (true) {
           /* log.info("网络状态监测线程：============>>> " + currentThread().getName());
            log.info("当前终端在线数量：============>>> " + WebSocketPushImpl.webSocketMap.size());*/

            ManaTerminalManager terminalManager = ApplicationContextHelper.popBean(ManaTerminalManager.class);
            WebSocketPushImpl webSocketPush = ApplicationContextHelper.popBean(WebSocketPushImpl.class);
            if (terminalManager == null || webSocketPush == null) continue;
            Query query = new Query();
            query.put("networkStatus", StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_ONLINE);
            // 1、获取网络状态为在线的终端
            List<ManaTerminalEntity> list = terminalManager.list(query);

            List<ManaTerminalEntity> onlineList = new ArrayList<>();
            for (ManaTerminalEntity terminalEntity : list) {
                for (String key : WebSocketPushImpl.webSocketMap.keySet()) {
                    log.info("在线终端的编号========>>>>>>: " + key);
                }
                if (WebSocketPushImpl.webSocketMap.containsKey(terminalEntity.getTerminalId())) {
                    onlineList.add(terminalEntity);
                }
            }

            for (ManaTerminalEntity terminalEntity : onlineList) {
                terminalEntity.setNetworkStatus(StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_ONLINE);
                terminalManager.update(terminalEntity);
            }

            // 3、循环比对，如果终端状态是在线，但连接里没有该终端则设置为离线
            List<ManaTerminalEntity> offlineList = new ArrayList<>();
            for (ManaTerminalEntity terminalEntity : list) {
                if (!onlineList.contains(terminalEntity)) {
                    offlineList.add(terminalEntity);
                }
            }

            // 4、如果终端的同步状态为正在同步则设置为同步失败
            for (ManaTerminalEntity terminalEntity : offlineList) {
                terminalEntity.setNetworkStatus(StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_OFFLINE);
                if (terminalEntity.getSyncStatus() == StatusConstant.TerminalSyncStatusConstant.SYNC_STATUS_WAIT_SYNC) {
                    terminalEntity.setSyncStatus(StatusConstant.TerminalSyncStatusConstant.SYNC_STATUS_FAIL_SYNC);
                }
                terminalManager.update(terminalEntity);
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
