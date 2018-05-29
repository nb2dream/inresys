package net.chenlin.dp.manage.push;

import java.util.List;

/**
 * 推送的统一接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2018/1/24 16:36
 */
public interface BasePushService {

    /**
     * 推送全部用户（广播）
     * @param message
     */
    void sendMessageAll(String message);

    /**
     * 批量推送
     * @param message
     * @param ids
     */
    void sendMessageGroup(String message, List<String> ids);

    /**
     * 单独推送
     * @param message
     * @param id
     */
    void sendMessageOne(String message, String id);

    /**
     * 判断是否在线
     * @param id
     * @return
     */
    Boolean checkOnline(String id);

}
