package com.yizhuoyan.txtgen.module.task.api.resp;

import com.yizhuoyan.txtgen.module.task.entity.TaskEntity;
import lombok.Data;

@Data
public class TaskEntityDetailVo {
    Long id;
    String namespace;
    String name;
    /**
     * 任务配置
     */
    String taskConfig;
    /**
     * 数据模型id，逗号隔开
     */
    String dataModels;
    /**
     * 模板文件id，逗号隔开
     */
    String templateFiles;
    String remark;

    public static TaskEntityDetailVo of(TaskEntity e) {
        TaskEntityDetailVo vo = new TaskEntityDetailVo();
        vo.setId(e.getId());
        vo.setNamespace(e.getNamespace());
        vo.setName(e.getName());
        vo.setDataModels(e.getDataModels());
        vo.setTemplateFiles(e.getTemplateFiles());
        vo.setRemark(e.getRemark());
        vo.setTaskConfig(e.getTaskConfig());
        return vo;
    }
}
