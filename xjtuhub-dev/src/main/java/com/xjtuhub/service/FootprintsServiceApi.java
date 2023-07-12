package com.xjtuhub.service;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Footprints;
import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;

import java.util.Date;
import java.util.List;

public interface FootprintsServiceApi {
    List<Footprints> selectFootprintsByRole(Role role, Page page);
    int insert(Footprints record);
    int countBlogViews(Blog blog);
    int countFootprintsByRole(Role role);
    int countViewsByDate(Footprints footprints);
}
