package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Department;
import com.xjtuhub.entity.Major;
import com.xjtuhub.service.impl.CourseService;
import com.xjtuhub.service.impl.DepartmentService;
import com.xjtuhub.service.impl.MajorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tree")
@Api(tags = "课程树")
public class TreeController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/getTree")
    @ApiOperation(value = "获取树")
    public JSONResult getCourseTree(){
        List<Course> courseList = courseService.selectCourseList();
        for (Course course : courseList){
            System.out.println(course.getCourseId());
        }
        System.out.println("-----------------");
        List<Major> majorList = majorService.selectMajorList();
        for (Major major : majorList){
            System.out.println(major.getMajorId());
        }
        System.out.println("-----------------");
        List<Major> majors = majorService.getMajorList(courseList, majorList);
        for (Major major : majors){
            System.out.println(major.getMajorId());
        }
        System.out.println("-----------------");
        List<Department> departmentList = departmentService.selectDepartmentList();
        List<Department> departments = departmentService.getDepartmentList(majors, departmentList);
        for (Department department : departments){
            System.out.println(department.getDepartmentId());
        }
        return JSONResult.ok(departments);
    }
}
