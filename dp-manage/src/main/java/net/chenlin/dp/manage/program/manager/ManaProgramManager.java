package net.chenlin.dp.manage.program.manager;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import net.chenlin.dp.manage.program.entity.ManaProgramEntity;

import java.util.List;

/**
 * 节目 manager 接口层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/10 15:14
 */
public interface ManaProgramManager {

    List<ManaProgramDTO> listProgramByPage(Page<ManaProgramDTO> page, Query search);

    ManaProgramDTO getProgramById(Long id);

    int saveProgram(ManaProgramDTO program);

    int batchRemove(Long[] ids);

    int updateProgram(ManaProgramDTO program);

    /**
     * 获取没被节目单选中的节目列表
     * @param page
     * @param query
     * @return
     */
    List<ManaProgramDTO> listSelectProgramByPage(Page<ManaProgramDTO> page, Query query);

    /**
     * 判断是否有子数据
     * @param id
     * @return
     */
    Boolean hasChildren(Long id);

}
