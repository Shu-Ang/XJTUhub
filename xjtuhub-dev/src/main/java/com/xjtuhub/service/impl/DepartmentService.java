package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Comment;
import com.xjtuhub.entity.Department;
import com.xjtuhub.entity.Major;
import com.xjtuhub.mapper.DepartmentMapper;
import com.xjtuhub.service.DepartmentServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService implements DepartmentServiceApi {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public int deleteByPrimaryKey(Integer departmentId) {
        return departmentMapper.deleteByPrimaryKey(departmentId);
    }

    @Override
    public int insert(Department record) {
        return departmentMapper.insert(record);
    }

    @Override
    public int insertSelective(Department record) {
        return departmentMapper.insertSelective(record);
    }

    @Override
    public Department selectByPrimaryKey(Integer departmentId) {
        return departmentMapper.selectByPrimaryKey(departmentId);
    }

    @Override
    public int updateByPrimaryKeySelective(Department record) {
        return departmentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return departmentMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Department> selectDepartmentList() {
        return departmentMapper.selectDepartmentList();
    }

    @Override
    public List<Department> getDepartmentList(List<Major> majorList, List<Department> departmentList) {
        Map<Integer, Department> map = new HashMap<>();   // (id, Department)
        List<Department> result = new ArrayList<>();
        for(Department department : departmentList) {
            map.put(department.getDepartmentId(), department);
        }
        for(Major major : majorList) {
            Integer id = major.getDepartmentId();
                Department p = map.get(id);
                if(p.getMajorList() == null)
                    p.setMajorList(new ArrayList<>());
                p.getMajorList().add(major);
            }
        map.forEach((id, department)->{
            result.add(department);
        });
        return result;
    }
}
