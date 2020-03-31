package com.hnu.pioneer.controller;

import com.hnu.pioneer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    final private MemberService memberService;

    @GetMapping("/")
    public String displayAdminPage(Model model) {
        model.addAttribute("members", memberService.getAllMemberForAdminPage());
        model.addAttribute("studies", "");
        return "admin";
    }

//    <td><a href="/admin/member/change-role-student/{{idx}}" class="btn btn-success">학생 권한 부여</a></td>
//            <td><a href="/admin/member/change-role-leader/{{idx}}" class="btn btn-success">학생 권한 부여</a></td>
//            <td><a href="/admin/member/change-role-admin/{{idx}}" class="btn btn-success">학생 권한 부여</a></td>
//            <td><a href="/admin/member/fire/{{idx}}" class="btn btn-success">제명</a></td>


}
