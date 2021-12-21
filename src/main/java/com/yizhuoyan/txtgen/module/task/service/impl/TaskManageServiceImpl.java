package com.yizhuoyan.txtgen.module.task.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.yizhuoyan.common.exception.DataNotFoundException;
import com.yizhuoyan.txtgen.module.dm.ao.ClassAddAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassModAo;
import com.yizhuoyan.txtgen.module.dm.ao.ClassQueryAo;
import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.ClassTypeEnum;
import com.yizhuoyan.txtgen.module.task.ao.*;
import com.yizhuoyan.txtgen.module.task.dao.TaskEntityDao;
import com.yizhuoyan.txtgen.module.task.dto.GenerateOneFileResult;
import com.yizhuoyan.txtgen.module.task.entity.TaskEntity;
import com.yizhuoyan.txtgen.module.task.exception.TaskExecuteException;
import com.yizhuoyan.txtgen.module.task.service.GenerateService;
import com.yizhuoyan.txtgen.module.task.service.TaskManageService;
import com.yizhuoyan.txtgen.module.vm.ao.TemplatePreviewAo;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

import javax.validation.Valid;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskManageServiceImpl implements TaskManageService {

    final TaskEntityDao taskEntityDao;

    final GenerateService generateService;

    @Override
    public void add(TaskAddAo ao) throws Exception {
        TaskEntity e = new TaskEntity();
        e.setNamespace(ao.getNamespace());
        e.setName(ao.getName());
        e.setDataModels(ao.getDataModels());
        e.setTemplateFiles(ao.getTemplateFiles());
        e.setTaskConfig(ao.getTaskConfig());
        e.setRemark(ao.getRemark());
        taskEntityDao.save(e);
    }

    @Override
    public void modify(TaskModAo ao) throws Exception {
        TaskEntity old = taskEntityDao.getById(ao.getId());
        if (old == null) {
            throw new DataNotFoundException("数据{}不存在", ao.getId());
        }
        String newName = ao.getName();
        if (newName != null && !Objects.equals(newName, old.getName())) {
            old.setName(newName);
        }

        String newNamespace = ao.getNamespace();
        if (newNamespace != null && !Objects.equals(newNamespace, old.getNamespace())) {
            old.setNamespace(newNamespace);
        }
        String newTaskConfig = ao.getTaskConfig();
        if (newTaskConfig != null && !Objects.equals(newTaskConfig, old.getTaskConfig())) {
            old.setTaskConfig(newTaskConfig);
        }

        String newDataModels = ao.getDataModels();
        if (newDataModels != null && !Objects.equals(newDataModels, old.getDataModels())) {
            old.setDataModels(newDataModels);
        }
        String newTemplateFiles = ao.getTemplateFiles();
        if (newTemplateFiles != null && !Objects.equals(newTemplateFiles, old.getTemplateFiles())) {
            old.setTemplateFiles(newTemplateFiles);
        }
        String newRemark = ao.getRemark();
        if (newRemark != null && !Objects.equals(newRemark, old.getRemark())) {
            old.setRemark(newRemark);
        }
        taskEntityDao.flush();
        log.debug("更新完毕{}", old);
    }

    @Override
    public TaskEntity load(Long id) throws Exception {
        TaskEntity e = taskEntityDao.getById(id);
        if (e == null) {
            throw new DataNotFoundException("数据不存在");
        }
        return e;
    }

    @Override
    public List<TaskEntity> query(@Valid TaskListAo ao) throws Exception {
        TaskEntity probe = new TaskEntity();
        probe.setNamespace(ao.getNamespace());
        probe.setName(ao.getName());
        Example<TaskEntity> exp = Example.of(probe, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return taskEntityDao.findAll(exp);
    }

    @Transactional
    @Override
    public void remove(Long... ids) throws Exception {
        for (Long id : ids) {
            TaskEntity e = taskEntityDao.getById(id);
            taskEntityDao.delete(e);
        }
    }


    @Override
    public List<GenerateOneFileResult> execute(Long id) throws Exception {
        TaskEntity e = this.load(id);

        String dataModels = StrUtil.trimToNull(e.getDataModels());
        if (dataModels == null) {
            throw new TaskExecuteException("no specific data model");
        }
        String templateFiles = StrUtil.trimToNull(e.getTemplateFiles());
        if (templateFiles == null) {
            throw new TaskExecuteException("no specific template Files");
        }

        long[] dataModelIdArray = StrUtil.splitToLong(dataModels, ",");
        long[] templateFileIdArray = StrUtil.splitToLong(templateFiles, ",");
        String taskConfig = StrUtil.trimToNull(e.getTaskConfig());
        Map taskConfigMap = Collections.emptyMap();
        if (taskConfig != null) {
            Object taskConfigObject = new Yaml().load(taskConfig);
            if (taskConfigObject instanceof Map) {
                taskConfigMap = (Map) taskConfigObject;
            }
        }

        List<GenerateOneFileResult> generateFileResultList = new ArrayList<>(dataModelIdArray.length * templateFileIdArray.length);

        for (int i = dataModelIdArray.length; i-- > 0; ) {
            Long dmId = dataModelIdArray[i];
            for (int j = templateFileIdArray.length; j-- > 0; ) {
                Long templateFileId = templateFileIdArray[j];
                GenerateOneFileAo generateAo = new GenerateOneFileAo();
                generateAo.setDmId(dmId);
                generateAo.setTemplateId(templateFileId);
                generateAo.setConfigMap(taskConfigMap);
                GenerateOneFileResult generateOneFileResult = generateService.generate(generateAo);
                generateFileResultList.add(generateOneFileResult);
            }
        }

        return generateFileResultList;

    }





}
