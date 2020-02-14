package com.hnu.pioneer.controller;

import com.hnu.pioneer.Dto.StudySaveRequestDto;
import com.hnu.pioneer.domain.StudyStatus;
import com.hnu.pioneer.factory.CardColorFactory;
import com.hnu.pioneer.service.ActivityService;
import com.hnu.pioneer.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UrlController {

    @Autowired
    ActivityService activityService;

    @Autowired
    StudyService studyService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/study")
    public String study(Model model) {
        model.addAttribute("studies", studyService.getIncruitStudy());
        return "study";
    }

    @GetMapping("/create-study")
    public String createStudy() {
        return "create-study";
    }

    @PostMapping("/create-study/save")
    public String saveStudy(@RequestBody StudySaveRequestDto requestDto) {
        studyService.save(requestDto);
        return "study";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }
}
