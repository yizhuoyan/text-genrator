package com.yizhuoyan.common.exception;

public class DataNotFoundException extends ThisApplicationException{

    private static final String CODE="data-not-found";
    public DataNotFoundException(String message, Object... args) {
        super(CODE, message, args);
    }

    public DataNotFoundException(Exception cause,String message, Object... args) {
        super(cause, CODE, message, args);
    }
}
