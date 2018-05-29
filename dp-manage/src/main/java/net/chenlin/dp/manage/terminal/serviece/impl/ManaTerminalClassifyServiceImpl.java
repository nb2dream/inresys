package net.chenlin.dp.manage.terminal.serviece.impl;

import net.chenlin.dp.common.constant.ClassifyTypeConstant;
import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.terminal.entity.ManaClassifyEntity;
import net.chenlin.dp.manage.terminal.manager.ManaClassifyManager;
import net.chenlin.dp.manage.terminal.serviece.ManaTerminalClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 终端分类 service 实现类
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/9/20 14:39
 */
@Service("manaTerminalClassifyService")
public class ManaTerminalClassifyServiceImpl implements ManaTerminalClassifyService {

    @Autowired
    private ManaClassifyManager manaClassifyManager;

    @Override
    public R listClassifyByParentId(Query query) {
        query.put("type", ClassifyTypeConstant.CLASSIFY_TYPE_TERMIAL);
        List<ManaClassifyEntity> list = manaClassifyManager.listClassify(query);
        for (ManaClassifyEntity terminalClassify : list) {
            terminalClassify.checkParent();
        }
        return CommonUtils.msgNotCheckNull(list);
    }

    @Override
    public R saveClassify(ManaClassifyEntity manaClassifyEntity) {
        manaClassifyEntity.setType(ClassifyTypeConstant.CLASSIFY_TYPE_TERMIAL);
        int count = manaClassifyManager.saveClassify(manaClassifyEntity);
        return CommonUtils.msg(count);
    }

    @Override
    public R listClassifyNotParentId(Query query) {
        query.put("type", ClassifyTypeConstant.CLASSIFY_TYPE_TERMIAL);
        List<ManaClassifyEntity> list = manaClassifyManager.listClassify(query);
        ManaClassifyEntity root = new ManaClassifyEntity();
        root.setClassifyId(0L);
        root.setName("主分组");
        root.setParentId(-1L);
        root.setOpen(true);
        list.add(root);
        return CommonUtils.msgNotCheckNull(list);
    }

    @Override
    public R updateClassify(ManaClassifyEntity manaClassifyEntity) {
        if (manaClassifyEntity.getClassifyId() == manaClassifyEntity.getParentId()) {
            return CommonUtils.msg(0);
        }
        int count = manaClassifyManager.updateClassify(manaClassifyEntity);
        return CommonUtils.msg(count);
    }

    @Override
    public R getClassifyById(Long id) {
        ManaClassifyEntity manaClassifyEntity = manaClassifyManager.getClassifyById(id);
        return CommonUtils.msg(manaClassifyEntity);
    }

    @Override
    public R removeClassify(Long id) {
        if (manaClassifyManager.hasChildren(id)) {
            return R.error(MsgConstant.MSG_HAS_CHILD);
        }
        int count = manaClassifyManager.remove(id);
        return CommonUtils.msg(count);
    }

}
