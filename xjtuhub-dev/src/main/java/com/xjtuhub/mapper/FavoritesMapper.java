package com.xjtuhub.mapper;

import com.xjtuhub.entity.Favorites;

import java.util.List;

public interface FavoritesMapper {
    int deleteByPrimaryKey(Integer favoriteId);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Integer favoriteId);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);

    List<Favorites> selectByRoleId(String roleId);
}