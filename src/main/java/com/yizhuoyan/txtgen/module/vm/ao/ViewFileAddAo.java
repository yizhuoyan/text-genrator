package com.yizhuoyan.txtgen.module.vm.ao;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;

@Data
public class ViewFileAddAo {
    Long id;
    String namespace;
    String name;
    String generatePath;
    ViewFileTypeEnum fileType;
    String fileContent;
}
