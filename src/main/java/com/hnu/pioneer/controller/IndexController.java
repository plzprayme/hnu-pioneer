package com.hnu.pioneer.controller;

import com.hnu.pioneer.controller.helper.AuthAttributeAddHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model) {
        AuthAttributeAddHelper.addAttributeIfLoggedIn(model);
        return "index";
    }

    @GetMapping("/howtogroom")
    public String displayHowToGroom(Model model) {
        AuthAttributeAddHelper.addAttributeIfLoggedIn(model);
        return "howtogroom";
    }
}
