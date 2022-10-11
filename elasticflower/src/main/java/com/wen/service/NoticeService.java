package com.wen.service;

import com.wen.pojo.Notice;

import java.util.List;

public interface NoticeService {
    /**
     * 写评论
     * @param notice
     */
    void writeNotice(Notice notice);

    /**
     * 读所有评论
     * @return
     */
    List<Notice> readNotice();

    /**
     * 更新评论
     * @param notice
     * @return
     */
    String updateNotice(Notice notice);

    /**
     * 删除评论
     * @param id
     * @return
     */
    String dalNotice(String id);

    List<Notice> readNoticeByID(Integer id);
}
