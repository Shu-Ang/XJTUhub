package com.xjtuhub.mapper;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Footprints;
import com.xjtuhub.entity.Role;

import java.util.Date;
import java.util.List;

public interface FootprintsMapper {
    int insert(Footprints record);

    int insertSelective(Footprints record);
    List<Footprints> selectFootprintsByRole(Role role);
    int countBlogViews(Blog blog);
    int countViewsByDate(Footprints footprints);
}