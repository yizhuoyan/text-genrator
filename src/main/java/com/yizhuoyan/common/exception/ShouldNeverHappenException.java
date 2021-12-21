package com.yizhuoyan.common.exception;


public class ShouldNeverHappenException extends RuntimeException{

    public ShouldNeverHappenException(String message, Object... args) {
        super(String.format(message,args),findThrowable(args));
    }

    private static Throwable findThrowable(Object... args){
         if(args.length==0)return null;
         if(args[args.length-1] instanceof Throwable){
             return (Throwable) args[args.length-1];
         }
         return null;
    }
}