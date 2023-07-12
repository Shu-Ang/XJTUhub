package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Likes;
import com.xjtuhub.entity.Role;
import com.xjtuhub.mapper.LikesMapper;
import com.xjtuhub.service.LikesServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService implements LikesServiceApi {
    @Autowired
    private LikesMapper likesMapper;

    @Override
    public int deleteByPrimaryKey(Integer likesId) {
        return likesMapper.deleteByPrimaryKey(likesId);
    }

    @Override
    public int insert(Likes record) {
        return likesMapper.insert(record);
    }

    @Override
    public int insertSelective(Likes record) {
        return likesMapper.insertSelective(record);
    }

    @Override
    public Likes selectByPrimaryKey(Integer likesId) {
        return likesMapper.selectByPrimaryKey(likesId);
    }

    @Override
    public int updateByPrimaryKeySelective(Likes record) {
        return likesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Likes record) {
        return likesMapper.updateByPrimaryKey(record);
    }

    @Override
    public int countLikesByBlog(Blog blog) {
        return likesMapper.countLikesByBlog(blog);
    }

    @Override
    public int countLikesByRole(Role role) {
        return likesMapper.countLikesByRole(role);
    }

    @Override
    public int isLike(Likes likes) {
        return likesMapper.isLike(likes);
    }

    @Override
    public int deleteLike(Likes likes) {
        return likesMapper.deleteLike(likes);
    }


}
