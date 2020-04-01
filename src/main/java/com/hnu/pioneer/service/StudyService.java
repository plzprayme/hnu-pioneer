
package com.hnu.pioneer.service;

import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import com.hnu.pioneer.dto.request.StudyUpdateRequestDto;
import com.hnu.pioneer.dto.response.AdminStudyListResponseDto;
import com.hnu.pioneer.dto.response.StudyDetailResponseDto;
import com.hnu.pioneer.dto.response.StudyListResponseDto;
import com.hnu.pioneer.dto.request.StudySaveRequestDto;
import com.hnu.pioneer.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudyService {
    private final StudyRepository studyRepository;

    @Transactional(readOnly = true)
    public List<AdminStudyListResponseDto> getAllStudyForAdminPage() {
        return studyRepository.findAll().stream()
                .map(AdminStudyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public String setStatusToIncruit(Long idx) {
        Study study = studyRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 입니다"));
        study.incruit();
        return study.getStatus().getStatus();
    }

    @Transactional
    public String setStatusToOpen(Long idx) {
        Study study = studyRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 입니다"));
        study.open();
        return study.getStatus().getStatus();
    }

    @Transactional
    public String setStatusToClose(Long idx) {
        Study study = studyRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 입니다"));
        study.close();
        return study.getStatus().getStatus();
    }

    @Transactional
    public void fireStudy(Long idx) {
        studyRepository.deleteById(idx);
    }

    @Transactional
    public Long save(StudySaveRequestDto requestDto) {
        return studyRepository.save(requestDto.toEntity()).getIdx();
    }

    @Transactional(readOnly = true)
    public Study getByIdx(Long idx) {
        return studyRepository.findById(idx).get();
    }

    @Transactional(readOnly = true)
    public List<StudyListResponseDto> getIncruitStudy() {
        return studyRepository.findAllByStatus(StudyStatus.INCRUIT).stream()
                .map(StudyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudyListResponseDto> getStudyByLeader(String leader) {
        return studyRepository.findAllByLeader(leader).stream()
                .map(StudyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void assignParticipant(Long studyIdx, StudyMemberMapping studyMember) {
        Study study = studyRepository.findById(studyIdx).get();
        study.addParticipants(studyMember);
        study.increaseOneStudyMate();
    }

    @Transactional(readOnly = true)
    public StudyDetailResponseDto getStudyDetail(Long studyIdx) {
        Study study = getByIdx(studyIdx);
        StudyDetailResponseDto responseDto = new StudyDetailResponseDto(study);
        responseDto.setParticipantNames(getParticipantNames(study));

        return responseDto;
    }

    @Transactional
    public Long update(Long studyIdx, StudyUpdateRequestDto requestDto) {
        Study study = studyRepository.findById(studyIdx).orElseThrow(() -> new IllegalArgumentException("잘못된 ID..."));
        study.update(requestDto);
        return study.getIdx();
    }

    @Transactional
    public void delete(Long studyIdx) {
        studyRepository.deleteById(studyIdx);
    }

    @Transactional
    public Long closeStudy(Long studyIdx) {
        Study study = studyRepository.findById(studyIdx).orElseThrow(() -> new IllegalArgumentException("잘못된 ID입니다"));
        study.close();
        return  study.getIdx();
    }

    private String getParticipantNames(Study study) {
        return study.getParticipants().stream()
                .map(StudyMemberMapping::getParticipant)
                .map(Member::getName)
                .collect(Collectors.joining(" 스터디, "));
    }
}