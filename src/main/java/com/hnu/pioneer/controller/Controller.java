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

        UserDetails user;

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("name", "");
        } else {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("name", user.getMemberName());
        }

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

    @GetMapping("/auth")
    public String handleRequest(HttpServletRequest request, Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("auth user: " + user);
        model.addAttribute("name", user.getMemberName());
        return "index";
   }
}
