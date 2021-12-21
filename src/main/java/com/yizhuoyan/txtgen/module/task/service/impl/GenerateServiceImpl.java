package com.yizhuoyan.txtgen.module.task.service.impl;

import com.yizhuoyan.common.exception.DataNotFoundException;
import com.yizhuoyan.common.exception.ShouldNeverHappenException;
import com.yizhuoyan.txtgen.module.dm.service.ClassEntityManageService;
import com.yizhuoyan.txtgen.module.task.ao.GenerateOneFileAo;
import com.yizhuoyan.txtgen.module.task.dto.GenerateOneFileResult;
import com.yizhuoyan.txtgen.module.task.service.GenerateService;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import com.yizhuoyan.txtgen.module.vm.service.VMFileService;
import com.yizhuoyan.txtgen.module.vm.service.ViewFileEntityManageService;
import com.yizhuoyan.txtgen.support.importfile.ImportStatement;
import com.yizhuoyan.txtgen.support.importfile.ImportStatementFile;
import com.yizhuoyan.txtgen.support.template.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenerateServiceImpl implements GenerateService {

    final ClassEntityManageService entityManageService;
    final ViewFileEntityManageService viewFileEntityManageService;
    final VMFileService vmFileService;
    final Map<String,TemplateService> templateServiceMap;

    public GenerateOneFileResult generate(GenerateOneFileAo ao) throws Exception {
        Long dmId = ao.getDmId();
        Long templateId = ao.getTemplateId();
        //load the  data model
        Map<String, Object> model = entityManageService.loadModel(dmId);

        Map configMap = ao.getConfigMap();
        if(configMap!=null){
            model.putAll(configMap);
        }

        ViewFileEntity templateFile = viewFileEntityManageService.load(templateId);
        //load the template service by file type
        TemplateService templateService = this.loadTemplateService(templateFile.getFileType());

        ImportStatementFile templateImportStatementFile = ImportStatementFile.parse(templateFile.getFileContent());

        List<ImportStatement> importVMList = templateImportStatementFile.getImportList();
        if(importVMList.size()>0) {
            executeImportVMScript(importVMList, templateFile.getNamespace(), model);
        }
        String filePath = templateService.render(templateFile.getGeneratePath(), model);
        File tempFile = File.createTempFile(UUID.randomUUID().toString(),"");
        try(PrintWriter writer=new PrintWriter(tempFile,"utf-8")){
            templateService.render(templateImportStatementFile.getFileContent(), model,writer);
        }

        GenerateOneFileResult dto=new GenerateOneFileResult();
        if(filePath==null){
            filePath=tempFile.getName();
        }
        dto.setPath(filePath);
        dto.setInputStream(new FileInputStream(tempFile));
        return dto;
    }

    private void executeImportVMScript(List<ImportStatement> importVMList, String contextNamespace, Map model)throws Exception{
        String namespace,name;
        for (ImportStatement statement : importVMList) {
            if(statement.getFileType()!= ViewFileTypeEnum.VM){
                continue;
            }
            namespace=statement.getNamespace();
            if(".".equals(namespace)){
                namespace=contextNamespace;
            }
            name=statement.getFileName();
            ViewFileEntity vmFile = viewFileEntityManageService.getByExample(new ViewFileEntity()
                    .setFileType(ViewFileTypeEnum.VM)
                    .setNamespace(namespace)
                    .setName(name));
            if(vmFile==null){
                throw new DataNotFoundException("导入的vm文件【{}】不存在，请核实", statement.getRaw());
            }
            model.putAll(vmFileService.execute(vmFile, model));
        }
    }





    private TemplateService loadTemplateService(ViewFileTypeEnum fileTypeEnum){
        TemplateService templateService = templateServiceMap.get(fileTypeEnum.getValue());
        if(templateService==null){
            throw new ShouldNeverHappenException("无法找到文件类型{}的模板服务" , fileTypeEnum.getValue());
        }
        return templateService;
    }

}
