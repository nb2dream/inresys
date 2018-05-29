package net.chenlin.dp.manage.program.service.impl;

import net.chenlin.dp.common.constant.ProgramTaskStatusConstant;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramMaterialManager;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskManager;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskRelationManager;
import net.chenlin.dp.manage.program.service.ManaProgramMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/21 16:43
 */
@Service("manaProgramMaterialService")
public class ManaProgramMaterialServiceImpl implements ManaProgramMaterialService {

    @Autowired
    private ManaProgramMaterialManager manaProgramMaterialManager;

    @Autowired
    private ManaProgramTaskManager manaProgramTaskManager;

    @Autowired
    private ManaProgramTaskRelationManager manaProgramTaskRelationManager;

    @Override
    public Page<ManaProgramMaterialDTO> listProgramMaterialByPage(Map<String, Object> params) {
        Query query = new Query(params);
        Page<ManaProgramMaterialDTO> page = new Page<>(query);
        manaProgramMaterialManager.listProgramMaterialByPage(page, query);
        return page;
    }

    @Override
    public R saveMaterialList(Map<String, Object> params) {
        Query query = new Query(params);
        String itemId = query.getAsString("itemId");
        Long programId = query.getAsLong("programId");
        List<Integer> ids = (List<Integer>) query.get("ids");
        if (CommonUtils.isNullOrEmpty(itemId) && CommonUtils.isNullOrEmpty(ids) && CommonUtils.isNullOrEmpty(programId)) {
            return R.error();
        }
        int count = manaProgramMaterialManager.countMaterialInItemId(query);
        for (Integer ido : ids) {
            Long id = ido.longValue();
            count++;
            ManaProgramMaterialDTO bean = new ManaProgramMaterialDTO();
            bean.setItemId(itemId);
            bean.setProgramId(programId);
            bean.setMaterialId(id);
            bean.setSortNum(count);
            manaProgramMaterialManager.saveProgramMaterial(bean);
        }
        // 根据节目id查找是否有节目单使用该节目
        List<ManaProgramTaskRelationDTO> list = manaProgramTaskRelationManager.listProgramTaskRelationByProgramId(programId);
        if (list.size() > 0) {
            // TODO 更新应该改为批量更新
            //List<ManaProgramTaskDTO> progranTaskList = new ArrayList<>();
            for (ManaProgramTaskRelationDTO relation: list) {
                ManaProgramTaskDTO programTask = new ManaProgramTaskDTO();
                programTask.setId(relation.getTaskId());
                programTask.setStatus(ProgramTaskStatusConstant.TASK_STATUS_NOT_CHECK);
                // 如果有，更新节目单的状态为待审核
                manaProgramTaskManager.updateProgramTask(programTask);
            }
        }
        return CommonUtils.msg(1);
    }

    @Override
    public R batchRemove(Map<String, Object> params) {
        Query query = new Query(params);
        List<Integer> idsInt = (List<Integer>) params.get("ids");
        if (CommonUtils.isNullOrEmpty(params) && CommonUtils.isNullOrEmpty(idsInt)) {
            return R.error();
        }
        Long[] ids = new Long[idsInt.size()];
        int i = 0;
        for (Integer idInt : idsInt) {
            ids[i] = idInt.longValue();
            i++;
        }
        int count = manaProgramMaterialManager.batchRemove(ids);
        if (!CommonUtils.isIntThanZero(count)) {  // 如果删除失败，直接结束
            return CommonUtils.msg(count);
        }
        List<ManaProgramMaterialDTO> list = manaProgramMaterialManager.listProgramMaterial(query);
        i = 1;
        for (ManaProgramMaterialDTO bean: list) {
            bean.setSortNum(i);
            manaProgramMaterialManager.updateProgramMaterial(bean);
            i++;
        }
        return CommonUtils.msg(1);
    }

    @Override
    public R moveMaterial(Map<String, Object> params) {
        Query query = new Query(params);
        Long id = query.getAsLong("id");
        String itemId = query.getAsString("itemId");
        Long programId = query.getAsLong("programId");
        Integer sortNum = query.getAsInt("sortNum"); // 变化后的 sortNum
        // 验证参数
        if (CommonUtils.isNullOrEmpty(itemId) || !CommonUtils.isIntThanZero(programId.intValue()) || !CommonUtils.isIntThanZero(id.intValue())) {
            return R.error();
        }
        int count = manaProgramMaterialManager.countMaterialInItemId(query);
        if (sortNum > count) {
            sortNum = count;
        }
        if (sortNum <= 0) {
            sortNum = 1;
        }
        ManaProgramMaterialDTO bean = manaProgramMaterialManager.getProgramMaterialSingle(query);
        if (CommonUtils.isNullOrEmpty(bean)) {
            return R.error();
        }
        // 要变的内容
        ManaProgramMaterialDTO exchangeBean = manaProgramMaterialManager.getProgramMaterialById(id);
        bean.setSortNum(exchangeBean.getSortNum());
        exchangeBean.setSortNum(sortNum);
        manaProgramMaterialManager.updateProgramMaterial(bean);
        manaProgramMaterialManager.updateProgramMaterial(exchangeBean);

        return R.ok();
    }

    @Override
    public List<String> listItemIdsByProgramId(Long programId) {
        return manaProgramMaterialManager.listItemIdsByProgramId(programId);
    }
}
