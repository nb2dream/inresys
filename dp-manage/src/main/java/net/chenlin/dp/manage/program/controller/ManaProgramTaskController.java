package net.chenlin.dp.manage.program.controller;

import net.chenlin.dp.common.constant.ProgramTaskStatusConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.controller.AbstractController;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.common.utils.DownloadUtils;
import net.chenlin.dp.common.utils.FileUtils;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;
import net.chenlin.dp.manage.program.service.ManaProgramTaskRelationService;
import net.chenlin.dp.manage.program.service.ManaProgramTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * 节目单controller 层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/14 8:49
 */
@RestController
@RequestMapping("/manage/programTask")
public class ManaProgramTaskController extends AbstractController {

    @Autowired
    private ManaProgramTaskService manaProgramTaskService;

    @Autowired
    private ManaProgramTaskRelationService manaProgramTaskRelationService;

    @RequestMapping("/list")
    public Page<ManaProgramTaskDTO> listProgramByPage(@RequestBody Map<String, Object> params) {
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        return manaProgramTaskService.listProgramTaskByPage(params);
    }

    @RequestMapping("/save")
    public R saveProgramTask(@RequestBody ManaProgramTaskDTO manaProgramTaskDTO) {
        manaProgramTaskDTO.setCreateUserId(getUserId());
        manaProgramTaskDTO.setStatus(ProgramTaskStatusConstant.TASK_STATUS_NOT_CHECK);  // 默认状态未审核
        manaProgramTaskDTO.setVersion(1);
        return manaProgramTaskService.saveProgramTask(manaProgramTaskDTO);
    }

    @RequestMapping("/info")
    public R getProgramTaskById(@RequestBody Long id) {
        return manaProgramTaskService.getProgramTaskById(id);
    }

    @RequestMapping("/update")
    public R updateProgramTask(@RequestBody ManaProgramTaskDTO manaProgramTaskDTO) {
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            manaProgramTaskDTO.setCreateUserId(getUserId());
        }
        return manaProgramTaskService.updateProgramTask(manaProgramTaskDTO);
    }

    @RequestMapping("/remove")
    public R batchRemove(@RequestBody Long[] ids) {
        return manaProgramTaskService.batchRemove(ids);
    }

    @RequestMapping("/list_relation")
    public Page<ManaProgramTaskRelationDTO> listProgramTaskRelationByPage(@RequestBody Map<String, Object> params) {
        return manaProgramTaskRelationService.listProgramTaskRelationByPage(params);
    }

    /**
     * 修改节目单下的节目的播放顺序
     * @param params 三个参数必带 id  taskId  playerNum
     * @return
     */
    @RequestMapping("/moveRelation")
    public R moveRelation(@RequestBody Map<String, Object> params) {
        return manaProgramTaskRelationService.moveRelation(params);
    }

    /**
     * 保存节目单的节目信息
     * @param params
     * @return
     */
    @RequestMapping("/save_program_list")
    public R saveProgramList(@RequestBody Map<String, Object> params) {
        return manaProgramTaskRelationService.saveProgramList(params);
    }

    /**
     * 批量移除节目关系
     * @param params 必带俩个参数 Long taskId 、 Long[] ids
     * @return
     */
    @RequestMapping("/remove_relation")
    public R removeRelation(@RequestBody Map<String, Object> params) {
        return manaProgramTaskRelationService.batchRemove(params);
    }

    /**
     * 审核
     * @param params 带两个参数 节目审核的状态值 status 节目单id数值  ids
     * @return
     */
    @RequestMapping("/check")
    public R check(@RequestBody Map<String, Object> params) {
        return manaProgramTaskService.check(params);
    }

    /**
     * 根据节目单节目关系id，更新信息
     * @param manaProgramTaskRelationDTO
     * @return
     */
    @RequestMapping("/update_relation")
    public R updateProgramTaskRelation(@RequestBody ManaProgramTaskRelationDTO manaProgramTaskRelationDTO) {
        return manaProgramTaskRelationService.updateProgramTaskRelation(manaProgramTaskRelationDTO);
    }

    /**
     * 根据用户id 获取其节目单
     * @return
     */
    @RequestMapping("/list_isPass_task")
    public R listIsPassTask() {
       /* if (getUserId() != SystemConstant.SUPER_ADMIN) {
            return manaProgramTaskService.listIsPassTask();
        }*/
        //管理员可看到全部分组
        return manaProgramTaskService.listIsPassTask(getUserId());
    }

    /**
     * 节目单下载
     * @param taskId    节目单id
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download_task")
    @ResponseBody
    public R downloadTask(@RequestParam(value = "taskId", required = false) Long taskId,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        if (taskId == null || !CommonUtils.isIntThanZero(taskId.intValue())) {
            return R.error("参数错误");
        }

        // tomcat tmp 文件夹路径
        String savePath = System.getProperty("java.io.tmpdir");
        // 压缩节目单文件
        String fileName = manaProgramTaskService.zipTask(taskId, savePath);
        try {
            DownloadUtils.downloadRealPath(fileName, savePath + "/" + fileName, request, response);
        } catch (Exception e) {
            return R.error("未知错误");
        } finally {
            // 一定要删除
            FileUtils.deleteFile(savePath, fileName);
        }
        return R.ok();
    }

}


