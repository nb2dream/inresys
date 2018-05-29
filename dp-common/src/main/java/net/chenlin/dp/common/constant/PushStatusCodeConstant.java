package net.chenlin.dp.common.constant;

import com.alibaba.druid.support.spring.stat.annotation.Stat;

/**
 *
 * 推送状态码常量
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 9:49
 */
public class PushStatusCodeConstant {

    /**
     * 关机
     */
    public static final int PUSH_SHUTDOWN_CODE = 10001;

    /**
     * 重启
     */
    public static final int PUSH_RESTART_CODE = 10002;

    /**
     * 推送节目单配置json
     */
    public static final int PUSH_PROGRAM_TASK_CODE = 10003;


    /**
     * 推送时间同步
     */
    public static final int PUSH_TIME_SYNC_CODE = 10004;

    /**
     * 虚拟按钮隐藏
     */
    public static final int PUSH_MENU_HIDE_CODE = 10005;

    /**
     * 虚拟按钮显示
     */
    public static final int PUSH_MENU_SHOW_CODE = 10006;

    /**
     * 恢复出厂
     */
    public static final int PUSH_RESET_CODE = 10007;

    /**
     * 定时开关机
     */
    public static final int PUSH_TIMING_ONOFF_CODE = 10008;

    /**
     * 开关背光
     */
    public static final int PUSH_BACKLIGHT_ONOFF_CODE = 10010;

    /**
     * 设置音量
     */
    public static final int PUSH_VOLUME_CODE = 10011;

    /**
     * 截屏
     */
    public static final int PUSH_SCREENSHOT_CODE = 10012;

}
