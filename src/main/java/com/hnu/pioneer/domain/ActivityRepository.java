package com.hnu.pioneer.domain;

import com.hnu.pioneer.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findByIdx(String idx);
}
