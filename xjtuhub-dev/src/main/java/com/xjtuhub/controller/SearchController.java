package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Course;
import com.xjtuhub.entity.Page;
import com.xjtuhub.service.impl.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "搜索")
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/newArticlePage")
    @ApiOperation(value = "分页搜索最新博客")
    public JSONResult searchNewArticle(Page page){
        int totalNum = blogService.countArticle();
        page.setTotalNum(totalNum);
        List<Blog> blogList = blogService.selectArticleList(page);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }
    @GetMapping("/newQuestionPage")
    @ApiOperation(value = "分页搜索最新问答")
    public JSONResult searchNewQuestion(Page page){
        int totalNum = blogService.countQuestion();
        page.setTotalNum(totalNum);
        List<Blog> blogList = blogService.selectQuestionList(page);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }
    @GetMapping("/keyArticle")
    @ApiOperation(value = "关键词搜索文章")
    public JSONResult searchArticleByKey(Blog blog){
        return JSONResult.ok(blogService.selectArticleByContent(blog));
    }

    @GetMapping("/keyQuestion")
    @ApiOperation(value = "关键词搜索问答")
    public JSONResult searchQuestionByKey(Blog blog){
        return JSONResult.ok(blogService.selectQuestionByContent(blog));
    }

    @GetMapping("/ArticleByCourse")
    @ApiOperation(value = "根据课程号查询文章")
    public JSONResult searchArticleByCourseId(Course course){
        return JSONResult.ok(blogService.selectArticleByCourse(course));
    }
    @GetMapping("/QuestionByCourse")
    @ApiOperation(value = "根据课程号查询问题")
    public JSONResult searchQuestionByCourseId(Course course){
        return JSONResult.ok(blogService.selectQuestionByCourse(course));
    }
    @GetMapping("/ArticlePageByCourse")
    @ApiOperation(value = "根据课程号分页查询文章")
    public JSONResult searchArticlePageByCourse( Page page, Course course){
        int totalNum = blogService.countArticleByCourse(course);
        page.setTotalNum(totalNum);
        List<Blog> blogList = blogService.selectArticlePageByCourse(page,course);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }
    @GetMapping("/QuestionPageByCourse")
    @ApiOperation(value = "根据课程号分页查询问题")
    public JSONResult searchQuestionPageByCourse( Page page, Course course){
        int totalNum = blogService.countQuestionByCourse(course);
        page.setTotalNum(totalNum);
        List<Blog> blogList = blogService.selectQuestionPageByCourse(page,course);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }

    @GetMapping("/ArticlePageByContent")
    @ApiOperation(value = "根据内容分页查询文章")
    public JSONResult searchArticlePageByContent( Page page, Blog blog){
        int totalNum = blogService.countArticleByContent(blog);
        page.setTotalNum(totalNum);
        List<Blog> blogList = blogService.selectArticlePageByContent(page, blog);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }
    @GetMapping("/QuestionPageByContent")
    @ApiOperation(value = "根据内容分页查询问题")
    public JSONResult searchQuestionPageByContent( Page page, Blog blog){
        int totalNum = blogService.countQuestionByContent(blog);
        page.setTotalNum(totalNum);
        List<Blog> blogList = blogService.selectQuestionPageByContent(page,blog);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }
}
