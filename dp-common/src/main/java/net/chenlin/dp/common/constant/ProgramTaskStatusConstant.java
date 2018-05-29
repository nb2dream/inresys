package net.chenlin.dp.common.constant;

/**
 * 节目单状态常量
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/17 17:16
 */
public class ProgramTaskStatusConstant {

    /**
     *  未审核：未申请审核
     */
    public static final int TASK_STATUS_NOT_CHECK = -2;

    /**
     *  待审核：等待审核
     */
    public static final int TASK_STATUS_WAIT_CHECK = -1;

    /**
     *  已审核
     */
    public static final int TASK_STATUS_SUCCESS_CHECK = 1;

    /**
     *  审核为通过
     */
    public static final int TASK_STATUS_FAILED_CHECK = 0;

}
