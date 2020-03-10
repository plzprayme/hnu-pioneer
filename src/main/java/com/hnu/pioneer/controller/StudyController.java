package com.hnu.pioneer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyMemberMapping;
import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyMemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;


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
            model.addAttribute("user",
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

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
    public String displayMyStudy(Model model) throws JsonProcessingException {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getAuthorities().toArray()[0].toString().equals("ROLE_LEADER")) {
            List<StudyMemberMapping> test = studyMemberService.test(memberService.getByStudentNumber(user.getStudentNumber()).get());
            List<Member> member = test.stream().map(StudyMemberMapping::getParticipant).collect(Collectors.toList());
            List<Study> study = test.stream().map(StudyMemberMapping::getRegisteredStudy).collect(Collectors.toList());

            System.out.println(new ObjectMapper().writeValueAsString(member));
            System.out.println(new ObjectMapper().writeValueAsString(study));
            System.out.println(new ObjectMapper().writeValueAsString(test));
//            model.addAttribute("studies", studyService.getStudyByLeader(user.getMemberName()));
//            model.addAttribute("user", user);
            return "index";
        }

        if (user.getAuthorities().toArray()[0].toString().equals("ROLE_STUDENT")) {
            List<StudyMemberMapping> test = studyMemberService.test(memberService.getByStudentNumber(user.getStudentNumber()).get());
            List<Member> member = test.stream().map(StudyMemberMapping::getParticipant).collect(Collectors.toList());
            List<Study> study = test.stream().map(StudyMemberMapping::getRegisteredStudy).collect(Collectors.toList());

            System.out.println(new ObjectMapper().writeValueAsString(member));
            System.out.println(new ObjectMapper().writeValueAsString(study));
            System.out.println(new ObjectMapper().writeValueAsString(test));
            model.addAttribute("studies", studyService.getStudyByLeader(user.getMemberName()));
            model.addAttribute("user", user);
        }


        return "index";
    }
}
