package com.hnu.pioneer.controller;

import com.hnu.pioneer.controller.helper.AuthAttributeAddHelper;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/studies")
public class StudyViewController {

    private final StudyService studyService;
    private final MemberService memberService;

    @GetMapping("")
    public String studies(Model model) {
        model.addAttribute("studies", studyService.getIncruitStudy());

        if (AuthAttributeAddHelper.isLoggedIn()) {
            model.addAttribute("user", AuthAttributeAddHelper.getUserDetails());

            if (AuthAttributeAddHelper.isStudent(AuthAttributeAddHelper.getUserDetails())) {
                model.addAttribute("student", "");
            } else {
                model.addAttribute("leader", "");
            }

        }

        return "study";
    }

    @GetMapping("/{idx}")
    public String read(Model model,
                         @PathVariable("idx") Long studyIdx) {
        model.addAttribute("user", AuthAttributeAddHelper.getUserDetails());
        model.addAttribute("study", studyService.getStudyDetail(studyIdx));
        return "study-detail";
    }

    @GetMapping("/save")
    public String create(Model model) {
        AuthAttributeAddHelper.addAttributeIfLoggedIn(model);
        return "create-study";
    }

    @GetMapping("/{idx}/update")
    public String update(Model model,
                         @PathVariable("idx") Long studyIdx) {
        model.addAttribute("user", AuthAttributeAddHelper.getUserDetails());
        model.addAttribute("study", studyService.getStudyDetail(studyIdx));
        return "study-update";
    }

    @GetMapping("/own")
    public String myStudy(Model model) {
        UserDetails user = AuthAttributeAddHelper.getUserDetails();
        Member member = memberService.getByStudentNumber(user.getStudentNumber());

        model.addAttribute("user", user);
        model.addAttribute("registeredStudies", memberService.getRegisteredStudyList(member));

        if (!AuthAttributeAddHelper.isStudent(user)) {
            model.addAttribute("createStudies", memberService.getCreateStudyList(member));
            return "mystudy-leader";
        }

        return "mystudy-student";
    }
}
