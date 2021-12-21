package com.yizhuoyan.common.exception;

public class VMExecuteException extends ThisApplicationException{
    private static final String CODE="vm-execute-error";
    public VMExecuteException() {
        super(CODE);
    }

    public VMExecuteException(String message, Object... args) {
        super(CODE, message, args);
    }

    public VMExecuteException(Exception cause, String message, Object... args) {
        super(cause, CODE, message, args);
    }
}
