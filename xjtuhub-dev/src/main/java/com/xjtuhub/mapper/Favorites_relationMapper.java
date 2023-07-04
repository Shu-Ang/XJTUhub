package com.xjtuhub.mapper;

import com.xjtuhub.entity.Favorites_relationKey;

public interface Favorites_relationMapper {
    int deleteByPrimaryKey(Favorites_relationKey key);

    int insert(Favorites_relationKey record);

    int insertSelective(Favorites_relationKey record);
}