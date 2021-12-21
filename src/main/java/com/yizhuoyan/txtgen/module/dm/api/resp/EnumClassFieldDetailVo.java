package com.yizhuoyan.txtgen.module.dm.api.resp;

import com.yizhuoyan.txtgen.module.dm.entity.EnumClassFieldEntity;
import lombok.Data;

@Data
public class EnumClassFieldDetailVo {
    Long id;
    String name;
    String value;
    String displayName;
    String remark;
    Integer ordinal;

    public static EnumClassFieldDetailVo of(EnumClassFieldEntity e) {
        EnumClassFieldDetailVo vo=new EnumClassFieldDetailVo();
        vo.setId(e.getId());
        vo.setName(e.getName());
        vo.setValue(e.getValue());
        vo.setDisplayName(e.getDisplayName());
        vo.setRemark(e.getRemark());
        vo.setOrdinal(e.getOrdinal());
        return vo;

    }
}
