package com.hnu.pioneer.service;

import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import com.hnu.pioneer.dto.request.ChangePasswordRequestDto;
import com.hnu.pioneer.dto.response.AdminMemberListResponseDto;
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

    @Transactional(readOnly = true)
    public List<AdminMemberListResponseDto> getAllMemberForAdminPage() {
        return memberRepository.findAll().stream()
                .map(AdminMemberListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMember(Long idx) {
        memberRepository.delete(
                memberRepository.findById(idx).orElseThrow(IllegalArgumentException::new));
    }

    @Transactional
    public Long signUp(MemberSaveRequestDto requestDto) {
        requestDto.setPassword(getBCryptEncodedPassword(requestDto.getPassword()));
        return memberRepository.save(requestDto.toEntity()).getIdx();
    }

    @Transactional
    public Long changePassword(ChangePasswordRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).get();
        member.changePassword(getBCryptEncodedPassword(requestDto.getPassword()));
        return member.getIdx();
    }

    private String getBCryptEncodedPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
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
        return memberRepository.findByStudentNumber(studentNumber).orElseThrow(() -> new IllegalArgumentException("잘못된 ID"));
    }

    @Transactional(readOnly = true)
    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("잘못된 E-MAIL"));
    }

    @Transactional(readOnly = true)
    public List<CreateStudyListResponseDto> getCreateStudyList(Member member) {
        return member.getCreateStudies().stream()
                .filter(study -> study.getStatus().equals(StudyStatus.INCRUIT))
                .map(CreateStudyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RegisteredStudyListResponseDto> getRegisteredStudyList(Member member) {
        return member.getRegisteredStudies().stream()
                .filter(study -> study.getRegisteredStudy().getStatus().equals(StudyStatus.INCRUIT))
                .map(RegisteredStudyListResponseDto::new)
                .collect(Collectors.toList());
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

    @Transactional
    public void updateRole(Long idx, Role role) {
        Member member = memberRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("잘못된 ID"));
        member.updateRole(role);
    }

    /**
     * 로그인 페이지에서 입력한 email
     * 로그인에 쓰인 email으로 조회를 한 후 권한을 부여한다.
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
