package com.yizhuoyan.txtgen.module.dm.api.resp;

import com.yizhuoyan.txtgen.module.dm.entity.DataClassFieldEntity;
import com.yizhuoyan.txtgen.module.dm.entity.FieldBaseDataTypeEnum;
import lombok.Data;

@Data
public class DataClassFieldDetailVo {
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
    //是否唯一
    Boolean unique;
    //最大长度
    Double min;
    Double max;
    //默认值
    String defaultValue;
    String pattern;
    String ext;


    public static DataClassFieldDetailVo of(DataClassFieldEntity e) {
        DataClassFieldDetailVo vo = new DataClassFieldDetailVo();
        vo.setExt(e.getExt());
        vo.setDataType(e.getDataType());
        vo.setSubDataType(e.getSubDataType());
        vo.setDefaultValue(e.getDefaultValue());
        vo.setId(e.getId());
        vo.setMax(e.getMax());
        vo.setMin(e.getMin());
        vo.setDisplayName(e.getDisplayName());
        vo.setName(e.getName());
        vo.setOrdinal(e.getOrdinal());
        vo.setRequired(e.getRequired());
        vo.setPrimary(e.getPrimary());
        vo.setUnique(e.getUnique());
        vo.setRemark(e.getRemark());
        vo.setPattern(e.getPattern());
        return vo;

    }
}
