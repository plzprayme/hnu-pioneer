package com.hnu.pioneer.controller;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyMemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class StudyController {

    private final StudyService studyService;
    private final MemberService memberService;

    @GetMapping("/study")
    public String study(Model model) {
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

    @GetMapping("/create-study")
    public String createStudy(Model model) {
        AuthAttributeAddHelper.addAttributeIfLoggedIn(model);
        return "create-study";
    }

    @GetMapping("/mystudy")
    public String displayMyStudy(Model model) {
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

    @GetMapping("/study/detail/{idx}")
    public String displayStudyDetail(Model model,
                                     @PathVariable("idx") Long studyIdx) {
        model.addAttribute("user", AuthAttributeAddHelper.getUserDetails());
        model.addAttribute("study", studyService.getStudyDetail(studyIdx));
        return "study-detail";
    }

    @GetMapping("/study/update/{idx}")
    public String displayStudyUpdate(Model model,
                                     @PathVariable("idx") Long studyIdx) {
        model.addAttribute("user", AuthAttributeAddHelper.getUserDetails());
        model.addAttribute("study", studyService.getStudyDetail(studyIdx));
        return "study-update";
    }
}
