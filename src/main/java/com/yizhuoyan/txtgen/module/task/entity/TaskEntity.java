package com.yizhuoyan.txtgen.module.task.entity;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
public class TaskEntity {
    @Id
    @GeneratedValue
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

}
