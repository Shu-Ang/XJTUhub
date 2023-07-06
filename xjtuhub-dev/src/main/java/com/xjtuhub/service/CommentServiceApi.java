package com.xjtuhub.service;

import com.xjtuhub.entity.Comment;
import com.xjtuhub.entity.Page;

import java.util.List;

public interface CommentServiceApi {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);


    int updateByPrimaryKey(Comment record);

    int countComment();

    List<Comment> selectCommentPage(Page page);
}
