package com.hnu.pioneer.service;

import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyStatus;
import com.hnu.pioneer.repository.StudyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudyService {
    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public Page<Study> findStudyList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());
        return studyRepository.findAll(pageable);
    }

    public Study findStudyByStatus(StudyStatus status) {
        return studyRepository.findByStatus(status);
    }
}
