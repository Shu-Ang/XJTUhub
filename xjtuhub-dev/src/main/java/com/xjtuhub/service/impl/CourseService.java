package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Page;
import com.xjtuhub.mapper.CourseMapper;
import com.xjtuhub.service.CourseServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseService implements CourseServiceApi {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public int deleteByPrimaryKey(String courseId) {
        return courseMapper.deleteByPrimaryKey(courseId);
    }

    @Override
    public int insert(Course record) {
        return courseMapper.insert(record);
    }

    @Override
    public int insertSelective(Course record) {
        return courseMapper.insertSelective(record);
    }

    @Override
    public Course selectByPrimaryKey(String courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public int updateByPrimaryKeySelective(Course record) {
        return courseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Course record) {
        return courseMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Course> selectCourseList() {
        return courseMapper.selectCourseList();
    }
}
