package com.wen.dao;

import com.wen.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    /**
     * 写评论
     * @param comment
     */
     void writeComment(Comment comment) ;

    /**
     * 根据订单号读评论
     * @param order_id
     * @return
     */
    String readCommentByOrderId(String order_id);

    /**
     * 根据用户id读评论
     * @param order_id
     * @return
     */
    List<String> readCommentByUserId(String order_id);

    /**
     * 删除评论
     * @param order_id
     * @return
     */
    String delComment(String order_id);
}
