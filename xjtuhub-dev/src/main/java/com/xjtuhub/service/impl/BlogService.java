package com.xjtuhub.service.impl;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Page;
import com.xjtuhub.entity.Role;
import com.xjtuhub.mapper.BlogMapper;
import com.xjtuhub.service.BlogServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogService implements BlogServiceApi {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private LikesService likesService;

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

    @Override
    public int countArticleByRole(Role role) {
        return blogMapper.countArticleByRole(role);
    }

    @Override
    public int countQuestionByRole(Role role) {
        return blogMapper.countQuestionByRole(role);
    }

    @Override
    public int countViewsByRole(Role role) {
        return blogMapper.countViewsByRole(role);
    }

    @Override
    public List<Blog> selectDraftByRole(Role role) {
        return blogMapper.selectDraftByRole(role);
    }

    @Override
    public List<Blog> selectArticleByRole(Role role) {
        return blogMapper.selectArticleByRole(role);
    }

    @Override
    public List<Blog> selectQuestionByRole(Role role) {
        return blogMapper.selectQuestionByRole(role);
    }

    @Override
    public List<Blog> selectArticleByContent(Blog blog) {
        return blogMapper.selectArticleByContent(blog);
    }

    @Override
    public List<Blog> selectQuestionByContent(Blog blog) {
        return blogMapper.selectQuestionByContent(blog);
    }

    @Override
    public List<Blog> selectArticleList(Page page) {
        return blogMapper.selectArticleList(page);
    }

    @Override
    public List<Blog> selectQuestionList(Page page) {
        return blogMapper.selectQuestionList(page);
    }

    @Override
    public List<Blog> selectArticleByCourse(Course course) {
        return blogMapper.selectArticleByCourse(course);
    }

    @Override
    public List<Blog> selectQuestionByCourse(Course course) {
        return blogMapper.selectQuestionByCourse(course);
    }

    @Override
    public List<Blog> selectRelativeBlog(Blog blog) {
        return blogMapper.selectRelativeBlog(blog);
    }

    @Override
    public int countStarByBlog(Blog blog) {
        return blogMapper.countStarByBlog(blog);
    }

    @Override
    public int countAnswerByRole(Role role) {
        return blogMapper.countAnswerByRole(role);
    }

    @Override
    public int countArticleByCourse(Course course) {
        return blogMapper.countArticleByCourse(course);
    }

    @Override
    public List<Blog> selectArticlePageByCourse(Page page, Course course) {
        Map map = new HashMap();
        // map的key要和mapper.xml中保持一致
        map.put("page", page);
        map.put("course", course);
        return blogMapper.selectArticlePageByCourse(map);
    }

    @Override
    public int countQuestionByCourse(Course course) {
        return blogMapper.countQuestionByCourse(course);
    }

    @Override
    public List<Blog> selectQuestionPageByCourse(Page page, Course course) {
        Map map = new HashMap();
        // map的key要和mapper.xml中保持一致
        map.put("page", page);
        map.put("course", course);
        return blogMapper.selectQuestionPageByCourse(map);
    }

    @Override
    public List<Blog> selectAnswerListByBlog(Blog blog) {
        List<Blog> blogList = blogMapper.selectAnswerListByBlog(blog);
        Collections.sort(blogList,(b1, b2)->{
            int ib1 = likesService.countLikesByBlog(b1);
            int ib2 = likesService.countLikesByBlog(b2);
            return ib2-ib1;
        });
        return blogList;
    }

    @Override
    public int countArticleByContent(Blog blog) {
        return blogMapper.countArticleByContent(blog);
    }

    @Override
    public List<Blog> selectArticlePageByContent(Page page, Blog blog) {
        Map map = new HashMap();
        // map的key要和mapper.xml中保持一致
        map.put("page", page);
        map.put("blog", blog);
        return blogMapper.selectArticlePageByContent(map);
    }

    @Override
    public int countQuestionByContent(Blog blog) {
        return blogMapper.countQuestionByContent(blog);
    }

    @Override
    public List<Blog> selectQuestionPageByContent(Page page, Blog blog) {
        Map map = new HashMap();
        // map的key要和mapper.xml中保持一致
        map.put("page", page);
        map.put("blog", blog);
        return blogMapper.selectQuestionPageByContent(map);
    }


}
