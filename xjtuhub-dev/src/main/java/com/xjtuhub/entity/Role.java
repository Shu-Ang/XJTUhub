package com.xjtuhub.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class Role {
    private String roleId;

    private String faceAddr;

    private String email;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createDate;

    private String password;

    private String sign;

    private Integer status;

    private Follow follow;


}