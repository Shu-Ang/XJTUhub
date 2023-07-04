package com.xjtuhub.mapper;

import com.xjtuhub.entity.Footprints;

public interface FootprintsMapper {
    int insert(Footprints record);

    int insertSelective(Footprints record);
}