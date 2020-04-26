package com.hnu.pioneer.controller;


import com.hnu.pioneer.controller.helper.AuthAttributeAddHelper;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexViewController {

    final private MemberService memberService;
    final private StudyService studyService;

    @GetMapping("/")
    public String index(Model model) {
        AuthAttributeAddHelper.addAttributeIfLoggedIn(model);
        return "index";
    }

    @GetMapping("/howtogroom")
    public String howtogroom(Model model){
        AuthAttributeAddHelper.addAttributeIfLoggedIn(model);
        return "howtogroom";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("members", memberService.getAllMemberForAdminPage());
        model.addAttribute("studies", studyService.getAllStudyForAdminPage());
        return "admin";
    }
}
