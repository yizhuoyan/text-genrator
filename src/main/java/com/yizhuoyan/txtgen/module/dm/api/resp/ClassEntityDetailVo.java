package com.yizhuoyan.txtgen.module.dm.api.resp;

import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import com.yizhuoyan.txtgen.module.dm.entity.ClassTypeEnum;
import lombok.Data;

@Data
public class ClassEntityDetailVo {
    Long id;
    String namespace;
    /**
     * 实体名称
     */
    String name;
    /**
     * 显示名称
     */
    String displayName;
    ClassTypeEnum classType;
    String remark;
    String ext;

    public static ClassEntityDetailVo of(ClassEntity e) {
        ClassEntityDetailVo vo = new ClassEntityDetailVo();
        vo.setDisplayName(e.getDisplayName());
        vo.setId(e.getId());
        vo.setNamespace(e.getNamespace());
        vo.setName(e.getName());
        vo.setClassType(e.getClassType());
        vo.setRemark(e.getRemark());
        vo.setExt(e.getExt());
        return vo;
    }
}
