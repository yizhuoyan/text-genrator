package com.yizhuoyan.txtgen.module.dm.api.req;

import com.yizhuoyan.txtgen.module.dm.entity.FieldBaseDataTypeEnum;
import lombok.Data;

@Data
public class DataClassFieldAddOrModReq {
    /**
     * 为null为新增
     */
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
    Boolean required;
    Boolean primary;
    Boolean unique;
    //最大长度
    Double min;
    //最小长度
    Double max;
    //默认值
    String defaultValue;
    String pattern;
    String ext;
}
