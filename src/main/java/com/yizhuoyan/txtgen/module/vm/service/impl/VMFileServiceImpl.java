package com.yizhuoyan.txtgen.module.vm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yizhuoyan.common.exception.DataNotFoundException;
import com.yizhuoyan.common.exception.ShouldNeverHappenException;
import com.yizhuoyan.common.exception.VMExecuteException;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import com.yizhuoyan.txtgen.module.vm.service.VMFileService;
import com.yizhuoyan.txtgen.module.vm.service.ViewFileEntityManageService;
import com.yizhuoyan.txtgen.support.importfile.ImportStatement;
import com.yizhuoyan.txtgen.support.importfile.ImportStatementFile;
import com.yizhuoyan.txtgen.support.js.JSScriptExecuteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Slf4j
@RequiredArgsConstructor
public class VMFileServiceImpl implements VMFileService {

    final ViewFileEntityManageService viewFileEntityManageService;
    final JSScriptExecuteService jsScriptExecuteService;
    @Override
    public Map execute(Long vmFileId, Map input) throws Exception{
        ViewFileEntity vmFile = viewFileEntityManageService.load(vmFileId);
        return execute(vmFile, input);
    }

    public Map execute(ViewFileEntity vmFile, Map input) throws Exception{
        if(vmFile.getFileType()!= ViewFileTypeEnum.VM){
            throw new ShouldNeverHappenException("非vm文件");
        }
        //解析vmfile
        ImportStatementFile vmFileContent= ImportStatementFile.parse(vmFile.getFileContent());
        List<ImportStatement> importJSFunctionList = vmFileContent.getImportList();
        StringBuilder vmFileCode=new StringBuilder();
        if(importJSFunctionList.size()>0){
            String functionScript=loadAllFunctionFileCode(importJSFunctionList,vmFile.getNamespace());
            vmFileCode.append(functionScript);
        }
        vmFileCode.append(vmFileContent.getFileContent());
        // execute script
        Map inputModel=new HashMap(1,1);
        inputModel.put("vm", input);
        try {
            Object scriptReturnObject = jsScriptExecuteService.execute(vmFileCode.toString(), inputModel);
            if(scriptReturnObject!=null){
                if(scriptReturnObject instanceof Map){
                    return ((Map)scriptReturnObject);
                }
            }
        }catch (Exception e){
            throw  new VMExecuteException("{}/{}.vm occur exception:\n{}",vmFile.getNamespace(),vmFile.getName(),e.getMessage());
        }

        return Collections.emptyMap();
    }

    public String loadAllFunctionFileCode(List<ImportStatement> importList, String contextNamespace)throws Exception{
        StringBuilder result=new StringBuilder();
        String namespace,name;
        for (ImportStatement statement : importList) {
            if (statement.getFileType() != ViewFileTypeEnum.JS) {
                continue;
            }
            namespace = statement.getNamespace();
            if (".".equals(namespace)) {
                namespace = contextNamespace;
            }
            name = statement.getFileName();

            ViewFileEntity functionFile = viewFileEntityManageService.getByExample(new ViewFileEntity()
                    .setFileType(ViewFileTypeEnum.JS)
                    .setNamespace(namespace)
                    .setName(name));
            if(functionFile==null){
                throw new DataNotFoundException("导入的js文件【{}】不存在，请核实", statement.getRaw());
            }
            String fileContent = StrUtil.trimToNull(functionFile.getFileContent());
            if(fileContent!=null) {
                try {
                    jsScriptExecuteService.compile(fileContent);
                }catch (Exception e){
                    log.error("{}/{}.js：",functionFile.getNamespace(),functionFile.getName(),e);
                    throw new VMExecuteException("{}/{}.js：{} ",functionFile.getNamespace(),functionFile.getName(),e.getMessage());
                }
                result.append(functionFile.getFileContent()).append(";\n");
            }
        }

        return result.toString();
    }
}
