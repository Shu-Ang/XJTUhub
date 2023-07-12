package com.xjtuhub.mapper;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Likes;
import com.xjtuhub.entity.Role;

public interface LikesMapper {
    int deleteByPrimaryKey(Integer likesId);

    int insert(Likes record);

    int insertSelective(Likes record);

    Likes selectByPrimaryKey(Integer likesId);

    int updateByPrimaryKeySelective(Likes record);

    int updateByPrimaryKey(Likes record);

    int countLikesByBlog(Blog blog);
    int countLikesByRole(Role role);
    int isLike(Likes likes);
    int deleteLike(Likes likes);

}