package com.hnu.pioneer.controller;

import com.hnu.pioneer.domain.Role;
import com.hnu.pioneer.domain.StudyStatus;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/v1")
public class AdminApiController {

    private final StudyService studyService;
    private final MemberService memberService;


    @PutMapping("/studies/{idx}/status/{status}")
    public String updateStatus(@PathVariable("idx") Long studyIdx,
                             @PathVariable("status") StudyStatus status) {
        studyService.updateStatus(studyIdx, status);
        return status.getStatus();
    }

    @PutMapping("/members/{idx}/role/{role}")
    public String updateRole(@PathVariable("idx") Long memberIdx,
                           @PathVariable("role") Role role) {
        memberService.updateRole(memberIdx, role);
        return role.getTitle();
    }

    @DeleteMapping("/members/{idx}")
    public Long deleteMember(@PathVariable("idx") Long memberIdx) {
        memberService.deleteMember(memberIdx);
        return memberIdx;
    }

    @DeleteMapping("/studies/{idx}")
    public Long deleteStudy(@PathVariable("idx") Long studyIdx) {
        studyService.delete(studyIdx);
        return studyIdx;
    }
}
