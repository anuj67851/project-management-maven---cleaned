package com.pms.controller;


import com.pms.entity.Guide;
import com.pms.entity.User;
import com.pms.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/guide")
public class GuideController {

    private GuideService guideService;

    @Autowired
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @RequestMapping("/index")
    public String getIndexPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Guide guide = guideService.getGuide(user.getId());
        guide.setPassword(null);
        guide.setSalt(null);
        session.setAttribute("user", guide);
        return "guide/sample";
    }

}
