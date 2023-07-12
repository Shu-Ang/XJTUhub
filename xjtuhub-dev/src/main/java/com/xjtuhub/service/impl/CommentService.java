package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Comment;
import com.xjtuhub.entity.Page;
import com.xjtuhub.mapper.CommentMapper;
import com.xjtuhub.service.CommentServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public List<Comment> processComment(List<Comment> list) {
        Map<Integer, Comment> map = new HashMap<>();   // (id, Comment)
        List<Comment> result = new ArrayList<>();
        // 将所有根评论加入 result
        for(Comment comment : list) {
            if(comment.getParentId() == null)
                result.add(comment);
            map.put(comment.getCommentId(), comment);
        }
        // 子评论加入到父评论的 child 中
        for(Comment comment : list) {
            Integer id = comment.getParentId();
            if(id != null) {   // 当前评论为子评论
                Comment p = map.get(id);
                if(p.getChild() == null)    // child 为空，则创建
                    p.setChild(new ArrayList<>());
                p.getChild().add(comment);
            }
        }
        return result;
    }

    @Override
    public List<Comment> selectCommentListByBlog(Blog blog) {
        return commentMapper.selectCommentListByBlog(blog);
    }

    @Override
    public Integer countCommentByBlog(Blog blog) {
        return commentMapper.countCommentByBlog(blog);
    }

    @Override
    public int deleteComment(Comment comment) {
        List<Comment> childList = comment.getChild();
        if (childList != null){
            for(Comment child : childList){
                deleteComment(child);
            }
        }
        return commentMapper.deleteByPrimaryKey(comment.getCommentId());
    }

}
