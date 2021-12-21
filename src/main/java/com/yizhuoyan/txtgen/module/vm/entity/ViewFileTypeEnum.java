package com.yizhuoyan.txtgen.module.vm.entity;

import com.yizhuoyan.common.ValueDisplayNameEnum;
import lombok.Getter;
import org.hibernate.annotations.Type;

@Getter
public enum ViewFileTypeEnum implements ValueDisplayNameEnum<String> {
    JS("js","工具方法"),
    VM("vm","视图模型扩展"),
    BEETL("beetl","Beetl模板")
        ;

    private final String value;
    private final String displayName;

    ViewFileTypeEnum(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public static ViewFileTypeEnum parseByValue(String value){
        return ValueDisplayNameEnum.parseByValue(ViewFileTypeEnum.class, value);
    }
}
