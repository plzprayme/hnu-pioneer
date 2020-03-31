package com.hnu.pioneer.controller;

import com.hnu.pioneer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminRestController {
    final private MemberService memberService;

    @GetMapping("/member/change-role-student/{idx}")
    public String changeRoleToStudent(@PathVariable("idx") Long idx) {
        return memberService.setRoleToStudent(idx);
    }

    @GetMapping("/member/change-role-leader/{idx}")
    public String changeRoleToLeader(@PathVariable("idx") Long idx) {
        return memberService.setRoleToLeader(idx);
    }

    @GetMapping("/member/change-role-admin/{idx}")
    public String changeRoleToAdmin(@PathVariable("idx") Long idx) {
        return memberService.setRoleToAdmin(idx);
    }

    @GetMapping("/member/fire/{idx}")
    public Long fireMember(@PathVariable Long idx) {
        memberService.deleteMember(idx);
        return idx;
    }
}
