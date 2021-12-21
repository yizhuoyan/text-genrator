package com.yizhuoyan.txtgen.module.dm.entity;

import com.yizhuoyan.common.ValueDisplayNameEnum;
import lombok.Getter;

@Getter
public enum ClassTypeEnum implements ValueDisplayNameEnum<Integer> {
    CLASS_TYPE_DATA(0,"数据"),
    CLASS_TYPE_ENUM(1,"Enum");

    Integer value;
    String displayName;

    ClassTypeEnum(Integer code, String displayName) {
        this.value = code;
        this.displayName = displayName;
    }
}
