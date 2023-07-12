package com.xjtuhub.service.impl;

import com.xjtuhub.entity.FavoriteBlog;
import com.xjtuhub.entity.Favorites;
import com.xjtuhub.entity.Favorites_relationKey;
import com.xjtuhub.entity.Role;
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
    public int countFavoriteByRole(Role role) {
        return favoritesRelationMapper.countFavoriteByRole(role);
    }

    @Override
    public List<Favorites> selectByRole(Role role) {
        return favoritesMapper.selectByRole(role);
    }

    @Override
    public List<FavoriteBlog> selectBlogListByFavorite(Favorites favorites) {
        return favoritesRelationMapper.selectBlogListByFavorite(favorites);
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
