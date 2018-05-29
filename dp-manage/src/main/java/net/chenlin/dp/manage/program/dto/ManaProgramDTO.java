package net.chenlin.dp.manage.program.dto;

import net.chenlin.dp.manage.program.entity.ItemEntity;
import net.chenlin.dp.manage.program.entity.ManaProgramEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 节目 dto 对象
 *
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/10 14:21
 */
@Component
public class ManaProgramDTO extends ManaProgramEntity {

    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

}
