package com.yizhuoyan.common;

import java.util.Map;

public class StringKeyValueEntry implements Map.Entry<String,Object> {
    private final String key;
    private final  Object value;

    public StringKeyValueEntry(String key, Object value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public String getKey() {
        return key;
    }
    @Override
    public Object getValue() {
        return value;
    }
    @Override
    public Object setValue(Object value) {
        return null;
    }

}
