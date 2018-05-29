package net.chenlin.dp.manage.program.service.impl;

import net.chenlin.dp.common.constant.ProgramTaskStatusConstant;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.exception.RRException;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskManager;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskRelationManager;
import net.chenlin.dp.manage.program.service.ManaProgramTaskRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/14 17:24
 */
@Service("manaProgramTaskRelationService")
public class ManaProgramTaskRelationServiceImpl implements ManaProgramTaskRelationService {

    @Autowired
    private ManaProgramTaskRelationManager manaProgramTaskRelationManager;

    @Autowired
    private ManaProgramTaskManager manaProgramTaskManager;

    @Override
    public Page<ManaProgramTaskRelationDTO> listProgramTaskRelationByPage(Map<String, Object> params) {
        Query query = new Query(params);
        Page<ManaProgramTaskRelationDTO> page = new Page<>(query);
        manaProgramTaskRelationManager.listProgramTaskRelationByPage(page, query);
        return page;
    }

    @Override
    public R saveProgramTaskRelation(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO) {
        int count = manaProgramTaskRelationManager.save(manaProgramTaskRelationDTO);
        return CommonUtils.msg(count);
    }

    @Override
    public R updateProgramTaskRelation(ManaProgramTaskRelationDTO manaProgramTaskRelationDTO) {
        String begin_time = manaProgramTaskRelationDTO.getBeginTime();
        String end_time = manaProgramTaskRelationDTO.getEndTime();
        if (StringUtils.isNotBlank(begin_time) && StringUtils.isNotBlank(end_time)) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            try {
                Date beginTime = df.parse(begin_time);
                Date endTime = df.parse(end_time);
                if (endTime.getTime() < beginTime.getTime()) {
                    throw new RRException("播发时间设置错误，保存失败；结束时间应迟于开始时间");
                }
            } catch (ParseException e) {
//                throw new RRException("");
            }
        } else {
            throw new RRException("请设置日期时间");
        }
        int count = manaProgramTaskRelationManager.update(manaProgramTaskRelationDTO);
        return CommonUtils.msg(count);
    }

    @Override
    public R countTotalByTaskId(Long taskId) {
        int reslt = manaProgramTaskRelationManager.countTotalByTaskId(taskId);
        return CommonUtils.msgNotCheckNull(reslt);
    }

    @Override
    public R moveRelation(Map<String, Object> params) {
        Query query = new Query(params);
        Long id = query.getAsLong("id");
        Long taskId = query.getAsLong("taskId");
        Integer playerNum = query.getAsInt("playerNum"); // 变化后的 playerNum
        // 验证参数
        if (!CommonUtils.isIntThanZero(taskId.intValue())) {
            return R.error();
        }
        int count = manaProgramTaskRelationManager.countTotalByTaskId(taskId);
        if (playerNum > count) {
            playerNum = count;
        }
        if (playerNum <= 0) {
            playerNum = 1;
        }
        ManaProgramTaskRelationDTO bean = manaProgramTaskRelationManager.getRelationSingle(query);
        if (CommonUtils.isNullOrEmpty(bean)) {
            return R.error();
        }
        // 要变的那个节目
        ManaProgramTaskRelationDTO exchangeBean = manaProgramTaskRelationManager.getRelationById(id);
        bean.setPlayerNum(exchangeBean.getPlayerNum());
        exchangeBean.setPlayerNum(playerNum);
        manaProgramTaskRelationManager.update(bean);
        manaProgramTaskRelationManager.update(exchangeBean);

        return R.ok();
    }

    @Override
    public R saveProgramList(Map<String, Object> params) {
        Query query = new Query(params);
        Long taskId = query.getAsLong("taskId");
        List<Integer> ids = (List<Integer>) params.get("ids");
        if (CommonUtils.isNullOrEmpty(taskId) && CommonUtils.isNullOrEmpty(ids)) {
            return R.error();
        }
        int count = manaProgramTaskRelationManager.countTotalByTaskId(taskId);
        for (Integer ido : ids) {
            Long id = ido.longValue();
            count++;
            ManaProgramTaskRelationDTO bean = new ManaProgramTaskRelationDTO();
            bean.setTaskId(taskId);
            bean.setProgramId(id);
            bean.setPlayerNum(count);
            manaProgramTaskRelationManager.save(bean);
        }
        ManaProgramTaskDTO bean = new ManaProgramTaskDTO();
        bean.setId(taskId);
        bean.setStatus(ProgramTaskStatusConstant.TASK_STATUS_NOT_CHECK);  // 由于更新了节目单，所以节目需要重新审核
        manaProgramTaskManager.updateProgramTask(bean);
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
        int count = manaProgramTaskRelationManager.batchRemove(ids);
        if (!CommonUtils.isIntThanZero(count)) {  // 如果删除失败，直接结束
            return CommonUtils.msg(count);
        }
        List<ManaProgramTaskRelationDTO> list = manaProgramTaskRelationManager.listProgramTaskRelation(query);
        i = 1;
        for (ManaProgramTaskRelationDTO bean : list) {
            bean.setPlayerNum(i);
            manaProgramTaskRelationManager.update(bean);
            i++;
        }
        return CommonUtils.msg(1);
    }

}
