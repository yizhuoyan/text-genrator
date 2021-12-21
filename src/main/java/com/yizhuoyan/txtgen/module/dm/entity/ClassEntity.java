package com.yizhuoyan.txtgen.module.dm.entity;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class ClassEntity {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    String displayName;

    String namespace;

    String remark;

    ClassTypeEnum classType;

    String ext;


    public Map toBeanMap(){
        Map map=new HashMap(16,1);
        map.put("name",name);
        map.put("displayName",displayName);
        map.put("namespace",namespace);
        map.put("remark",remark);
        map.put("classType",classType.getValue());
        if(this.ext!=null){
            Object customFieldsObject = new Yaml().load(ext);
            if(customFieldsObject instanceof Map){
                map.putAll((Map) customFieldsObject);
            }
        }
        return map;
    }



}
