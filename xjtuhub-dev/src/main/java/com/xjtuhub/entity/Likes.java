package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Likes {
    private Integer likesId;

    private Integer commentId;

    private Integer blogId;

    private String roleId;

    private Integer flag;
    private Blog blog;
    private Comment comment;


}