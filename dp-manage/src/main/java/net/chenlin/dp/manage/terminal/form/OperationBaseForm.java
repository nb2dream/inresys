package net.chenlin.dp.manage.terminal.form;

/**
 * 远程操作的基础 form对象
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2018/1/11 9:58
 */
public class OperationBaseForm {

    public Long[] ids;

    public int netType;


    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }
}
