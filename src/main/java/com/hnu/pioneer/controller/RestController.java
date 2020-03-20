package com.hnu.pioneer.controller;

import com.hnu.pioneer.dto.request.ChangePasswordRequestDto;
import com.hnu.pioneer.dto.request.MemberSaveRequestDto;
import com.hnu.pioneer.dto.request.StudySaveRequestDto;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import com.hnu.pioneer.domain.UserDetails;
import com.hnu.pioneer.dto.request.StudyUpdateRequestDto;
import com.hnu.pioneer.service.MemberService;
import com.hnu.pioneer.service.StudyMemberService;
import com.hnu.pioneer.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final MemberService memberService;
    private final StudyService studyService;
    private final StudyMemberService studyMemberService;

    private final Long ALREADY_REGISTER_ERROR = -1L;
    private final String  NOT_REGISTER_STUDENT_NUMBER = "-1";
    private final String  NOT_REGISTER_EMAIL = "-1L";
    private final Long ALREADY_SIGNUP_EMAIL = -1L;
    private final Long ALREADY_SIGNUP_STUDENTNUMBER = -2L;
    private final Long NO_PERMISSION_ERROR = -2L;

    @GetMapping("/forgot-id/{studentNumber}")
    public String forgotIdRequest(@PathVariable("studentNumber") Long studentNumber) {

        try {
            return memberService.getByStudentNumber(studentNumber).getEmail();
        } catch (IllegalArgumentException e) {
            return NOT_REGISTER_STUDENT_NUMBER;
        }
    }

    @GetMapping("/forgot-password/{email}")
    public String  forgotPasswordRequest(@PathVariable("email") String email) {
        try {
            return memberService.getByEmail(email).getEmail();
        } catch (IllegalArgumentException e) {
            return NOT_REGISTER_EMAIL;
        }
    }

    @PostMapping("/change-password/request")
    public Long changePassword(@RequestBody ChangePasswordRequestDto requestDto) {
        return memberService.changePassword(requestDto);
    }

    @PostMapping("/signup/request")
    public Long signUpRequest(
            @RequestBody MemberSaveRequestDto requestDto, Model model) {

        if (memberService.checkAlreadySignUpEmail(requestDto.getEmail())) {
           return ALREADY_SIGNUP_EMAIL;
        } else if (memberService.checkAlreadySignUpStudentNumber(requestDto.getStudentNumber())) {
            return ALREADY_SIGNUP_STUDENTNUMBER;
        }

        return memberService.signUp(requestDto);
    }

    @PostMapping("/create-study/save")
    public Long saveStudy(@RequestBody StudySaveRequestDto requestDto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestDto.setMember(memberService.getByStudentNumber(user.getStudentNumber()));
        return studyService.save(requestDto);
    }

    @PostMapping("/update-study/{idx}")
    public Long updateStudy(@PathVariable("idx") Long studyIdx,
                            @RequestBody StudyUpdateRequestDto requestDto) {
        return studyService.update(studyIdx, requestDto);
    }

    @GetMapping("/study/delete/{idx}")
    public Long deleteStudy(@PathVariable("idx") Long studyIdx) {
        studyService.delete(studyIdx);
        return studyIdx;
    }

    @GetMapping("/study/register/{idx}")
    public Long applyStudy(@PathVariable("idx") Long studyIdx) {


        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return NO_PERMISSION_ERROR;
        }

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Study study = studyService.getByIdx(studyIdx);
        Member member = memberService.getByStudentNumber(user.getStudentNumber());

        if (!studyMemberService.isAlreadyRegister(study, member.getIdx())) {
            StudyMemberMapping studyMember = studyMemberService.getAfterMapping(study, member);

            memberService.registerStudy(member.getIdx(), studyMember);
            studyService.assignParticipant(study.getIdx(), studyMember);

            return study.getIdx();
        }

        return ALREADY_REGISTER_ERROR;
    }

    @GetMapping("/study/unregister/{idx}")
    public Long unregisterStudy(@PathVariable("idx") Long studyIdx) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberService.unregisterStudy(user.getStudentNumber(), studyIdx);
    }

    @GetMapping("/study/close/{idx}")
    public Long closeStudy(@PathVariable("idx") Long studyIdx) {
        return studyService.closeStudy(studyIdx);
    }
}
