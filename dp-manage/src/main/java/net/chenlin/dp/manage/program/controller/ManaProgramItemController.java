package net.chenlin.dp.manage.program.controller;

import com.alibaba.fastjson.JSON;
import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.controller.AbstractController;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramManager;
import net.chenlin.dp.manage.program.manager.ManaProgramMaterialManager;
import net.chenlin.dp.manage.program.service.ManaProgramMaterialService;
import net.chenlin.dp.manage.program.service.ManaProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节目管理 controller
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/10 10:26
 */
@Controller
@RequestMapping("/manage/program")
public class ManaProgramItemController extends AbstractController {

    @Autowired
    private ManaProgramService manaProgramService;

    @Autowired
    private ManaProgramMaterialService manaProgramMaterialService;

    @Autowired
    private ManaProgramManager manaProgramManager;

    @Autowired
    private ManaProgramMaterialManager manaProgramMaterialManager;

    @ResponseBody
    @RequestMapping("/list")
    public Page<ManaProgramDTO> listProgramByPage(@RequestBody Map<String, Object> params) {
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        return manaProgramService.listProgramByPage(params);
    }

    @ResponseBody
    @RequestMapping("/info")
    public R info(@RequestBody Long id) {
        return manaProgramService.getProgramById(id);
    }

    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestBody ManaProgramDTO manaProgramDTO) {
        manaProgramDTO.setCreateUserId(getUserId());
        return manaProgramService.saveProgram(manaProgramDTO);
    }

    @SysLog("删除节目")
    @ResponseBody
    @RequestMapping("/remove")
    public R batchRemove(@RequestBody Long[] ids) {
        return manaProgramService.batchRemove(ids);
    }

    @SysLog("更新节目信息")
    @ResponseBody
    @RequestMapping("/update")
    public R updateProgram(@RequestBody ManaProgramDTO programDTO) {
        // 除了超级管理员外，其他用户只能更新自己的节目信息
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            programDTO.setCreateUserId(getUserId());
        }
        return manaProgramService.updateProgram(programDTO);
    }

    /**
     * 节目布局页跳转
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/layout/{id}")
    public String toLayoutPage(@PathVariable("id") Long id, Model model) {

        R r = manaProgramService.getProgramById(id);
        ManaProgramDTO manaProgramDTO = (ManaProgramDTO)r.get("rows");
        model.addAttribute("program", manaProgramDTO);

        return "manage/program/program_layout.html";
    }

    /**
     * 节目素材插入页跳转
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/material/{id}")
    public String toMaterialPage(@PathVariable("id") Long id, Model model) {

        R r = manaProgramService.getProgramById(id);
        ManaProgramDTO manaProgramDTO = (ManaProgramDTO)r.get("rows");
        model.addAttribute("program", manaProgramDTO);

        return "manage/program/program_material.html";
    }

    /**
     * 获取没被某节目单选中的节目列表
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/listSelectProgram")
    public Page<ManaProgramDTO> listSelectProgramByPage(@RequestBody Map<String, Object> params) {
        params.put("createUserId", getUserId());
        return manaProgramService.listSelectProgramByPage(params);
    }

    /**
     * 分页获取节目素材列表
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/listProgramMaterial")
    public Page<ManaProgramMaterialDTO> listProgramMaterialByPage(@RequestBody Map<String, Object> params) {
        return manaProgramMaterialService.listProgramMaterialByPage(params);
    }

    /**
     * 保存节目的素材列表
     * @param params 必带三个参数 String itemId 、 Long programId 、Long[] ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveMaterialList")
    public R saveMaterialList(@RequestBody Map<String, Object> params) {
        return manaProgramMaterialService.saveMaterialList(params);
    }

    /**
     * 批量移除节目素材信息
     * @param params 必带三个参数 String itemId 、 Long programId 、Long[] ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeProgramMaterial")
    public R batchRemoveProgramMaterial(@RequestBody Map<String, Object> params) {
        return manaProgramMaterialService.batchRemove(params);
    }

    /**
     * 移动节目的播放关系
     * @param params 必带四个参数 Long id 、 String itemId 、 Long programId 、int sortNum
     * @return
     */
    @ResponseBody
    @RequestMapping("/moveProgramMaterial")
    public R moveProgramMaterial(@RequestBody Map<String, Object> params) {
        return manaProgramMaterialService.moveMaterial(params);
    }

    /**
     * 跳转的节目预览页面
     * @param model
     * @param id   节目id
     * @return
     */
    @RequestMapping("/toPreview/{id}")
    public String toPreview(@PathVariable("id") Long id,Model model) {
        if (!CommonUtils.isIntThanZero(id.intValue())) {
            return "";
        }
        ManaProgramDTO programDTO = manaProgramManager.getProgramById(id);
        List<String> itemIdList = manaProgramMaterialService.listItemIdsByProgramId(id);
        //寻找素材
        if (!CommonUtils.isNullOrEmpty(itemIdList)) {
            Map itemMaterialMap = new HashMap<>();
            Query search = new Query();
            search.put("programId", id);
            for (String itemId : itemIdList) {
                search.put("itemId", itemId);
                List<ManaProgramMaterialDTO> itemList = manaProgramMaterialManager.listProgramMaterial(search);
                itemMaterialMap.put(itemId, itemList);
            }
            model.addAttribute("itemMaterialMap", itemMaterialMap);
        }
        String itemJson = programDTO.getItems();
        List<Map> mapList = JSON.parseArray(itemJson, Map.class);
        if (mapList != null) {
            Map itemPositionMap = new HashMap<>();
            for (Map map : mapList) {
                itemPositionMap.put(map.get("id") + "", map);
            }
            model.addAttribute("itemPositionMap", itemPositionMap);
        }
        model.addAttribute("program", programDTO);

        return "manage/program/program_preview.html";
//        return "manage/program/program_preview_test.html";
    }

}
