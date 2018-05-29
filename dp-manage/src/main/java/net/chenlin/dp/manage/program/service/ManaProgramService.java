package net.chenlin.dp.manage.program.service;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;

/**
 * 节目Service 接口层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/10 15:22
 */
public interface ManaProgramService {

    /**
     * 分页查询 节目列表
     * @param params 节目的参数信息
     * @return
     */
    Page<ManaProgramDTO> listProgramByPage(Map<String, Object> params);

    /**
     * 获取没被节目单选中的节目列表
     * @param params
     * @return
     */
    Page<ManaProgramDTO> listSelectProgramByPage(Map<String, Object> params);

    /**
     * 根据节目id 获取节目信息
     * @param id
     * @return
     */
    R getProgramById(Long id);

    /**
     * 保存节目信息
     * @param manaProgramDTO
     * @return
     */
    R saveProgram(ManaProgramDTO manaProgramDTO);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    R batchRemove(Long[] ids);

    /**
     * 更新节目信息
     * @param program
     * @return
     */
    R updateProgram(ManaProgramDTO program);

}
