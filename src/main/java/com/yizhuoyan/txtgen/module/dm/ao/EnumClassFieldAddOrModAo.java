package com.yizhuoyan.txtgen.module.dm.ao;

import lombok.Data;

@Data
public class EnumClassFieldAddOrModAo  {
    Long classId;
    Long id;
    String name;
    String value;
    String displayName;
    String remark;
    Integer ordinal;
}
