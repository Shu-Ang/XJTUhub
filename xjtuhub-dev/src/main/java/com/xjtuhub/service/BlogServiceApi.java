package com.xjtuhub.service;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;

import java.util.List;
import java.util.Map;

public interface BlogServiceApi {

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
    int countArticleByRole(Role role);
    int countQuestionByRole(Role role);
    int countDraftByRole(Role role);
    int countViewsByRole(Role role);
    List<Blog> selectDraftByRole(Role role, Page page);
    List<Blog> selectArticleByRole(Role role, Page page);
    List<Blog> selectQuestionByRole(Role role, Page page);
    List<Blog> selectArticleByContent(Blog blog);
    List<Blog> selectQuestionByContent(Blog blog);
    List<Blog> selectArticleList(Page page);
    List<Blog> selectQuestionList(Page page);
    List<Blog> selectArticleByCourse(Course course);
    List<Blog> selectQuestionByCourse(Course course);
    List<Blog> selectRelativeBlog(Blog blog);
    int countStarByBlog(Blog blog);
    int countAnswerByRole(Role role);
    int countArticleByCourse(Course course);
    List<Blog> selectArticlePageByCourse(Page page, Course course);
    int countQuestionByCourse(Course course);
    List<Blog> selectQuestionPageByCourse(Page page, Course course);
    List<Blog> selectAnswerListByBlog(Blog blog);
    int countArticleByContent(Blog blog);
    List<Blog> selectArticlePageByContent(Page page, Blog blog);
    int countQuestionByContent(Blog blog);
    List<Blog> selectQuestionPageByContent(Page page, Blog blog);
}
