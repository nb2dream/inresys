package net.chenlin.dp.manage.terminal.manager;

import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.terminal.entity.ManaClassifyEntity;

import java.util.List;

/**
 * 终端分类manager 层接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/20 14:45
 */
public interface ManaClassifyManager {

    List<ManaClassifyEntity> listClassify(Query query);

    List<ManaClassifyEntity> listClassifyByParentId(Query query);

    int saveClassify(ManaClassifyEntity manaClassifyEntity);

    List<ManaClassifyEntity> listClassifyNotParentId();

    int updateClassify(ManaClassifyEntity manaClassifyEntity);

    ManaClassifyEntity getClassifyById(Long id);

    /**
     * 判断是否有子节点数据
     * @param id
     * @return 有:true  无:false
     */
    Boolean hasChildren(Long id);

    int remove(Long id);

}
