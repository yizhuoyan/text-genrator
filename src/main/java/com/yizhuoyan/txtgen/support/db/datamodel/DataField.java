package com.yizhuoyan.txtgen.support.db.datamodel;

import lombok.Data;

import java.util.Map;

@Data
public class DataField<T> {
    String displayName;
    String name;
    String remark;

    //字段类型，用java类型标识
    Class<T> type;
    //默认值
    Object defaultValue;
    //其他自定义字段
    Map<String,Object> extFields;
}