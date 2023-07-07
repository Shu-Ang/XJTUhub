package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Footprints;
import com.xjtuhub.mapper.FootprintsMapper;
import com.xjtuhub.service.FootprintsServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FootprintsService implements FootprintsServiceApi {
    @Autowired
    private FootprintsMapper footprintsMapper;
    @Override
    public List<Footprints> selectFootprintsByRoleId(String roleId) {
        return footprintsMapper.selectFootprintsByRoleId(roleId);
    }
}
