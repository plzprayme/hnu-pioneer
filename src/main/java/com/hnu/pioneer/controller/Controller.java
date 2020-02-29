package com.hnu.pioneer.controller;

import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    private final StudyService studyService;

    @GetMapping("/")
    public String index(Model model) {

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user",
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        return "index";
    }

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

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/signin")
    public String displayLoginPage(
            @RequestParam(required = false) String error, Model model) {

        if (error != null) {
            model.addAttribute("isWrong", error.equals(""));
        }

        return "signin";
    }
}
