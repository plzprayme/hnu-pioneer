package com.hnu.pioneer.controller;

import com.hnu.pioneer.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UrlController {

    @Autowired
    ActivityService activityService;

    @GetMapping("/")
    public String index(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("activityList", activityService.findActivityList(pageable));
        return "index";
    }

    @GetMapping("/study")
    public String study() {
        return "study";
    }
}
