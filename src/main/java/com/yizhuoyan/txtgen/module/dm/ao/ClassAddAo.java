package com.yizhuoyan.txtgen.module.dm.ao;

import com.yizhuoyan.txtgen.module.dm.api.req.ClassAddReq;
import com.yizhuoyan.txtgen.module.dm.entity.ClassTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassAddAo extends ClassAddReq {
    ClassTypeEnum classType;
}
