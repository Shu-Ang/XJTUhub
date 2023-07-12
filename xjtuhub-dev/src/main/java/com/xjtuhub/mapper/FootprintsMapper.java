package com.xjtuhub.mapper;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Footprints;
import com.xjtuhub.entity.Role;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FootprintsMapper {
    int insert(Footprints record);

    int insertSelective(Footprints record);
    int countFootprintsByRole(Role role);
    List<Footprints> selectFootprintsByRole(Map map);
    int countBlogViews(Blog blog);
    int countViewsByDate(Footprints footprints);
}