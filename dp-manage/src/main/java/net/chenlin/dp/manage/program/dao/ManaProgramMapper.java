package net.chenlin.dp.manage.program.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 节目的 dao 接口
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/10 11:17
 */
@Component
public interface ManaProgramMapper extends BaseMapper<ManaProgramDTO> {

    /**
     * 获取没被节目单选中的节目列表
     * @param page
     * @param query
     * @return
     */
    List<ManaProgramDTO> listSelectProgramByPage(Page<ManaProgramDTO> page, Query query);

}
