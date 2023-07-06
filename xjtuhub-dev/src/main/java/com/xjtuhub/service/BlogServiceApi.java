package com.xjtuhub.service;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Page;

import java.util.List;

public interface BlogServiceApi {

    int deleteByPrimaryKey(Integer blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    public int countRawBlog();
    List<Blog> selectAdminRawBlog(Page page);
    public int countAllBlog();
    List<Blog> selectAdminAllBlog(Page page);
    int countArticle();
    int countQuestion();
    int countViews();
}
