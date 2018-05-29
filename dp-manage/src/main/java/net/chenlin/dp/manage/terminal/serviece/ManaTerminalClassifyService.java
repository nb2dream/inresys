package net.chenlin.dp.manage.terminal.serviece;

import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.terminal.entity.ManaClassifyEntity;

/**
 * 终端分类 service 接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/20 14:35
 */
public interface ManaTerminalClassifyService {

    /**
     * 根据父节点id 获取子节点
     * @param parentId
     * @return
     */
    R listClassifyByParentId(Query query);

    /**
     * 保存分组信息
     * @param manaClassifyEntity
     * @return
     */
    R saveClassify(ManaClassifyEntity manaClassifyEntity);

    /**
     * 直接获取全部分组信息
     * @return
     */
    R listClassifyNotParentId(Query query);

    /**
     * 更新分组信息
     * @param manaClassifyEntity
     * @return
     */
    R updateClassify(ManaClassifyEntity manaClassifyEntity);

    /**
     * 根据id 获取分组信息
     * @param id
     * @return
     */
    R getClassifyById(Long id);

    /**
     * 根据id 删除分组
     * @param id
     * @return
     */
    R removeClassify(Long id);

}
