package com.wen.service.impl;

import com.wen.dao.NoticeDao;
import com.wen.pojo.Notice;
import com.wen.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceimpl implements NoticeService {
    @Autowired
    NoticeDao noticeDao;

    @Override
    public void writeNotice(Notice notice) {
        System.out.println(notice);
        noticeDao.writeNotice(notice);
    }

    @Override
    public List<Notice> readNotice() {
        return noticeDao.readNotice();
    }

    @Override
    public String updateNotice(Notice notice) {
        noticeDao.updateNotice(notice);
        return "ok";
    }

    @Override
    public String dalNotice(String id) {
        noticeDao.dalNotice(id);
        return "ok";
    }

    @Override
    public List<Notice> readNoticeByID(Integer id) {
        return noticeDao.readNoticeByID(id);
    }
}
