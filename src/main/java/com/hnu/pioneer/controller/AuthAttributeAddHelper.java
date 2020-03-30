package com.hnu.pioneer.controller;

import com.hnu.pioneer.domain.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class AuthAttributeAddHelper {
    public static boolean isStudent(UserDetails user) {
        return user.getAuthorities().toArray()[0].toString().equals("ROLE_STUDENT");
    }

    public static void addAttributeIfLoggedIn(Model model) {
        if (isLoggedIn()) {
            model.addAttribute("user", getUserDetails());
        }
    }

    public static boolean isLoggedIn() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }

    public static UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
