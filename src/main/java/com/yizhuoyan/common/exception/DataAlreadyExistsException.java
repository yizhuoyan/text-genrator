package com.yizhuoyan.common.exception;

public class DataAlreadyExistsException extends ThisApplicationException{
    private static final String CODE="data-not-found";
    public DataAlreadyExistsException() {
        super(CODE);
    }

    public DataAlreadyExistsException(String message, Object... args) {
        super(CODE, message, args);
    }

    public DataAlreadyExistsException(Exception cause,String message, Object... args) {
        super(cause, CODE, message, args);
    }
}
