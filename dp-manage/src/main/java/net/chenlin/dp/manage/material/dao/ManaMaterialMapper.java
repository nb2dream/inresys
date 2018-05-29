package net.chenlin.dp.manage.material.dao;

import net.chenlin.dp.common.dao.BaseMapper;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.manage.material.dto.ManaMaterialDTO;
import net.chenlin.dp.manage.program.dto.ManaProgramDTO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 素材dao层
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 14:53
 */
@MapperScan
@Component
public interface ManaMaterialMapper extends BaseMapper<ManaMaterialDTO> {

    /**
     * 分页未被选中素材列表
     * @param page
     * @param search
     * @return
     */
    List<ManaMaterialDTO> listSelectMaterialByPage(Page<ManaMaterialDTO> page, Query search);

}
