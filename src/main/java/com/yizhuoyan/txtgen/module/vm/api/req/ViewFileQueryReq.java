package com.yizhuoyan.txtgen.module.vm.api.req;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;

@Data
public class ViewFileQueryReq {
    String namespaceLike;
    String nameLike;
    ViewFileTypeEnum fileType;
}
