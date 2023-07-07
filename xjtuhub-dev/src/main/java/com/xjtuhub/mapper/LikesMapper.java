package com.xjtuhub.mapper;

import com.xjtuhub.entity.Likes;

public interface LikesMapper {
    int deleteByPrimaryKey(Integer likesId);

    int insert(Likes record);

    int insertSelective(Likes record);

    Likes selectByPrimaryKey(Integer likesId);

    int updateByPrimaryKeySelective(Likes record);

    int updateByPrimaryKey(Likes record);

    int countUserLikes(String roleId);
}