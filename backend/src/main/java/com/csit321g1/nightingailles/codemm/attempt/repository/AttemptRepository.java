package com.csit321g1.nightingailles.codemm.attempt.repository;

import com.csit321g1.nightingailles.codemm.attempt.entity.AttemptEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<AttemptEntity, Long> {

    List<AttemptEntity> findByUserUserIdOrderBySubmittedAtDesc(Long userId);

    List<AttemptEntity> findByActivityActivityIdOrderBySubmittedAtDesc(Long activityId);

    long countByUserUserIdAndActivityActivityId(Long userId, Long activityId);
}
