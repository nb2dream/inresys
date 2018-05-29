package net.chenlin.dp.manage.push.impl;

import net.chenlin.dp.common.constant.StatusConstant;
import net.chenlin.dp.common.exception.RRException;
import net.chenlin.dp.common.utils.ApplicationContextHelper;
import net.chenlin.dp.common.utils.DateUtils;
import net.chenlin.dp.manage.terminal.dto.ManaDevicesDTO;
import net.chenlin.dp.manage.terminal.entity.ManaTerminalEntity;
import net.chenlin.dp.manage.terminal.manager.ManaTerminalManager;
import net.chenlin.dp.manage.terminal.serviece.ManaDevicesService;
import net.chenlin.dp.manage.push.BasePushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * webSocket 推送处理类
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2018/1/19 11:59
 */
@Component("webSocketPushImpl")
@ServerEndpoint("/webSocket/{deviceId}")
public class WebSocketPushImpl implements BasePushService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketPushImpl.class);

    /**
     * 连接会话
     */
    private Session session;

    /**
     * 设备id
     */
    private String deviceId;


    /**
     * 由于使用@ServerEndpoint注解导致没法自动注入service类，所以只能以这种形式注入
     */
    private ManaDevicesService manaDevicesService = ApplicationContextHelper.popBean(ManaDevicesService.class);

    private ManaTerminalManager manaTerminalManager = ApplicationContextHelper.popBean(ManaTerminalManager.class);

    public static ConcurrentHashMap<String, WebSocketPushImpl> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 建立连接时运行
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId") String deviceId) {

        ManaDevicesDTO manaDevicesDTO = manaDevicesService.getDeviceByDeviceId(deviceId);
        if (manaDevicesDTO == null) {
            manaDevicesDTO = new ManaDevicesDTO();
            manaDevicesDTO.setDeviceId(deviceId);
            manaDevicesService.saveDevice(manaDevicesDTO);
           /* try {
                webSocketMap.get(deviceId).session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            webSocketMap.remove(deviceId);
            return;*/
        }

        WebSocketPushImpl websocket = webSocketMap.get(deviceId);
        if (websocket == null) {
            // 每个连接都是一个session对象
            session.getContainer().setAsyncSendTimeout(1500L);
            this.session = session;
            this.deviceId = deviceId;
            // 添加到容器里
            webSocketMap.put(deviceId, this);

            log.info("有新的终端接入...deviceId: {}", deviceId);
        } else if (websocket.session == null) {
            websocket.session = session;
        }

        // 更新终端状态
        ManaTerminalEntity terminalEntity = manaTerminalManager.getTerminalByTerminalId(deviceId);
        if (terminalEntity != null) {
            terminalEntity.setNetworkStatus(StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_ONLINE);
            manaTerminalManager.update(terminalEntity);
        }

        /*ManaDevicesDTO manaDevicesDTO = manaDevicesService.getDeviceByDeviceId(deviceId);
        if (manaDevicesDTO == null) {
//            log.error("未知设备, deviceId={}", deviceId);
            try {
                webSocketMap.get(deviceId).session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            webSocketMap.remove(deviceId);

            return;
        }*/
        /*log.info("有新的终端接入...deviceId: {}", deviceId);
        log.info("onOpen()...webSocketMap.size() " + webSocketMap.size());*/
    }

    /**
     * 关闭连接时运行
     */
    @OnClose
    public void onClose() {

        if (deviceId == null) {
            return;
        }

        // 该连接所属终端
        ManaTerminalEntity terminalEntity = manaTerminalManager.getTerminalByTerminalId(deviceId);

        // 更新终端状态
        if (terminalEntity != null) {
            terminalEntity.setNetworkStatus(StatusConstant.TerminalNetworkStatusConstant.NETWORK_STATUS_OFFLINE);
            if (terminalEntity.getSyncStatus() == StatusConstant.TerminalSyncStatusConstant.SYNC_STATUS_WAIT_SYNC) {
                // 如果终端处于同步中，这时断开连接，所以同步失败
                terminalEntity.setSyncStatus(StatusConstant.TerminalSyncStatusConstant.SYNC_STATUS_FAIL_SYNC);
            }
            manaTerminalManager.update(terminalEntity);
        }
        // 移除会话
        try {
            if (webSocketMap.get(deviceId) != null) {
                webSocketMap.get(deviceId).session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        webSocketMap.remove(deviceId);
        log.info("终端连接断开...deviceId: {}", deviceId);
    }

    /**
     * 接收消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        /**
         * 收到消息后将消息返回
         */
        if (webSocketMap.get(message).session.isOpen()) {
            sendMessageOne(message, message);
        }
    }

    /**
     * 发送失败
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("【websocket消息】推送出错...", error);
    }

    /**
     * 广播推送消息
     *
     * @param message
     */
    @Override
    public void sendMessageAll(String message) {
        for (String key : webSocketMap.keySet()) {
            WebSocketPushImpl webSocket = webSocketMap.get(key);
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessageGroup(String message, List<String> ids) {
        log.info("【推送内容】 message = {}", message);
        for (String id : ids) {
            WebSocketPushImpl webSocket = webSocketMap.get(id);
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessageOne(String message, String id) {
        WebSocketPushImpl webSocket = webSocketMap.get(id);
        try {
            if (webSocket.session.isOpen()) {
                webSocket.session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean checkOnline(String id) {
        return webSocketMap.get(id) != null;
    }

}
