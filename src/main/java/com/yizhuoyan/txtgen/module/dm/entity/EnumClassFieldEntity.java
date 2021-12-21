package com.yizhuoyan.txtgen.module.dm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class EnumClassFieldEntity {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    ClassEntity belongClass;
    String name;
    String value;
    String displayName;
    String remark;
    Integer ordinal;

    public Map toBeanMap(){
        Map map=new HashMap(8,1);
        map.put("name",name);
        map.put("value",value);
        map.put("displayName",displayName);
        map.put("ordinal",ordinal);
        map.put("remark",remark);
        return map;
    }
}
