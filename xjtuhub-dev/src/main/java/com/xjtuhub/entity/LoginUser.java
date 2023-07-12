package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser {
    private String userId;
    private String userPwd;
    private String verificationCode;
    private String verificationKey;



    @Override
    public String toString() {
        return "LoginUser{" +
                "userId='" + userId + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", verificationKey='" + verificationKey + '\'' +
                '}';
    }
}
