package com.hnu.pioneer.service;

import com.hnu.pioneer.domain.Activity;
import com.hnu.pioneer.domain.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Page<Activity> findActivityList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0: pageable.getPageNumber() - 1,
                pageable.getPageSize());
        return activityRepository.findAll(pageable);
    }

    public Activity findActivityByIdx(Long idx) {
        return activityRepository.findById(idx).orElse(new Activity());
    }
}
