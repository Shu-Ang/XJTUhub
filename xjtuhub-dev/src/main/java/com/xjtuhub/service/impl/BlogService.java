package com.xjtuhub.service.impl;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Page;
import com.xjtuhub.mapper.BlogMapper;
import com.xjtuhub.service.BlogServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements BlogServiceApi {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public int deleteByPrimaryKey(Integer blogId) {
        return blogMapper.deleteByPrimaryKey(blogId);
    }

    @Override
    public int insert(Blog record) {
        return blogMapper.insert(record);
    }

    @Override
    public int insertSelective(Blog record) {
        return blogMapper.insertSelective(record);
    }

    @Override
    public Blog selectByPrimaryKey(Integer blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    public int updateByPrimaryKeySelective(Blog record) {
        return blogMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public int updateByPrimaryKey(Blog record) {
        return blogMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Blog> selectAdminRawBlog(Page page) {
        return blogMapper.selectAdminRawBlog(page);
    }
    @Override
    public int countRawBlog() {
        return blogMapper.countRawBlog();
    }

    @Override
    public int countAllBlog() {
        return blogMapper.countAllBlog();
    }

    @Override
    public List<Blog> selectAdminAllBlog(Page page) {
        return blogMapper.selectAdminAllBlog(page);
    }

    @Override
    public int countArticle() {
        return blogMapper.countArticle();
    }

    @Override
    public int countQuestion() {
        return blogMapper.countQuestion();
    }

    @Override
    public int countViews() {
        return blogMapper.countViews();
    }
}
