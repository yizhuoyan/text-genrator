package com.yizhuoyan.txtgen.support.template.impl.beetl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.Ipv4Util;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
@Component
public class CommonTemplateRenderFunctions {
    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter hhmmss = DateTimeFormatter.ofPattern("HHmmss");
    public String uuid32() {
        return UUID.fastUUID().toString();
    }
    public String uuid20(){
        return leftFill(20,String.valueOf(System.nanoTime()),'0');
    }


    public String space(int length){
        if(length<=0)return "";
        char[] chars=new char[length];
        for (int i =length; i-->0;chars[i]=' ');
        return new String(chars);
    }
    public String leftFillSpace(String raw,int length){
        return leftFill(length,raw,' ');
    }
    public String leftFill(int length,String raw,char fillChar){
        if(length<0){
            return raw;
        }
        int rawLength=raw.length();
        if(rawLength>=length){
            return raw.substring(rawLength-length);
        }
        char[] chars=new char[length];
        int insertAt=length-rawLength;
        for(int i=chars.length;i-->0;){
            if(i<insertAt){
                chars[i]=fillChar;
            }else{
                chars[i]=raw.charAt(i-insertAt);
            }
        }
        return new String(chars);
    }

    public String yyyyMMdd() {
        return yyyyMMdd.format(LocalDate.now());
    }

    public String hhmmss() {
        return hhmmss.format(LocalTime.now());
    }

    public static void main(String[] args) {
        System.out.println(new CommonTemplateRenderFunctions().uuid20());
    }
}
