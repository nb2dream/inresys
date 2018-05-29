package net.chenlin.dp.common.constant;

/**
 * 通用状态常量
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/11/24 14:15
 */
public class StatusConstant {

    /**
     * 终端同步状态常量
     */
    public interface TerminalSyncStatusConstant {

        /**
         * 未同步
         */
        int SYNC_STATUS_NOT_SYNC = -1;

        /**
         * 同步中
         */
        int SYNC_STATUS_WAIT_SYNC = 0;

        /**
         * 已同步
         */
        int SYNC_STATUS_SUCCESS_SYNC = 1;

        /**
         * 同步失败
         */
        int SYNC_STATUS_FAIL_SYNC = 2;
    }

    /**
     * 终端网络状态常量
     */
    public interface TerminalNetworkStatusConstant {

        /**
         * 离线
         */
        int NETWORK_STATUS_OFFLINE = 0;

        /**
         * 在线
         */
        int NETWORK_STATUS_ONLINE = 1;

    }


}
