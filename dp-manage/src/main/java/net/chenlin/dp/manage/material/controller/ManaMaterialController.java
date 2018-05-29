package net.chenlin.dp.manage.material.controller;

import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.controller.AbstractController;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.manage.material.dto.ManaMaterialDTO;
import net.chenlin.dp.manage.material.service.ManaMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 素材类controller
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 10:41
 */

@RestController
@RequestMapping("/manage/material")
public class ManaMaterialController extends AbstractController {

    @Autowired
    private ManaMaterialService manaMaterialService;

    @RequestMapping("/list")
    public Page<ManaMaterialDTO> listMaterialByPage(@RequestBody Map<String, Object> params) {
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        return manaMaterialService.listMaterialByPage(params);
    }

    @RequestMapping("/save")
    public R saveMaterialController(ManaMaterialDTO manaMaterialDTO) {
        manaMaterialDTO.setCreateUserId(getUserId());
        return manaMaterialService.saveMaterial(manaMaterialDTO);
    }

    @RequestMapping("/listSelectMaterial")
    public Page<ManaMaterialDTO> listSelectMaterialByPage(@RequestBody Map<String, Object> params) {
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        return manaMaterialService.listSelectMaterialByPage(params);
    }

    @RequestMapping("/remove")
    public R batchRemove(@RequestBody Long[] ids) {
        return manaMaterialService.batchRemove(ids);
    }

    @RequestMapping("/info")
    public R info(@RequestBody Long id) {
        return manaMaterialService.getMaterialById(id);
    }

    @RequestMapping("/update")
    public R update(@RequestBody ManaMaterialDTO manaMaterialDTO) {
        return manaMaterialService.updateMaterial(manaMaterialDTO);
    }

}
