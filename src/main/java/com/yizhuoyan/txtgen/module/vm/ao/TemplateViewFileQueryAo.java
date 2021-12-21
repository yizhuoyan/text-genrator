package com.yizhuoyan.txtgen.module.vm.ao;

import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import lombok.Data;

@Data
public class TemplateViewFileQueryAo {
    String namespaceLike;
    String nameLike;
}
