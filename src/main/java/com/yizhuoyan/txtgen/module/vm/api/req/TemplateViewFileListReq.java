package com.yizhuoyan.txtgen.module.vm.api.req;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;

@Data
public class TemplateViewFileListReq {
    String namespaceLike;
    String nameLike;
}
