package com.xjtuhub.service;

import com.xjtuhub.entity.FavoriteBlog;
import com.xjtuhub.entity.Favorites;
import com.xjtuhub.entity.Favorites_relationKey;
import com.xjtuhub.entity.Role;

import java.util.List;

public interface FavoritesServiceApi {
    int countFavoriteByRole(Role role);
    List<Favorites> selectByRole(Role role);
    List<FavoriteBlog> selectBlogListByFavorite(Favorites favorites);

    int starBlog(Favorites_relationKey favoritesRelationKey);
    int newFavorite(Favorites record);
    int updateByPrimaryKeySelective(Favorites record);
    int deleteFavoriteBlog(Favorites_relationKey favoritesRelationKey);
    int deleteFavoriteRelation(Favorites favorites);
    int deleteFavoriteByPrimaryKey(Integer favoriteId);
}
