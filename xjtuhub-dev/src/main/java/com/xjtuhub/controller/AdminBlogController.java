package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Page;
import com.xjtuhub.service.impl.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@Api(tags = "稿件管理")
@RequestMapping("/admin/blog")
@RestController
public class AdminBlogController {

    @Autowired
    private BlogService blogService;
    @PostMapping("/addBlog")
    @ApiOperation(value = "插入博客")
    public JSONResult insertBlog(@RequestBody Blog blog){
        blog.setStatus(0);
        blog.setReleaseDate(new Date());
        blogService.insert(blog);
        return JSONResult.ok();
    }
    @GetMapping("/rawBlogPage")
    @ApiOperation(value = "分页查询未审核博客")
    public JSONResult selectRawBlogPage(Page page){
        // 获取总条数
        int totalNum = blogService.countRawBlog();
        page.setTotalNum(totalNum);
        // 根据条件查询
        List<Blog> blogList = blogService.selectAdminRawBlog(page);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }

    @PostMapping("/passBlog")
    @ApiOperation(value = "审核通过")
    public JSONResult passBlog(@RequestBody Blog blog){
        Integer blogId = blog.getBlogId();
        Blog dbblog = blogService.selectByPrimaryKey(blogId);
        dbblog.setStatus(1);
        blogService.updateByPrimaryKeySelective(dbblog);
        return JSONResult.ok();
    }

    @PostMapping("/unPassBlog")
    @ApiOperation(value = "审核未通过")
    public JSONResult unPassBlog(@RequestBody Blog blog){
        Integer blogId = blog.getBlogId();
        Blog dbblog = blogService.selectByPrimaryKey(blogId);
        dbblog.setStatus(-1);
        blogService.updateByPrimaryKeySelective(dbblog);
        return JSONResult.ok();
    }

    @GetMapping("/allBlogPage")
    @ApiOperation(value = "分页查询所有审核过的博客")
    public JSONResult selectAllBlogPage(Page page){
        // 获取总条数
        int totalNum = blogService.countAllBlog();
        page.setTotalNum(totalNum);
        // 根据条件查询
        List<Blog> blogList = blogService.selectAdminAllBlog(page);
        page.setResultList(blogList);
        return JSONResult.ok(page);
    }
    @PostMapping("/deleteBlog")
    @ApiOperation(value = "删除博客")
    public JSONResult deleteBlog(@RequestBody Blog blog){
        Integer blogId = blog.getBlogId();
        blogService.deleteByPrimaryKey(blogId);
        return JSONResult.ok();
    }

    @GetMapping("/countArticle")
    @ApiOperation(value = "文章数")
    public JSONResult countArticle(){
        return JSONResult.ok(blogService.countArticle());
    }
    @GetMapping("/countQuestion")
    @ApiOperation(value = "问题数")
    public JSONResult countQuestion(){
        return JSONResult.ok(blogService.countQuestion());
    }
    @GetMapping("/countView")
    @ApiOperation(value = "访问量")
    public JSONResult countViews(){
        return JSONResult.ok(blogService.countViews());
    }

}
