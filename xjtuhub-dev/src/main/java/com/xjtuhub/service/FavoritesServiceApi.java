package com.xjtuhub.service;

import com.xjtuhub.entity.FavoriteBlog;
import com.xjtuhub.entity.Favorites;

import java.util.List;

public interface FavoritesServiceApi {
    int countFavorite(String roleId);
    List<Favorites> selectByRoleId(String roleId);
    List<FavoriteBlog> selectBlogListByFavorite(Integer favoriteId);
}
