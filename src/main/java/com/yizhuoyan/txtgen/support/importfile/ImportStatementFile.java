package com.yizhuoyan.txtgen.support.importfile;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 对包含import 语句的文件的抽象
 */
@Data
public class ImportStatementFile {
    private static final String PREFIX_IMPORT="import ";
    List<ImportStatement> importList;
    String fileContent;


    public  static ImportStatementFile parse(String text){
        if(text==null)return null;
        return doParse(text);
    }

    /**
     * 处理模板文件中的脚本导入
     * @param text
     * @return
     */
    private static ImportStatementFile doParse(String text){
        List<ImportStatement> importScriptList=new LinkedList<>();
        String row=null;
        int beginIndex=0;
        int endIndex=text.indexOf('\n');
        while(endIndex!=-1){
            row=text.substring(beginIndex,endIndex);
            if(row.startsWith(PREFIX_IMPORT)){
                row= StrUtil.trimToNull(row.substring(PREFIX_IMPORT.length()));
                if(row!=null){
                    importScriptList.add(ImportStatement.parse(row));
                }
            }else{
                break;
            }
            beginIndex=endIndex+1;
            endIndex=text.indexOf('\n',endIndex+1);
        }
        ImportStatementFile result=new ImportStatementFile();
        if(importScriptList.size()==0){
            result.setFileContent(text);
        }else{
            result.setFileContent(text.substring(endIndex+1));
        }

        result.setImportList(importScriptList);
        return result;
    }
}
