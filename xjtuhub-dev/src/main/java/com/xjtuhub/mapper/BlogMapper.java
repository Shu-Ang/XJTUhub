package com.xjtuhub.mapper;

import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;

import java.util.List;
import java.util.Map;

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
    int countArticleByRole(Role role);
    int countQuestionByRole(Role role);
    int countViewsByRole(Role role);
    List<Blog> selectDraftByRole(Role role);
    List<Blog> selectArticleByRole(Role role);
    List<Blog> selectQuestionByRole(Role role);
    List<Blog> selectArticleByContent(Blog blog);
    List<Blog> selectQuestionByContent(Blog blog);
    List<Blog> selectArticleList(Page page);
    List<Blog> selectQuestionList(Page page);
    List<Blog> selectArticleByCourse(Course course);
    List<Blog> selectQuestionByCourse(Course course);
    List<Blog> selectRelativeBlog(Blog blog);
    int countStarByBlog(Blog blog);
    List<Blog> selectAnswerListByBlog(Blog blog);
    int countAnswerByRole(Role role);
    int countArticleByCourse(Course course);
    List<Blog> selectArticlePageByCourse(Map map);
    int countQuestionByCourse(Course course);
    List<Blog> selectQuestionPageByCourse(Map map);
    int countArticleByContent(Blog blog);
    List<Blog> selectArticlePageByContent(Map map);
    int countQuestionByContent(Blog blog);
    List<Blog> selectQuestionPageByContent(Map map);


}