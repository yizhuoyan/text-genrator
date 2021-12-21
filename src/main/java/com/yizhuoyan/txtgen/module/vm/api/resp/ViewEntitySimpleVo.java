package com.yizhuoyan.txtgen.module.vm.api.resp;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;

@Data
public class ViewEntitySimpleVo {
    Long id;
    String namespace;
    String name;
    ViewFileTypeEnum fileType;

    public static ViewEntitySimpleVo of(ViewFileEntity e) {
        ViewEntitySimpleVo vo = new ViewEntitySimpleVo();
        vo.setFileType(e.getFileType());
        vo.setId(e.getId());
        vo.setNamespace(e.getNamespace());
        vo.setName(e.getName());
        return vo;
    }
}
