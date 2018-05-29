package net.chenlin.dp.manage.terminal.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.terminal.entity.ManaClassifyEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 终端分类树dao
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/20 14:03
 */
@Component
public interface ManaClassifyMapper extends BaseMapper<ManaClassifyEntity> {

    /**
     * 根据查询条件查询分组
     * @param query
     * @return
     */
    List<ManaClassifyEntity> listClassify(Query query);

    /**
     * 根据父节点id获取 分类树目录
     * @param query
     * @return
     */
    List<ManaClassifyEntity> listClassifyByParentId(Query query);

    /**
     * 直接获取全部节点信息
     * @return
     */
    List<ManaClassifyEntity> listClassifyNotParentId();

    /**
     * 计算子节点数
     * @param classifyId
     * @return
     */
    Integer countClassifyChildren(Long classifyId);

}
