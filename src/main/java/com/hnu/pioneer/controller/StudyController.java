package com.hnu.pioneer.controller;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyMemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class StudyController {

    private final StudyService studyService;
    private final StudyMemberService studyMemberService;
    private final MemberService memberService;

    @GetMapping("/study")
    public String study(Model model) {
        model.addAttribute("studies", studyService.getIncruitStudy());

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);

            if (user.getAuthorities().toArray()[0].toString().equals("ROLE_STUDENT")) {
                model.addAttribute("student", "");
            } else {
                model.addAttribute("leader", "");
            }

        }

        return "study";
    }

    @GetMapping("/create-study")
    public String createStudy(Model model) {

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user",
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        return "create-study";
    }

    @GetMapping("/mystudy")
    public String displayMyStudy(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        Member member = memberService.getByStudentNumber(user.getStudentNumber());
        model.addAttribute("registeredStudies", memberService.getRegisteredStudyList(member));

        if (user.getAuthorities().toArray()[0].toString().equals("ROLE_LEADER")) {
            model.addAttribute("createStudies", memberService.getCreateStudyList(member));
            return "mystudy-leader";
        }

        if (user.getAuthorities().toArray()[0].toString().equals("ROLE_STUDENT")) {
            return "mystudy-student";
        }

        return "error";
    }
}
