package net.chenlin.dp.manage.program.service.impl;

import com.alibaba.fastjson.JSONArray;
import net.chenlin.dp.common.constant.ProgramTaskStatusConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.exception.RRException;
import net.chenlin.dp.common.utils.*;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramTaskRelationDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramManager;
import net.chenlin.dp.manage.program.manager.ManaProgramMaterialManager;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskManager;
import net.chenlin.dp.manage.program.manager.ManaProgramTaskRelationManager;
import net.chenlin.dp.manage.program.service.ManaProgramTaskService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/13 18:18
 */
@Service("manaProgramTaskService")
public class ManaProgramTaskServiceImpl implements ManaProgramTaskService {

    @Autowired
    private ManaProgramTaskManager manaProgramTaskManager;

    @Autowired
    private ManaProgramTaskRelationManager manaProgramTaskRelationManager;

    @Autowired
    private ManaProgramMaterialManager manaProgramMaterialManager;

    @Autowired
    private ManaProgramManager manaProgramManager;

    @Value("${material.savePath}")
    private String matSavePath;

    /**
     * 静态文件保存域名地址
     */
    @Value("${webfile.domain}")
    private String domain;

    /**
     * 节目单生成保存路径
     */
    @Value("${task.savePath}")
    private String baseSavePath;

    @Override
    public Page<ManaProgramTaskDTO> listProgramTaskByPage(Map<String, Object> params) {
        Query query = new Query(params);
        Page<ManaProgramTaskDTO> page = new Page<>(query);
        manaProgramTaskManager.listProgramByPage(page, query);
        return page;
    }

    @Override
    public R getProgramTaskById(Long id) {
        ManaProgramTaskDTO manaProgramTaskDTO = manaProgramTaskManager.getProgramTaskById(id);
        return CommonUtils.msg(manaProgramTaskDTO);
    }

    @Override
    public R saveProgramTask(ManaProgramTaskDTO manaProgramTaskDTO) {
        int count = manaProgramTaskManager.saveProgramTask(manaProgramTaskDTO);
        return CommonUtils.msg(count);
    }

    @Override
    public R batchRemove(Long[] ids) {
        //TODO 未添加验证，判断节目单下是否有节目 ，有节目则无法删除
        int count = manaProgramTaskManager.batchRemove(ids);
        return CommonUtils.msg(count);
    }

    @Override
    public R updateProgramTask(ManaProgramTaskDTO ProgramTask) {
        int count = manaProgramTaskManager.updateProgramTask(ProgramTask);
        return CommonUtils.msg(count);
    }

    @Override
    public R applyCheck(Long[] ids, Long createUserId) {
        if (CommonUtils.isNullOrEmpty(createUserId) || CommonUtils.isNullOrEmpty(ids)) {
            return R.error();
        }
        ManaProgramTaskDTO bean = new ManaProgramTaskDTO();
        for (Long id : ids) {
            bean.setId(id);
            bean.setCreateUserId(createUserId);
            bean.setStatus(ProgramTaskStatusConstant.TASK_STATUS_WAIT_CHECK);
            manaProgramTaskManager.updateProgramTask(bean);
        }
        return CommonUtils.msg(1);
    }

    @Override
    public R check(Map<String, Object> params) {
        String statusStr = (String) params.get("status");
        Integer status = Integer.parseInt(statusStr);
        List<Integer> ids = (List<Integer>) params.get("ids");
        if (CommonUtils.isNullOrEmpty(status) && CommonUtils.isNullOrEmpty(ids)) {
            throw new RRException("参数错误");
        }
        ManaProgramTaskDTO bean = new ManaProgramTaskDTO();
        for (Integer ido : ids) {
            Long id = ido.longValue();
            bean.setId(id);
            bean.setStatus(status);
            manaProgramTaskManager.updateProgramTask(bean);
        }
        return CommonUtils.msg(1);
    }


