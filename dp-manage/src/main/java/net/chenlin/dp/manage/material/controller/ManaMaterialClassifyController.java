package net.chenlin.dp.manage.material.controller;

import net.chenlin.dp.common.annotation.SysLog;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.controller.AbstractController;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.material.service.ManaMaterialClassifyService;
import net.chenlin.dp.manage.terminal.entity.ManaClassifyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  素材 分组
 */
@RestController
@RequestMapping("/manage/materialClassify")
public class ManaMaterialClassifyController extends AbstractController {

    @Autowired
    private ManaMaterialClassifyService manaMaterialClassifyService;

    /**
     * 根据父级id 查找子级
     * @return
     */
    @RequestMapping("/select")
    public R select(@RequestParam(value = "parentId", defaultValue = "0")Long parentId) {
        Query query = new Query();
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            query.put("createUserId", getUserId());
        }
        query.put("parentId", parentId);
        return manaMaterialClassifyService.listClassifyByParentId(query);
    }

    /**
     * 获取全部分组
     * @return
     */
    @RequestMapping("/list")
    public R list() {
        Query query = new Query();
        if (getUserId() != SystemConstant.SUPER_ADMIN) {
            query.put("createUserId", getUserId());
        }
        return manaMaterialClassifyService.listClassifyNotParentId(query);
    }

    @SysLog("新增分组")
    @RequestMapping("/save")
    public R saveClassify(@RequestBody ManaClassifyEntity manaClassifyEntity) {
        manaClassifyEntity.setCreateUserId(getUserId());
        return manaMaterialClassifyService.saveClassify(manaClassifyEntity);
    }

    @SysLog("修改分组")
    @RequestMapping("/edit")
    public R editClassify(@RequestBody ManaClassifyEntity manaClassifyEntity) {
        return manaMaterialClassifyService.updateClassify(manaClassifyEntity);
    }

    @RequestMapping("/info")
    public R getClassifyById(@RequestBody Long id) {
        return manaMaterialClassifyService.getClassifyById(id);
    }

    @SysLog("删除分组")
    @RequestMapping("/remove")
    public R removeClassify(@RequestBody Long id) {
        return manaMaterialClassifyService.removeClassify(id);
    }


}
