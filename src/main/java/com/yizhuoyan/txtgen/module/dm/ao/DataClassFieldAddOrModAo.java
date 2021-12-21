package com.yizhuoyan.txtgen.module.dm.ao;

import lombok.Data;

@Data
public class DataClassFieldAddOrModAo {
    Long classId;
    Long id;
    //名称
    String name;
    //显示名称
    String displayName;
    //显示顺序
    Integer ordinal;
    //字段类型
    String dataType;
    //字段类型辅助
    String subDataType;
    //字段备注
    String remark;
    //是否必须
    Boolean required;
    Boolean primary;
    Boolean unique;
    Double min;
    Double max;
    String pattern;
    String defaultValue;
    String ext;

}
