package net.chenlin.dp.manage.push.listener;

import net.chenlin.dp.common.utils.DateUtils;
import net.chenlin.dp.manage.push.thread.OnlineTerminalDetectionThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2018/2/7 17:33
 */
public class WebSocketHeartbeatListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHeartbeatListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.debug("网络监听启动...{}", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        OnlineTerminalDetectionThread detectionThread = OnlineTerminalDetectionThread.getInstance();
        detectionThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
