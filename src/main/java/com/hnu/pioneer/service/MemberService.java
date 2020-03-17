package com.hnu.pioneer.service;

import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import com.hnu.pioneer.dto.response.CreateStudyListResponseDto;
import com.hnu.pioneer.dto.request.MemberSaveRequestDto;
import com.hnu.pioneer.domain.*;
import com.hnu.pioneer.dto.response.RegisteredStudyListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberSaveRequestDto requestDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return memberRepository.save(requestDto.toEntity()).getIdx();
    }

    @Transactional(readOnly = true)
    public boolean checkAlreadySignUpEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean checkAlreadySignUpStudentNumber(Long studentNumber) {
        return memberRepository.findByStudentNumber(studentNumber).isPresent();
    }

    @Transactional(readOnly = true)
    public Member getByStudentNumber(Long studentNumber) {
        return memberRepository.findByStudentNumber(studentNumber).get();
    }

    @Transactional(readOnly = true)
    public List<CreateStudyListResponseDto> getCreateStudyList(Member member) {
        return member.getCreateStudies().stream().map(CreateStudyListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RegisteredStudyListResponseDto> getRegisteredStudyList(Member member) {
        return member.getRegisteredStudies().stream().map(RegisteredStudyListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void registerStudy(Long memberIdx, StudyMemberMapping studyMember) {
        Member member = memberRepository.findById(memberIdx).get();
        member.addRegisteredStudy(studyMember);
        memberRepository.save(member);
    }

    @Transactional
    public Long unregisterStudy(Long studentNumber, Long studyIdx) {
        Member member = memberRepository.findByStudentNumber(studentNumber).get();
        member.getRegisteredStudies().stream()
                .filter(a -> a.getRegisteredStudy().getIdx().equals(studyIdx))
                .forEach(studyMember -> {
                    studyMember.getParticipant().removeRegisteredStudy(studyMember);
                    studyMember.getRegisteredStudy().removeParticipant(studyMember);
                });

        return member.getIdx();
    }

    /**
     * @param 로그인 페이지에서 입력한 email
     *            로그인에 쓰인 email으로 조회를 한 후 권한을 부여한다.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberEntityWrapper = loadByEmail(email);
        Member memberEntity = memberEntityWrapper.get();
        return new com.hnu.pioneer.domain.UserDetails(memberEntity.getEmail(), memberEntity.getPassword(), authorities(memberEntity),
                memberEntity.getName(), memberEntity.getStudentNumber());
    }

    private Optional<Member> loadByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (!member.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        return member;
    }

    private static Collection<? extends GrantedAuthority> authorities(Member member) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (member.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getKey()));
        } else if (member.isLeader()) {
            authorities.add(new SimpleGrantedAuthority(Role.LEADER.getKey()));
        } else if (member.isStudent()) {
            authorities.add(new SimpleGrantedAuthority(Role.STUDENT.getKey()));
        } else {
            System.out.println("!!!!!에러!!!!! 없는 권한입니다!!!!!");
        }

        return authorities;
    }
}
