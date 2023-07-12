package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Footprints;
import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;
import com.xjtuhub.mapper.FootprintsMapper;
import com.xjtuhub.service.FootprintsServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FootprintsService implements FootprintsServiceApi {
    @Autowired
    private FootprintsMapper footprintsMapper;
    @Override
    public List<Footprints> selectFootprintsByRole(Role role, Page page) {
        Map map = new HashMap();
        // map的key要和mapper.xml中保持一致
        map.put("page", page);
        map.put("role", role);
        return footprintsMapper.selectFootprintsByRole(map);
    }

    @Override
    public int insert(Footprints record) {
        return footprintsMapper.insert(record);
    }

    @Override
    public int countBlogViews(Blog blog) {
        return footprintsMapper.countBlogViews(blog);
    }

    @Override
    public int countFootprintsByRole(Role role) {
        return footprintsMapper.countFootprintsByRole(role);
    }

    @Override
    public int countViewsByDate(Footprints footprints) {
        return footprintsMapper.countViewsByDate(footprints);
    }
}
