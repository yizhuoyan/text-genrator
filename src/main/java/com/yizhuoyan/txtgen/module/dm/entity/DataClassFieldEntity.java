package com.yizhuoyan.txtgen.module.dm.entity;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class DataClassFieldEntity {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    ClassEntity belongClass;
    //名称
    String name;
    //显示名称
    String displayName;
    //显示顺序
    Integer ordinal;
    //字段类型
    String dataType;
    //字段子类型
    String subDataType;
    //字段备注
    String remark;
    //是否非空
    Boolean required;
    @Column(name = "is_primary")
    Boolean primary;
    //是否唯一
    @Column(name = "is_unique")
    Boolean unique;
    //最小
    Double max;
    //最大
    Double min;
    //默认值
    String defaultValue;

    String pattern;

    @Column(length = 4000)
    String ext;

    public Map toBeanMap(){
        Map map=new HashMap(16,1);
        map.put("name",name);
        map.put("displayName",displayName);
        map.put("dataType",dataType);
        map.put("subDataType",subDataType);
        map.put("remark",remark);
        map.put("required",required);
        map.put("unique",unique);
        map.put("primary",primary);
        map.put("min",min);
        map.put("max",max);
        map.put("pattern",pattern);
        map.put("defaultValue",defaultValue);
        if(this.ext!=null){
            Object customFieldsObject = new Yaml().load(ext);
            if(customFieldsObject instanceof Map){
                map.putAll((Map) customFieldsObject);
            }
        }
        return map;
    }
}
