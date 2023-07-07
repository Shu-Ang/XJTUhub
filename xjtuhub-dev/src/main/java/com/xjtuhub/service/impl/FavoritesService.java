package com.xjtuhub.service.impl;

import com.xjtuhub.entity.FavoriteBlog;
import com.xjtuhub.entity.Favorites;
import com.xjtuhub.mapper.FavoritesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.xjtuhub.mapper.Favorites_relationMapper;
import com.xjtuhub.service.FavoritesServiceApi;

import java.util.List;

@Service
public class FavoritesService implements FavoritesServiceApi {
    @Autowired
    private Favorites_relationMapper favoritesRelationMapper;
    @Autowired
    private FavoritesMapper favoritesMapper;
    @Override
    public int countFavorite(String roleId) {
        return favoritesRelationMapper.countFavorite(roleId);
    }

    @Override
    public List<Favorites> selectByRoleId(String roleId) {
        return favoritesMapper.selectByRoleId(roleId);
    }

    @Override
    public List<FavoriteBlog> selectBlogListByFavorite(Integer favoriteId) {
        return favoritesRelationMapper.selectBlogListByFavorite(favoriteId);
    }
}
