package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.service.impl.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "搜索")
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/newArticle")
    @ApiOperation(value = "搜索最新博客")
    public JSONResult searchNewArticle(){
        return JSONResult.ok(blogService.selectArticleList());
    }
    @GetMapping("/newQuestion")
    @ApiOperation(value = "搜索最新博客")
    public JSONResult searchNewQuestion(){
        return JSONResult.ok(blogService.selectQuestionList());
    }
    @GetMapping("/keyArticle")
    @ApiOperation(value = "关键词搜索文章")
    public JSONResult searchArticleByKey(@RequestBody String key){
        return JSONResult.ok(blogService.selectArticleByContent(key));
    }

    @GetMapping("/keyQuestion")
    @ApiOperation(value = "关键词搜索问答")
    public JSONResult searchQuestionByKey(@RequestBody String key){
        return JSONResult.ok(blogService.selectQuestionByContent(key));
    }

    @GetMapping("/courseIdArticle")
    @ApiOperation(value = "根据课程号查询文章")
    public JSONResult searchArticleByCourseId(@RequestBody Integer courseId){
        return JSONResult.ok(blogService.selectArticleByCourseId(courseId));
    }
    @GetMapping("/courseIdQuestion")
    @ApiOperation(value = "根据课程号查询文章")
    public JSONResult searchQuestionByCourseId(@RequestBody Integer courseId){
        return JSONResult.ok(blogService.selectQuestionByCourseId(courseId));
    }
}
