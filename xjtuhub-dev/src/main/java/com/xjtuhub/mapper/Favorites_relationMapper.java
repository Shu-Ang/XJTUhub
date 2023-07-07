package com.xjtuhub.mapper;

import com.xjtuhub.entity.FavoriteBlog;
import com.xjtuhub.entity.Favorites_relationKey;

import java.util.List;

public interface Favorites_relationMapper {
    int deleteByPrimaryKey(Favorites_relationKey key);

    int insert(Favorites_relationKey record);

    int insertSelective(Favorites_relationKey record);
    int countFavorite(String roleId);
    List<FavoriteBlog> selectBlogListByFavorite(Integer favoriteId);
}