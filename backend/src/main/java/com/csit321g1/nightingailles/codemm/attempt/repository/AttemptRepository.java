package com.csit321g1.nightingailles.codemm.attempt.repository;

import com.csit321g1.nightingailles.codemm.attempt.entity.AttemptEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<AttemptEntity, Long> {

    List<AttemptEntity> findByUserUserIdOrderBySubmittedAtDesc(Long userId);

    List<AttemptEntity> findByActivityActivityIdOrderBySubmittedAtDesc(Long activityId);

    Optional<AttemptEntity> findTopByUserUserIdAndActivityActivityIdOrderByAttemptNoDesc(Long userId, Long activityId);
}
