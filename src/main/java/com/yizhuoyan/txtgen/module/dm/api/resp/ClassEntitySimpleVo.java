package com.yizhuoyan.txtgen.module.dm.api.resp;

import com.yizhuoyan.txtgen.module.dm.entity.ClassEntity;
import lombok.Data;

@Data
public class ClassEntitySimpleVo {
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


    public static ClassEntitySimpleVo of(ClassEntity e) {
        ClassEntitySimpleVo vo = new ClassEntitySimpleVo();
        vo.setDisplayName(e.getDisplayName());
        vo.setId(e.getId());
        vo.setNamespace(e.getNamespace());
        vo.setName(e.getName());
        return vo;
    }
}
