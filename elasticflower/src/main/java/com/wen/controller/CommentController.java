package com.wen.controller;

import com.wen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
/**
 * 评论
 */
public class CommentController  {
    @Autowired
    CommentService commentService;


    /**
     * 写评论
     * @param order_id
     * @param user_id
     * @param comment
     * @return
     */
    @PostMapping("/writeComment")
    public String writeComment(String order_id,String user_id,String comment){
        return commentService.writeComment(order_id,user_id,comment);
    }

    /**
     * 读评论
     * @param order_id
     * @return
     */
    @PostMapping("/readCommentByOrderId/{id}")
    public String readCommentByOrderId(@PathVariable("id") String order_id){
        return commentService.readCommentByOrderId(order_id);
    }
    @PostMapping("/readCommentByUserId/{id}")
    public List<String> readCommentByUserId(@PathVariable("id") String user_id){
        return commentService.readCommentByUserId(user_id);
    }



    /**
     * 删除评论
     * @param order_id
     * @return
     */
    @PostMapping("/delComment/{id}")
    public String delComment(@PathVariable("id") String order_id){
        return commentService.delComment(order_id);
    }
}
