package com.xjtuhub.service;

import com.xjtuhub.entity.Course;

import java.util.List;

public interface CourseServiceApi {
    int deleteByPrimaryKey(String courseId);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(String courseId);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectCourseList();
}
