package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Department;
import com.xjtuhub.entity.Major;
import com.xjtuhub.mapper.MajorMapper;
import com.xjtuhub.service.MajorServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MajorService implements MajorServiceApi {
    @Autowired
    private MajorMapper majorMapper;
    @Override
    public int deleteByPrimaryKey(Integer majorId) {
        return majorMapper.deleteByPrimaryKey(majorId);
    }

    @Override
    public int insert(Major record) {
        return majorMapper.insert(record);
    }

    @Override
    public int insertSelective(Major record) {
        return majorMapper.insertSelective(record);
    }

    @Override
    public Major selectByPrimaryKey(Integer majorId) {
        return majorMapper.selectByPrimaryKey(majorId);
    }

    @Override
    public int updateByPrimaryKeySelective(Major record) {
        return majorMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Major record) {
        return majorMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Major> selectMajorList() {
        return majorMapper.selectMajorList();
    }

    @Override
    public List<Major> getMajorList(List<Course> courseList, List<Major> majorList) {
        Map<Integer, Major> map = new HashMap<>();   // (id, Major)
        List<Major> result = new ArrayList<>();
        for(Major major : majorList) {
            map.put(major.getMajorId(), major);
        }
        for(Course course : courseList) {
            Integer id = course.getMajorId();
            Major p = map.get(id);
            if(p.getCourseList() == null)
                p.setCourseList(new ArrayList<>());
            p.getCourseList().add(course);
        }
        map.forEach((id, major)->{
            result.add(major);
        });
        return result;
    }
}
