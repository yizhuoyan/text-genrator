package com.yizhuoyan.common;

import java.util.HashMap;
import java.util.Map;

public class Maps {
    public static <K,V>Map<K, V> of(K k,V v){
        Map<K,V> map=new HashMap<>(1,1);
        map.put(k, v);
        return map;
    }

    public static <K,V>Map<K, V> of(K k1,V v1,K k2,V v2){
        Map<K,V> map=new HashMap<>(2,1);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }
}
