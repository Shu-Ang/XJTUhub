package com.xjtuhub.mapper;

import com.xjtuhub.entity.FavoriteBlog;
import com.xjtuhub.entity.Favorites;
import com.xjtuhub.entity.Favorites_relationKey;
import com.xjtuhub.entity.Role;

import java.util.List;
import java.util.Map;

public interface Favorites_relationMapper {
    int deleteFavoriteBlog(Favorites_relationKey key);

    int deleteFavoriteRelation(Favorites favorites);
    int insert(Favorites_relationKey record);

    int insertSelective(Favorites_relationKey record);
    int countFavoriteByRole(Role role);
    int countFavoriteByFavorite(Favorites favorites);

    List<FavoriteBlog> selectBlogListByFavorite(Map map);

    List<FavoriteBlog> selectBlogByFavorite(Favorites favorites);
}