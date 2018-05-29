package net.chenlin.dp.manage.material.service.impl;

import net.chenlin.dp.common.constant.ClassifyTypeConstant;
import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.material.service.ManaMaterialClassifyService;
import net.chenlin.dp.manage.terminal.entity.ManaClassifyEntity;
import net.chenlin.dp.manage.terminal.manager.ManaClassifyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service("ManaMaterialClassifyService")
public class ManaMaterialClassifyServiceImpl implements ManaMaterialClassifyService {

    @Autowired
    private ManaClassifyManager manaClassifyManager;

    @Override
    public R listClassifyByParentId(Query query) {
        query.put("type", ClassifyTypeConstant.CLASSIFY_TYPE_MATERIAL);
        List<ManaClassifyEntity> list = manaClassifyManager.listClassify(query);
        for (ManaClassifyEntity terminalClassify : list) {
            terminalClassify.checkParent();
        }
        return CommonUtils.msgNotCheckNull(list);
    }

    @Override
    public R saveClassify(ManaClassifyEntity manaClassifyEntity) {
        manaClassifyEntity.setType(ClassifyTypeConstant.CLASSIFY_TYPE_MATERIAL);
        int count = manaClassifyManager.saveClassify(manaClassifyEntity);
        return CommonUtils.msg(count);
    }

    @Override
    public R listClassifyNotParentId(Query query) {
        query.put("type", ClassifyTypeConstant.CLASSIFY_TYPE_MATERIAL);
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
