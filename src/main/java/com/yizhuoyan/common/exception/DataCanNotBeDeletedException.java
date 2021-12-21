package com.yizhuoyan.common.exception;

public class DataCanNotBeDeletedException extends ThisApplicationException{
    private static final String CODE="data-can-not-be-deleted";
    public DataCanNotBeDeletedException() {
        super(CODE);
    }

    public DataCanNotBeDeletedException(String message, Object... args) {
        super(CODE, message, args);
    }

    public DataCanNotBeDeletedException(Exception cause, String message, Object... args) {
        super(cause, CODE, message, args);
    }
}
