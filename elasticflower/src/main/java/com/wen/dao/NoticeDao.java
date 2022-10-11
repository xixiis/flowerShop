package com.wen.dao;

import com.wen.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {
    /**
     * 写评论
     * @param notice
     */
    void writeNotice(Notice notice);

    /**
     * 读评论
     * @return
     */
    List<Notice> readNotice();

    /**
     * 更新评论
     * @return
     */
    void updateNotice(Notice notice);

    /**
     * 删除评论
     * @param id
     * @return
     */
    void dalNotice(String id);

    List<Notice> readNoticeByID(Integer id);
}
