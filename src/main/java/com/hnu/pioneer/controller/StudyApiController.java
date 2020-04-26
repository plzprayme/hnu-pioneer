package com.hnu.pioneer.controller;

import com.hnu.pioneer.domain.StudyStatus;
import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.dto.request.StudySaveRequestDto;
import com.hnu.pioneer.dto.request.StudyUpdateRequestDto;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyMemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/studies")
public class StudyApiController {

    private final MemberService memberService;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    @PostMapping("")
    public Long save(@RequestBody StudySaveRequestDto requestDto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestDto.setMember(memberService.getByStudentNumber(user.getStudentNumber()));
        return studyService.save(requestDto);
    }

    @PutMapping("/{idx}")
    public Long update(@PathVariable("idx") Long studyIdx,
                            @RequestBody StudyUpdateRequestDto requestDto) {
        return studyService.update(studyIdx, requestDto);
    }

    @DeleteMapping("/{idx}")
    public Long delete(@PathVariable("idx") Long studyIdx) {
        studyService.delete(studyIdx);
        return studyIdx;
    }

    @PutMapping("/{idx}/status/{status}")
    public Long close(@PathVariable("idx") Long studyIdx,
                      @PathVariable("status") StudyStatus status) {
        return studyService.updateStatus(studyIdx, status);
    }

}
