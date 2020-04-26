package com.hnu.pioneer.controller;

import com.hnu.pioneer.controller.helper.AuthAttributeAddHelper;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import com.hnu.pioneer.dto.request.ChangePasswordRequestDto;
import com.hnu.pioneer.dto.request.MemberSaveRequestDto;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyMemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class AuthApiController {

    private final MemberService memberService;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    private final Long ALREADY_REGISTER_ERROR = -1L;
    private final String  NOT_REGISTER_STUDENT_NUMBER = "-1";
    private final String  NOT_REGISTER_EMAIL = "-1";
    private final Long ALREADY_SIGNUP_EMAIL = -1L;
    private final Long ALREADY_SIGNUP_STUDENTNUMBER = -2L;
    private final Long NO_PERMISSION_ERROR = -2L;

    @PostMapping("")
    public Long save(@RequestBody MemberSaveRequestDto requestDto) {
        if (memberService.checkAlreadySignUpEmail(requestDto.getEmail())) {
            return ALREADY_SIGNUP_EMAIL;
        } else if (memberService.checkAlreadySignUpStudentNumber(requestDto.getStudentNumber())) {
            return ALREADY_SIGNUP_STUDENTNUMBER;
        }

        return memberService.signUp(requestDto);
    }

    @GetMapping("/{studentNumber}/email")
    public String findId(@PathVariable("studentNumber") Long studentNumber) {
        try {
            return memberService.getByStudentNumber(studentNumber).getEmail();
        } catch (IllegalArgumentException e) {
            return NOT_REGISTER_STUDENT_NUMBER;
        }
    }

    @GetMapping("/{email}")
    public String findIdByEmail(@PathVariable("email") String email) {
        try {
            return memberService.getByEmail(email).getEmail();
        } catch (IllegalArgumentException e) {
            return NOT_REGISTER_EMAIL;
        }
    }

    @PutMapping("/{email}/password")
    public Long updatePassword(@RequestBody ChangePasswordRequestDto requestDto) {
        return memberService.changePassword(requestDto);
    }

    @PutMapping("/{work}/studies/{idx}")
    public Long registerOrUnregister(
            @PathVariable("work") String work, @PathVariable("idx") Long idx ) {

        if (!AuthAttributeAddHelper.isLoggedIn()) {
            return NO_PERMISSION_ERROR;
        }

        UserDetails user = AuthAttributeAddHelper.getUserDetails();

        if (work.equals("register")) {
            return register(idx, user.getStudentNumber());
        } else {
            return unregister(idx, user.getStudentNumber());
        }
    }

    private Long register(Long idx, Long studentNumber) {
        Study study = studyService.getByIdx(idx);
        Member member = memberService.getByStudentNumber(studentNumber);

        if (!studyMemberService.isAlreadyRegister(study, member.getIdx())) {
            StudyMemberMapping studyMember = studyMemberService.getAfterMapping(study, member);
            memberService.registerStudy(member.getIdx(), studyMember);
            studyService.assignParticipant(study.getIdx(), studyMember);
            return study.getIdx();
        }

        return ALREADY_REGISTER_ERROR;
    }

    private Long unregister(Long idx, Long studentNumber) {
        return memberService.unregisterStudy(studentNumber, idx);
    }
}
