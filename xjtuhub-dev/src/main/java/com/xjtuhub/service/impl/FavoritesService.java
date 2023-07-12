package com.xjtuhub.service.impl;

import com.xjtuhub.entity.*;
import com.xjtuhub.mapper.FavoritesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.xjtuhub.mapper.Favorites_relationMapper;
import com.xjtuhub.service.FavoritesServiceApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoritesService implements FavoritesServiceApi {
    @Autowired
    private Favorites_relationMapper favoritesRelationMapper;
    @Autowired
    private FavoritesMapper favoritesMapper;
    @Override
    public int countFavoriteByRole(Role role) {
        return favoritesRelationMapper.countFavoriteByRole(role);
    }

    public int countFavoritesByRole(Role role){
        return favoritesMapper.countFavoriteByRole(role);
    }

    @Override
    public int countFavoriteByFavorite(Favorites favorites) {
        return favoritesRelationMapper.countFavoriteByFavorite(favorites);
    }

    @Override
    public List<Favorites> selectFavoriteByRole(Role role, Page page) {
        Map map = new HashMap();
        // map的key要和mapper.xml中保持一致
        map.put("page", page);
        map.put("role", role);
        return favoritesMapper.selectFavoriteByRole(map);
    }

    @Override
    public List<Favorites> selectByRole(Role role) {
        return favoritesMapper.selectByRole(role);
    }

    @Override
    public List<FavoriteBlog> selectBlogByFavorite(Favorites favorites) {
        return favoritesRelationMapper.selectBlogByFavorite(favorites);
    }

    @Override
    public List<FavoriteBlog> selectBlogListByFavorite(Favorites favorites, Page page) {
        Map map = new HashMap();
        // map的key要和mapper.xml中保持一致
        map.put("page", page);
        map.put("favorites", favorites);
        return favoritesRelationMapper.selectBlogListByFavorite(map);
    }

    @Override
    public int starBlog(Favorites_relationKey favoritesRelationKey) {
        return favoritesRelationMapper.insert(favoritesRelationKey);
    }

    @Override
    public int newFavorite(Favorites record) {
        return favoritesMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Favorites record) {
        return favoritesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteFavoriteBlog(Favorites_relationKey favoritesRelationKey) {
        return favoritesRelationMapper.deleteFavoriteBlog(favoritesRelationKey);
    }


    @Override
    public int deleteFavoriteRelation(Favorites favorites) {
        return favoritesRelationMapper.deleteFavoriteRelation(favorites);
    }

    @Override
    public int deleteFavoriteByPrimaryKey(Integer favoriteId) {
        return favoritesMapper.deleteByPrimaryKey(favoriteId);
    }
}
