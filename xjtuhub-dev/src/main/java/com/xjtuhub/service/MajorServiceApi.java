package com.xjtuhub.service;

import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Major;

import java.util.List;

public interface MajorServiceApi {
    int deleteByPrimaryKey(Integer majorId);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(Integer majorId);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);
    List<Major> selectMajorList();

    List<Major> getMajorList(List<Course> courseList, List<Major> majorList);
}
