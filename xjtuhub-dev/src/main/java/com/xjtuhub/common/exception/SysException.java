package com.xjtuhub.common.exception;

public class SysException extends RuntimeException{
    public SysException(String message, Exception e){
        super(message, e);
    }
}
