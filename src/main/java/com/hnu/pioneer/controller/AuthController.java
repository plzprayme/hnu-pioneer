package com.hnu.pioneer.controller;

import com.hnu.pioneer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final MemberService memberService;

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

    @GetMapping("/forgot-password")
    public String displayFindPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/forgot-id")
    public String displayFindIdPage() {
        return "forgot-id";
    }

    @GetMapping("/change-password/{email}")
    public String displayChangePassWordPage(Model model ,
                                            @PathVariable("email") String email) {
        model.addAttribute("email", email);
        return "change-password";
    }
}
