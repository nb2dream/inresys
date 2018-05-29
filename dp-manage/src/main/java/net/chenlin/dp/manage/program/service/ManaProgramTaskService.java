package net.chenlin.dp.manage.program.service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;

import java.util.List;
import java.util.Map;

/**
 * 节目单单service层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/13 18:15
 */
public interface ManaProgramTaskService {

    /**
     * 分页查询 节目单列表
     * @param params 节目单的参数信息
     * @return
     */
    Page<ManaProgramTaskDTO> listProgramTaskByPage(Map<String, Object> params);

    /**
     * 根据节目单id 获取节目单信息
     * @param id
     * @return
     */
    R getProgramTaskById(Long id);

    /**
     * 保存节目单信息
     * @param manaProgramTaskDTO
     * @return
     */
    R saveProgramTask(ManaProgramTaskDTO manaProgramTaskDTO);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    R batchRemove(Long[] ids);

    /**
     * 更新节目单信息
     * @param ProgramTask
     * @return
     */
    R updateProgramTask(ManaProgramTaskDTO ProgramTask);

    /**
     * 申请节目单审核
     * @param ids
     * @return
     */
    R applyCheck(Long[] ids, Long createUserId);

    /**
     * 节目单审核
     * @param params
     * @return
     */
    R check(Map<String, Object> params);


    /**
     * 根据用户id 获取其已经通过审核的节目单列表
     * @param createUserId
     * @return
     */
    R listIsPassTask(Long createUserId);

    /**
     * 生成节目单配置json串
     * @param taskId
     * @return
     */
    String generateJson(Long taskId);

    /**
     * 打包节目单
     * @param taskId
     * @return
     */
    R publishTask(Long taskId);

    /**
     * 压缩节目单
     * @param taskId
     * @param savePath
     * @return
     */
    String zipTask(Long taskId, String savePath);

}
