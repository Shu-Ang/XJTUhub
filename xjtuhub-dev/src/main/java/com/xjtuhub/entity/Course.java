package com.xjtuhub.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class Course {
    private Integer courseId;

    private Integer majorId;

    private String courseName;

    private BigDecimal credit;

    private Integer period;

    private String term;

}