package com.xjtuhub.service;

import com.xjtuhub.entity.*;

import java.util.List;

public interface FavoritesServiceApi {
    int countFavoriteByRole(Role role);
    int countFavoritesByRole(Role role);
    int countFavoriteByFavorite(Favorites favorites);
    List<Favorites> selectFavoriteByRole(Role role, Page page);
    List<Favorites> selectByRole(Role role);
    List<FavoriteBlog> selectBlogByFavorite(Favorites favorites);
    List<FavoriteBlog> selectBlogListByFavorite(Favorites favorites, Page page);

    int starBlog(Favorites_relationKey favoritesRelationKey);
    int newFavorite(Favorites record);
    int updateByPrimaryKeySelective(Favorites record);
    int deleteFavoriteBlog(Favorites_relationKey favoritesRelationKey);
    int deleteFavoriteRelation(Favorites favorites);
    int deleteFavoriteByPrimaryKey(Integer favoriteId);
}