    /**
     * 生成节目单json字符串
     *
     * @param taskId
     * @return
     */
    @Override
    public String generateJson(Long taskId) {
        // TODO json的生成可以再抽象封装
        JSONObject rootJson = new JSONObject();
//        com.alibaba.fastjson.JSONObject aliRootJson = new com.alibaba.fastjson.JSONObject();
        List<Map<String, Object>> programList = new ArrayList<>();

        List<ManaProgramTaskRelationDTO> relationList = manaProgramTaskRelationManager.listProgramTaskRelationByTaskId(taskId);
        if (relationList != null && !relationList.isEmpty()) {
            for (ManaProgramTaskRelationDTO relationitem : relationList) {
                Map<String, Object> programItemMap = new HashMap<>();
                Map<String, Object> timingMap = new HashMap<>();
                Map<String, Object> layoutMap = new HashMap<>();
                List<Map<String, Object>> matItemList = new ArrayList<>();
                if(relationitem.getBeginDate() != null) {
                    timingMap.put("beginDate", relationitem.getBeginDate());
                } else {
                    timingMap.put("beginDate", "2018-05-01");
                }
                if(relationitem.getEndDate() != null) {
                    timingMap.put("endDate", relationitem.getEndDate());
                } else {
                    timingMap.put("endDate", "2058-05-01");
                }
                if(relationitem.getBeginTime() != null) {
                    timingMap.put("beginTime", relationitem.getBeginTime());
                } else {
                    timingMap.put("beginTime", "00:00:00");
                }
                if(relationitem.getEndTime() != null) {
                    timingMap.put("endTime", relationitem.getEndTime());
                } else {
                    timingMap.put("endTime", "00:00:00");
                }
                programItemMap.put("playNum", relationitem.getPlayerNum());
                //获取素材
                List<ManaProgramMaterialDTO> programMaterialList = manaProgramMaterialManager.listProgramMaterialByProgramId(relationitem.getProgramId());
                Set resourceList = new HashSet();  // 儲存素材鏈接
                for (ManaProgramMaterialDTO programMaterialItem : programMaterialList) {
                    Map<String, Object> matItemMap = new HashMap<>();
                    matItemMap.put("sortNum", programMaterialItem.getSortNum());
                    matItemMap.put("itemId", programMaterialItem.getItemId());
                    System.out.println(programMaterialItem.getManaMaterialDTO());
                    matItemMap.put("matName", programMaterialItem.getManaMaterialDTO().getMaterialName());
                    Integer type = programMaterialItem.getManaMaterialDTO().getType();
                    matItemMap.put("type", type);
                    matItemMap.put("fileSize", programMaterialItem.getManaMaterialDTO().getFileSize());
                    matItemMap.put("suffix", programMaterialItem.getManaMaterialDTO().getSuffix());
                    matItemMap.put("path", programMaterialItem.getManaMaterialDTO().getPath());
                    matItemMap.put("content", programMaterialItem.getManaMaterialDTO().getContent());
                    if (type == 1 || type == 2 || type == 3) {
                        resourceList.add(domain + programMaterialItem.getManaMaterialDTO().getPath());
                    }
                    matItemList.add(matItemMap);
                }
                ManaProgramDTO manaProgramDTO = manaProgramManager.getProgramById(relationitem.getProgramId());
                layoutMap.put("width", manaProgramDTO.getWidth());
                layoutMap.put("height", manaProgramDTO.getHeight());
                layoutMap.put("items", JSONArray.parseArray(manaProgramDTO.getItems().replaceAll("\\.[0-9]+", "")));
                String items = manaProgramDTO.getItems().replaceAll("\\.[0-9]+", "");
                System.out.println(items);
                programItemMap.put("matItem", matItemList);
                programItemMap.put("timing", timingMap);
                programItemMap.put("layout", layoutMap);
                programItemMap.put("resource", resourceList);
                programList.add(programItemMap);
            }
            String bankPlayInfo = "0100261000000109010103010100ABCD0f";
            rootJson.put("bankPlayInfo", bankPlayInfo);
            rootJson.put("programList", programList);
            return rootJson.toJSONString();
        }
        return null;
    }


