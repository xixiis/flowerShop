package com.wen.service.impl;

import com.wen.dao.CommentDao;
import com.wen.pojo.Comment;
import com.wen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceimpl implements CommentService {
    @Autowired
    CommentDao commentDao;
    @Override
    public String writeComment(String order_id, String user_id, String comment) {
        if (order_id.equals("") || user_id.equals("") || comment.equals("")){
            return null;
        }
//        写入到数据库中
        commentDao.writeComment(new Comment(null,order_id,user_id,comment));
        return "ok";
    }

    @Override
    public String readCommentByOrderId(String order_id) {
        if (order_id.equals("")){
            return null;
        }
        return commentDao.readCommentByOrderId(order_id);
    }

    @Override
    public List<String> readCommentByUserId(String order_id) {
        if (order_id.equals("")){
            return null;
        }
        return commentDao.readCommentByUserId(order_id);
    }

    @Override
    public String delComment(String order_id) {
        return commentDao.delComment(order_id);
    }
}
