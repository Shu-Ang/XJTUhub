package com.xjtuhub.service;

import com.xjtuhub.entity.Department;
import com.xjtuhub.entity.Major;

import java.util.List;

public interface DepartmentServiceApi {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    List<Department> selectDepartmentList();

    List<Department> getDepartmentList(List<Major> majorList, List<Department> departmentList);
}
