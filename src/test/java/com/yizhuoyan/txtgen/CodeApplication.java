package com.yizhuoyan.txtgen;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;


public class CodeApplication {

    public static void main(String[] args) throws Exception{
        HashMap map=new HashMap(8,0.9f);

        for(int i=8;i-->0;){
            map.put(""+i,i);
        }
        Field tableField = HashMap.class.getDeclaredField("table");

        tableField.setAccessible(true);
        Object o = tableField.get(map);

        System.out.println(Array.getLength(o));



    }
}