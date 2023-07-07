package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Blog;
import com.xjtuhub.service.impl.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "稿件")
@RequestMapping("/blog")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/detail")
    @ApiOperation(value = "根据id获取博客")
    public JSONResult getBlogDetail( Integer blogId){
        return JSONResult.ok(blogService.selectByPrimaryKey(blogId));
    }

    @PostMapping("/releaseBlog")
    @ApiOperation(value = "发布博客")
    public JSONResult releaseBlog(@RequestBody Blog blog){
        return JSONResult.ok(blogService.insert(blog));
    }
    @PostMapping("/editBlog")
    @ApiOperation(value = "编辑博客")
    public JSONResult editBlog(@RequestBody Blog blog){
        return JSONResult.ok(blogService.updateByPrimaryKeySelective(blog));
    }
}
