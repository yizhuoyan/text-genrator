package com.yizhuoyan.txtgen.module.task.service;

import com.yizhuoyan.txtgen.module.task.ao.TaskAddAo;
import com.yizhuoyan.txtgen.module.task.ao.TaskDetailAo;
import com.yizhuoyan.txtgen.module.task.ao.TaskListAo;
import com.yizhuoyan.txtgen.module.task.ao.TaskModAo;
import com.yizhuoyan.txtgen.module.task.dto.GenerateOneFileResult;
import com.yizhuoyan.txtgen.module.task.entity.TaskEntity;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileAddAo;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileModAo;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileQueryAo;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TaskManageService {

    void add(@Valid TaskAddAo ao)throws Exception;
    void modify(@Valid TaskModAo ao)throws Exception;
    TaskEntity load(Long  id)throws Exception;
    List<TaskEntity> query(@Valid TaskListAo ao)throws Exception;
    void remove(Long... ids)throws Exception;
    List<GenerateOneFileResult> execute(Long id)throws Exception;
}
