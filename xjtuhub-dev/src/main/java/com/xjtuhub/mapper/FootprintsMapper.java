package com.xjtuhub.mapper;

import com.xjtuhub.entity.Footprints;

import java.util.List;

public interface FootprintsMapper {
    int insert(Footprints record);

    int insertSelective(Footprints record);
    List<Footprints> selectFootprintsByRoleId(String roleId);
}