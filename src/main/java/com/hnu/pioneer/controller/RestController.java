package com.hnu.pioneer.controller;

import com.hnu.pioneer.dto.MemberSaveRequestDto;
import com.hnu.pioneer.dto.StudySaveRequestDto;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyMemberMapping;
import com.hnu.pioneer.domain.UserDetails;
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
    private final Long ALREADY_SIGNUP_EMAIL = -1L;
    private final Long ALREADY_SIGNUP_STUDENTNUMBER = -2L;
    private final Long NO_PERMISSION_ERROR = -2L;

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
        requestDto.setMember(memberService.getByStudentNumber(user.getStudentNumber()).get());
        return studyService.save(requestDto);
    }

    @GetMapping("/study/register/{idx}")
    public Long applyStudy(@PathVariable("idx") Long studyIdx) {


        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return NO_PERMISSION_ERROR;
        }

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Study study = studyService.getByIdx(studyIdx);
        Member member = memberService.getByStudentNumber(user.getStudentNumber()).get();

        if (!studyMemberService.isAlreadyRegister(study, member.getIdx())) {
            StudyMemberMapping studyMember = studyMemberService.getAfterMapping(study, member);

            memberService.registerStudy(member, studyMember);
            studyService.assignParticipant(study, studyMember);

            return study.getIdx();
        }

        return ALREADY_REGISTER_ERROR;
    }
}
