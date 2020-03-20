package com.hnu.pioneer.controller;

import com.hnu.pioneer.domain.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user",
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        return "index";
    }

    @GetMapping("/howtogroom")
    public String displayHowToGroom(Model model) {

        if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user", (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        return "howtogroom";
    }
}
