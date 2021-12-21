package com.yizhuoyan.common.exception;


import cn.hutool.core.text.StrFormatter;

public class ThisApplicationException extends RuntimeException{
    private  final String code;

    public ThisApplicationException(String code) {
        super(code);
        this.code = code;
    }

    public ThisApplicationException(String code,String message,Object... args) {
        super(StrFormatter.format(message,args));
        this.code = code;
    }
    public ThisApplicationException(Exception cause,String code,String message,Object... args) {
        super(StrFormatter.format(message,args),cause);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}