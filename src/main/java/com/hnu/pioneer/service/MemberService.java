package com.hnu.pioneer.service;

import com.hnu.pioneer.Dto.MemberSaveRequestDto;
import com.hnu.pioneer.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getIdx();
    }
}
