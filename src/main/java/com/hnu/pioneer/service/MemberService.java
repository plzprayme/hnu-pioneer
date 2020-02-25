package com.hnu.pioneer.service;

import com.hnu.pioneer.Dto.MemberSaveRequestDto;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void test() {

        Optional<Member> member = memberRepository.findByEmail("dbfpzk142@gmail.com");

    }


    /**
     * @param 로그인에 쓰인 email
     * 로그인에 쓰인 email으로 조회를 한 후 권한을 부여한다.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        Optional<Member> memberEntityWrapper = memberRepository.findByEmail(email);
        Member memberEntity = memberEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        System.out.println(memberEntity.getRole());
        

        return new User(memberEntity.getEmail(), memberEntity.getPassword(), authorities);

    }
}
