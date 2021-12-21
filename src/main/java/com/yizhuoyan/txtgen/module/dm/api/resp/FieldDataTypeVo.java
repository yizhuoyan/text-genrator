package com.yizhuoyan.txtgen.module.dm.api.resp;

import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.FieldBaseDataTypeEnum;
import lombok.Data;

@Data
public class FieldDataTypeVo {
    String value;
    String displayName;
    Integer ordinal;

   static public FieldDataTypeVo of(ClassEntity e){
       FieldDataTypeVo vo=new FieldDataTypeVo();
       vo.setDisplayName(e.getDisplayName());
       vo.setValue(e.getName());
       return vo;
   }
    static public FieldDataTypeVo of(FieldBaseDataTypeEnum e){
        FieldDataTypeVo vo=new FieldDataTypeVo();
        vo.setDisplayName(e.name());
        vo.setValue(e.name());
        return vo;
    }
}
