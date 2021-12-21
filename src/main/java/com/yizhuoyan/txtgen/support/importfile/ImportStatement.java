package com.yizhuoyan.txtgen.support.importfile;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;

@Data
public class ImportStatement {
    String raw;
    String namespace;
    String fileName;
    ViewFileTypeEnum fileType;


    public static ImportStatement parse(String importScript){
        if(importScript==null)return null;
        if(importScript.endsWith(";")){
            importScript=importScript.substring(0,importScript.length()-1);
        }
        importScript=importScript.trim();
        String namespace,name,fileType;
        int namespaceEndAt = importScript.lastIndexOf('/');
        int nameEndAt = importScript.lastIndexOf('.');
        if(namespaceEndAt!=-1){
            namespace=importScript.substring(0,namespaceEndAt);
            name=importScript.substring(namespaceEndAt+1,nameEndAt);
            fileType=importScript.substring(nameEndAt+1);
        }else{
            namespace="";
            name=importScript.substring(0,nameEndAt);
            fileType=importScript.substring(nameEndAt+1);
        }
        ImportStatement result=new ImportStatement();
        result.setFileName(name);
        result.setNamespace(namespace);

        result.setFileType(ViewFileTypeEnum.parseByValue(fileType));
        result.setRaw(importScript);
        return  result;
    }
}
