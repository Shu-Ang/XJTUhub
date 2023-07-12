package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.*;
import com.xjtuhub.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "稿件")
@RequestMapping("/blog")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikesService likesService;
    @Autowired
    private FavoritesService favoritesService;
    @Autowired
    private FootprintsService footprintsService;
    @Autowired
    private RoleService roleService;
    @GetMapping("/detail")
    @ApiOperation(value = "根据id获取博客")
    public JSONResult getBlogDetail(Blog blog){
        return JSONResult.ok(blogService.selectByPrimaryKey(blog.getBlogId()));
    }

    @PostMapping("/releaseBlog")
    @ApiOperation(value = "发布博客")
    public JSONResult releaseBlog(@RequestBody Blog blog){
        blog.setStatus(0);
        if(blog.getRootId() == null){
            blog.setRootId(-1);
        }
        blog.setReleaseDate(new Date());
        return JSONResult.ok(blogService.insert(blog));
    }
    @PostMapping("/editBlog")
    @ApiOperation(value = "编辑博客")
    public JSONResult editBlog(@RequestBody Blog blog){
        return JSONResult.ok(blogService.updateByPrimaryKey(blog));
    }

    final static String PIC_PATH = "static/img/"; //图片存放的相对于项目的相对位置

    /**
     *上传图片
     */
    @PostMapping("/uploadImg")
    @ApiOperation(value = "上传图片")
    public JSONResult uploadPic(MultipartHttpServletRequest multiRequest, HttpServletRequest request){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //生成日期格式
        String datePrefix = dateFormat.format(new Date()); //生成当前日期作为前缀
        File folder = new File(PIC_PATH+datePrefix); //生成带当前日期的文件路径

        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        String randomName = multiRequest.getFile("image").getOriginalFilename(); //获取图片名
        //生成随机数确保唯一性，并加上图片后缀
        String saveName = UUID.randomUUID() + randomName.substring(randomName.lastIndexOf("."),randomName.length());
        String absolutePath = folder.getAbsolutePath(); //转换成绝对路径

        try {
            File fileToSave = new File(absolutePath + File.separator + saveName);
            multiRequest.getFile("image").transferTo(fileToSave); //图片存储到服务端
            String returnPath = request.getScheme() + "://"
                    + request.getServerName()+":"+request.getServerPort()
                    + "/img/" + datePrefix +"/"+ saveName;
            Map<String, String> map = new HashMap<>();
            map.put("imgUrl", returnPath);
            return JSONResult.ok("上传成功", map);

        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONResult.error("上传失败");
    }



    @PostMapping("/setPrivate")
    @ApiOperation(value = "设为私密")
    public JSONResult setPrivate(@RequestBody Blog blog){
        blog.setIsPrivate(1);
        return JSONResult.ok(blogService.updateByPrimaryKeySelective(blog));
    }

    @GetMapping("/relativeBlog")
    @ApiOperation(value = "获取相关博客")
    public JSONResult getRelativeBlog(Blog blog){
        return JSONResult.ok(blogService.selectRelativeBlog(blog));
    }

    @PostMapping("/like")
    @ApiOperation(value = "点赞/取消点赞")
    public JSONResult like(@RequestBody Likes likes){
        if (likesService.isLike(likes) == 0){
            return  JSONResult.ok(likesService.insert(likes));
        }else {
            return JSONResult.ok(likesService.deleteLike(likes));
        }
    }

    @GetMapping("/getLikes")
    @ApiOperation(value = "更新点赞数")
    public JSONResult getLikes(Blog blog){
        return JSONResult.ok(likesService.countLikesByBlog(blog));
    }

    @PostMapping("/comment")
    @ApiOperation(value = "添加评论")
    public JSONResult addComment(@RequestBody Comment comment){
        comment.setCommentDate(new Date());
        return JSONResult.ok(commentService.insert(comment));
    }
    @GetMapping("/getCommentList")
    @ApiOperation(value = "获取评论列表")
    public JSONResult getCommentList(Blog blog){
        List<Comment> commentList = commentService.selectCommentListByBlog(blog);
        for(Comment comment : commentList){
            comment.setFaceAddr(roleService.findRoleById(comment.getRoleId()).getFaceAddr());
        }
        List<Comment> comments = commentService.processComment(commentList);
        return JSONResult.ok(comments);
    }


    @PostMapping("deleteComment")
    @ApiOperation(value = "删除评论")
    public JSONResult deleteComment(@RequestBody Comment comment){
        return JSONResult.ok(commentService.deleteComment(comment));
    }

    @GetMapping("countComment")
    @ApiOperation(value = "获取博客评论数")
    public JSONResult countComment(Blog blog){
        return JSONResult.ok(commentService.countCommentByBlog(blog));
    }

    @PostMapping("/star")
    @ApiOperation(value = "收藏")
    public JSONResult starBlog(@RequestBody Favorites_relationKey favoritesRelationKey){
        return JSONResult.ok(favoritesService.starBlog(favoritesRelationKey));
    }

    @GetMapping("/getStar")
    @ApiOperation(value = "获取收藏数")
    public JSONResult getStar(Blog blog){
        return JSONResult.ok(blogService.countStarByBlog(blog));
    }

    @GetMapping("/isStar")
    @ApiOperation(value = "查询是否收藏")
    public JSONResult isStar(Blog blog){
        Role role = new Role();
        role.setRoleId(blog.getRoleId());
        List<Favorites> favoritesList = favoritesService.selectByRole(role);
        HashMap<Integer, FavoriteBlog> favoriteBlogHashMap = new HashMap<>();
        for(Favorites favorites : favoritesList){
            for(FavoriteBlog favoriteBlog : favoritesService.selectBlogListByFavorite(favorites)){
                favoriteBlogHashMap.put(favoriteBlog.getBlogId(), favoriteBlog);
            }
        }
        if (favoriteBlogHashMap.containsKey(blog.getBlogId())) return JSONResult.ok(1);
        else return JSONResult.ok(0);
    }

    @GetMapping("/isLike")
    @ApiOperation(value = "是否点赞")
    public JSONResult isLike(Likes likes){
        return JSONResult.ok(likesService.isLike(likes));
    }

    @GetMapping("/isLikeList")
    @ApiOperation(value = "是否点赞回答列表")
    public JSONResult isLikeList(Blog blog){
        List<Integer> isLike = new ArrayList<>();
        List<Blog> answerList = blogService.selectAnswerListByBlog(blog);
        Likes likes = new Likes();
        for(Blog blog1 : answerList){
            likes.setRoleId(blog.getRoleId());
            likes.setBlogId(blog1.getBlogId());
            isLike.add(likesService.isLike(likes));
        }
        return JSONResult.ok(isLike);
    }

    @PostMapping("/view")
    @ApiOperation(value = "浏览博客")
    public JSONResult viewBlog(@RequestBody Footprints footprints){
        footprints.setViewDate(new Date());
        footprintsService.insert(footprints);
        Blog blog = blogService.selectByPrimaryKey(footprints.getBlogId());
        blog.setViews(footprintsService.countBlogViews(blog));
        return JSONResult.ok(blogService.updateByPrimaryKeySelective(blog));
    }

    @GetMapping("/getAnswerList")
    @ApiOperation(value = "获取回答列表")
    public JSONResult getAnswerList(Blog blog){
        return JSONResult.ok(blogService.selectAnswerListByBlog(blog));
    }

    @GetMapping("getAnswerLikeList")
    @ApiOperation(value = "获取回答列表的点赞")
    public JSONResult getAnswerLikeList(Blog blog){
        List<Integer> likeList = new ArrayList<>();
        for (Blog blog1 : blogService.selectAnswerListByBlog(blog)){
            likeList.add(likesService.countLikesByBlog(blog1));
        }
        return JSONResult.ok(likeList);
    }

    @GetMapping("/getWriterList")
    @ApiOperation(value = "获取回答的作者列表")
    public  JSONResult getWriterList(Blog blog){
        List<Blog> blogList = blogService.selectAnswerListByBlog(blog);
        List<Role> roleList = new ArrayList<>();
        for(Blog blog1 : blogList){
            Role role = new Role();
            role.setRoleId(blog1.getRoleId());
            roleList.add(roleService.selectInfoByRole(role));
        }
        return JSONResult.ok(roleList);
    }

    @GetMapping("/getFaceAddrList")
    @ApiOperation(value = "根据评论列表获取头像")
    public JSONResult getFaceAddrList(List<Comment> commentList){
        List<String> faceAddrList = new ArrayList<>();
        for (Comment comment  : commentList){
            Role role = new Role();
            role.setRoleId(comment.getRoleId());
            faceAddrList.add(roleService.selectInfoByRole(role).getFaceAddr());
        }
        return JSONResult.ok(faceAddrList);
    }
}
