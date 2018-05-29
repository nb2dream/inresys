package net.chenlin.dp.manage.program.service.impl;

import com.alibaba.fastjson.JSONArray;
import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.constant.ProgramTaskStatusConstant;
import net.chenlin.dp.common.constant.StatusConstant;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramManager;
import net.chenlin.dp.manage.program.manager.ManaProgramMaterialManager;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskManager;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskRelationManager;
import net.chenlin.dp.manage.program.service.ManaProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/10 15:25
 */
@Service("manaProgramService")
public class ManaProgramServiceImpl implements ManaProgramService {

    @Autowired
    private ManaProgramManager manaProgramManager;

    @Autowired
    private ManaProgramMaterialManager manaProgramMaterialManager;

    @Autowired
    private ManaProgramTaskRelationManager manaProgramTaskRelationManager;

    @Autowired
    private ManaProgramTaskManager manaProgramTaskManager;

    @Override
    public Page<ManaProgramDTO> listProgramByPage(Map<String, Object> params) {
        Query query = new Query(params);
        Page<ManaProgramDTO> page = new Page<>(query);
        manaProgramManager.listProgramByPage(page, query);
        return page;
    }

    @Override
    public Page<ManaProgramDTO> listSelectProgramByPage(Map<String, Object> params) {
        Query query = new Query(params);
        Page<ManaProgramDTO> page = new Page<>(query);
        manaProgramManager.listSelectProgramByPage(page, query);
        return page;
    }

    @Override
    public R getProgramById(Long id) {
        ManaProgramDTO manaProgramDTO = manaProgramManager.getProgramById(id);
        return CommonUtils.msgNotCheckNull(manaProgramDTO);
    }

    @Override
    public R saveProgram(ManaProgramDTO manaProgramDTO) {
        int count = manaProgramManager.saveProgram(manaProgramDTO);
        return CommonUtils.msg(count);
    }

    @Override
    public R batchRemove(Long[] ids) {
        // 判断节目是否有被节目单引用
        for (Long id : ids) {
            List<ManaProgramTaskRelationDTO> list = manaProgramTaskRelationManager.listProgramTaskRelationByProgramId(id);
            if (!CommonUtils.isListNullOrZeroEmpty(list)) {
                StringBuilder  errMsg = new StringBuilder ("删除失败!撤销关联节目后方可删除。使用该节目的节目单编号:");
                if(list.size() >= 0) {
                    errMsg.append(list.get(0).getTaskId());
                    for(int i = 1; i < list.size(); i++) {
                        errMsg.append("、" + list.get(i).getTaskId());
                    }
                }
                return R.error(errMsg.toString());
            }
        }
        // 移除节目的全部素材关系
        for (Long id : ids) {
            manaProgramMaterialManager.removeByProgramId(id);
        }
        int count = manaProgramManager.batchRemove(ids);
        return CommonUtils.msg(count);
    }

    @Override
    public R updateProgram(ManaProgramDTO program) {

        ManaProgramDTO bean = manaProgramManager.getProgramById(program.getId());

        JSONArray old_Items = JSONArray.parseArray(bean.getItems());
        JSONArray current_Items = JSONArray.parseArray(program.getItems());
        List<String> itemTemList = new ArrayList<String>();
        Query query = new Query();
        query.put("programId", program.getId());
        //TODO 判断逻辑太过冗余，需要改进
        if (old_Items != null && old_Items.size() > 0) {
            if (current_Items.size() == 0) {
                for (int i=0; i < old_Items.size(); i++) {
                    String item_id = (String) old_Items.getJSONObject(i).get("id");
                    // 只要有一个ItemId下有素材，就返回 保存失败
                    query.put("itemId", item_id);

                    if (manaProgramMaterialManager.countMaterialInItemId(query) != 0L) {
                        return R.error(MsgConstant.MSG_HAS_CHILD);
                    }
                }
            } else {
                // 比较哪个ItemId被移除了，然后判断下面是否有素材
                for (int i=0; i < old_Items.size(); i++) {
                    String item_id = (String) old_Items.getJSONObject(i).get("id");
                    int f = 0;
                    for (int t=0; t < current_Items.size(); t++) {
                        if (current_Items.getJSONObject(t).get("id").equals(item_id)) {
                            break;
                        } else {
                            f = t;
                        }
                    }
                    if (f == current_Items.size()-1) {
                        itemTemList.add(item_id);
                    }
                }
                for (String itemId : itemTemList) {
                    query.put("itemId", itemId);
                    if (manaProgramMaterialManager.countMaterialInItemId(query) != 0L) {
                        return R.error(MsgConstant.MSG_HAS_CHILD);
                    }
                }
            }
        }

        int count = manaProgramManager.updateProgram(program);
        return CommonUtils.msg(count);
    }
}
