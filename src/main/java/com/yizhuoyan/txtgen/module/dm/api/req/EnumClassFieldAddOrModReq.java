package com.yizhuoyan.txtgen.module.dm.api.req;

import lombok.Data;

@Data
public class EnumClassFieldAddOrModReq {
    Long id;
    String name;
    String value;
    String displayName;
    String remark;
    Integer ordinal;
}
