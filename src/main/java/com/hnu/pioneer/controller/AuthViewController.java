package com.hnu.pioneer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthViewController {
    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/signin")
    public String login(
            @RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("isWrong", error.equals(""));
        }
        return "signin";
    }

    @GetMapping("/findid")
    public String findId() {
        return "forgot-id";
    }

    @GetMapping("/findpassword")
    public String findPassword() {
        return "forgot-password";
    }

    @GetMapping("/changepassword/{email}")
    public String changePassword(Model model ,
                                 @PathVariable("email") String email) {
        model.addAttribute("email", email);
        return "change-password";
    }
}
