package com.yizhuoyan.txtgen.module.task.exception;

import com.yizhuoyan.common.exception.ThisApplicationException;

public class TaskExecuteException extends ThisApplicationException {
    private static final String CODE="task-execute-fail";
    public TaskExecuteException() {
        super(CODE);
    }
    public TaskExecuteException(String message, Object... args) {
        super(CODE, message, args);
    }

    public TaskExecuteException(Exception cause,String message, Object... args) {
        super(cause, CODE, message, args);
    }
}
