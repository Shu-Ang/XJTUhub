package com.xjtuhub.common.exception;

public class MailException extends RuntimeException{
    public MailException(){
        super("发送邮件失败");
    }
}
