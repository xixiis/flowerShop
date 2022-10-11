package com.wen.controller;

import com.wen.pojo.Notice;
import com.wen.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    /**
     * 写公告
     * @param notice
     */
    @PostMapping("/writeNotice")
    public void writeNotice(Notice notice){
        noticeService.writeNotice(notice);
    }

    /**
     * 读评论
     * @return
     */
    @PostMapping("/readNotice")
    public List<Notice> readNotice(){
        return noticeService.readNotice();
    }

    /**
     * 读评论
     * @return
     */
    @PostMapping("/readNoticeByID/{id}")
    public List<Notice> readNoticeByID(@PathVariable("id") Integer id){
        return noticeService.readNoticeByID(id);
    }

    /**
     * 更新评论
     * @return
     */
    @PostMapping("/updateNotice")
    public String updateNotice(Notice notice){
        return noticeService.updateNotice(notice);
    }

    /**
     * 更新评论
     * @return
     */
    @PostMapping("/dalNotice/{id}")
    public String updateNotice(@PathVariable("id") String id){
        return noticeService.dalNotice(id);
    }
}
