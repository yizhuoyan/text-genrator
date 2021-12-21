package com.yizhuoyan.txtgen.module.task.api.req;

import lombok.Data;

@Data
public class TaskModReq {
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
}
