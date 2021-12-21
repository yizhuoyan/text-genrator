package com.yizhuoyan.txtgen.module.vm.api.resp;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import lombok.Data;

@Data
public class ViewEntityDetailVo {
    Long id;
    String namespace;
    String name;
    String fileContent;
    ViewFileTypeEnum fileType;
    String generatePath;

    public static ViewEntityDetailVo of(ViewFileEntity e) {
        ViewEntityDetailVo vo = new ViewEntityDetailVo();
        vo.setId(e.getId());
        vo.setNamespace(e.getNamespace());
        vo.setName(e.getName());
        vo.setFileContent(e.getFileContent());
        vo.setGeneratePath(e.getGeneratePath());
        vo.setFileType(e.getFileType());
        return vo;
    }
}
