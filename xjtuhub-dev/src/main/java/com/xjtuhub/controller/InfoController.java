package com.xjtuhub.controller;

import com.alibaba.fastjson.JSON;
import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.*;
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
    public JSONResult countArticleByRole(Role role){
        return JSONResult.ok(blogService.countArticleByRole(role));
    }
    @GetMapping("/countQuestion")
    @ApiOperation(value = "问题数")
    public JSONResult countQuestionByRole(Role role){
        return JSONResult.ok(blogService.countQuestionByRole(role));
    }
    @GetMapping("/countViews")
    @ApiOperation(value = "浏览量")
    public JSONResult countViewsByRole(Role role){
        return JSONResult.ok(blogService.countViewsByRole(role));
    }
    @GetMapping("/countLikes")
    @ApiOperation(value = "点赞数")
    public JSONResult countUserLikesByRole(Role role){
        return JSONResult.ok(likesService.countLikesByRole(role));
    }

    @GetMapping("/countFans")
    @ApiOperation(value = "粉丝数")
    public JSONResult countUserFans(Role role){
        return JSONResult.ok(followService.countFansByRole(role));
    }

    @GetMapping("/countFavorite")
    @ApiOperation(value = "收藏数")
    public JSONResult countFavoriteByRole( Role role){
        return JSONResult.ok(favoriteService.countFavoriteByRole(role));
    }

    @GetMapping("/selectFollowList")
    @ApiOperation(value = "关注列表")
    public JSONResult selectFollowList( Role role){
        for(Role role1 : roleService.selectFollowList(role)){
        }
        return JSONResult.ok(roleService.selectFollowList(role));
    }

    @GetMapping("/selectFanList")
    @ApiOperation(value = "粉丝列表")
    public JSONResult selectFanList( Role role){
        return JSONResult.ok(roleService.selectFanList(role));
    }

    @PostMapping("/follow")
    @ApiOperation(value = "关注")
    public JSONResult follow(@RequestBody Follow follow){
        return JSONResult.ok(followService.follow(follow));
    }

    @GetMapping("/isFollow")
    @ApiOperation("是否关注")
    public JSONResult isFollow(Follow follow){
        return JSONResult.ok(followService.isFollow(follow));
    }

    @PostMapping("/unFollow")
    @ApiOperation(value = "取关")
    public JSONResult unFollow(@RequestBody Follow follow){
        return JSONResult.ok(followService.unFollow(follow));
    }
    @PostMapping("/editInfo")
    @ApiOperation(value = "编辑个人信息")
    public JSONResult editInfo(@RequestBody Role role){
        return JSONResult.ok(roleService.updateRoleSelective(role));
    }

    @GetMapping("/favorites")
    @ApiOperation(value = "获取用户的收藏夹")
    public JSONResult getFavorites( Role role){
        return JSONResult.ok(favoriteService.selectByRole(role));
    }
    @GetMapping("/favoriteBlog")
    @ApiOperation(value = "根据收藏夹ID获取收藏夹内容")
    public JSONResult getFavoriteBlog(Favorites favorite){
        return JSONResult.ok(favoriteService.selectBlogListByFavorite(favorite));
    }
    @PostMapping("/setFavoritePrivate")
    @ApiOperation(value = "将收藏夹设为私密")
    public JSONResult setFavoritePrivate(@RequestBody  Favorites favorites){
        favorites.setIsPrivate(1);
        return JSONResult.ok(favoriteService.updateByPrimaryKeySelective(favorites));
    }
    @PostMapping("/deleteFavoriteBlog")
    @ApiOperation(value = "删除收藏夹博客")
    public JSONResult deleteFavoriteBlog(@RequestBody Favorites_relationKey favoritesRelationKey){
        return JSONResult.ok(favoriteService.deleteFavoriteBlog(favoritesRelationKey));
    }
    @PostMapping("/deleteFavorite")
    @ApiOperation(value = "删除整个收藏夹")
    public JSONResult deleteFavorite(@RequestBody Favorites favorites){
        System.out.println(favorites.toString());
        favoriteService.deleteFavoriteRelation(favorites);
        return JSONResult.ok(favoriteService.deleteFavoriteByPrimaryKey(favorites.getFavoriteId()));
    }
    @PostMapping("/deleteBlog")
    @ApiOperation(value = "删除博客")
    public JSONResult deleteBlog(@RequestBody Blog blog){
        Integer blogId = blog.getBlogId();
        return JSONResult.ok(blogService.deleteByPrimaryKey(blogId));
    }
    @GetMapping("/draft")
    @ApiOperation(value = "查看草稿箱")
    public JSONResult getDraft(Role role){
        return JSONResult.ok(blogService.selectDraftByRole(role));
    }
    @GetMapping("/footprints")
    @ApiOperation(value = "查看历史记录")
    public JSONResult getFootprints(Role role){
        List<Footprints> footprintsList = footprintsService.selectFootprintsByRole(role);
        List<Blog> BlogList=new ArrayList<Blog>();//结果博客列表
        for (Footprints footprints : footprintsList){
            Integer blogId = footprints.getBlogId();
            Blog blog = blogService.selectByPrimaryKey(blogId);
            BlogList.add(blog);
        }
        return JSONResult.ok(BlogList);
    }

    @GetMapping("/getInfo")
    @ApiOperation(value = "根据roleId获取信息")
    public JSONResult getInfoByRole(Role role){
        return JSONResult.ok(roleService.selectInfoByRole(role));
    }

    @GetMapping("/getArticleByRole")
    @ApiOperation("根据用户获得文章列表")
    public JSONResult getArticleByRole(Role role){
        return JSONResult.ok(blogService.selectArticleByRole(role));
    }

    @GetMapping("/getQuestionByRole")
    @ApiOperation("根据用户获得文章列表")
    public JSONResult getQuestionByRole(Role role){
        return JSONResult.ok(blogService.selectQuestionByRole(role));
    }

    @PostMapping("newFavorite")
    @ApiOperation(value = "新建收藏夹")
    public JSONResult newFavorite(@RequestBody Favorites favorites){
        return JSONResult.ok(favoriteService.newFavorite(favorites));
    }
}
