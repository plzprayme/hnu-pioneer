package com.hnu.pioneer.controller;

import com.hnu.pioneer.Dto.MemberSaveRequestDto;
import com.hnu.pioneer.Dto.StudySaveRequestDto;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class UrlController {
    private final StudyService studyService;
    private final MemberService memberService;

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

    @PostMapping("/signup/request")
    public String signUpRequest(@RequestBody MemberSaveRequestDto requestDto) {
        memberService.signUp(requestDto);
        return "index";
    }

    @GetMapping("/text")
    public String test() {
        memberService.test();

        return "index";
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
