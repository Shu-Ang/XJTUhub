package com.xjtuhub.mapper;

import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Major;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(String courseId);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(String courseId);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectCourseList();
}