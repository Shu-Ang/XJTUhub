package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Major {
    private Integer majorId;

    private Integer departmentId;

    private String majorName;

    private List<Course> courseList;



}