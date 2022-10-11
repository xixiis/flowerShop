package com.wen.service;

import java.util.List;

public interface CommentService {
    /**
     * 写评论
     * @param order_id
     * @param user_id
     * @param comment
     * @return
     */
    String writeComment(String order_id, String user_id, String comment);

    /**
     * 根据订单号读取评论
     * @param order_id
     * @return
     */
    String readCommentByOrderId(String order_id);


    List<String> readCommentByUserId(String order_id);

    String delComment(String order_id);
}