    @Override
    public R listIsPassTask(Long createUserId) {
        List<ManaProgramTaskDTO> list = manaProgramTaskManager.listIsPassTask(createUserId);
        return R.ok().put(SystemConstant.DATA_ROWS, list);
    }

    /**
     * 生成节目单
     *
     * @param taskId
     * @return
     */
    @Override
    public R publishTask(Long taskId) {
        if (CommonUtils.isNullOrEmpty(taskId) || !CommonUtils.isIntThanZero(taskId.intValue())) {
            return R.error("请求参数错误");
        }
        // 打包资源
        boolean result = packTask(taskId);
        if (!result) {
            return R.error();
        }
        return CommonUtils.msg(1);
    }

    /**
     * 压缩节目单
     *
     * @param taskId
     * @param savePath
     * @return
     */
    @Override
    public String zipTask(Long taskId, String savePath) {
        boolean result = packTask(taskId);
        if (result) {
            ManaProgramTaskDTO bean = manaProgramTaskManager.getProgramTaskById(taskId);
            if (bean == null && !StringUtils.isNotBlank(bean.getPublishPath())) {
                return null;
            }
            String srcDir = baseSavePath + "/" + bean.getPublishPath();  // 要压缩的目录
            String zipFileName = taskId + "_v" + bean.getVersion() + ".zip";//文件名：节目单ID+版本号
            String zipFile = savePath + "/" + zipFileName;  //压缩后保存的路径

            ZipUtils zipUtils = new ZipUtils(zipFile);
            zipUtils.compressExe(srcDir);
            return zipFileName;
        } else {
            throw new RRException("节目单压缩失败");
        }
    }

    /**
     * 打包节目单资源
     *
     * @param taskId 节目单id
     * @return
     */
    public boolean packTask(Long taskId) {
        // tomcat tmp 文件夹路径
        System.getProperty("java.io.tmpdir");

        ManaProgramTaskDTO manaProgramTaskDTO = manaProgramTaskManager.getProgramTaskById(taskId);
        if (CommonUtils.isNullOrEmpty(manaProgramTaskDTO)) {
            return false;
        } else {
            String oldPath = manaProgramTaskDTO.getPublishPath();
            if (StringUtils.isNotEmpty(oldPath)) {
                FileUtils.delFolder(baseSavePath + "/" + oldPath);
            }
        }

        // 查找节目单信息
        List<ManaProgramTaskRelationDTO> relationList = manaProgramTaskRelationManager.listProgramTaskRelationByTaskId(taskId);
        if (relationList != null && !relationList.isEmpty()) {
            //创建节目单保存目录
            String savePath = DateUtils.format(new Date(), "yyyyMMdd") + "/" + taskId;
            File dir = new File(baseSavePath + "/" + savePath);
            String taskSaveDir = baseSavePath + "/" + savePath;
            if (!dir.exists()) {
                dir.mkdirs();
            }
            for (ManaProgramTaskRelationDTO relationitem : relationList) {
                // 获取资源
                List<ManaProgramMaterialDTO> programMaterialList = manaProgramMaterialManager.listProgramMaterialByProgramId(relationitem.getProgramId());
                for (ManaProgramMaterialDTO programMaterialItem : programMaterialList) {
                    String fileName = programMaterialItem.getManaMaterialDTO().getPath();
                    FileUtils.copyFileAndDir(matSavePath + "/" + fileName, taskSaveDir + "/program/" + fileName, true);
                }
            }
            // 更新保存路径
            ManaProgramTaskDTO bean = new ManaProgramTaskDTO();
            bean.setId(taskId);
            bean.setPublishPath(savePath);
            manaProgramTaskManager.updateProgramTask(bean);

            // 根据节目单信息生成json文件
            String jsonStr = generateJson(taskId);
            if (jsonStr == null || jsonStr.trim() == "") {
                return false;
            }
            try {
                Boolean result = JSONUtils.jsonStrToFile(jsonStr, "taskConfig", taskSaveDir + "/");
                if (!result) return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        // 压缩打包节目单资源，保存到tomcat服务器的临时目录下
        // 生成单用户下载完成后删除该文件
        return false;
    }


}
