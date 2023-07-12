package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Department {
    private Integer departmentId;

    private String departmentName;

    private List<Major> majorList;

}