package com.xjtuhub.controller;

import com.alibaba.fastjson.JSON;
import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Follow;
import com.xjtuhub.entity.Footprints;
import com.xjtuhub.entity.Role;
import com.xjtuhub.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "个人中心")
@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private LikesService likesService;
    @Autowired
    private FollowService followService;
    @Autowired
    private FavoritesService favoriteService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FootprintsService footprintsService;
    @GetMapping("/countArticle")
    @ApiOperation(value = "文章数")
    public JSONResult countUserArticle(@RequestBody String roleId){
        return JSONResult.ok(blogService.countUserArticle(roleId));
    }
    @GetMapping("/countQuestion")
    @ApiOperation(value = "问题数")
    public JSONResult countUserQuestion(@RequestBody String roleId){
        return JSONResult.ok(blogService.countUserQuestion(roleId));
    }
    @GetMapping("/countViews")
    @ApiOperation(value = "浏览量")
    public JSONResult countUserViews(@RequestBody String roleId){
        return JSONResult.ok(blogService.countUserViews(roleId));
    }
    @GetMapping("/countLikes")
    @ApiOperation(value = "点赞数")
    public JSONResult countUserLikes(@RequestBody String roleId){
        return JSONResult.ok(likesService.countUserLikes(roleId));
    }

    @GetMapping("/countFans")
    @ApiOperation(value = "粉丝数")
    public JSONResult countUserFans(@RequestBody String roleId){
        return JSONResult.ok(followService.countFans(roleId));
    }

    @GetMapping("/countFavorite")
    @ApiOperation(value = "收藏数")
    public JSONResult countFavorite(@RequestBody String roleId){
        return JSONResult.ok(favoriteService.countFavorite(roleId));
    }

    @GetMapping("/selectFollowList")
    @ApiOperation(value = "关注列表")
    public JSONResult selectFollowList(@RequestBody String roleId){
        return JSONResult.ok(roleService.selectFollowList(roleId));
    }

    @GetMapping("/selectFanList")
    @ApiOperation(value = "粉丝列表")
    public JSONResult selectFanList(@RequestBody String roleId){
        return JSONResult.ok(roleService.selectFanList(roleId));
    }

    @PostMapping("/follow")
    @ApiOperation(value = "关注")
    public JSONResult follow(@RequestBody Follow follow){
        return JSONResult.ok(followService.follow(follow));
    }

    @PostMapping("/editInfo")
    @ApiOperation(value = "编辑个人信息")
    public JSONResult editInfo(@RequestBody Role role){
        return JSONResult.ok(roleService.updateRoleSelective(role));
    }

    @GetMapping("/favorites")
    @ApiOperation(value = "获取用户的收藏夹")
    public JSONResult getFavorites(@RequestBody String roleId){
        return JSONResult.ok(favoriteService.selectByRoleId(roleId));
    }
    @GetMapping("/favoriteBlog")
    @ApiOperation(value = "根据收藏夹ID获取收藏夹内容")
    public JSONResult getFavoriteBlog(@RequestBody Integer favoriteId){
        return JSONResult.ok(favoriteService.selectBlogListByFavorite(favoriteId));
    }
    @GetMapping("/draft")
    @ApiOperation(value = "查看草稿箱")
    public JSONResult getDraft(@RequestBody String roleId){
        return JSONResult.ok(blogService.selectDraftById(roleId));
    }
    @GetMapping("/footprints")
    @ApiOperation(value = "查看历史记录")
    public JSONResult getFootprints(@RequestBody String roleId){
        List<Footprints> footprintsList = footprintsService.selectFootprintsByRoleId(roleId);
        List<Blog> BlogList=new ArrayList<Blog>();//结果博客列表
        for (Footprints footprints : footprintsList){
            Integer blogId = footprints.getBlogId();
            Blog blog = blogService.selectByPrimaryKey(blogId);
            BlogList.add(blog);
        }
        return JSONResult.ok(BlogList);
    }

}
