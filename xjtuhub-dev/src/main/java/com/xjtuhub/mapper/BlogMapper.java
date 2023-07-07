package com.xjtuhub.mapper;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Page;

import java.util.List;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    int countRawBlog();
    List<Blog> selectAdminRawBlog(Page page);
    int countAllBlog();
    List<Blog> selectAdminAllBlog(Page page);

    int countArticle();
    int countQuestion();
    int countViews();
    int countUserArticle(String roleId);
    int countUserQuestion(String roleId);
    int countUserViews(String roleId);
    List<Blog> selectDraftById(String roleId);
    List<Blog> selectArticleById(String roleId);
    List<Blog> selectQuestionById(String roleId);
    List<Blog> selectArticleByContent(String content);
    List<Blog> selectQuestionByContent(String content);
    List<Blog> selectArticleList();
    List<Blog> selectQuestionList();
    List<Blog> selectArticleByCourseId(Integer courseId);
    List<Blog> selectQuestionByCourseId(Integer courseId);
}