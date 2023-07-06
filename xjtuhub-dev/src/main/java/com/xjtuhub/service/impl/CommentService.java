package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Comment;
import com.xjtuhub.entity.Page;
import com.xjtuhub.mapper.CommentMapper;
import com.xjtuhub.service.CommentServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentService implements CommentServiceApi {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int deleteByPrimaryKey(Integer commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    @Override
    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    @Override
    public Comment selectByPrimaryKey(Integer commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

    @Override
    public int countComment() {
        return commentMapper.countComment();
    }

    @Override
    public List<Comment> selectCommentPage(Page page) {
        return commentMapper.selectCommentPage(page);
    }
}
