package com.yizhuoyan.txtgen.module.vm.api.req;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;

@Data
public class ViewFileAddReq {
    String namespace;
    String name;
    String generatePath;
    ViewFileTypeEnum fileType;
    String fileContent;
}
