package com.xjtuhub.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Comment {
    private Integer commentId;

    private Integer blogId;

    private String roleId;

    private Integer parentId;

    private Integer rootId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date commentDate;

    private String commentContent;

    private Blog blog;

    private List<Comment> child;

    private String faceAddr;

}