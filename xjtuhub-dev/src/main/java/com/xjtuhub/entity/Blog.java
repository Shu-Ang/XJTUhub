package com.xjtuhub.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Blog {
    private Integer blogId;

    private String roleId;

    private Integer courseId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date releaseDate;

    private Integer isDraft;

    private Integer isPrivate;

    private Integer isTop;

    private Integer category;

    private Integer status;

    private Integer views;

    private String title;

    private String graphAddr;

    private String fileAddr;

    private String content;

    private String summary;

    private Integer rootId;

    private Integer isDone;

    private Course course;


    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", roleId='" + roleId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", releaseDate=" + releaseDate +
                ", isDraft=" + isDraft +
                ", isPrivate=" + isPrivate +
                ", isTop=" + isTop +
                ", category=" + category +
                ", status=" + status +
                ", views=" + views +
                ", title='" + title + '\'' +
                ", graphAddr='" + graphAddr + '\'' +
                ", fileAddr='" + fileAddr + '\'' +
                ", content='" + content + '\'' +
                ", summary='" + summary + '\'' +
                ", rootId=" + rootId +
                ", course=" + course +
                '}';
    }


}