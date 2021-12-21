package com.yizhuoyan.txtgen.support.js;

import cn.hutool.core.util.StrUtil;

public class CommonFunction {



    public String toUnderlineCase(String s){
        return StrUtil.toUnderlineCase(s);
    }

    public String toCamelCase(String s){
        return StrUtil.toCamelCase(s);
    }

    public String upperFirstChar(String s){
        if(s==null)return null;
        char[] chars = s.toCharArray();
        if(chars.length==0)return "";
        int c=chars[0];
        if('a'<=c&&c<='z'){
            chars[0]=(char)(c^32);
        }
        return new String(chars);
    }


}
