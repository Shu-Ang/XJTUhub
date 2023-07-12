package com.xjtuhub.mapper;

import com.xjtuhub.entity.Favorites;
import com.xjtuhub.entity.Role;

import java.util.List;
import java.util.Map;

public interface FavoritesMapper {
    int deleteByPrimaryKey(Integer favoriteId);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Integer favoriteId);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);

    int countFavoriteByRole(Role role);
    List<Favorites> selectByRole(Role role);
    List<Favorites> selectFavoriteByRole(Map map);
}