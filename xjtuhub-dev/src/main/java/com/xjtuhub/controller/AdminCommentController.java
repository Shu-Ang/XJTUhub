package com.xjtuhub.controller;

import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.entity.Blog;
import com.xjtuhub.entity.Comment;
import com.xjtuhub.entity.Page;
import com.xjtuhub.service.impl.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@Api(tags = "后台评论管理")
@RestController
@RequestMapping("/admin/comment")
public class AdminCommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentPage")
    @ApiOperation(value = "获取评论列表")
    public JSONResult selectCommentPage(Page page){
        // 获取总条数
        Integer totalNum = commentService.countComment();
        System.out.println(totalNum);
        page.setTotalNum(totalNum);
        // 根据条件查询
        List<Comment> commentList = commentService.selectCommentPage(page);
        page.setResultList(commentList);
        return JSONResult.ok(page);
    }

    @PostMapping("/deleteComment")
    @ApiOperation(value = "删除评论")
    public JSONResult deleteComment(@RequestBody Comment comment){
        System.out.println(comment.getCommentId());
        int commentId = comment.getCommentId();
        commentService.deleteByPrimaryKey(commentId);
        return JSONResult.ok();
    }
}
