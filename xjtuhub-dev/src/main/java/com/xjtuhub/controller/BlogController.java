package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.service.impl.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "稿件")
@RequestMapping("/blog")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/detail")
    @ApiOperation(value = "根据id获取博客")
    public JSONResult getBlogDetail(Integer blogId){
        return JSONResult.ok(blogService.selectByPrimaryKey(blogId));
    }
}
