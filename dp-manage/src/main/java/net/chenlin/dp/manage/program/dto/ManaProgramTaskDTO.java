package net.chenlin.dp.manage.program.dto;

import net.chenlin.dp.manage.program.entity.ManaProgramTaskEntity;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 节目单DTO实体
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/13 17:42
 */
//@JsonInclude(Include.NON_NULL)
public class ManaProgramTaskDTO extends ManaProgramTaskEntity {

    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

}
