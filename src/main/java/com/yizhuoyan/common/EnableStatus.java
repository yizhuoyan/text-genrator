package com.yizhuoyan.common;

import lombok.Getter;

@Getter
public enum EnableStatus implements ValueDisplayNameEnum<Integer> {
    YES(1, "启用"),
    NO(0, "停用");

    Integer value;
    String displayName;

    EnableStatus(Integer code, String displayName) {
        this.value = code;
        this.displayName = displayName;
    }

}
