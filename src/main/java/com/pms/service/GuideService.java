package com.pms.service;

import com.pms.Dao.LoginDao;
import com.pms.entity.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuideService {

    private LoginDao loginDao;

    @Autowired
    public GuideService(com.pms.Dao.LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public Guide getGuide(int id) {
        return loginDao.getGuide(id);
    }
}
