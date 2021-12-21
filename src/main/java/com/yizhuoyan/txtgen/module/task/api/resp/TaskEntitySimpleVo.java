package com.yizhuoyan.txtgen.module.task.api.resp;

import com.yizhuoyan.txtgen.module.task.entity.TaskEntity;
import lombok.Data;

@Data
public class TaskEntitySimpleVo {
    Long id;
    String namespace;
    /**
     * 实体名称
     */
    String name;

    public static TaskEntitySimpleVo of(TaskEntity e) {
        TaskEntitySimpleVo vo = new TaskEntitySimpleVo();
        vo.setId(e.getId());
        vo.setNamespace(e.getNamespace());
        vo.setName(e.getName());
        return vo;
    }
}
